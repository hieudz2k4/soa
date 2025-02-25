package org.app;

import java.util.Optional;

public interface ProductService {
  Optional getPriceById(String productId);
}
