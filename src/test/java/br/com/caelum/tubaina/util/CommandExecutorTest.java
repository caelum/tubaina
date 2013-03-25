package br.com.caelum.tubaina.util;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import br.com.caelum.tubaina.TubainaException;

public class CommandExecutorTest {
    
    @Test
    public void shouldThrowExceptionWithInvalidCommand() throws Exception {
        try {
            new SimpleCommandExecutor().execute("asdlkjaslkdhaskjdh", "");
            fail();
        } catch (TubainaException e) {
        }
    }
    
    @Test
    public void shouldWorkWithValidCommands() throws Exception {
        new SimpleCommandExecutor().execute("ls -l", "");
        new SimpleCommandExecutor().execute(Arrays.asList("ls" ,"-l"), "");
    }
}
