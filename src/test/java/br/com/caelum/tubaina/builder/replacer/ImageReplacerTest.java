package br.com.caelum.tubaina.builder.replacer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.resources.Resource;
import br.com.caelum.tubaina.resources.ResourceLocator;

public class ImageReplacerTest {

	private ImageReplacer replacer;

	private List<Chunk> chunks;

	private List<Resource> resources;

	@Before
	public void setUp() {
		resources = new ArrayList<Resource>();
		replacer = new ImageReplacer(resources);
		chunks = new ArrayList<Chunk>();
		ResourceLocator.initialize(new File("."));
	}

	@Test
	public void testReplacesCorrectImage() {
		String image = "[img src/test/resources/baseJpgImage.jpg] ola resto";
		Assert.assertTrue(replacer.accepts(image));
		String resto = replacer.execute(image, chunks);
		Assert.assertEquals(" ola resto", resto);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(ImageChunk.class, chunks.get(0).getClass());
	}

	@Test(expected = IllegalStateException.class)
	public void testReplacesImageWithNoResource() {
		String image = "[img] ola resto";
		Assert.assertFalse(replacer.accepts(image));
		replacer.execute(image, chunks);
	}
	
	@Test
	public void shouldWorkCorrectlyWIthBracketsInDescription() {
	    String image = "[img src/test/resources/baseJpgImage.jpg \"description with brackets []\"] text";
	    Assert.assertTrue(replacer.accepts(image));
	    String execute = replacer.execute(image, chunks);
	    Assert.assertEquals(" text", execute);
	}
}