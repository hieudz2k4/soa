"use client";

import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "../components/ui/card";
import { Button } from "../components/ui/button";
import { getPaste, trackPageView } from "../api/pasteApi";

export default function ViewPastePage() {
  const { pasteId } = useParams();
  const navigate = useNavigate();
  const [paste, setPaste] = useState<{ content: string } | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchPaste() {
      if (!pasteId) return;

      try {
        const pasteData = await getPaste(pasteId);
        if (pasteData) {
          setPaste(pasteData);
          trackPageView(pasteId);
        } else {
          navigate("/not-found", { replace: true });
        }
      } catch (error) {
        console.error("Failed to fetch paste:", error);
        navigate("/not-found", { replace: true });
      } finally {
        setLoading(false);
      }
    }

    fetchPaste();
  }, [pasteId, navigate]);

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen bg-gradient-to-r from-gray-900 to-black">
        <div className="text-white text-lg animate-pulse">Loading paste...</div>
      </div>
    );
  }

  return (
    <div className="flex items-center justify-center min-h-screen bg-gradient-to-br from-gray-900 via-gray-800 to-black px-4">
      <div className="w-full max-w-4xl">
        <Card className="bg-white/10 backdrop-blur-md text-white shadow-2xl rounded-xl border border-gray-800 p-6">
          <CardHeader>
            <CardTitle className="text-2xl font-bold text-blue-400 text-center">
              Paste ID: {pasteId}
            </CardTitle>
          </CardHeader>
          <CardContent>
            <pre className="bg-gray-900 text-green-300 p-4 rounded-lg overflow-x-auto whitespace-pre-wrap font-mono text-sm shadow-md border border-gray-700">
              {paste?.content}
            </pre>
          </CardContent>
        </Card>

        {/* Nút trở về trang chính */}
        <div className="mt-6 flex justify-center">
          <Button
            onClick={() => navigate("/")}
            className="bg-blue-600 text-white px-6 py-3 rounded-lg text-lg font-semibold 
            shadow-xl transition-all duration-300 transform hover:scale-110 hover:shadow-2xl active:scale-95"
          >
            Go Home 🚀
          </Button>
        </div>
      </div>
    </div>
  );
}
