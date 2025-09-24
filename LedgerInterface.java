package com.se310.ledger;

import java.util.Map;

public interface LedgerInterface {
    
    public String getName() ;

    public String getDescription() ;

    public String getSeed() ;

    public AccountInterface createAccount(String address) throws LedgerException ;

    public String processTransaction(TransactionInterface transaction) throws LedgerException ;

    public Integer getAccountBalance(String address) throws LedgerException ;

    public Map<String,Integer> getAccountBalances() ;

    public BlockInterface getBlock (Integer blockNumber) throws LedgerException ;

    public TransactionInterface getTransaction (String transactionId) ;

    public void validate() throws LedgerException ;

    public BlockInterface getUncommittedBlock() ;

}
