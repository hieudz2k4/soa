FROM node:18
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 8545
CMD npx hardhat node --hostname 0.0.0.0 & \
  sleep 5 && \
  npx hardhat compile && \
  npx hardhat run scripts/deploy.js --network localhost && \
  tail -f /dev/null
