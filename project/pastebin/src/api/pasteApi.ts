// API endpoints for paste operations
// const API_BASE_URL = process.env.REACT_APP_API_URL || "http://localhost:3001/api"

// Types
interface Paste {
  content: string;
  createdAt: number;
  views: number;
}

interface PasteResponse {
  id: string;
  url: string;
}

// Create a new paste
export async function createPaste(
  content: string,
  expiration = "never",
): Promise<PasteResponse> {
  const response = await fetch(`${API_BASE_URL}/pastes`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ content, expiration }),
  });

  if (!response.ok) {
    throw new Error("Failed to create paste");
  }

  return await response.json();
}

// Get a paste by ID
export async function getPaste(pasteId: string): Promise<Paste | null> {
  console.log("getPaste");
  const response = await fetch(`${API_BASE_URL}/pastes/${pasteId}`);

  if (!response.ok) {
    if (response.status === 404) {
      return null;
    }
    throw new Error("Failed to fetch paste");
  }

  return await response.json();
}

// Track page view for a paste
export async function trackPageView(pasteId: string): Promise<void> {
  try {
    await fetch(`${API_BASE_URL}/pastes/${pasteId}/view`, {
      method: "POST",
    });
  } catch (error) {
    console.error("Failed to track page view:", error);
  }
}

// Get monthly stats
export async function getMonthlyStats(): Promise<Record<string, number>> {
  const response = await fetch(`${API_BASE_URL}/stats/monthly`);

  if (!response.ok) {
    throw new Error("Failed to fetch monthly stats");
  }

  return await response.json();
}
