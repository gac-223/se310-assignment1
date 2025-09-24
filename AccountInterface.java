

package com.se310.ledger;


public interface AccountInterface{

    public String getAddress() ;

    public int getBalance() ;

    public void setBalance(Integer balance) ;

    public AccountInterface clone() ;
}