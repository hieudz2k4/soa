package org.app;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

public class OrderServiceImp extends UnicastRemoteObject implements OrderService {

  protected OrderServiceImp() throws RemoteException {
    super();
  }

  @Override
  public Double calculateTotal(String productId, int quantity) throws RemoteException {
    ProductService productService = new ProductServiceImp();
    Optional priceById = productService.getPriceById(productId);

    if (priceById.isEmpty()) return null;
    else {
      Double totalPrice = (Double) priceById.get() * quantity;
      Double totalPriceRound = Math.round(totalPrice * 100.0) / 100.0;
      return totalPriceRound;

    }
  }

}
