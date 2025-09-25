package com.se310.ledger;

import java.util.*;
import java.util.Map.Entry;



public class BlockManager implements BlockManagerInterface {
    

    private NavigableMap<Integer, BlockInterface> blockMap ;
    private BlockInterface uncommittedBlock ;

    public BlockManager() {
        this.blockMap = new TreeMap<>() ;
        this.uncommittedBlock = new Block(1, "");
        this.uncommittedBlock.addAccount("master", new Account("master", Integer.MAX_VALUE));

    }

    /**
     * Helper method for CommandProcessor
     * @return current block we are working with
     */
    public BlockInterface getUncommittedBlock(){
        return this.uncommittedBlock;
    }

    public NavigableMap<Integer, BlockInterface> getBlockMap() {
        return blockMap ;
    }

    public void commitBlock(String seed) {
        List<String> tempTxList = new ArrayList<>();
        tempTxList.add(seed);

        for( TransactionInterface tempTx : uncommittedBlock.getTransactionList()){
            tempTxList.add(tempTx.toString());
        }

        MerkleTrees merkleTrees = new MerkleTrees(tempTxList);
        merkleTrees.merkle_tree();
        uncommittedBlock.setHash(merkleTrees.getRoot());

        blockMap.put(uncommittedBlock.getBlockNumber(), uncommittedBlock);

        BlockInterface committedBlock = blockMap.lastEntry().getValue();
        Map<String,AccountInterface> accountMap = committedBlock.getAccountBalanceMap();

        List<AccountInterface> accountList = new ArrayList<AccountInterface>(accountMap.values());

        uncommittedBlock = new Block(uncommittedBlock.getBlockNumber() + 1,
                committedBlock.getHash());

        for (AccountInterface account : accountList) {
            AccountInterface tempAccount = (AccountInterface) account.clone();
            uncommittedBlock.addAccount(tempAccount.getAddress(), tempAccount);
        }

        uncommittedBlock.setPreviousBlock(committedBlock);
    }

    /**
     * Get Block by id
     * @param blockNumber
     * @return Block or Null
     */
    public BlockInterface getBlock (Integer blockNumber) throws LedgerException {
        BlockInterface block = blockMap.get(blockNumber);
        if(block == null){
            throw new LedgerException("Get Block", "Block Does Not Exist");
        }
        return block;
    }

    /**
     * Get Transaction by id
     * @param transactionId
     * @return Transaction or Null
     */
    public TransactionInterface getTransaction (String transactionId){

        for ( Entry mapElement : blockMap.entrySet()) {

            // Finding specific transactions in the committed blocks
            BlockInterface tempBlock = (BlockInterface) mapElement.getValue();
            for (TransactionInterface transaction : tempBlock.getTransactionList()){
                if(transaction.getTransactionId().equals(transactionId)){
                    return transaction;
                }
            }
        }
        // Finding specific transactions in the uncommitted block
        for (TransactionInterface transaction : uncommittedBlock.getTransactionList()){
            if(transaction.getTransactionId().equals(transactionId)){
                return transaction;
            }
        }
        return null;
    }

    /**
     * Get number of Blocks in the Blockchain
     * @return int representing number of blocks committed to Blockchain
     */
    public int getNumberOfBlocks(){
        return blockMap.size();
    }

    public void addAccount(String address, AccountInterface account) {
        uncommittedBlock.addAccount(address, account) ;
    }

    /**
     * Helper method allowing reset the state of the Ledger
     */
    public synchronized void reset(){
        blockMap = new TreeMap<>();
        uncommittedBlock = new Block(1, "");
        uncommittedBlock.addAccount("master", new Account("master", Integer.MAX_VALUE));
    }


}
