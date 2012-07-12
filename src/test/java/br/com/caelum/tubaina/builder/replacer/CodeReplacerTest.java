package br.com.caelum.tubaina.builder.replacer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.ParseType;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.parser.SimpleIndentator;

public class CodeReplacerTest {

	private CodeReplacer replacer;
	private List<Chunk> chunks;

	@Before
	public void setUp() {
		replacer = new CodeReplacer();
		chunks = new ArrayList<Chunk>();
	}

	@Test
	public void testReplacesCorrectCode() {
		String code = "[code]ola mundo[/code] ola resto";
		Assert.assertTrue(replacer.accepts(code));
		String resto = replacer.execute(code, chunks);
		Assert.assertEquals(" ola resto", resto);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(CodeChunk.class, chunks.get(0).getClass());
	}

	@Test(expected = TubainaException.class)
	public void testDoesntAcceptWithoutEndTag() {
		String code = "[code]ola mundo ola resto";
		Assert.assertTrue(replacer.accepts(code));
		replacer.execute(code, chunks);
	}

	@Test(expected = IllegalStateException.class)
	public void testDoesntAcceptWithoutBeginTag() {
		String answer = "ola mundo[/code] ola resto";
		Assert.assertFalse(replacer.accepts(answer));
		replacer.execute(answer, chunks);
	}
	
	@Test
	public void codeTagWithLanguageArgument() throws Exception {
		String original = "[code java]public class Arroz{[/code]";
		Assert.assertTrue(replacer.accepts(original));
		replacer.execute(original, chunks);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(CodeChunk.class, chunks.get(0).getClass());
	}

	@Test
    public void shouldThrowExeptionWhenMaxCodeWidthIsExceeded() throws Exception {
	    TubainaBuilder builder = new TubainaBuilder(ParseType.LATEX);
        builder.codeLength(10);
	    String original = "[code java]12345678901[/code]";
        Assert.assertTrue(replacer.accepts(original));
        try {
            replacer.execute(original, chunks);
            Assert.fail("should throw exception");
        } catch (TubainaException e) {
        } finally {
            builder.codeLength(10000);
        }
    }
	
	@Test
	public void shouldThrowExeptionWithCodeWithTabs() throws Exception {
	    TubainaBuilder builder = new TubainaBuilder(ParseType.LATEX);
	    String tabReplacement = SimpleIndentator.TAB_REPLACEMENT;
        builder.codeLength(6 + tabReplacement.length());
	    String original = "[code java]5\t678901[/code]";
	    Assert.assertTrue(replacer.accepts(original));
	    try {
	        replacer.execute(original, chunks);
	        Assert.fail("should throw exception");
	    } catch (TubainaException e) {
	    } finally {
	        builder.codeLength(10000);
	    }
	}
}
