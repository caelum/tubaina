package br.com.caelum.tubaina.parser.html;

import junit.framework.Assert;

import org.junit.Test;

public class RefTagTest {
    @Test
    public void testItem() {
        String result = new RefTag().parse("texto", null);
        Assert.assertEquals("", result);
    }
}
