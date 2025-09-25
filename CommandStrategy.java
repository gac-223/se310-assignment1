package com.se310.ledger;

import java.util.*;



public interface CommandStrategy {
    
    public Boolean appliesTo(String command) ;

    public void processCommand(List<String> command, LedgerInterface ledger) throws CommandProcessorException ;

}
