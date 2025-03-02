package org.app;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Optional;

public interface OrderService extends Remote {
  Double calculateTotal(String productId, int quantity, long processingDelayMs) throws RemoteException;
}
