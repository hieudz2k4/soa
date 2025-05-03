"use client";

import { useEffect } from "react";
import Cookies from "js-cookie";

export default function AppWrapper({
  children,
}: {
  children: React.ReactNode;
}) {
  useEffect(() => {
    console.log("Default function running");
    const user = Cookies.get("user");
    const refreshToken = Cookies.get("refreshToken");
    if (user === undefined && refreshToken === undefined) {
    }
  }, []);

  return <>{children}</>;
}
