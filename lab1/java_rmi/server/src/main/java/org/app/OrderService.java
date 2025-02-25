package org.app;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OrderService extends Remote {
  String calculateTotal(String productId, int quantity) throws RemoteException;
}
