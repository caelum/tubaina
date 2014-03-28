package br.com.caelum.tubaina.parser.html.kindle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.AfcFile;
import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.TubainaBuilderData;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.builder.BookBuilder;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.RegexTag;
import br.com.caelum.tubaina.parser.html.desktop.HtmlParser;
import br.com.caelum.tubaina.resources.ResourceLocator;

public class KindleGeneratorTest {
    private KindleGenerator generator;
    private File tempDir;

    @Before
    public void setUp() throws IOException {
        RegexConfigurator configurator = new RegexConfigurator();
        List<RegexTag> tags = configurator.read("/regex.properties", "/html.properties");
        HtmlParser parser = new HtmlParser(tags);

        File path = new File("src/test/resources");
        ResourceLocator.initialize(path);

        TubainaBuilderData data = new TubainaBuilderData(false,
                TubainaBuilder.DEFAULT_TEMPLATE_DIR, false, false, null);

        generator = new KindleGenerator(parser, data);

        tempDir = new File("tmp");
        tempDir.mkdir();

    }

    @After
    public void deleteTempFiles() throws IOException {
        FileUtils.deleteDirectory(tempDir);
    }

    @Test
    public void shouldCreateAppropriateDirectoryStructure() throws Exception {
        Book book = createsSimpleBookWithTitle("livro");
        new KindleModule().inject(book);

        generator.generate(book, tempDir);

        File includes = new File(tempDir, "includes/");

        assertTrue("should contain includes directory", includes.exists());
    }

    private Book createsSimpleBookWithTitle(String title) {
        BookBuilder builder = builder(title);

        builder.addReaderFromString(
                "[chapter     O que é java?   ]\n" + "texto da seção\n"
                        + "[section Primeira seção]\n" + "texto da prim seção\n"
                        + "[section Segunda seção]\n" + "texto da segunda seção\n\n");

        builder.addReaderFromString("[chapter Introdução]\n"
                + "Algum texto de introdução\n");

        return builder.build();
    }

    private Book createsSimpleEscapedColonBook(String title) {
    	BookBuilder builder = builder(title);
    	
    	builder.addReaderFromString(
    			"[chapter     O que é java?   ]\n" + "texto da seção\n Perceba que renomeamos a classe %%mysql-server%% para "
    					+ "%%mysql<::server%%. Esta é a nomenclatura padrão que o Puppet entende para importar classes e tipos "
    					+ "dentro do mesmo módulo. Da mesma forma, devemos também renomear o tipo definido %%mysql-db%% para"
    					+ " %%mysql<::db%% dentro :");
    	
    	builder.addReaderFromString("[chapter Introdução]\n"
    			+ "Algum texto de introdução\n");
    	
    	return builder.build();
    }

    @Test
    public void shouldCreateTheBookFileWithEscapedSemicolon() throws Exception {
    	Book book = createsSimpleEscapedColonBook("livro");
    	new KindleModule().inject(book);
    	
    	generator.generate(book, tempDir);
    	
    	File theBookItself = new File(tempDir, "index.html");
    	String fileContent = new Scanner(theBookItself).useDelimiter("$$").next();
    	
    	File expectedHtml = new File("src/test/resources/kindle/htmlWithEscapedRubyHackCodeExpected.html");
    	String expectedContent = new Scanner(expectedHtml).useDelimiter("$$").next();
    	
    	assertEquals(expectedContent, fileContent);
    }
    
    @Test
    public void shouldCreateTheBookFile() throws Exception {
        Book book = createsSimpleBookWithTitle("livro");
        new KindleModule().inject(book);

        generator.generate(book, tempDir);

        File theBookItself = new File(tempDir, "index.html");

        assertTrue("should create the index.html containing the whole book", theBookItself.exists());
    }

