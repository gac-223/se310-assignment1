package com.se310.ledger;

import java.util.List;









public class GetBlockCommand implements CommandStrategy {

    public Boolean appliesTo(String command) {
        return "get-block".equals(command) ;
    }


    public void processCommand(List<String> tokens, LedgerInterface ledger) throws CommandProcessorException {
        if(tokens.size() != 2)
            throw new CommandProcessorException("get-block", "Missing Arguments");

        System.out.println("Get Block: " + tokens.get(1));
        BlockInterface block = null;
        try {
            block = ledger.getBlock(Integer.parseInt(tokens.get(1)));
        } catch (LedgerException e) {
            System.out.println("Failed due to: " + e.getReason());
            return ;
        }

        System.out.println("Block Number: " + block.getBlockNumber() + " "
            + "Hash: " + block.getHash() + " " + "Previous Hash: " + block.getPreviousHash()
        );

        for(TransactionInterface transaction: block.getTransactionList()){
            System.out.println(transaction.toString());
        }
    }
    
}
