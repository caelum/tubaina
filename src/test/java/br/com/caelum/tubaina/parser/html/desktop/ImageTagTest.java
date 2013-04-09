package br.com.caelum.tubaina.parser.html.desktop;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.ParseType;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;

public class ImageTagTest {

    @Test
    public void shouldThrowExpectionWhenTagContainsLabel() throws IOException {
    	Parser parser = ParseType.HTML.getParser(new RegexConfigurator(), false, false, "");
        ImageTag tag = new ImageTag(parser);
        try {
            tag.parse("some/path/imagem.png", "label=somelabel");
            Assert.fail("should throw excpetion");
        } catch (TubainaException e) {
        }
    }

}
