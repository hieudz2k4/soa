"use client";

import { PasteCreator } from "@/components/paste-creator";
import { ThemeToggle } from "@/components/theme-toggle";
import { useEffect, useState } from "react";
import { UserMenu } from "@/components/user-menu";
import Cookies from "js-cookie";
import axios from "axios";
import { jwtDecode } from "jwt-decode";

export default function Home() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const [userName, setUserName] = useState("");
  useEffect(() => {
    console.log("mounted");

    const userCookie = Cookies.get("user");
    const accessToken = userCookie ? JSON.parse(userCookie).access_token : null;
    const refreshToken = userCookie
      ? JSON.parse(userCookie).refresh_token
      : null;
    const decodedToken = accessToken ? jwtDecode(accessToken) : null;
    const userProfile = Cookies.get("userProfile");
    const userProfileParsed = JSON.parse(userProfile || "{}");
    if (decodedToken !== null) {
      console.log("Decoded Token:", decodedToken);
      console.log(decodedToken.exp * 1000, Date.now());
      if (decodedToken.exp * 1000 > Date.now()) {
        setIsAuthenticated(true);
        setUserName(userProfileParsed.name);
      }
    }
  }, []);

  return (
    <div className="min-h-screen bg-gradient-to-b from-background to-muted/50">
      <div className="container mx-auto px-4 py-8">
        <header className="mb-8 flex justify-between items-center">
          <div className="flex items-center">
            <ThemeToggle />
          </div>
          <div className="text-center flex-1">
            <h1 className="text-4xl font-bold tracking-tight mb-2">PasteBin</h1>
            <p className="text-muted-foreground max-w-2xl mx-auto">
              Share code snippets, notes, and text easily with our modern
              pastebin service. Create a paste, get a link, and share it with
              anyone.
            </p>
          </div>
          <UserMenu
            className=""
            isLoggedIn={isAuthenticated}
            username={userName}
          />
        </header>

        <div className="max-w-3xl mx-auto">
          <PasteCreator />
        </div>
      </div>
    </div>
  );
}
