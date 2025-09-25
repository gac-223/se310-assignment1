package com.se310.ledger;

public interface TransactionValidatorInterface {
    
    public void validateTransaction(TransactionInterface transaction) throws LedgerException ;

}
