package com.se310.ledger;

import java.util.List;

public class ValidateCommand implements CommandStrategy {
    
    public Boolean appliesTo(String command) {
        return "validate".equals(command) ;
    }

    public void processCommand(List<String> tokens, LedgerInterface ledger) throws CommandProcessorException {
        System.out.print("Validate: ");
        try {
            ledger.validate();
            System.out.println("Valid");
        } catch (LedgerException e) {
            System.out.println("Failed due to: " + e.getReason());
        }
    }

}
