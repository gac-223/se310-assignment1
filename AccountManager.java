package com.se310.ledger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountManager implements AccountManagerInterface {



    /**
     * Method for creating accounts in the blockchain
     * @param address
     * @return Account representing account in the Blockchain
     */
    public AccountInterface createAccount(String address) throws LedgerException {

        return new Account(address, 0);
    }
    


    /**
     * Get Account balance by address
     * @param address
     * @return Integer representing balance of the Account
     * @throws LedgerException
     */
    public Integer getAccountBalance(String address, BlockInterface block) throws LedgerException {

        AccountInterface account = block.getAccount(address);

        if (account == null)
            throw new LedgerException("Get Account Balance", "Account Does Not Exist");
        else
            return account.getBalance();
    }


    /**
     * Get all Account balances that are part of the Blockchain
     * @return Map representing Accounts and balances
     */
    public Map<String,Integer> getAccountBalances(BlockInterface committedBlock){

        Map<String,AccountInterface> accountMap = committedBlock.getAccountBalanceMap();

        Map<String, Integer> balances = new HashMap<>();
        List<AccountInterface> accountList = new ArrayList<>(accountMap.values());

        for (AccountInterface account : accountList) {
            balances.put(account.getAddress(), account.getBalance());
        }

        return balances;
    }
}
