package com.se310.ledger;

import java.util.*;



// this interface defines a strategy for processing commands 
public interface CommandStrategy {
    

    // Determines if this strategy applies to the given command
    public Boolean appliesTo(String command) ;

    // processes the given command
    public void processCommand(List<String> command, LedgerInterface ledger) throws CommandProcessorException ;

}
