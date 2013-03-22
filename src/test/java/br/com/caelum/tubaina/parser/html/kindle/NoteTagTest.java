package br.com.caelum.tubaina.parser.html.kindle;

import org.junit.Assert;
import org.junit.Test;

public class NoteTagTest {

    @Test
    public void testNoteTag() {
        String result = new NoteTag().parse(chunk);
        String begin = "---------------------------<br />";
        String end = "<br />---------------------------";
        Assert.assertEquals(result, begin + "qualquer texto de nota" + end);
    }

}
