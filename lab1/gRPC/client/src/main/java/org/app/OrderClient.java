package org.app;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import org.app.order.OrderServiceGrpc;
import org.app.order.OrderServiceProto.OrderRequest;
import org.app.order.OrderServiceProto.OrderResponse;

public class OrderClient {
  public static void main(String[] args) throws InterruptedException {
    String serverIP = "192.168.33.10";
    ManagedChannel channel = ManagedChannelBuilder.forAddress(serverIP, 50051)
        .usePlaintext()
        .build();

    try {
      OrderServiceGrpc.OrderServiceBlockingStub stub = OrderServiceGrpc.newBlockingStub(channel);
      OrderRequest request = OrderRequest.newBuilder()
          .setProductId("0013d3b5-b41e-40e3-86eb-34e169fd0769")
          .setQuantity(10)
          .build();

      OrderResponse response = stub.calculateTotal(request);

      if (response.hasTotalPrice()) {
        System.out.println("Order Confirmation: " + response.getTotalPrice());
      } else {
        System.out.println("Error");
      }

    } finally {
      channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
  }
}

