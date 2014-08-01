package br.com.caelum.tubaina.parser.html.desktop;

import static java.util.Arrays.asList;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.TubainaBuilderData;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.builder.BookBuilder;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.RegexTag;
import br.com.caelum.tubaina.resources.ResourceLocator;
import br.com.caelum.tubaina.util.FileUtilities;

public class FlatHtmlGeneratorTest {

    private FlatHtmlGenerator generator;
    private Book book;
    private File temp;

    private TubainaBuilderData data;

    @Before
    public void setUp() throws IOException {
        RegexConfigurator configurator = new RegexConfigurator();
        List<RegexTag> tags = configurator.read("/regex.properties", "/html.properties");
        HtmlParser parser = new HtmlParser(tags);

        File path = new File("src/test/resources");
        ResourceLocator.initialize(path);
        data = new TubainaBuilderData(false, TubainaBuilder.DEFAULT_TEMPLATE_DIR, false, false,
                "teste.tex");

        generator = new FlatHtmlGenerator(parser, data);

        String content = "[chapter     O que é java?   ]\n" + "texto da seção\n"
                + "[section Primeira seção]\n" + "texto da prim seção\n"
                + "[section Segunda seção]\n" + "texto da segunda seção\n\n";
        BookBuilder builder = builder("livro");
        builder.addAllReadersOfNonNumberedFromStrings(Arrays.asList("[chapter introduction]\n" + "texto do prefácio\n"));
		builder.addReaderFromString(content);
        builder.addReaderFromString("[chapter Introdução]\n"
                + "Algum texto de introdução\n");
        book = builder.build();
        temp = new File("tmp");
        temp.mkdir();

    }

    @After
    public void deleteTempFiles() throws IOException {
        FileUtils.deleteDirectory(temp);
    }

    @Test
    public void shouldCreateDirectoriesAndCopyFiles() throws Exception {
    	new HtmlModule().inject(book);
        generator.generate(book, temp);

        File livro = new File(temp, "livro/");
        Assert.assertTrue(livro.exists());

        // livro/
        File index = new File(livro, "index.html");
        Assert.assertTrue(index.exists());

        // se copiou os arquivos corretamente
        File html = new File(livro, "includes/");
        Assert.assertTrue(FileUtilities.contentEquals(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR,
                "html/includes"), html, new NotFileFilter(new NameFileFilter(new String[] { "CVS",
                ".svn" }))));
    }

    @Test
    public void shouldCreateDirectoriesForChapters() throws Exception {
    	new HtmlModule().inject(book);
        generator.generate(book, temp);
        File livro = new File(temp, "livro/");
        File index = new File(livro, "index.html");

        // Diretories and indexes for chapters should exist
        File cap1 = new File(livro, "o-que-e-java");
        Assert.assertTrue(cap1.exists());
        index = new File(cap1, "index.html");
        Assert.assertTrue(index.exists());
        File cap2 = new File(livro, "introducao");
        Assert.assertTrue(cap2.exists());
        index = new File(cap2, "index.html");
        Assert.assertTrue(index.exists());
    }

    @Test
    public void shouldNotCreateDirectoriesForSections() throws IOException {
    	new HtmlModule().inject(book);
        generator.generate(book, temp);

        File livro = new File(temp, "livro/");
        File index = new File(livro, "index.html");
        File cap1 = new File(livro, "o-que-e-java");

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
    	BookBuilder builder = builder("com-imagens");
        builder.addReaderFromString("[chapter qualquer um]\n"
                + "[img baseJpgImage.jpg]");
        Book b = builder.build();
        new HtmlModule().inject(b);

        generator.generate(b, temp);
        File images = new File(temp, "com-imagens/qualquer-um/");
        Assert.assertTrue(images.exists());

        Assert.assertEquals(1, images.list(new SuffixFileFilter(asList("jpg"))).length);
        File copied = new File(images, "baseJpgImage.jpg");
        Assert.assertTrue(copied.exists());
    }

    @Test
    public void testGeneratorWithDoubledImage() throws TubainaException, IOException {
        String content = "[chapter qualquer um]\n"
                + "[img baseJpgImage.jpg]\n[img baseJpgImage.jpg]";
        BookBuilder builder = builder("com-erro");
        builder.addReaderFromString(content);

        Book b = builder.build();
        new HtmlModule().inject(b);
        try {
            generator.generate(b, temp);
        } catch (TubainaException t) {
            Assert.fail("Should not raise an exception");
        }
        // OK
    }

    @Test
    public void testGeneratorWithUnexistantImage() throws TubainaException, IOException {
        String chapterContent = "[chapter qualquer um]\n"
                + "[img src/test/resources/someImage.gif]";
        BookBuilder builder = builder("com-erro");
        builder.addReaderFromString(chapterContent);
        try {
            Book b = builder.build();
            new HtmlModule().inject(b);
            generator.generate(b, temp);
            Assert.fail("Should raise an exception");
        } catch (TubainaException t) {
            // OK
        }
    }

    @Test
    public void testGeneratorWithDuppedChapterName() throws TubainaException, IOException {
        String fileContent = "[chapter qualquer um]\n"
                + "alguma coisa\n[chapter qualquer um]outra coisa";
        BookBuilder builder = builder("com-erro");
        builder.addReaderFromString(fileContent);
        try {
            Book b = builder.build();
            new HtmlModule().inject(b);
            generator.generate(b, temp);
            Assert.fail("Should raise an exception");
        } catch (TubainaException t) {
            System.out.println(t.getMessage());
            // OK
        }
    }
    
    @Test
    public void shouldNotCreateDirectoryForIntroduction() throws Exception {
    	new HtmlModule().inject(book);
        generator.generate(book, temp);
        File livro = new File(temp, "livro/");
        File index = new File(livro, "index.html");

        File introduction = new File(livro, "introduction");
        Assert.assertFalse(introduction.exists());
    }
    
    @Test
    public void indexShouldContainIntroductionChapters() throws Exception{
    	
    }
    
    public BookBuilder builder(String title) {
    	return new BookBuilder(title, new SectionsManager());
    }
}