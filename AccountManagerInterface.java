package com.se310.ledger;

import java.util.Map;

public interface AccountManagerInterface {

    public AccountInterface createAccount(String address) throws LedgerException ;

    public Integer getAccountBalance(String address, BlockInterface block) throws LedgerException ;

    public Map<String,Integer> getAccountBalances(BlockInterface committedBlock) ;
    
}