    @Test
    public void shouldCreateADirectoryForEachChapterThatContainsImages() throws Exception {
        BookBuilder builder = builder("Com Imagens");
        builder.addReaderFromString("[chapter Um capítulo]\n"
                + "Uma introdução com imagem: \n\n" + "[img baseJpgImage.jpg]");
        builder.addReaderFromString("[chapter Outro capítulo]\n"
                + "Uma introdução com imagem: \n\n" + "[img basePngImage.png]");
        Book imageBook = builder.build();
        new KindleModule().inject(imageBook);

        generator.generate(imageBook, tempDir);

        File firstChapter = new File(tempDir, "um-capitulo/");
        File firstChaptersImage = new File(firstChapter, "baseJpgImage.jpg");
        File secondChapter = new File(tempDir, "outro-capitulo/");
        File secondChaptersImage = new File(secondChapter, "basePngImage.png");

        assertTrue(firstChapter.exists());
        assertTrue(firstChaptersImage.exists());
        assertTrue(secondChapter.exists());
        assertTrue(secondChaptersImage.exists());
    }

    @Test
    public void shouldNotCreateADirectoryChapterThatDoesntContainAnyImages() throws Exception {
        BookBuilder builder = builder("Com Imagens");
        builder.addReaderFromString("[chapter Um capítulo]\n"
                + "Uma introdução com imagem:\n");
        builder.addReaderFromString("[chapter Outro capítulo]\n"
                + "Uma introdução com imagem: \n\n" + "[img basePngImage.png]");
        Book imageBook = builder.build();
        new KindleModule().inject(imageBook);

        generator.generate(imageBook, tempDir);

        File firstChapter = new File(tempDir, "um-capitulo/");

        assertFalse(firstChapter.exists());
    }

    @Test
    public void testGeneratorWithCorrectImages() throws IOException {
        BookBuilder builder = builder("Com imagens");
        builder.addReaderFromString("[chapter qualquer um]\n"
                + "[img baseJpgImage.jpg]");
        Book b = builder.build();
        new KindleModule().inject(b);

        generator.generate(b, tempDir);
        // testar se a imagem foi copiada pro diretorio images
        File chapterDir = new File(tempDir, "qualquer-um/");
        Assert.assertTrue(chapterDir.exists());

        Assert.assertEquals(1, chapterDir.list(new SuffixFileFilter(Arrays.asList("jpg"))).length);
        File copied = new File(chapterDir, "baseJpgImage.jpg");
        Assert.assertTrue(copied.exists());
    }

    @Test
    public void testGeneratorWithDoubledImage() throws TubainaException, IOException {
        BookBuilder builder = builder("Com imagens");
        builder.addReaderFromString("[chapter qualquer um]\n"
                + "[img baseJpgImage.jpg]\n[img baseJpgImage.jpg]");

        Book b = builder.build();
        new KindleModule().inject(b);
        try {
            generator.generate(b, tempDir);
        } catch (TubainaException e) {
            Assert.fail("Should not complain if one uses twice the same image");
        }
    }
    
    @Test
    public void shouldCopyImagesFromIntroduction() throws IOException {
        BookBuilder builder = builder("With images in intro");
        List<AfcFile> chapterReaders = new ArrayList<AfcFile>();
        String imageName = "introImage.jpg";
        List<AfcFile> introductionReaders = Arrays.asList(new AfcFile(new StringReader("[chapter intro]\n[img " + imageName + "]"), "file from string"));
        builder.addAllReaders(chapterReaders, introductionReaders);
        Book book = builder.build();
        new KindleModule().inject(book);
        generator.generate(book, tempDir);
        File introDir = new File(tempDir, IntroductionChaptersToKindle.RESOURCES_PATH);
        File copied = new File(introDir, imageName);

        Assert.assertTrue(copied.exists());
    }

    @Test(expected = TubainaException.class)
    public void testGeneratorWithUnexistantImage() throws TubainaException, IOException {
        BookBuilder builder = builder("Com imagens");
        builder.addReaderFromString("[chapter qualquer um]\n"
                + "[img src/test/resources/someImage.gif]");
        Book b = builder.build();
        generator.generate(b, tempDir);
    }
    
    private BookBuilder builder(String title) {
    	return new BookBuilder(title, new SectionsManager());
    }
}
