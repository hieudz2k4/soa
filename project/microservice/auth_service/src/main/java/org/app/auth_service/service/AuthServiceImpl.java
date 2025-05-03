package org.app.auth_service.service; // Package chứa code gRPC đã generate

import com.google.protobuf.Empty;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Struct;
import com.google.protobuf.util.JsonFormat;
import com.nimbusds.jwt.JWTClaimsSet;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.app.auth_service.grpc.AuthServiceGrpc.AuthServiceImplBase;
import org.app.auth_service.grpc.AuthenticateResponse;
import org.app.auth_service.interceptor.AuthServerInterceptor; // Import interceptor để lấy context key

import java.text.ParseException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class AuthServiceImpl extends AuthServiceImplBase {

  @Autowired
  private JwtTokenValidator tokenValidator;

  private final Logger log = org.slf4j.LoggerFactory.getLogger(AuthServiceImpl.class);
  @Override
  public void authenticate(Empty request, StreamObserver<AuthenticateResponse> responseObserver) {
    log.info("AuthServiceImpl.authenticate invoked.");

    // Lấy token từ Context (đã được Interceptor xử lý và đặt vào)
    String token = AuthServerInterceptor.TOKEN_CONTEXT_KEY.get(); // Lấy giá trị từ Context

    if (token == null) {
      log.warn("Token not found in context. Denying access.");
      responseObserver.onError(Status.UNAUTHENTICATED
                                   .withDescription("Authorization token not found in context")
                                   .asRuntimeException());
      return;
    }

    try {
      JWTClaimsSet claimsSet = tokenValidator.validate(token);

      String userId = claimsSet.getSubject();
      Struct.Builder claimsStructBuilder = Struct.newBuilder();
      try {
        // Chuyển đổi toàn bộ claims thành JSON rồi parse vào Struct
        JsonFormat.parser().ignoringUnknownFields().merge(claimsSet.toJSONObject().toString(), claimsStructBuilder);
      } catch ( InvalidProtocolBufferException e) {
        log.warn("Could not convert claims to Protobuf Struct, claims field will be empty.", e);
        // Có thể bỏ qua hoặc xử lý lỗi này tùy yêu cầu
      }

      // Tạo response thành công
      AuthenticateResponse response = AuthenticateResponse.newBuilder()
          .setAuthenticated(true)
          .setUserId(userId != null ? userId : "N/A") // Handle null subject if needed
          .setClaims(claimsStructBuilder.build())
          .build();

      log.info("Authentication successful for user ID: {}", userId);
      responseObserver.onNext(response); // Gửi response về client
      responseObserver.onCompleted(); // Hoàn thành RPC

    } catch (Exception e) {
      // Bắt tất cả các lỗi từ tokenValidator.validate()
      log.error("Token validation failed: {}", e.getMessage());
      responseObserver.onError(Status.UNAUTHENTICATED
                                   .withDescription("Invalid token: " + e.getMessage()) // Gửi mô tả lỗi về client
                                   .withCause(e) // Gắn nguyên nhân lỗi (optional)
                                   .asRuntimeException());
    }
  }
}