package com.se310.ledger;

public class TransactionNoteValidator implements TransactionValidationStrategy {
    

    public void validate(TransactionInterface transaction) throws LedgerException {
        if (transaction.getNote().length() > 1024){
            throw new LedgerException("Process Transaction", "Note Length Must Be Less Than 1024 Chars");
        }
    }
}