package com.se310.ledger;

import java.util.*;




public class CreateLedgerCommand implements CommandStrategy {

    private final CommandProcessor processor ;

    public CreateLedgerCommand(CommandProcessor processor) {
        this.processor = processor ;
    }
    
    public Boolean appliesTo(String command) {
        return "create-ledger".equals(command) ;
    }

    public void processCommand(List<String> tokens, LedgerInterface ledger) throws CommandProcessorException {

        if(tokens.size() != 6) {
            throw new CommandProcessorException("create-ledger", "missing Arguments") ;
        }

        System.out.println("Creating Ledger: " + tokens.get(1) + " " + tokens.get(3) + " " + tokens.get(5));
        ledger = Ledger.getInstance(tokens.get(1), tokens.get(3), tokens.get(5));
        processor.setLedger(ledger) ;
    }

}
