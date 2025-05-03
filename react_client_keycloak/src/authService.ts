import { UserManager, WebStorageStateStore } from "oidc-client-ts";

const config = {
  // Địa chỉ Keycloak (authority) gồm realm mà bạn đang dùng
  authority: "http://localhost:8080/realms/soa",
  client_id: "react-client",
  redirect_uri: "http://localhost:5173/callback", // Địa chỉ callback sau khi đăng nhập thành công
  response_type: "code", // Sử dụng Authorization Code Flow
  scope: "openid profile email", // Những scope bạn cần
  post_logout_redirect_uri: "http://localhost:5173",
};

const userManager = new UserManager(config);

export default userManager;
