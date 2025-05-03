import userManager from "./authService";
function Authen() {
  return (
    <div>
      <h1>Authentication</h1>
      <p>This is the authentication page.</p>
      <button
        onClick={() => {
          userManager.signinRedirect();
        }}
      >
        Login
      </button>

      <button
        onClick={() => {
          userManager.signoutRedirect();
        }}
      >
        Sign out
      </button>
    </div>
  );
}

export default Authen;
