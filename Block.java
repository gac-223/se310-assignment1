package com.se310.ledger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * Block class implementation representing block in the Blockchain
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 */
public class Block implements BlockInterface {

    private Integer blockNumber;
    private String previousHash;
    private String hash;
    private final Map<String,AccountInterface> accountBalanceMap = new HashMap<String,AccountInterface>();
    private final List<TransactionInterface> transactionList = new ArrayList<TransactionInterface>();
    private BlockInterface previousBlock;

    /**
     * Block Constructor
     * @param blockNumber
     * @param previousHash
     */
    public Block(int blockNumber, String previousHash) {
        this.blockNumber = blockNumber;
        this.previousHash = previousHash;
    }

    /**
     * Getter Method for block number
     * @return
     */
    public Integer getBlockNumber() {
        return blockNumber;
    }

    /**
     * Setter Method for block number
     * @param blockNumber
     */
    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    /**
     * Getter method for the Hash of the previous block
     * @return
     */
    public String getPreviousHash() {
        return previousHash;
    }

    /**
     * Setter method for the Hash of the previous block
     * @param previousHash
     */
    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    /**
     * Getter method for current block's Hash
     * @return
     */
    public String getHash() {
        return hash;
    }

    /**
     * Setter method for current block's Hash
     * @param hash
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * Get a map of all the accounts in the system
     * @return
     */
    public Map<String, AccountInterface> getAccountBalanceMap() {
        return accountBalanceMap;
    }

    /**
     * Get a list of all the Transactions in the block
     * @return
     */
    public List<TransactionInterface> getTransactionList() {
        return transactionList;
    }

    /**
     * Helper Method for adding Account to the Block
     * @param address
     * @param account
     */
    public void addAccount(String address, AccountInterface account){
        this.accountBalanceMap.put(address, account);
    }

    /**
     * Get Account from the Blockchain given account address
     * @param address
     * @return
     */
    public AccountInterface getAccount(String address){
        return this.accountBalanceMap.get(address);
    }

    /**
     * Getter method for previous block
     * @return
     */
    public BlockInterface getPreviousBlock() {
        return previousBlock;
    }

    /**
     * Setter method for previous block
     * @param previousBlock
     */
    public void setPreviousBlock(BlockInterface previousBlock) {
        this.previousBlock = previousBlock;
    }
}
