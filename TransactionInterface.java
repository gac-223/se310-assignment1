
package com.se310.ledger ;

public interface TransactionInterface {

    public String getTransactionId() ;

    public Integer getAmount() ;
    
    public Integer getFee() ;

    public String getNote() ;

    public AccountInterface getPayer() ;

    public AccountInterface getReceiver() ;



}