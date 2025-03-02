package org.app;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

public class OrderServiceImp extends UnicastRemoteObject implements OrderService {

  protected OrderServiceImp() throws RemoteException {
    super();
  }

  @Override
  public Double calculateTotal(String productId, int quantity, long processingDelayMs) throws RemoteException {
    ProductService productService = new ProductServiceImp();
    Optional priceById = productService.getPriceById(productId);

    if (processingDelayMs > 0) {
      try {
        Thread.sleep(processingDelayMs);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RemoteException("Thread interrupted during processing delay", e);
      }
    }

    if (priceById.isEmpty()) return null;
    else {
      Double totalPrice = (Double) priceById.get() * quantity;
      Double totalPriceRound = Math.round(totalPrice * 100.0) / 100.0;
      return totalPriceRound;
    }
  }
}
