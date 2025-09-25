package com.se310.ledger;

public class SufficientBalanceValidator implements TransactionValidationStrategy {
    

    public void validate(TransactionInterface transaction) throws LedgerException {
        if(transaction.getPayer().getBalance() < (transaction.getAmount() + transaction.getFee())) {
            throw new LedgerException("Process Transaction", "Payer Does Not Have Required Funds");
        }
    }
}
