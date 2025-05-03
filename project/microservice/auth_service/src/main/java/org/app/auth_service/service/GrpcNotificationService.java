package org.app.auth_service.service;

import com.google.common.util.concurrent.ListenableFuture;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.app.auth_service.grpc.EmailRequest;
import org.app.auth_service.grpc.EmailResponse;
import org.app.auth_service.grpc.NotificationServiceGrpc;
import org.springframework.stereotype.Service;

@Service
public class GrpcNotificationService {
  @GrpcClient("notification-service")
  private NotificationServiceGrpc.NotificationServiceFutureStub notificationServiceFutureStub;

  public ListenableFuture<EmailResponse> sendVerificationEmail(String email, String code) {
    EmailRequest request = EmailRequest.newBuilder()
        .setTo(email)
        .setSubject("Your Verification Code")
        .setBody("Your verification code is: " + code)
        .build();

    return notificationServiceFutureStub.sendEmail(request);
  }

}
