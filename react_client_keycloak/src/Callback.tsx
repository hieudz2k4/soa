// src/components/Callback.tsx
import React, { useEffect, useState, useRef } from "react"; // Thêm useRef
import { useNavigate } from "react-router-dom";
import userManager from "./authService";

const Callback = () => {
  const [message, setMessage] = useState("Đang xử lý đăng nhập...");
  const navigate = useNavigate();
  // Tạo một ref để theo dõi xem effect đã chạy lần đầu tiên chưa
  const effectRan = useRef(false);

  useEffect(() => {
    // Chỉ chạy logic chính nếu effect chưa chạy lần nào trong chu trình mount/unmount/remount của StrictMode
    // Hoặc nếu đây không phải là môi trường development (effectRan.current sẽ luôn là false ban đầu)
    // React 18+ với StrictMode: Mount -> Unmount -> Mount
    // Lần Mount 1: effectRan.current là false -> Chạy logic. Cleanup chưa chạy.
    // Lần Unmount 1 (Cleanup): effectRan.current được đặt thành true.
    // Lần Mount 2: effectRan.current là true -> Không chạy logic nữa.
    if (effectRan.current === false) {
      console.log("Effect running for the first time OR in production."); // Log để kiểm tra
      setMessage("Đang gọi signinRedirectCallback..."); // Cập nhật trạng thái

      userManager
        .signinRedirectCallback()
        .then((user) => {
          console.log("Người dùng đã đăng nhập thành công:", user); // Log thành công
          setMessage("Đăng nhập thành công! Đang chuyển hướng...");

          if (user && user.access_token) {
            console.log("Access Token:", user.access_token);
          } else {
            console.log("Không tìm thấy Access Token.");
          }
          if (user && user.refresh_token) {
            console.log("Refresh Token:", user.refresh_token);
          } else {
            console.log("Không tìm thấy Refresh Token.");
          }

          const returnUrl = user?.state?.returnUrl || "/";
          navigate(returnUrl, { replace: true });
        })
        .catch((error) => {
          // Lỗi này bây giờ chỉ nên xảy ra nếu LẦN GỌI ĐẦU TIÊN thực sự thất bại
          console.error(
            "Lỗi thực sự xảy ra trong quá trình xử lý callback:",
            error,
          );
          // Kiểm tra xem có phải lỗi "Code not valid" không, nếu có thể lỗi này vẫn xảy ra do race condition rất hiếm
          if (error.message === "Code not valid") {
            setMessage(
              "Lỗi: Mã xác thực không hợp lệ hoặc đã được sử dụng. Vui lòng thử đăng nhập lại.",
            );
          } else {
            setMessage(
              `Đã xảy ra lỗi: ${error.message}. Vui lòng thử đăng nhập lại.`,
            );
          }
          // navigate('/login-error', { replace: true });
        });
    }

    // Cleanup function của useEffect
    // Đánh dấu rằng effect đã chạy (và cleanup) ít nhất một lần
    return () => {
      console.log("Callback useEffect cleanup ran."); // Log để kiểm tra
      effectRan.current = true;
    };

    // Chỉ phụ thuộc vào navigate vì nó được dùng bên trong .then()
  }, [navigate]);

  return (
    <div>
      <Spin spinning={loading} tip="Đang tải...">
        <div>
          <p>Dữ liệu đang được xử lý...</p>
        </div>
      </Spin>
    </div>
  );
};

export default Callback;
