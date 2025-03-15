"use client";

import type React from "react";

import { useState } from "react";
import { Button } from "./ui/button";
import { Textarea } from "./ui/textarea";
import { Label } from "./ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "./ui/select";
import { createPaste } from "../api/pasteApi";
import { Clipboard, Check } from "lucide-react";
import { Alert, AlertDescription } from "./ui/alert";

export function CreatePasteForm() {
  const [content, setContent] = useState("");
  const [expiration, setExpiration] = useState("never");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [pasteUrl, setPasteUrl] = useState("");
  const [copied, setCopied] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!content.trim()) return;

    setIsSubmitting(true);
    try {
      const result = await createPaste(content, expiration);
      setPasteUrl(result.url);
    } catch (error) {
      console.error("Failed to create paste:", error);
    } finally {
      setIsSubmitting(false);
    }
  };

  const copyToClipboard = () => {
    navigator.clipboard.writeText(pasteUrl);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      {/* Input Nội dung Paste */}
      <div className="space-y-2">
        <Label htmlFor="content" className="text-cyan-300 text-lg">
          Paste Content
        </Label>
        <Textarea
          id="content"
          placeholder="Enter your text or code here..."
          className="min-h-[200px] font-mono bg-gray-900 text-white border border-cyan-500 rounded-md p-3 focus:ring-2 focus:ring-cyan-400"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          required
        />
      </div>

      {/* Chọn thời gian Expiration */}
      <div className="space-y-2">
        <Label htmlFor="expiration" className="text-cyan-300 text-lg">
          Expiration
        </Label>
        <Select value={expiration} onValueChange={setExpiration}>
          <SelectTrigger
            id="expiration"
            className="border border-cyan-500 bg-gray-800 text-white"
          >
            <SelectValue placeholder="Select expiration time" />
          </SelectTrigger>
          <SelectContent className="bg-gray-900 text-white border border-cyan-500">
            <SelectItem value="never">Never</SelectItem>
            <SelectItem value="10m">10 Minutes</SelectItem>
            <SelectItem value="1h">1 Hour</SelectItem>
            <SelectItem value="1d">1 Day</SelectItem>
            <SelectItem value="1w">1 Week</SelectItem>
            <SelectItem value="1m">1 Month</SelectItem>
          </SelectContent>
        </Select>
      </div>

      {/* Hiển thị link sau khi tạo paste */}
      {pasteUrl ? (
        <div className="space-y-4">
          <Alert className="bg-green-900 border border-green-500 text-green-300">
            <AlertDescription>
              ✅ Your paste has been created successfully!
            </AlertDescription>
          </Alert>

          <div className="flex items-center space-x-2">
            <input
              type="text"
              value={pasteUrl}
              readOnly
              className="flex-1 px-3 py-2 border border-cyan-500 bg-gray-900 text-white rounded-md text-sm"
            />
            <Button
              type="button"
              size="icon"
              onClick={copyToClipboard}
              className="bg-cyan-500 hover:bg-cyan-600"
            >
              {copied ? (
                <Check className="h-4 w-4 text-white" />
              ) : (
                <Clipboard className="h-4 w-4 text-white" />
              )}
            </Button>
          </div>

          <Button
            type="button"
            className="w-full bg-gray-700 hover:bg-gray-600 text-white py-2 rounded-md transition"
            onClick={() => {
              setContent("");
              setPasteUrl("");
              setExpiration("never");
            }}
          >
            Create Another Paste
          </Button>
        </div>
      ) : (
        <Button
          className="w-full bg-cyan-500 hover:bg-cyan-600 text-white py-3 rounded-md transition disabled:bg-gray-700"
          type="submit"
          disabled={isSubmitting || !content.trim()}
        >
          {isSubmitting ? "Creating..." : "Create Paste"}
        </Button>
      )}
    </form>
  );
}
