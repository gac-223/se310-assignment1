package com.se310.ledger;

import java.util.NavigableMap;


public interface BlockManagerInterface {
    

    public BlockInterface getUncommittedBlock() ;

    public void commitBlock(String seed) ;

    public BlockInterface getBlock (Integer blockNumber) throws LedgerException ;

    public int getNumberOfBlocks() ;

    public void reset() ;

    public TransactionInterface getTransaction(String transactionId) ;

    public NavigableMap<Integer, BlockInterface> getBlockMap() ;

    public void addAccount(String address, AccountInterface account) ;

}
