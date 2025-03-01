package main

import (
	"context"
	"database/sql"
	"fmt"
	"log"
	"math"
	"net"
	"order_service/order"

	_ "github.com/go-sql-driver/mysql"
	"google.golang.org/grpc"
	"google.golang.org/protobuf/types/known/wrapperspb"
)

type server struct {
	order.UnimplementedOrderServiceServer
	db *sql.DB
}

func connectDB() (*sql.DB, error) {
	dsn := "soa:soa@tcp(localhost:3306)/soa"
	db, err := sql.Open("mysql", dsn)
	if err != nil {
		return nil, err
	}
	if err := db.Ping(); err != nil {
		return nil, err
	}
	return db, nil
}

func (s *server) CalculateTotal(ctx context.Context, req *order.OrderRequest) (*order.OrderResponse, error) {
	var price float64
	err := s.db.QueryRow("SELECT price FROM product WHERE id = ?", req.ProductId).Scan(&price)
	if err != nil {
		return nil, fmt.Errorf("failed to fetch product price: %w", err)
	}
	totalPrice := price * float64(req.Quantity)

	totalPriceRound := math.Round(totalPrice*100) / 100
	return &order.OrderResponse{
		TotalPrice: wrapperspb.Double(totalPriceRound),
	}, nil
}

func main() {
	db, err := connectDB()
	if err != nil {
		log.Fatalf("Failed to connect to database: %v", err)
	}
	defer db.Close()

	lis, err := net.Listen("tcp", ":50051")
	if err != nil {
		log.Fatalf("Failed to listen: %v", err)
	}

	grpcServer := grpc.NewServer()
	order.RegisterOrderServiceServer(grpcServer, &server{db: db})

	log.Println("gRPC server is running on port 50051...")
	if err := grpcServer.Serve(lis); err != nil {
		log.Fatalf("Failed to serve: %v", err)
	}
}
