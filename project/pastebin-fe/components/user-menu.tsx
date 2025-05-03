"use client";
import Link from "next/link";
import { User, LogOut, LogIn, Save } from "lucide-react";
import Cookies from "js-cookie";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Button } from "@/components/ui/button";
import userManager from "@/lib/keycloak-config";

interface UserMenuProps {
  isLoggedIn?: boolean;
  username?: string;
  className?: string;
}

export function UserMenu({
  isLoggedIn = false,
  username = "",
  className = "",
}: UserMenuProps) {
  return (
    <div className={className}>
      <DropdownMenu>
        <DropdownMenuTrigger asChild>
          <Button
            variant="ghost"
            size="icon"
            className="rounded-full border border-blue-600"
            aria-label="User menu"
          >
            <User className="h-96 w-96" />
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent align="end" className="w-56">
          {isLoggedIn ? (
            <>
              <div className="px-2 py-1.5">
                <p className="text-sm font-medium">Hello, {username}</p>
                <p className="text-xs text-muted-foreground">Logged in</p>
              </div>
              <DropdownMenuSeparator />
              <DropdownMenuItem asChild>
                <Link href="/profile" className="cursor-pointer">
                  My Profile
                </Link>
              </DropdownMenuItem>
              <DropdownMenuItem asChild>
                <Link href="/my-pastes" className="cursor-pointer">
                  My Pastes
                </Link>
              </DropdownMenuItem>

              <DropdownMenuItem asChild>
                <Link href="/blockchain" className="cursor-pointer">
                  Claims Token
                </Link>
              </DropdownMenuItem>

              <DropdownMenuSeparator />
              <DropdownMenuItem
                onClick={(e) => {
                  try {
                    Cookies.remove("user");
                    Cookies.remove("refreshToken");
                    e.preventDefault();
                    userManager.signoutRedirect();
                    console.log("User logged out");
                  } catch (error) {
                    console.error("Error during logout:", error);
                  }
                }}
                className="cursor-pointer text-red-500"
              >
                <LogOut className="mr-2 h-4 w-4" />
                Logout
              </DropdownMenuItem>
            </>
          ) : (
            <>
              <DropdownMenuItem asChild>
                <Link
                  href="#"
                  onClick={(e) => {
                    e.preventDefault();
                    userManager.signinRedirect();
                  }}
                  className="cursor-pointer"
                >
                  <LogIn className="mr-2 h-4 w-4" />
                  Login/Register
                </Link>
              </DropdownMenuItem>
            </>
          )}
        </DropdownMenuContent>
      </DropdownMenu>
    </div>
  );
}
