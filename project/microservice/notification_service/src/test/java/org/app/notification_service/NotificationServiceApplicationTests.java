package org.app.notification_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "grpc.server.port=-1")
class NotificationServiceApplicationTests {

  @Test
  void contextLoads() {
  }

}
