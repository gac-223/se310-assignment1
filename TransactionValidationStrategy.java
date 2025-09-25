package com.se310.ledger;

public interface TransactionValidationStrategy {
    

    public void validate(TransactionInterface transaction) throws LedgerException ;

}
