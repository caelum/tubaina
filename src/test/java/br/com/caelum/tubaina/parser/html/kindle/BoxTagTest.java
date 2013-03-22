package br.com.caelum.tubaina.parser.html.kindle;

import org.junit.Assert;
import org.junit.Test;

public class BoxTagTest {

    @Test
    public void testBox() {
        BoxTag tag = new BoxTag();
        String result = tag.parse(chunk);
        Assert.assertEquals(BoxTag.BEGIN + BoxTag.TITLE_BEGIN + "Titulo do Box" + BoxTag.TITLE_END
                + "Texto do Box" + BoxTag.END, result);
    }

    @Test
    public void testBoxWithMultilineContent() {
        BoxTag tag = new BoxTag();
        String result = tag.parse(chunk);
        Assert.assertEquals(BoxTag.BEGIN + BoxTag.TITLE_BEGIN + "Titulo do Box"
                + BoxTag.TITLE_END + "Texto do Box\n blablabla" + BoxTag.END, result);
    }

}
