package br.com.caelum.tubaina.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import br.com.caelum.tubaina.TubainaException;

public class CommandExecutor {

    public String execute(String command, String input) {
        Process proc;
        try {
            proc = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new TubainaException("Could not execute command: "+command, e);
        }
        PrintWriter writer = new PrintWriter(proc.getOutputStream());
        writer.print(input);
        writer.close();
        Scanner scanner = new Scanner(proc.getInputStream());
        scanner.useDelimiter("$$");
        return scanner.next();
    }
    
}
