package org.app;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OrderClient {
  public static void main(String[] args) {
    try {
      Registry registry = LocateRegistry.getRegistry("localhost", 1099);
      OrderService service = (OrderService) registry.lookup("OrderService");
      String response = service.calculateTotal("00288978-506e-40e1-93c8-954390f3032c", 2);
      System.out.println(response);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
