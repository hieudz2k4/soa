package org.app.notification_service.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.app.notification_service.grpc.EmailRequest;
import org.app.notification_service.grpc.EmailResponse;
import org.app.notification_service.grpc.NotificationServiceGrpc;
import org.app.notification_service.grpc.NotificationServiceGrpc.NotificationServiceImplBase;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class NotificationServiceImpl extends NotificationServiceImplBase {
    @Autowired
    private MailService mailService;

    @Override
    public void sendEmail(EmailRequest request, StreamObserver<EmailResponse> responseObserver) {
        EmailResponse.Builder builder = EmailResponse.newBuilder();
        try {
            mailService.sendEmailAsync(request.getTo(), request.getSubject(), request.getBody());
            builder.setSuccess(true)
                .setMessage("Email sent successfully");
        } catch (Exception e) {
            // Ghi log nếu cần
            builder.setSuccess(false)
                .setMessage("Failed to send email: " + e.getMessage());
        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

}
