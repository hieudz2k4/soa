package org.app;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OrderServer {

  public static void main(String[] args) throws RemoteException {
    try {
      OrderService service = new OrderServiceImp();
      Registry registry = LocateRegistry.createRegistry(1099);
      registry.rebind("OrderService", service);
      System.out.println("RMI Server is running...");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}