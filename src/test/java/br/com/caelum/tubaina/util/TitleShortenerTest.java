package br.com.caelum.tubaina.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TitleShortenerTest {

    @Test
    public void shouldShortenTest() throws Exception {
        TitleShortener titleShortener = new TitleShortener(10);
        assertEquals("1234567890...", titleShortener.shortenTitle("12345678901"));
        
        titleShortener = new TitleShortener(5);
        assertEquals("12345...", titleShortener.shortenTitle("12345678901"));
    }
    
    @Test
    public void shouldNotShortenTest() throws Exception {
        TitleShortener titleShortener = new TitleShortener(10);
        assertEquals("123456789", titleShortener.shortenTitle("123456789"));
        
        titleShortener = new TitleShortener(15);
        assertEquals("123456789", titleShortener.shortenTitle("123456789"));
    }
    
}
