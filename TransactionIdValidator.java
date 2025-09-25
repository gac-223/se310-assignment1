package com.se310.ledger;

public class TransactionIdValidator implements TransactionValidationStrategy {

    private BlockManagerInterface blockManager ;

    public TransactionIdValidator(BlockManagerInterface blockManager) {
        this.blockManager = blockManager ;
    }
    

    public void validate(TransactionInterface transaction) throws LedgerException {
        if(blockManager.getTransaction(transaction.getTransactionId()) != null){
            throw new LedgerException("Process Transaction", "Transaction Id Must Be Unique");
        }
    }
}