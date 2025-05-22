"use client";

import type React from "react";

import { useState, useEffect } from "react";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Mail, X, Save, AlertCircle, CheckCircle } from "lucide-react";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogDescription,
  DialogClose,
  DialogFooter,
} from "@/components/ui/dialog";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { useRouter } from "next/navigation";
import Cookies from "js-cookie";
import axios from "axios";

export default function ProfilePage() {
  const router = useRouter();

  const [editProfileOpen, setEditProfileOpen] = useState(false);
  const [profile, setProfile] = useState({
    firstName: "",
    lastName: "",
    name: "",
    username: "",
    email: "",
  });

  const [editedProfile, setEditedProfile] = useState({ ...profile });
  const [formErrors, setFormErrors] = useState<{
    username?: string;
    email?: string;
    general?: string;
  }>({});

  // Email verification states
  const [isEmailChanged, setIsEmailChanged] = useState(false);
  const [verificationSent, setVerificationSent] = useState(false);
  const [verificationCode, setVerificationCode] = useState("");
  const [expectedCode, setExpectedCode] = useState("");
  const [isVerified, setIsVerified] = useState(false);

  const auth_api_base_url = "http://localhost:8081/api/v1";
  useEffect(() => {
    console.log("ProfilePage mounted");
    const userProfile = Cookies.get("userProfile");

    if (userProfile !== undefined) {
      const parsedUserProfile = JSON.parse(userProfile);
      console.log(parsedUserProfile);
      setProfile({
        firstName: parsedUserProfile.given_name || "",
        lastName: parsedUserProfile.family_name || "",
        name: parsedUserProfile.name || "",
        username:
          parsedUserProfile.preferred_username ||
          parsedUserProfile.username ||
          "",
        email: parsedUserProfile.email || "",
      });
    } else {
      router.push("/");
    }
  }, []);

  // Reset states when dialog opens/closes
  useEffect(() => {
    if (editProfileOpen) {
      setEditedProfile({ ...profile });
      setFormErrors({});
      setIsEmailChanged(false);
      setVerificationSent(false);
      setVerificationCode("");
      setIsVerified(false);
    }
  }, [editProfileOpen, profile]);

  // Check if email has been changed
  useEffect(() => {
    if (editedProfile.email !== profile.email) {
      setIsEmailChanged(true);
      setIsVerified(false);
    } else {
      setIsEmailChanged(false);
      setVerificationSent(false);
      setIsVerified(true); // No verification needed if email hasn't changed
    }
  }, [editedProfile.email, profile.email]);

  const handleEditProfileOpen = () => {
    const userProfile = Cookies.get("userProfile");
    const user = Cookies.get("user");
    if (userProfile === undefined) {
      router.push("/");
      return;
    } else {
      const parsedUserProfile = JSON.parse(userProfile);
      setEditedProfile({
        firstName: parsedUserProfile.given_name || "",
        lastName: parsedUserProfile.family_name || "",
        name: parsedUserProfile.name || "",
        username:
          parsedUserProfile.preferred_username ||
          parsedUserProfile.username ||
          "",
        email: parsedUserProfile.email || "",
      });
    }
    setEditProfileOpen(true);
  };

  const validateForm = async () => {
    const errors: { username?: string; email?: string; general?: string } = {};
    console.log(auth_api_base_url);
    const res = await axios.get(auth_api_base_url + "/auth/user-info");
    const users: { username: string; email: string }[] = res.data;

    console.log(users);
    const existingUsernames = users.map((u) => u.username);
    const existingEmails = users.map((u) => u.email);

    try {
      if (
        editedProfile.username !== profile.username &&
        existingUsernames.includes(editedProfile.username)
      ) {
        errors.username = "This username is already taken";
      }

      if (
        editedProfile.email !== profile.email &&
        existingEmails.includes(editedProfile.email)
      ) {
        console.log(editedProfile.email);
        errors.email = "This email is already taken";
      }
    } catch (err) {
      errors.general = "Unable to validate user info. Please try again.";
      console.error(err);
    }

    if (!editedProfile.username.trim()) {
      errors.username = "Username is required";
    }

    if (!editedProfile.email.trim()) {
      errors.email = "Email is required";
    } else if (!/\S+@\S+\.\S+/.test(editedProfile.email)) {
      errors.email = "Please enter a valid email address";
    }

    if (
      isEmailChanged &&
      !isVerified &&
      !(
        editedProfile.email !== profile.email &&
        existingEmails.includes(editedProfile.email)
      )
    ) {
      errors.email = "Please verify your new email address";
    }

    setFormErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleEditProfileSave = async () => {
    if (await validateForm()) {
      const response = await axios.post(
        "http://localhost:8081/api/v1/auth/save-profile",
        {
          userId: Cookies.get("userId"),
          firstName: editedProfile.name.split(" ")[0],
          lastName: editedProfile.name.split(" ")[1],
          username: editedProfile.username,
          email: editedProfile.email,
        },
      );

      console.log(response);
      alert(response.data);

      const res = await axios.get(
        "http://localhost:8081/api/v1/auth/get-profile/" +
          Cookies.get("userId"),
      );

      console.log(res.data);
      const updatedProfile = res.data;
      updatedProfile.name = editedProfile.name;
      Cookies.set("userProfile", JSON.stringify(updatedProfile), {
        expires: 0.0208,
      });

      setProfile({ ...editedProfile });
      setEditProfileOpen(false);
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setEditedProfile((prev) => ({ ...prev, [name]: value }));

    // Clear specific error when field is changed
    if (formErrors[name as keyof typeof formErrors]) {
      setFormErrors((prev) => ({ ...prev, [name]: undefined }));
    }
  };

  const handleSendVerification = async () => {
    if (!/\S+@\S+\.\S+/.test(editedProfile.email)) {
      alert("Please enter a valid email address");
    } else {
      const response = await axios.post(
        "http://localhost:8081/api/v1/auth/verification-email",
        { email: editedProfile.email },
      );
      console.log(response);

      setExpectedCode(response.data.toString());
    }

    setVerificationSent(true);
  };

  const handleVerifyCode = () => {
    if (verificationCode == expectedCode) {
      setIsVerified(true);
      // Clear email error when verification is successful
      setFormErrors((prev) => ({ ...prev, email: undefined }));
    } else {
      setFormErrors((prev) => ({
        ...prev,
        email: "Invalid verification code",
      }));
    }
  };

  const stats = [
    {
      title: "Total Pastes",
      value: 5,
    },
    {
      title: "Total Views",
      value: 14,
    },
  ];

  // Check if form has any errors or email verification is pending
  const hasErrors = Object.values(formErrors).some(
    (error) => error !== undefined,
  );
  const isEmailVerificationPending = isEmailChanged && !isVerified;

  return (
    <div className="container mx-auto py-10">
      <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
        <div className="md:col-span-1">
          <Card>
            <CardContent className="pt-6">
              <div className="flex flex-col items-center space-y-4">
                <Avatar className="h-24 w-24">
                  <AvatarImage
                    src={profile.avatar || "/placeholder.svg"}
                    alt="User Avatar"
                  />
                  <AvatarFallback>
                    {profile.name
                      .split(" ")
                      .map((n) => n[0])
                      .join("")}
                  </AvatarFallback>
                </Avatar>
                <div className="text-center">
                  <h2 className="text-2xl font-bold">{profile.name}</h2>
                  <p className="text-muted-foreground">@{profile.username}</p>
                </div>
                <Button className="w-full" onClick={handleEditProfileOpen}>
                  Edit Profile
                </Button>
              </div>

              <div className="mt-8 space-y-4">
                <div className="flex items-center gap-2">
                  <Mail className="h-4 w-4 text-muted-foreground" />
                  <span className="text-sm">{profile.email}</span>
                </div>
              </div>
            </CardContent>
          </Card>
        </div>

        <div className="md:col-span-2">
          <div className="mt-4">
            <h3 className="text-xl font-semibold mb-4">Stats</h3>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              {stats.map((stat, index) => (
                <Card
                  key={index}
                  className="cursor-pointer transition-all hover:shadow-md"
                >
                  <CardHeader className="pb-2">
                    <CardTitle className="text-sm font-medium">
                      {stat.title}
                    </CardTitle>
                  </CardHeader>
                  <CardContent>
                    <div className="text-2xl font-bold">
                      {stat.value.toLocaleString()}
                    </div>
                  </CardContent>
                </Card>
              ))}
            </div>
          </div>
        </div>
      </div>

      {/* Edit Profile Dialog */}
      <Dialog open={editProfileOpen} onOpenChange={setEditProfileOpen}>
        <DialogContent className="sm:max-w-md">
          <DialogHeader>
            <DialogTitle>Edit Profile</DialogTitle>
            <DialogDescription>
              Make changes to your profile information.
            </DialogDescription>
          </DialogHeader>

          {formErrors.general && (
            <Alert variant="destructive" className="mt-4">
              <AlertCircle className="h-4 w-4" />
              <AlertDescription>{formErrors.general}</AlertDescription>
            </Alert>
          )}

          <div className="grid gap-4 py-4">
            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="name" className="text-right">
                Name
              </Label>
              <Input
                id="name"
                name="name"
                value={editedProfile.name}
                onChange={handleInputChange}
                className="col-span-3"
              />
            </div>

            <div className="grid grid-cols-4 items-center gap-4">
              <Label htmlFor="username" className="text-right">
                Username
              </Label>
              <div className="col-span-3 space-y-2">
                <Input
                  id="username"
                  name="username"
                  value={editedProfile.username}
                  onChange={handleInputChange}
                  className={formErrors.username ? "border-red-500" : ""}
                />
                {formErrors.username && (
                  <p className="text-sm text-red-500 flex items-center">
                    <AlertCircle className="h-3 w-3 mr-1" />
                    {formErrors.username}
                  </p>
                )}
              </div>
            </div>

            <div className="grid grid-cols-4 items-start gap-4">
              <Label htmlFor="email" className="text-right pt-2">
                Email
              </Label>
              <div className="col-span-3 space-y-2">
                <Input
                  id="email"
                  name="email"
                  type="email"
                  value={editedProfile.email}
                  onChange={handleInputChange}
                  className={formErrors.email ? "border-red-500" : ""}
                />

                {isEmailChanged && !verificationSent && (
                  <Button
                    variant="outline"
                    size="sm"
                    onClick={handleSendVerification}
                    className="mt-2"
                  >
                    Send Verification Code
                  </Button>
                )}

                {isEmailChanged && verificationSent && !isVerified && (
                  <div className="mt-2 space-y-2">
                    <div className="flex gap-2">
                      <Input
                        placeholder="Enter verification code"
                        value={verificationCode}
                        onChange={(e) => setVerificationCode(e.target.value)}
                        className="max-w-[200px]"
                      />
                      <Button size="sm" onClick={handleVerifyCode}>
                        Verify
                      </Button>
                    </div>
                    <p className="text-xs text-muted-foreground">
                      A verification code has been sent to your new email
                      address.
                    </p>
                  </div>
                )}

                {isEmailChanged && isVerified && (
                  <p className="text-sm text-green-500 flex items-center">
                    <CheckCircle className="h-3 w-3 mr-1" />
                    Email verified successfully
                  </p>
                )}

                {formErrors.email && (
                  <p className="text-sm text-red-500 flex items-center">
                    <AlertCircle className="h-3 w-3 mr-1" />
                    {formErrors.email}
                  </p>
                )}
              </div>
            </div>
          </div>
          <DialogFooter>
            <Button variant="outline" onClick={() => setEditProfileOpen(false)}>
              Cancel
            </Button>
            <Button onClick={handleEditProfileSave}>
              <Save className="mr-2 h-4 w-4" />
              Save Changes
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  );
}
