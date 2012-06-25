package br.com.caelum.tubaina.util;

import static org.junit.Assert.fail;

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
}
