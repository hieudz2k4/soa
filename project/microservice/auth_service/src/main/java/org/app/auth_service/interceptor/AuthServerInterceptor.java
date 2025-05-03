package org.app.auth_service.interceptor;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.slf4j.Logger;
import org.springframework.core.annotation.Order;

@GrpcGlobalServerInterceptor // Đăng ký interceptor này cho mọi gRPC call đến server
@Order(10) // Đặt độ ưu tiên (nếu có nhiều interceptor)
public class AuthServerInterceptor implements ServerInterceptor {

  private Logger log = org.slf4j.LoggerFactory.getLogger(AuthServerInterceptor.class);
  // Key để lấy header 'authorization'
  public static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY =
      Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);
  // Key để lưu token vào context cho service sử dụng
  public static final Context.Key<String> TOKEN_CONTEXT_KEY = Context.key("authToken");

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> call,
      Metadata headers, // Metadata từ client gửi lên
      ServerCallHandler<ReqT, RespT> next) { // Handler để gọi đến service thực tế

    String authHeader = headers.get(AUTHORIZATION_METADATA_KEY);
    Context context = Context.current(); // Context hiện tại của request

    if (authHeader != null && authHeader.toLowerCase().startsWith("bearer ")) {
      String token = authHeader.substring(7).trim();
      if (!token.isEmpty()) {
        // Gắn token vào Context để service có thể truy cập
        context = context.withValue(TOKEN_CONTEXT_KEY, token);
        log.debug("Bearer token extracted and attached to context.");
      } else {
        log.warn("Authorization header present but token is empty.");
        // Nếu cần chặn luôn ở đây:
        call.close(Status.UNAUTHENTICATED.withDescription("Bearer token is empty"), new Metadata());
        return new ServerCall.Listener<ReqT>() {};
      }
    } else {
      log.warn("Authorization header missing or not in 'Bearer' format.");
      // Nếu service này YÊU CẦU mọi request phải có token, hãy chặn luôn ở đây:
      call.close(Status.UNAUTHENTICATED.withDescription("Authorization token is missing or invalid"), new Metadata());
      return new ServerCall.Listener<ReqT>() {}; // Trả về listener rỗng để dừng xử lý
      // Nếu có những RPC không cần token, thì không chặn ở đây, service sẽ tự kiểm tra context
    }

    // Tiếp tục chuỗi xử lý với context đã được cập nhật (có token hoặc không)
    // Contexts.interceptCall sẽ đảm bảo context được gắn vào thread xử lý request
    return Contexts.interceptCall(context, call, headers, next);
  }
}