import { UserManager, WebStorageStateStore } from "oidc-client-ts";

const config = {
  authority: "http://localhost:8080/realms/soa",
  client_id: "fe-client",
  redirect_uri: "http://localhost:3000/callback",
  response_type: "code",
  scope: "openid profile email",
  post_logout_redirect_uri: "http://localhost:3000",
};

const userManager = new UserManager(config);

export default userManager;
