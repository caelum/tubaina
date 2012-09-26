package br.com.caelum.tubaina.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.log4j.Logger;

import br.com.caelum.tubaina.TubainaException;

public class CommandExecutor {
    
    private final Logger LOG = Logger.getLogger(CommandExecutor.class);

    public String execute(String command, String input) {
        OutputStream out = new ByteArrayOutputStream();
        OutputStream err = new ByteArrayOutputStream();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        String output;
        try {
            CommandLine c = CommandLine.parse(command);
            DefaultExecutor executor = new DefaultExecutor();
            
            ExecuteStreamHandler streamHandler = new PumpStreamHandler(out, err, in);
            executor.setStreamHandler(streamHandler);
            
            int execute = executor.execute(c);
            
            output = new String(((ByteArrayOutputStream) out).toByteArray());
            
            verifyExitStatus(execute, command, output);
            
            LOG.debug("Executing: '" + command + "'");
        } catch (IOException e) {
            throw new TubainaException("Could not execute command: "+command, e);
        }
        return output;
    }

    private void verifyExitStatus(int exitValue, String command, String output) {
        if (exitValue != 0) {
            throw new TubainaException("Command: '" + command + "' could not be executed, failing with the following output:\n " + output);
        }
    }

}
