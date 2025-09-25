package com.se310.ledger;

import java.util.*;


/**
 * Ledger Class representing simple implementation of Blockchain
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 */
public class Ledger implements LedgerInterface {
    private String name;
    private String description;
    private String seed;
    private BlockManagerInterface blockManager ;
    private AccountManagerInterface accountManager ;
    private TransactionManagerInterface transactionManager ;
    private TransactionValidatorInterface transactionValidator ;

    private static LedgerInterface ledger;


    /**
     * Create singleton of the Ledger
     * @param name
     * @param description
     * @param seed
     * @return
     */
    public static synchronized LedgerInterface getInstance(String name, String description, String seed) {
        if (ledger == null) {
            ledger = new Ledger(name, description, seed);
        }
        return ledger;
    }

    /**
     * Private Ledger Constructor
     * @param name
     * @param description
     * @param seed
     */
    private Ledger(String name, String description, String seed) {
        this.name = name;
        this.description = description;
        this.seed = seed;

        this.blockManager = new BlockManager() ;
        this.accountManager = new AccountManager() ;
        this.transactionManager = new TransactionManager() ;
        this.transactionValidator = new TransactionValidator(blockManager) ;
    }

    /**
     * Getter method for the name of the Ledger
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Setter Method for the name of the Ledger
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter Method for Ledger description
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter Method for Description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter Method for the seed
     * @return String
     */
    public String getSeed() {
        return seed;
    }

    /**
     * Setter Method for the seed
     * @param seed
     */
    public void setSeed(String seed) {
        this.seed = seed;
    }

    /**
     * Method for creating accounts in the blockchain
     * @param address
     * @return Account representing account in the Blockchain
     */
    public AccountInterface createAccount(String address) throws LedgerException {
        
        BlockInterface uncommittedBlock = blockManager.getUncommittedBlock() ;

        if(uncommittedBlock.getAccount(address) != null){
            throw new LedgerException("Create Account", "Account Already Exists");
        }

        AccountInterface account = accountManager.createAccount(address) ;

        blockManager.addAccount(address, account) ;

        return account ;
    }

    /**
     * Method implementing core functionality of the Blockchain by handling given transaction
     * @param transaction
     * @return String representing transaction id
     * @throws LedgerException
     */
    public synchronized String processTransaction(TransactionInterface transaction) throws LedgerException {

        transactionValidator.validateTransaction(transaction) ;

        transaction = transactionManager.processTransaction(transaction) ;

        BlockInterface uncommittedBlock = blockManager.getUncommittedBlock() ;

        uncommittedBlock.getTransactionList().add(transaction) ;

        // Check to see if account blocked has reached max size
        if (uncommittedBlock.getTransactionList().size() == 10){
            blockManager.commitBlock(seed);
        }

        return transaction.getTransactionId() ;
    }

    /**
     * Get Account balance by address
     * @param address
     * @return Integer representing balance of the Account
     * @throws LedgerException
     */
    public Integer getAccountBalance(String address) throws LedgerException {

        if (blockManager.getNumberOfBlocks() == 0) {
            throw new LedgerException("Get Account Balance", "Account Is Not Committed to a Block");
        }

        BlockInterface block = blockManager.getBlockMap().lastEntry().getValue() ;



        return accountManager.getAccountBalance(address, block) ;
    }

    /**
     * Get all Account balances that are part of the Blockchain
     * @return Map representing Accounts and balances
     */
    public Map<String,Integer> getAccountBalances(){

        if (blockManager.getNumberOfBlocks() == 0) {
            return null ;
        }

        BlockInterface committedBlock = blockManager.getBlockMap().lastEntry().getValue();

        return accountManager.getAccountBalances(committedBlock) ;
    }

    /**
     * Get Block by id
     * @param blockNumber
     * @return Block or Null
     */
    public BlockInterface getBlock (Integer blockNumber) throws LedgerException {
        return blockManager.getBlock(blockNumber) ;
    }

    /**
     * Get Transaction by id
     * @param transactionId
     * @return Transaction or Null
     */
    public TransactionInterface getTransaction (String transactionId){

        return blockManager.getTransaction(transactionId) ;
    }

    /**
     * Get number of Blocks in the Blockchain
     * @return int representing number of blocks committed to Blockchain
     */
    public int getNumberOfBlocks(){
        return blockManager.getNumberOfBlocks() ;
    }

    /**
     * Method for validating Blockchain.
     * Check each block for Hash consistency
     * Check each block for Transaction count
     * Check account balances against the total
     */
    public void validate() throws LedgerException {

        if (blockManager.getNumberOfBlocks() == 0) {
            throw new LedgerException("Validate", "No Block Has Been Committed");
        }


        BlockInterface committedBlock = blockManager.getBlockMap().lastEntry().getValue();
        Map<String,AccountInterface> accountMap = committedBlock.getAccountBalanceMap();
        List<AccountInterface> accountList = new ArrayList<>(accountMap.values());

        int totalBalance = 0;
        for (AccountInterface account : accountList) {
            totalBalance += account.getBalance();
        }

        int fees = 0;
        String hash;
        for(Integer key : blockManager.getBlockMap().keySet()){
            BlockInterface block = blockManager.getBlockMap().get(key);

            //Check for Hash Consistency
            if(block.getBlockNumber() != 1)
                if(!block.getPreviousHash().equals(block.getPreviousBlock().getHash())){
                    throw new LedgerException("Validate", "Hash Is Inconsistent: "
                            + block.getBlockNumber());
            }

            //Check for Transaction Count
            if(block.getTransactionList().size() != 10){
                throw new LedgerException("Validate", "Transaction Count Is Not 10 In Block: "
                        + block.getBlockNumber());
            }

            for(TransactionInterface transaction : block.getTransactionList()){
                fees += transaction.getFee();
            }
        }

        int adjustedBalance = totalBalance + fees;

        //Check for account balances against the total
        if(adjustedBalance != Integer.MAX_VALUE){
            throw new LedgerException("Validate", "Balance Does Not Add Up");
        }

    }

    /**
     * Helper method for CommandProcessor
     * @return current block we are working with
     */
    public BlockInterface getUncommittedBlock(){
        return blockManager.getUncommittedBlock();
    }

    /**
     * Helper method allowing reset the state of the Ledger
     */
    public synchronized void reset(){
        blockManager.reset() ;
    }
}
