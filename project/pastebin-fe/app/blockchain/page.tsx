"use client";

import type React from "react";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { ethers } from "ethers";
import PasteTokenAbi from "@/abi/PasteToken.json";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import {
  CheckCircle,
  AlertCircle,
  Loader2,
  Copy,
  ExternalLink,
  FileCode,
  Shield,
} from "lucide-react";
import axios from "axios";
import { providers } from "ethers";
import { useEffect } from "react";
import Cookies from "js-cookie";

// Token information
const tokenInfo = {
  name: "PasteToken",
  symbol: "PST",
  decimals: 18,
  contractAddress: "0x5FbDB2315678afecb367f032d93F642f64180aa3",
  network: "Hardhat Local Network",
  blockExplorer: "http://localhost:8545",
};

export default function BlockchainPage() {
  const [promotedWallets, setPromotedWallets] = useState<string[]>([]);
  const [walletAddress, setWalletAddress] = useState("");
  const [isValidating, setIsValidating] = useState(false);
  const [isProcessing, setIsProcessing] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<{
    message: string;
    txHash: string;
    amount: string;
  } | null>(null);

  useEffect(() => {
    const user = Cookies.get("user");

    if (!user) {
      window.location.href = "/";
    }
  }, []);

  // Validate Ethereum address format
  const isValidEthereumAddress = (address: string) => {
    return /^0x[a-fA-F0-9]{40}$/.test(address);
  };

  const handleWalletAddressChange = (
    e: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setWalletAddress(e.target.value);
    setError(null);
    setSuccess(null);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setSuccess(null);
    setIsValidating(true);

    await axios
      .get("/api/wallet")
      .then((res) => {
        // res.data là object JSON trả về từ Next.js
        setPromotedWallets(res.data.wallets);
      })
      .catch((err) => {
        console.error("Failed to load wallets:", err);
      });

    console.log(promotedWallets);

    // Validate wallet address format
    if (!isValidEthereumAddress(walletAddress)) {
      setError(
        "Please enter a valid Ethereum wallet address (0x followed by 40 hexadecimal characters)",
      );
      setIsValidating(false);
      return;
    }

    // Check if wallet has already received a promotion
    if (promotedWallets.includes(walletAddress)) {
      setError("This wallet has already received a promotion");
      setIsValidating(false);
      return;
    }

    setIsValidating(false);
    setIsProcessing(true);

    try {
      if (!(window as any).ethereum) {
        throw new Error("MetaMask not installed");
      }

      const provider = new providers.JsonRpcProvider("http://localhost:8545");
      const ownerSigner = provider.getSigner(0);

      const contract = new ethers.Contract(
        tokenInfo.contractAddress,
        PasteTokenAbi.abi,
        ownerSigner,
      );

      const randomAmount = Math.floor(Math.random() * (500 - 100 + 1)) + 100;
      const amount = ethers.utils.parseUnits(
        randomAmount.toString(),
        tokenInfo.decimals,
      );
      const tx = await contract.transferTokens(walletAddress, amount);
      const receipt = await tx.wait();

      const res = await axios.post("/api/wallet", { address: walletAddress });

      if (res.status === 201) {
        setSuccess({
          message: "Transferred Token Successfully",
          txHash: receipt.transactionHash,
          amount: `${randomAmount} ${tokenInfo.symbol}`,
        });
      }
    } catch (err: any) {
      console.error(err);
      setError("This wallet has already received a promotion");
    } finally {
      setIsProcessing(false);
    }
  };

  const copyToClipboard = (text: string) => {
    navigator.clipboard.writeText(text);
  };

  return (
    <div className="container mx-auto py-10">
      <div className="max-w-2xl mx-auto">
        <h1 className="text-3xl font-bold mb-2">Token Promotion</h1>
        <p className="text-muted-foreground mb-8">
          Enter your Ethereum wallet address to receive free {tokenInfo.name} (
          {tokenInfo.symbol}) tokens deployed with Hardhat.
        </p>

        <div className="mb-8 grid grid-cols-1 md:grid-cols-2 gap-4">
          <Card>
            <CardHeader className="pb-2">
              <CardTitle className="text-sm font-medium flex items-center">
                <FileCode className="mr-2 h-4 w-4" />
                Token Contract
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-2 text-sm">
                <div className="flex justify-between">
                  <span className="text-muted-foreground">Name:</span>
                  <span className="font-medium">{tokenInfo.name}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-muted-foreground">Symbol:</span>
                  <span className="font-medium">{tokenInfo.symbol}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-muted-foreground">Decimals:</span>
                  <span className="font-medium">{tokenInfo.decimals}</span>
                </div>
                <div className="flex justify-between items-center">
                  <span className="text-muted-foreground">Address:</span>
                  <div className="flex items-center gap-1">
                    <span className="font-medium truncate max-w-[120px]">
                      {tokenInfo.contractAddress}
                    </span>
                    <Button
                      variant="ghost"
                      size="icon"
                      className="h-6 w-6"
                      onClick={() => copyToClipboard(tokenInfo.contractAddress)}
                    >
                      <Copy className="h-3 w-3" />
                    </Button>
                  </div>
                </div>
              </div>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="pb-2">
              <CardTitle className="text-sm font-medium flex items-center">
                <Shield className="mr-2 h-4 w-4" />
                Network Information
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-2 text-sm">
                <div className="flex justify-between">
                  <span className="text-muted-foreground">Network:</span>
                  <span className="font-medium">{tokenInfo.network}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-muted-foreground">Deployment:</span>
                  <span className="font-medium">Hardhat</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-muted-foreground">Chain ID:</span>
                  <span className="font-medium">31337</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-muted-foreground">RPC URL:</span>
                  <span className="font-medium truncate max-w-[150px]">
                    http://localhost:8545
                  </span>
                </div>
              </div>
            </CardContent>
          </Card>
        </div>

        <Card>
          <CardHeader>
            <CardTitle>Claim Your Tokens</CardTitle>
            <CardDescription>
              Each wallet can only receive the promotion once. The tokens are
              created and distributed using Hardhat.
            </CardDescription>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit}>
              <div className="space-y-4">
                <div className="space-y-2">
                  <Label htmlFor="walletAddress">Ethereum Wallet Address</Label>
                  <Input
                    id="walletAddress"
                    placeholder="0x..."
                    value={walletAddress}
                    onChange={handleWalletAddressChange}
                    disabled={isValidating || isProcessing}
                  />
                  <p className="text-xs text-muted-foreground">
                    Enter your Ethereum (ETH) wallet address to receive{" "}
                    {tokenInfo.symbol} tokens.
                  </p>
                </div>

                {error && (
                  <Alert variant="destructive">
                    <AlertCircle className="h-4 w-4" />
                    <AlertDescription>{error}</AlertDescription>
                  </Alert>
                )}

                {success && (
                  <Alert>
                    <CheckCircle className="h-4 w-4 text-green-500" />
                    <AlertDescription>
                      <div className="space-y-2">
                        <p className="font-medium text-green-500">
                          {success.message}
                        </p>
                        <div className="bg-muted p-3 rounded-md space-y-2">
                          <div className="flex justify-between items-center">
                            <span className="text-sm font-medium">Amount:</span>
                            <span className="text-sm">{success.amount}</span>
                          </div>
                          <div className="flex justify-between items-center">
                            <span className="text-sm font-medium">
                              Transaction Hash:
                            </span>
                            <div className="flex items-center gap-1">
                              <span className="text-sm truncate max-w-[200px]">
                                {success.txHash}
                              </span>
                              <Button
                                variant="ghost"
                                size="icon"
                                className="h-6 w-6"
                                onClick={() => copyToClipboard(success.txHash)}
                              >
                                <Copy className="h-3 w-3" />
                              </Button>
                              <Button
                                variant="ghost"
                                size="icon"
                                className="h-6 w-6"
                                asChild
                              >
                                <a
                                  href={`${tokenInfo.blockExplorer}/tx/${success.txHash}`}
                                  target="_blank"
                                  rel="noopener noreferrer"
                                >
                                  <ExternalLink className="h-3 w-3" />
                                </a>
                              </Button>
                            </div>
                          </div>
                        </div>
                      </div>
                    </AlertDescription>
                  </Alert>
                )}

                <Button
                  type="submit"
                  className="w-full"
                  disabled={!walletAddress || isValidating || isProcessing}
                >
                  {isValidating ? (
                    <>
                      <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                      Validating...
                    </>
                  ) : isProcessing ? (
                    <>
                      <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                      Processing Transaction...
                    </>
                  ) : (
                    `Claim ${tokenInfo.symbol} Tokens`
                  )}
                </Button>
              </div>
            </form>
          </CardContent>
          <CardFooter className="flex flex-col items-start border-t pt-6">
            <h3 className="text-sm font-medium mb-2">How it works:</h3>
            <ol className="list-decimal list-inside space-y-1 text-sm text-muted-foreground">
              <li>Enter your Ethereum wallet address</li>
              <li>We'll check if your wallet is eligible for the promotion</li>
              <li>
                If eligible, we'll send {tokenInfo.symbol} tokens to your wallet
              </li>
              <li>
                The transaction will be processed on the Hardhat local network
              </li>
              <li>
                You'll receive a confirmation with the transaction details
              </li>
            </ol>
          </CardFooter>
        </Card>

        <div className="mt-8">
          <h2 className="text-xl font-bold mb-4">Frequently Asked Questions</h2>
          <div className="space-y-4">
            <div>
              <h3 className="font-medium">
                What is {tokenInfo.name} ({tokenInfo.symbol})?
              </h3>
              <p className="text-sm text-muted-foreground">
                {tokenInfo.name} is an ERC-20 token created and deployed using
                Hardhat, a development environment for Ethereum. It's designed
                specifically for our paste service platform.
              </p>
            </div>
            <div>
              <h3 className="font-medium">How many tokens will I receive?</h3>
              <p className="text-sm text-muted-foreground">
                Each eligible wallet will receive between 100 and 500{" "}
                {tokenInfo.symbol} tokens as a promotion.
              </p>
            </div>
            <div>
              <h3 className="font-medium">What is Hardhat?</h3>
              <p className="text-sm text-muted-foreground">
                Hardhat is a development environment for Ethereum software. It's
                designed to help developers create, test, and deploy smart
                contracts and decentralized applications (dApps).
              </p>
            </div>
            <div>
              <h3 className="font-medium">
                How do I connect to the Hardhat network?
              </h3>
              <p className="text-sm text-muted-foreground">
                To interact with the {tokenInfo.symbol} token, you'll need to
                configure your wallet (like MetaMask) to connect to the Hardhat
                network. Use RPC URL: http://localhost:8545 and Chain ID: 31337.
              </p>
            </div>
            <div>
              <h3 className="font-medium">
                Can I claim the promotion multiple times?
              </h3>
              <p className="text-sm text-muted-foreground">
                No, each wallet address can only receive the promotion once.
              </p>
            </div>
            <div>
              <h3 className="font-medium">Are there any fees?</h3>
              <p className="text-sm text-muted-foreground">
                No, we cover all transaction fees for the promotion on the
                Hardhat network.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
