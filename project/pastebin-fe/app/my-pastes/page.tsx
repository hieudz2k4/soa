"use client";

import { useState, useEffect } from "react";
import { Input } from "@/components/ui/input";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Card, CardContent, CardHeader } from "@/components/ui/card";
import { Tabs, TabsContent } from "@/components/ui/tabs";
import { Clock, Code, Search } from "lucide-react";
import Cookies from "js-cookie";

// Sample data for pastes
const pastesData = [
  {
    id: "paste1",
    title: "Hieudz",
    created: "2025-05-04T10:30:00",
    expires: "Never",
    views: 5,
  },
  {
    id: "paste2",
    title: "dz",
    created: "2025-05-04T14:20:00",
    expires: "2025-05-04T15:20:00",
    views: 6,
  },
  {
    id: "paste3",
    title: "Hieudz2k4",
    created: "2025-05-04T09:15:00",
    expires: "Never",
    views: 1,
  },
  {
    id: "paste4",
    title: "Hello World",
    created: "2025-05-04T16:45:00",
    expires: "Never",
    views: 1,
  },
  {
    id: "paste5",
    title: "Test Paste",
    created: "2025-05-04T11:10:00",
    expires: "2025-05-04T12:10:00",
    views: 1,
  },
];

export default function MyPastesPage() {
  const [pastes, setPastes] = useState(pastesData);
  const [searchQuery, setSearchQuery] = useState("");

  useEffect(() => {
    if (Cookies.get("userId") === ) {
    const totalView = Cookies.set("totalView", 0);
    const userProfile = Cookies.get("userProfile");

    if (userProfile === undefined) {
      // Redirect to login page if userProfile is not found
      window.location.href = "/";
    }
  }, []);
  // Filter pastes based on search query and filters
  const filteredPastes = pastes.filter((paste) => {
    const matchesSearch = paste.title
      .toLowerCase()
      .includes(searchQuery.toLowerCase());

    return matchesSearch;
  });

  // Function to format date
  const formatDate = (dateString) => {
    if (dateString === "Never") return "Never";
    const date = new Date(dateString);
    return new Intl.DateTimeFormat("en-US", {
      year: "numeric",
      month: "short",
      day: "numeric",
    }).format(date);
  };

  // Function to delete a paste
  const deletePaste = (id) => {
    setPastes(pastes.filter((paste) => paste.id !== id));
  };

  return (
    <div className="container mx-auto py-10">
      <div className="mb-8">
        <h1 className="text-3xl font-bold">My Pastes</h1>
        <p className="text-muted-foreground">
          Manage all your created pastes in one place.
        </p>
      </div>

      <Tabs defaultValue="all">
        <Card>
          <CardHeader className="pb-3">
            <div className="flex flex-col sm:flex-row gap-4 justify-between">
              <div className="relative w-full sm:w-64">
                <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-muted-foreground" />
                <Input
                  type="search"
                  placeholder="Search pastes..."
                  className="pl-8"
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                />
              </div>
            </div>
          </CardHeader>

          <CardContent>
            <TabsContent value="all" className="m-0">
              <div className="rounded-md border">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Content</TableHead>
                      <TableHead>Created</TableHead>
                      <TableHead>Expires</TableHead>
                      <TableHead className="text-right">Views</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {filteredPastes.length > 0 ? (
                      filteredPastes.map((paste) => (
                        <TableRow key={paste.id}>
                          <TableCell className="font-medium">
                            <div className="flex items-center gap-2">
                              <Code className="h-4 w-4 text-muted-foreground" />
                              {paste.title}
                            </div>
                          </TableCell>

                          <TableCell>
                            <div className="flex items-center gap-1">
                              <Clock className="h-3 w-3 text-muted-foreground" />
                              <span>{formatDate(paste.created)}</span>
                            </div>
                          </TableCell>
                          <TableCell>{formatDate(paste.expires)}</TableCell>
                          <TableCell className="text-right">
                            {paste.views}
                          </TableCell>
                        </TableRow>
                      ))
                    ) : (
                      <TableRow>
                        <TableCell colSpan={8} className="h-24 text-center">
                          No pastes found.
                        </TableCell>
                      </TableRow>
                    )}
                  </TableBody>
                </Table>
              </div>
            </TabsContent>
          </CardContent>
        </Card>
      </Tabs>
    </div>
  );
}
