package br.com.caelum.tubaina.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import br.com.caelum.tubaina.TubainaException;

public class SimpleCommandExecutor implements CommandExecutor {
    
    private final Logger LOG = Logger.getLogger(SimpleCommandExecutor.class);

    @Override
	public String execute(String command, String input) {
        Process proc;
        try {
            LOG.debug("Executing: " + command);
            proc = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new TubainaException("Could not execute command: "+command, e);
        }
        
        writeInput(input, proc);
        verifyExitStatus(command, proc);
        String output = readOutput(proc);
        
        return output;
    }
    
    @Override
	public String execute(List<String> args, String input) {
        Process proc;
        try {
            LOG.debug("Executing: " + args);
            String[] argsArray = buildArgs(args);
            proc = Runtime.getRuntime().exec(argsArray);
        } catch (IOException e) {
            throw new TubainaException("Could not execute command: " + args, e);
        }
        
        writeInput(input, proc);
        verifyExitStatus(args.toString(), proc);
        String output = readOutput(proc);
        
        return output;
    }

    private String[] buildArgs(List<String> args) {
        String[] argsArray = new String[args.size()];
        for (int i = 0; i < argsArray.length; i++) {
            argsArray[i] = args.get(i);
        }
        return argsArray;
    }

    private void verifyExitStatus(String command, Process proc) {
        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            throw new TubainaException("Could not execute command: "+command, e);
        }
        if (proc.exitValue() != 0) {
            String commandOutput = "";
            Scanner scanner = new Scanner(proc.getErrorStream());
            scanner.useDelimiter("$$");
            if (scanner.hasNext())
                commandOutput = scanner.next();
            throw new TubainaException("Command: " + command + " could not be executed: " + commandOutput);
        }
    }

    private String readOutput(Process proc) {
        Scanner scanner = new Scanner(proc.getInputStream());
        scanner.useDelimiter("$$");
        String output = "";
        if (scanner.hasNext())
            output = scanner.next();
        return output;
    }

    private void writeInput(String input, Process proc) {
        PrintWriter writer = new PrintWriter(proc.getOutputStream());
        writer.print(input);
        writer.close();
    }
    
}
