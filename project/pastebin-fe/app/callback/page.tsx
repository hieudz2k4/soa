"use client";

import { useEffect, useState, useRef } from "react";
import { useRouter } from "next/navigation";
import userManager from "@/lib/keycloak-config";
import Spin from "antd/es/spin";
import Cookies from "js-cookie";

const Callback = () => {
  const router = useRouter();
  const effectRan = useRef(false);

  useEffect(() => {
    if (effectRan.current === false) {
      console.log("Effect running for the first time OR in production.");

      userManager
        .signinRedirectCallback()
        .then((user) => {
          console.log("Authenticated", user);
          Cookies.set("userId", user.profile.sub, { expires: 0.0208 });
          Cookies.set("user", JSON.stringify(user), { expires: 0.0208 });
          Cookies.set("userProfile", JSON.stringify(user.profile), {
            expires: 0.0208,
          });
          Cookies.set("refreshToken", user.refresh_token, {
            expires: 0.0208,
          });

          const returnUrl = user?.state?.returnUrl || "/";
          router.replace(returnUrl);
        })
        .catch((error) => {
          console.error("Error callback:", error);
        });
    }

    return () => {
      console.log("Callback useEffect cleanup ran.");
      effectRan.current = true;
    };
  }, [router]);

  return (
    <div className="flex justify-center items-center h-screen">
      <Spin spinning={true} tip="Loading..." size="large" fullscreen></Spin>
    </div>
  );
};

export default Callback;
