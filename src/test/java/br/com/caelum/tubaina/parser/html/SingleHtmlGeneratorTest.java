package br.com.caelum.tubaina.parser.html;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.builder.BookBuilder;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.resources.ResourceLocator;


public class SingleHtmlGeneratorTest {
	private SingleHtmlGenerator generator;
	private Book book;
	private File directory;

	@Before
	public void setUp() throws IOException {
		RegexConfigurator configurator = new RegexConfigurator();
		List<Tag> tags = configurator.read("/regex.properties", "/html.properties");
		HtmlParser parser = new HtmlParser(tags, false);

		File path = new File("src/test/resources");
		ResourceLocator.initialize(path);

		generator = new SingleHtmlGenerator(parser, TubainaBuilder.DEFAULT_TEMPLATE_DIR);

		BookBuilder builder = new BookBuilder("livro");
		builder.add(new StringReader("[chapter     O que é java?   ]\n" + "texto da seção\n"
				+ "[section Primeira seção]\n" + "texto da prim seção\n" + "[section Segunda seção]\n"
				+ "texto da segunda seção\n\n"));
		builder.add(new StringReader("[chapter Introdução]\n" + "Algum texto de introdução\n"));
		book = builder.build();
		directory = new File("tmp");
		directory.mkdir();

	}

	@After
	public void deleteTempFiles() throws IOException {
		FileUtils.deleteDirectory(directory);
	}
	
	@Test
	public void shouldCreateAppropriateDirectoryStructure() throws Exception {
		generator.generate(book, directory);
		
		File bookRoot = new File(directory, "livro/");
		File includes = new File(bookRoot, "includes/");
		
		assertTrue("should contain directory with chosen book name", bookRoot.exists());
		assertTrue("should contain includes directory", includes.exists());
	}
	
	@Test
	public void shouldCreateTheBookFile() throws Exception {
		generator.generate(book, directory);
		
		File bookRoot = new File(directory, "livro/");
		File theBookItself = new File(bookRoot, "index.html");
		
		assertTrue("should create the index.html containing the whole book", theBookItself.exists());
	}
}
