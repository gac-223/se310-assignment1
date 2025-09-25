package com.se310.ledger;

import java.util.List;






public class GetTransactionCommand implements CommandStrategy {
    
    public Boolean appliesTo(String command) {
        return "get-transaction".equals(command) ;
    }

    public void processCommand(List<String> tokens, LedgerInterface ledger) throws CommandProcessorException {
        if(tokens.size() != 2)
            throw new CommandProcessorException("get-transaction", "Missing Arguments");

        System.out.println("Get Transaction: " + tokens.get(1));
        TransactionInterface transaction = ledger.getTransaction(tokens.get(1));

        System.out.println("Transaction ID: " + transaction.getTransactionId() + " "
                + "Amount: " + transaction.getAmount() + " " + "Fee: "
                + transaction.getFee() + " " + "Note: " + transaction.getNote() + " " + "Payer: "
                + transaction.getPayer().getAddress() + " " + "Receiver: "
                + transaction.getReceiver().getAddress()
        );
    }
}
