package com.se310.ledger;

public interface TransactionManagerInterface {


    public TransactionInterface processTransaction(TransactionInterface transaction) throws LedgerException ;

    
}
