/** @type import('hardhat/config').HardhatUserConfig */
require("@nomiclabs/hardhat-ethers");
require("@tenderly/hardhat-tenderly");

module.exports = {
  solidity: "0.8.20",
  networks: {
    hardhat: {}, // local testnet
  },
  tenderly: {
    project: "soa",
    username: "Hieudz",
  },
};
