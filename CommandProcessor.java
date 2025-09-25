package com.se310.ledger;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;



/**
 * CommandProcessor class implementation designed to process individual Blockchain commands
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 */
public class CommandProcessor {

    private static LedgerInterface ledger ;

    private final List<CommandStrategy> commandStrategies ;

    public void setLedger(LedgerInterface newLedger) {
        ledger = newLedger ;
    }

    public CommandProcessor() {


        commandStrategies = new ArrayList<>() ;

        commandStrategies.add(new CreateAccountCommand()) ;
        commandStrategies.add(new CreateLedgerCommand(this)) ;
        commandStrategies.add(new GetAccountBalanceCommand()) ;
        commandStrategies.add(new GetAccountBalancesCommand()) ;
        commandStrategies.add(new GetBlockCommand()) ;
        commandStrategies.add(new GetTransactionCommand()) ;
        commandStrategies.add(new ProcessTransactionCommand()) ;
        commandStrategies.add(new ValidateCommand()) ;
    }

    public void processCommand(String command) throws CommandProcessorException {

        List<String> tokens = new ArrayList<>();
        //Split the line into tokens between spaces and quotes
        Matcher matcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command);
        while (matcher.find())
            tokens.add(matcher.group(1).replace("\"", ""));

        // loop thru all the command strategies
            // if applies to the strat, then run the strat
        for(int i = 0 ; i < commandStrategies.size() ; ++i) {


            if(commandStrategies.get(i).appliesTo(tokens.get(0))) {
                commandStrategies.get(i).processCommand(tokens, ledger);
                return ;
            }
        }

        throw new CommandProcessorException(tokens.get(0), "Invalid Command");

    }
    

    /**
     * Process File from the command line
     */
    public void processCommandFile(String fileName){

        List<String> tokens = new ArrayList<>();

        AtomicInteger atomicInteger = new AtomicInteger(0);

        //Process all the lines in the file
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream
                    .forEach(line -> {
                        try {
                            atomicInteger.getAndIncrement();
                            if(!line.trim().startsWith("#") && !line.trim().isEmpty()) {
                                // processCommand(line);
                                processCommand(line) ;
                            }
                        } catch (CommandProcessorException e) {
                            e.setLineNumber(atomicInteger.get());
                            System.out.println("Failed due to: " + e.getReason() + " for Command: " + e.getCommand()
                                    + " On Line Number: " + e.getLineNumber());
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
