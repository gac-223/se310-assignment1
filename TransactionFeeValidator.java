package com.se310.ledger;

public class TransactionFeeValidator implements TransactionValidationStrategy {
    

    public void validate(TransactionInterface transaction) throws LedgerException {
        if (transaction.getFee() < 10) {
            throw new LedgerException("Process Transaction", "Transaction Fee Must Be Greater Than 10");
        }
    }
}