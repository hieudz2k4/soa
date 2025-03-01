package org.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class ProductServiceImp implements ProductService{
  private static final String DB_URL = "jdbc:mysql://localhost:3306/soa";
  private static final String USER = "soa";
  private static final String PASSWORD = "soa";

  @Override
  public Optional<Double> getPriceById(String productId) {
    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        PreparedStatement stmt = conn.prepareStatement("SELECT price FROM product WHERE id = ?")) {
      stmt.setString(1, productId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) return Optional.of(rs.getDouble("price"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }
}
