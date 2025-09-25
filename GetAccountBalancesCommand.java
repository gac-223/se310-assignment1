package com.se310.ledger;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;





public class GetAccountBalancesCommand implements CommandStrategy {
    public Boolean appliesTo(String command) {
        return "get-account-balances".equals(command) ;
    }

    public void processCommand(List<String> tokens, LedgerInterface ledger) {
        System.out.println("Getting All Balances");

        Map<String,Integer> map = ledger.getAccountBalances();

        if(map == null){
            System.out.println("No Account Has Been Committed");
            return ;
        }

        Set<String> keys = new HashSet<>(map.keySet());

        for (String key : keys) {
            System.out.println("Account Balance for: " + key + " is " + map.get(key));
        }
    }
}
