package org.app;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.app.order.OrderService;
import org.app.order.OrderConfirmation;

public class OrderClient {
  public static void main(String[] args) {
    TTransport transport = null;
    try {
      transport = new TSocket("localhost", 9090);
      transport.open();

      TBinaryProtocol protocol = new TBinaryProtocol(transport);
      OrderService.Client client = new OrderService.Client(protocol);

      OrderConfirmation confirmation = client.calculateTotal("0013d3b5-b41e-40e3-86eb-34e169fd0769", 10);
      System.out.println(confirmation.getTotalPrice());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (transport != null) {
        transport.close();
      }
    }
  }
}
