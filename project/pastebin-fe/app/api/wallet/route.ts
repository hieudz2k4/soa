import { NextResponse } from "next/server";
import { promises as fs } from "fs";
import path from "path";

const DATA_FILE = path.join(process.cwd(), "app", "blockchain", "db.json");

export async function GET() {
  const json = await fs.readFile(DATA_FILE, "utf-8");
  const wallets: string[] = JSON.parse(json);
  return NextResponse.json({ wallets });
}

export async function POST(request: Request) {
  const { address } = (await request.json()) as { address: string };
  const json = await fs.readFile(DATA_FILE, "utf-8");
  const wallets: string[] = JSON.parse(json);
  if (wallets.includes(address)) {
    return NextResponse.json({ error: "Exists" }, { status: 409 });
  }
  wallets.push(address);
  await fs.writeFile(DATA_FILE, JSON.stringify(wallets, null, 2), "utf-8");
  return NextResponse.json({ wallets }, { status: 201 });
}
