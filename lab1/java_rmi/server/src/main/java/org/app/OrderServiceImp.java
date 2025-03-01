package org.app;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

public class OrderServiceImp extends UnicastRemoteObject implements OrderService {

  protected OrderServiceImp() throws RemoteException {
    super();
  }

  @Override
  public String calculateTotal(String productId, int quantity) throws RemoteException {
    ProductService productService = new ProductServiceImp();
    Optional priceById = productService.getPriceById(productId);

    if (priceById.isEmpty()) return "Error Try Again!";
    else {
      Double totalPrice = Math.round((Double) priceById.get() * quantity * 100.0) / 100.0;
      return "Order Comfirmed-----Total Price: " + totalPrice;
    }
  }

}
