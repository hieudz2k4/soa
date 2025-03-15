import { useEffect, useState } from "react";
import { CreatePasteForm } from "../components/create-paste-form";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "../components/ui/card";
import { getAnalyticsData } from "../api/analyticsApi";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from "recharts";

export default function HomePage() {
  const [analyticsData, setAnalyticsData] = useState<
    { month: string; views: number }[]
  >([]);

  useEffect(() => {
    async function fetchAnalytics() {
      try {
        const data = await getAnalyticsData();
        setAnalyticsData(data);
      } catch (error) {
        console.error("Failed to fetch analytics data:", error);
      }
    }
    fetchAnalytics();
  }, []);

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gradient-to-br from-[#1a1a2e] via-[#16213e] to-[#0f3460] px-6 py-12">
      {/* Header */}
      <div className="text-center mb-16">
        <h1 className="text-5xl font-extrabold text-cyan-300 drop-shadow-lg mb-4 hover:text-white transition-colors">
          Paste Sharing Service
        </h1>
        <p className="text-gray-400 text-lg max-w-2xl mx-auto">
          Share code snippets, notes, and text with others using unique URLs.
        </p>
      </div>

      {/* Paste Form Section */}
      <div className="max-w-3xl w-full mb-20">
        <Card className="bg-white/10 border border-white/10 backdrop-blur-2xl shadow-xl shadow-cyan-500/50 ring-4 ring-cyan-500/50 rounded-2xl p-6 transition-all duration-500 transform hover:scale-105 hover:shadow-cyan-400/50">
          <CardHeader>
            <CardTitle className="text-cyan-200 text-2xl drop-shadow-md hover:text-white transition-colors">
              Create a New Paste
            </CardTitle>
            <CardDescription className="text-gray-400">
              Enter your text below to receive a randomly generated link.
            </CardDescription>
          </CardHeader>
          <CardContent>
            <CreatePasteForm />
          </CardContent>
        </Card>
      </div>

      {/* Analytics Section */}
      <div className="max-w-4xl w-full bg-white/10 border border-white/10 backdrop-blur-lg shadow-lg rounded-2xl p-8">
        <h2 className="text-2xl font-bold text-white mb-6 text-center">
          📊 Monthly Views Analytics
        </h2>
        {analyticsData.length > 0 ? (
          <ResponsiveContainer width="100%" height={350}>
            <LineChart
              data={analyticsData}
              margin={{ top: 10, right: 30, left: 20, bottom: 10 }}
            >
              <CartesianGrid
                strokeDasharray="3 3"
                stroke="rgba(255,255,255,0.2)"
              />
              <XAxis dataKey="month" stroke="#ddd" />
              <YAxis stroke="#ddd" />
              <Tooltip
                contentStyle={{ backgroundColor: "#222", color: "#fff" }}
              />
              <Line
                type="monotone"
                dataKey="views"
                stroke="#00d8ff"
                strokeWidth={2}
                dot={{ r: 5 }}
              />
            </LineChart>
          </ResponsiveContainer>
        ) : (
          <p className="text-gray-400 text-center">Loading analytics...</p>
        )}
      </div>
    </div>
  );
}
