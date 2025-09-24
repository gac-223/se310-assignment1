package com.se310.ledger;

import java.util.List;
import java.util.Map;

public interface BlockInterface {
    

    public Integer getBlockNumber() ;

    public String getPreviousHash() ;

    public String getHash() ;

    public void setHash(String hash) ;

    public Map<String, AccountInterface> getAccountBalanceMap() ;

    public List<TransactionInterface> getTransactionList() ;

    public void addAccount(String address, AccountInterface account) ;

    public AccountInterface getAccount(String address) ;

    public BlockInterface getPreviousBlock() ;

    public void setPreviousBlock(BlockInterface previousBlock) ;


}
