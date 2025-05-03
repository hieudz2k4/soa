// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "@openzeppelin/contracts/token/ERC20/extensions/ERC20Capped.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract PasteToken is ERC20Capped, Ownable {

    constructor()
        ERC20("PasteToken", "PST")                        
        ERC20Capped(10_000_000 * (10 ** uint256(decimals()))) 
        Ownable(msg.sender)                                
    {
        _mint(msg.sender, cap());
    }

    function mint(address to, uint256 amount) external onlyOwner {
        _mint(to, amount);
    }

    function transferTokens(address to, uint256 amount) external returns (bool) {
        return transfer(to, amount);
    }
}
