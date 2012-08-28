package br.com.caelum.tubaina.parser.html.desktop;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.TubainaException;

public class ImageTagTest {

    @Test
    public void shouldThrowExpectionWhenTagContainsLabel() {
        ImageTag tag = new ImageTag();
        try {
            tag.parse("some/path/imagem.png", "label=somelabel");
            Assert.fail("should throw excpetion");
        } catch (TubainaException e) {
        }
    }

}
