package br.com.caelum.tubaina.parser.latex;

import junit.framework.Assert;

import org.junit.Test;

public class RefTagTest {
    @Test
    public void testItem() {
        String result = new RefTag().parse("texto", null);
        Assert.assertEquals("\n\\ref{texto}\n", result);
    }

    @Test
    public void testMultipleItems() {
        String result = new RefTag().parse("texto1, texto2", null);
        Assert.assertEquals("\n\\ref{texto1, texto2}\n", result);
    }
}
