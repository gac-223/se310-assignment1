
package com.se310.ledger ;

public interface TransactionInterface {

    // getters and setters for transactionID
    public String getTransactionId() ;


    // getters and setters for Amount
    public Integer getAmount() ;
    

    // not every type of transaction has a fee
    // getters and setters for Fee
    public Integer getFee() ;

    // not every type of transaction will have a note
    // getters and setters for Note
    public String getNote() ;

    // getters and setters for Payer
    public AccountInterface getPayer() ;


    // getters and setters for Receiver
    public AccountInterface getReceiver() ;



}