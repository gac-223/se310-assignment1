package com.se310.ledger;

import java.util.List;




public class ProcessTransactionCommand implements CommandStrategy {
    

    public Boolean appliesTo(String command) {
        return "process-transaction".equals(command) ;
    }

    public void processCommand(List<String> tokens, LedgerInterface ledger) throws CommandProcessorException {
        if(tokens.size() != 12)
            throw new CommandProcessorException("process-transaction", "Missing Arguments");

        System.out.println("Processing Transaction: " + tokens.get(1) + " "
            + tokens.get(3) + " " + tokens.get(5) + " " + tokens.get(7) + " "
            + tokens.get(9) + " " + tokens.get(11) + " ");

        BlockInterface block = ledger.getUncommittedBlock();

        AccountInterface payer = block.getAccount (tokens.get(9));
        AccountInterface receiver = block.getAccount(tokens.get(11));

        if(payer == null || receiver == null){
            throw new CommandProcessorException("process-transaction", "Account Does Not Exist") ;
        }

        TransactionInterface tempTransaction = new Transaction(tokens.get(1), Integer.parseInt(tokens.get(3)),
                Integer.parseInt(tokens.get(5)), tokens.get(7), payer, receiver);
        try {
            ledger.processTransaction(tempTransaction);
        } catch (LedgerException e) {
            System.out.println("Failed due to: " + e.getReason());
        }        
    }
}
