package br.com.caelum.tubaina.parser.html.kindle;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.chunk.BoxChunk;

public class BoxTagTest extends AbstractTagTest {

    @Test
    public void testBox() {
        BoxChunk chunk = new BoxChunk("Título do Box", text("Texto do Box"));
		String result = getContent(chunk);
        Assert.assertEquals(BoxTag.BEGIN + BoxTag.TITLE_BEGIN + "T&iacute;tulo do Box" + BoxTag.TITLE_END
                + "Texto do Box" + BoxTag.END, result);
    }

    @Test
    public void testBoxWithMultilineContent() {
    	BoxChunk chunk = new BoxChunk("Título do Box", text("Texto do Box\n blablabla"));
        String result = getContent(chunk);
        Assert.assertEquals(BoxTag.BEGIN + BoxTag.TITLE_BEGIN + "T&iacute;tulo do Box"
                + BoxTag.TITLE_END + "Texto do Box\n blablabla" + BoxTag.END, result);
    }

}
