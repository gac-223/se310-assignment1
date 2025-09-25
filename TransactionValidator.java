package com.se310.ledger;

import java.util.*;


public class TransactionValidator implements TransactionValidatorInterface {
    
    private final List<TransactionValidationStrategy> validationStrategies ;

    public TransactionValidator(BlockManagerInterface blockManager) {
        validationStrategies = new ArrayList<>() ;

        validationStrategies.add(new TransactionAmountValidator()) ;
        validationStrategies.add(new TransactionFeeValidator()) ;
        validationStrategies.add(new TransactionIdValidator(blockManager)) ;
        validationStrategies.add(new TransactionNoteValidator()) ;
        validationStrategies.add(new SufficientBalanceValidator()) ;
    }

    public void validateTransaction(TransactionInterface transaction) throws LedgerException {
        for(int i = 0 ; i < validationStrategies.size() ; ++i) {
            validationStrategies.get(i).validate(transaction);
        }
    }

}
