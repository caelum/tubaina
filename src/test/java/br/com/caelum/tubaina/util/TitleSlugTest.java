package br.com.caelum.tubaina.util;

import org.junit.Assert;
import org.junit.Test;

public class TitleSlugTest {

    @Test
    public void shouldCreateTitleSlug() throws Exception {
        Assert.assertEquals("o-que-e-java", new TitleSlug("O que é Java?").toString());
        Assert.assertEquals("o-que-e-java", new TitleSlug("O    que\t é Java?").toString());
        Assert.assertEquals("c-que-e-java", new TitleSlug("Ç  %  que\t é Java?").toString());
        Assert.assertEquals("c-que-e-java", new TitleSlug(" $  Ç  %  que\t é Java?").toString());
        
    }
}
