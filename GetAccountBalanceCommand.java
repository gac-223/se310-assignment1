package com.se310.ledger;

import java.util.List;




public class GetAccountBalanceCommand implements CommandStrategy {

    public Boolean appliesTo(String command) {
        return "get-account-balance".equals(command) ;
    }

    public void processCommand(List<String> tokens, LedgerInterface ledger) throws CommandProcessorException {
        if(tokens.size() != 2) {
            throw new CommandProcessorException("create-account", "Missing Arguments");
        }

        System.out.println("Getting Balance for: " + tokens.get(1));
        try {
            System.out.println("Account Balance for: " + tokens.get(1) + " is " + ledger.getAccountBalance(tokens.get(1)));
        } catch (LedgerException e) {
            System.out.println("Failed due to: " + e.getReason());
        }
    }
    
}
