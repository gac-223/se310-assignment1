package com.se310.ledger;

public class TransactionAmountValidator implements TransactionValidationStrategy {
    

    public void validate(TransactionInterface transaction) throws LedgerException {
        if(transaction.getAmount() < 0 || transaction.getAmount() > Integer.MAX_VALUE ){
            throw new LedgerException("Process Transaction", "Transaction Amount Is Out of Range");
        }
    }
}
