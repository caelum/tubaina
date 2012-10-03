package br.com.caelum.tubaina.util;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import br.com.caelum.tubaina.TubainaException;

public class CommandExecutorTest {
    
    @Test
    public void shouldThrowExceptionWithInvalidCommand() throws Exception {
        try {
            new CommandExecutor().execute("asdlkjaslkdhaskjdh", "");
            fail();
        } catch (TubainaException e) {
        }
    }
    
    @Test
    public void shouldWorkWithValidCommands() throws Exception {
        new CommandExecutor().execute("ls -l", "");
        new CommandExecutor().execute(Arrays.asList("ls" ,"-l"), "");
    }
}
