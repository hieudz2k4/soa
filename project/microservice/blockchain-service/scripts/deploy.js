async function main() {
  const [deployer] = await ethers.getSigners();
  console.log("Deploying with: ", deployer.address);

  const Token = await ethers.getContractFactory("PasteToken");
  const token = await Token.deploy();

  console.log("PasteToken deployed at: ", token.target);
}

main().catch((err) => {
  console.error(err);
  process.exit(1);
});
