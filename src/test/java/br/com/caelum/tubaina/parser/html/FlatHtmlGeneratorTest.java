package br.com.caelum.tubaina.parser.html;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.builder.BookBuilder;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.resources.ResourceLocator;
import br.com.caelum.tubaina.util.FileUtilities;
import br.com.caelum.tubaina.util.Utilities;

public class FlatHtmlGeneratorTest {

	private FlatHtmlGenerator generator;

	private Book book;

	private File temp;
	
	@Before
	public void setUp() throws IOException {
		RegexConfigurator configurator = new RegexConfigurator();
		List<Tag> tags = configurator.read("/regex.properties", "/html.properties");
		HtmlParser parser = new HtmlParser(tags, false);

		File path = new File("src/test/resources");
		ResourceLocator.initialize(path);

		generator = new FlatHtmlGenerator(parser, false, TubainaBuilder.DEFAULT_TEMPLATE_DIR);

		BookBuilder builder = new BookBuilder("livro");
		builder.add(new StringReader("[chapter     O que é java?   ]\n" + "texto da seção\n"
				+ "[section Primeira seção]\n" + "texto da prim seção\n" + "[section Segunda seção]\n"
				+ "texto da segunda seção\n\n"));
		builder.add(new StringReader("[chapter Introdução]\n" + "Algum texto de introdução\n"));
		book = builder.build();
		temp = new File("tmp");
		temp.mkdir();

	}

	@After
	public void deleteTempFiles() throws IOException {
		FileUtils.deleteDirectory(temp);
	}

	@Test
	public void testGenerator() throws IOException {
		generator.generate(book, temp);

		File livro = new File(temp, "livro/");
		Assert.assertTrue(livro.exists());

		// livro/
		File index = new File(livro, "index.html");
		Assert.assertTrue(index.exists());

		// se copiou os arquivos corretamente
		File html = new File(livro, "includes/");
		Assert.assertTrue(FileUtilities.contentEquals(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR, "html/includes"), html,
				new NotFileFilter(new NameFileFilter(new String[] { "CVS", ".svn" }))));

		// Diretories and indexes for chapters should exist
		File cap1 = new File(livro, "o-que-e-java");
		Assert.assertTrue(cap1.exists());
		index = new File(cap1, "index.html");
		Assert.assertTrue(index.exists());
		File cap2 = new File(livro, "introducao");
		Assert.assertTrue(cap2.exists());
		index = new File(cap2, "index.html");
		Assert.assertTrue(index.exists());


		// Diretories and indexes for sections should not exist
		File sec1 = new File(cap1, "primeira-secao");
		Assert.assertFalse(sec1.exists());
		index = new File(sec1, "index.html");
		Assert.assertFalse(index.exists());
		File sec2 = new File(cap1, "segunda-secao");
		Assert.assertFalse(sec1.exists());
		index = new File(sec2, "index.html");
		Assert.assertFalse(index.exists());
	}

	@Test
	public void testGeneratorWithCorrectImages() throws IOException {
		BookBuilder builder = new BookBuilder("Com imagens");
		builder.add(new StringReader("[chapter qualquer um]\n" + "[img baseJpgImage.jpg]"));
		Book b = builder.build();

		generator.generate(b, temp);
		// testar se a imagem foi copiada pro diretorio images

		// System.out.println(new File(temp, "com-imagens/").list()[2]);

		File images = new File(temp, "com-imagens/qualquer-um/resources/");
		Assert.assertTrue(images.exists());

		Assert.assertEquals(1, images.list().length);
		// File original = new File("src/test/h1-caelum.gif");
		File copied = new File(images, "baseJpgImage.jpg");
		Assert.assertTrue(copied.exists());
		// Assert.assertTrue(FileUtils.contentEquals(original, copied));
	}

	@Test
	public void testGeneratorWithDoubledImage() throws TubainaException, IOException {
		BookBuilder builder = new BookBuilder("Com imagens");
		builder.add(new StringReader("[chapter qualquer um]\n" + "[img baseJpgImage.jpg]\n[img baseJpgImage.jpg]"));

		Book b = builder.build();
		try {
			generator.generate(b, temp);
		} catch (TubainaException t) {
			Assert.fail("Should not raise an exception");
		}
		// OK
	}

	@Test
	public void testGeneratorWithUnexistantImage() throws TubainaException, IOException {
		BookBuilder builder = new BookBuilder("Com imagens");
		builder.add(new StringReader("[chapter qualquer um]\n" + "[img src/test/resources/someImage.gif]"));
		try {
			Book b = builder.build();
			generator.generate(b, temp);
			Assert.fail("Should raise an exception");
		} catch (TubainaException t) {
			// OK
		}
	}

	@Test
	public void testGeneratorWithDuppedChapterName() throws TubainaException, IOException {
		BookBuilder builder = new BookBuilder("teste");
		builder.add(new StringReader("[chapter qualquer um]\n" + "alguma coisa\n[chapter qualquer um]outra coisa"));
		try {
			Book b = builder.build();
			generator.generate(b, temp);
			Assert.fail("Should raise an exception");
		} catch (TubainaException t) {
			System.out.println(t.getMessage());
			// OK
		}
	}

	@Test
	public void testToDirectoryName() {
		// TODO Testar mais && Mudar de classe
		Assert.assertEquals("01-o-que-e-java", Utilities.toDirectoryName(1, "O que é Java?"));
		Assert.assertEquals("10-o-que-e-java", Utilities.toDirectoryName(10, "O    que\t é Java?"));
		Assert.assertEquals("05-c-que-e-java", Utilities.toDirectoryName(5, "Ç  %  que\t é Java?"));
		Assert.assertEquals("08-c-que-e-java", Utilities.toDirectoryName(8, " $  Ç  %  que\t é Java?"));
	}
}