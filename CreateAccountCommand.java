package com.se310.ledger;

import java.util.* ;




public class CreateAccountCommand implements CommandStrategy {

    public Boolean appliesTo(String command) {
        return "create-account".equals(command) ;
    }

    public void processCommand(List<String> tokens, LedgerInterface ledger) throws CommandProcessorException {
        if(tokens.size() != 2) {
            throw new CommandProcessorException("create-account", "Missing Arguments") ;
        }

        System.out.println("Creating Account: " + tokens.get(1));

        try {
            ledger.createAccount(tokens.get(1)) ;
        } catch (LedgerException e) {
            System.out.println("Failed due to: " + e.getReason()) ;
        }
    }
    
}
