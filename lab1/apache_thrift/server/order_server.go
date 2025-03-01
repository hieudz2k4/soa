package main

import (
	"context"
	"database/sql"
	"fmt"
	"log"
	"math"
	"os"
	"server/order"
	"strconv"
	"time"

	"github.com/apache/thrift/lib/go/thrift"
	_ "github.com/go-sql-driver/mysql"
)

type OrderServiceHandler struct {
	db    *sql.DB
	delay time.Duration
}

func (h *OrderServiceHandler) CalculateTotal(ctx context.Context, productId string, quantity int32) (*order.OrderConfirmation, error) {
	if h.delay > 0 {
		time.Sleep(h.delay)
	}

	var price float64
	err := h.db.QueryRow("SELECT price FROM product WHERE id = ?", productId).Scan(&price)
	if err != nil {
		return nil, fmt.Errorf("failed to get product price: %v", err)
	}

	totalPrice := price * float64(quantity)
	totalPriceRound := math.Round(totalPrice*100) / 100

	confirmation := order.NewOrderConfirmation()
	confirmation.TotalPrice = &totalPriceRound
	return confirmation, nil
}

func main() {
	dsn := "soa:soa@tcp(localhost:3306)/soa"
	db, err := sql.Open("mysql", dsn)
	if err != nil {
		log.Fatalf("Cannot connect to database: %v", err)
	}
	defer db.Close()

	if err := db.Ping(); err != nil {
		log.Fatalf("Database ping failed: %v", err)
	}

	delayMs, _ := strconv.Atoi(os.Getenv("PROCESS_DELAY_MS"))
	delay := time.Duration(delayMs) * time.Millisecond

	handler := &OrderServiceHandler{
		db:    db,
		delay: delay,
	}

	processor := order.NewOrderServiceProcessor(handler)

	transport, err := thrift.NewTServerSocket(":9090")
	if err != nil {
		log.Fatalf("Error creating server socket: %v", err)
	}

	transportFactory := thrift.NewTBufferedTransportFactory(8192)
	protocolFactory := thrift.NewTBinaryProtocolFactoryDefault()

	server := thrift.NewTSimpleServer4(processor, transport, transportFactory, protocolFactory)
	log.Println("Starting Go Thrift server on port :9090...")
	if err := server.Serve(); err != nil {
		log.Fatalf("Error running server: %v", err)
	}
}
