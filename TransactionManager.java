package com.se310.ledger;



public class TransactionManager implements TransactionManagerInterface {


    public synchronized TransactionInterface processTransaction(TransactionInterface transaction) {
        AccountInterface tempPayerAccount = transaction.getPayer();
        AccountInterface tempReceiverAccount = transaction.getReceiver();

        //Deduct balance of the payer
        tempPayerAccount.setBalance(tempPayerAccount.getBalance()
                - transaction.getAmount() - transaction.getFee());
        //Increase balance of the receiver
        tempReceiverAccount.setBalance(tempReceiverAccount.getBalance() + transaction.getAmount());

        return transaction ;
    }



}
