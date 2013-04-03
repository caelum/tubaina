package br.com.caelum.tubaina.parser.html.desktop;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.TubainaBuilderData;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.builder.BookBuilder;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.RegexTag;
import br.com.caelum.tubaina.resources.ResourceLocator;

public class SingleHtmlGeneratorTest {
	private SingleHtmlGenerator generator;
	private File directory;

	@Before
	public void setUp() throws IOException {
		RegexConfigurator configurator = new RegexConfigurator();
		List<RegexTag> tags = configurator.read("/regex.properties", "/html.properties");
		HtmlParser parser = new HtmlParser(tags);

		File path = new File("src/test/resources");
		ResourceLocator.initialize(path);

		TubainaBuilderData data = new TubainaBuilderData(false, TubainaBuilder.DEFAULT_TEMPLATE_DIR, false, false,
				"book");

		generator = new SingleHtmlGenerator(parser, data);

		directory = new File("tmp");
		directory.mkdir();
	}

	@After
	public void deleteTempFiles() throws IOException {
		FileUtils.deleteDirectory(directory);
	}

	@Test
	public void shouldCreateAppropriateDirectoryStructure() throws Exception {
		Book book = createsSimpleBookWithTitle("livro");
		new HtmlModule().inject(book);

		generator.generate(book, directory);

		File bookRoot = new File(directory, "livro/");
		File includes = new File(bookRoot, "includes/");

		assertTrue("should contain directory with chosen book name", bookRoot.exists());
		assertTrue("should contain includes directory", includes.exists());
	}

	private Book createsSimpleBookWithTitle(String title) {
		BookBuilder builder = new BookBuilder(title);

		String fileContent = "[chapter     O que é java?   ]\n" + "texto da seção\n" + "[section Primeira seção]\n"
				+ "texto da prim seção\n" + "[section Segunda seção]\n" + "texto da segunda seção\n\n";
		builder.addReaderFromString(fileContent);

		builder.addReaderFromString("[chapter Introdução]\n" + "Algum texto de introdução\n");

		return builder.build();
	}

	@Test
	public void shouldCreateTheBookFile() throws Exception {
		Book book = createsSimpleBookWithTitle("livro");
		new HtmlModule().inject(book);

		generator.generate(book, directory);

		File bookRoot = new File(directory, "livro/");
		File theBookItself = new File(bookRoot, "index.html");

		assertTrue("should create the index.html containing the whole book", theBookItself.exists());
	}

	@Test
	public void shouldCreateADirectoryForEachChapterThatContainsImages() throws Exception {
		BookBuilder builder = new BookBuilder("Com Imagens");
		builder.addReaderFromString("[chapter Um capítulo]\n" + "Uma introdução com imagem: \n\n"
				+ "[img baseJpgImage.jpg]");
		builder.addReaderFromString("[chapter Outro capítulo]\n" + "Uma introdução com imagem: \n\n"
				+ "[img basePngImage.png]");
		Book imageBook = builder.build();
		new HtmlModule().inject(imageBook);

		generator.generate(imageBook, directory);

		File bookRoot = new File(directory, "com-imagens/");
		File firstChapter = new File(bookRoot, "um-capitulo/");
		File firstChaptersImage = new File(firstChapter, "baseJpgImage.jpg");
		File secondChapter = new File(bookRoot, "outro-capitulo/");
		File secondChaptersImage = new File(secondChapter, "basePngImage.png");

		assertTrue(bookRoot.exists());
		assertTrue(firstChapter.exists());
		assertTrue(firstChaptersImage.exists());
		assertTrue(secondChapter.exists());
		assertTrue(secondChaptersImage.exists());
	}

	@Test
	public void shouldNotCreateADirectoryChapterThatDoesntContainAnyImages() throws Exception {
		BookBuilder builder = new BookBuilder("Com Imagens");
		builder.addReaderFromString("[chapter Um capítulo]\n" + "Uma introdução com imagem:\n");
		builder.addReaderFromString("[chapter Outro capítulo]\n" + "Uma introdução com imagem: \n\n"
				+ "[img basePngImage.png]");
		Book imageBook = builder.build();
		new HtmlModule().inject(imageBook);

		generator.generate(imageBook, directory);

		File bookRoot = new File(directory, "com-imagens/");
		File firstChapter = new File(bookRoot, "um-capitulo/");

		assertTrue(bookRoot.exists());
		assertFalse(firstChapter.exists());
	}

	@Test
	public void testGeneratorWithCorrectImages() throws IOException {
		BookBuilder builder = new BookBuilder("Com imagens");
		builder.addReaderFromString("[chapter qualquer um]\n" + "[img baseJpgImage.jpg]");
		Book b = builder.build();
		new HtmlModule().inject(b);

		generator.generate(b, directory);
		// testar se a imagem foi copiada pro diretorio images
		File chapterDir = new File(directory, "com-imagens/qualquer-um/");
		Assert.assertTrue(chapterDir.exists());

		Assert.assertEquals(1, chapterDir.list(new SuffixFileFilter(Arrays.asList("jpg"))).length);
		File copied = new File(chapterDir, "baseJpgImage.jpg");
		Assert.assertTrue(copied.exists());
	}

	@Test
	public void testGeneratorWithDoubledImage() throws TubainaException, IOException {
		BookBuilder builder = new BookBuilder("Com imagens");
		String fileContent = "[chapter qualquer um]\n" + "[img baseJpgImage.jpg]\n[img baseJpgImage.jpg]";
		builder.addReaderFromString(fileContent);

		Book b = builder.build();
		new HtmlModule().inject(b);

		try {
			generator.generate(b, directory);
		} catch (TubainaException e) {
			Assert.fail("Should not complain if one uses twice the same image");
		}
	}

	@Test(expected = TubainaException.class)
	public void testGeneratorWithUnexistantImage() throws TubainaException, IOException {
		BookBuilder builder = new BookBuilder("Com imagens");
		String fileContent = "[chapter qualquer um]\n" + "[img src/test/resources/someImage.gif]";
		builder.addReaderFromString(fileContent);
		Book b = builder.build();
		generator.generate(b, directory);
	}
}
