package br.com.caelum.tubaina.parser.html.kindle;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
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
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.desktop.HtmlParser;
import br.com.caelum.tubaina.resources.ResourceLocator;

public class KindleGeneratorTest {
    private KindleGenerator generator;
    private File directory;

    @Before
    public void setUp() throws IOException {
        RegexConfigurator configurator = new RegexConfigurator();
        List<Tag> tags = configurator.read("/regex.properties", "/html.properties");
        HtmlParser parser = new HtmlParser(tags, false);

        File path = new File("src/test/resources");
        ResourceLocator.initialize(path);

        TubainaBuilderData data = new TubainaBuilderData(false,
                TubainaBuilder.DEFAULT_TEMPLATE_DIR, false, false, null);

        generator = new KindleGenerator(parser, data);

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

        generator.generate(book, directory);

        File includes = new File(directory, "includes/");

        assertTrue("should contain includes directory", includes.exists());
    }

    private Book createsSimpleBookWithTitle(String title) {
        BookBuilder builder = new BookBuilder(title);

        builder.addAllReaders(Arrays.asList((Reader) new StringReader(
                "[chapter     O que é java?   ]\n" + "texto da seção\n"
                        + "[section Primeira seção]\n" + "texto da prim seção\n"
                        + "[section Segunda seção]\n" + "texto da segunda seção\n\n")));

        builder.addAllReaders(Arrays.asList((Reader) new StringReader("[chapter Introdução]\n"
                + "Algum texto de introdução\n")));

        return builder.build();
    }

    @Test
    public void shouldCreateTheBookFile() throws Exception {
        Book book = createsSimpleBookWithTitle("livro");

        generator.generate(book, directory);

        File theBookItself = new File(directory, "index.html");

        assertTrue("should create the index.html containing the whole book", theBookItself.exists());
    }

    @Test
    public void shouldCreateADirectoryForEachChapterThatContainsImages() throws Exception {
        BookBuilder builder = new BookBuilder("Com Imagens");
        builder.addAllReaders(Arrays.asList((Reader) new StringReader("[chapter Um capítulo]\n"
                + "Uma introdução com imagem: \n\n" + "[img baseJpgImage.jpg]")));
        builder.addAllReaders(Arrays.asList((Reader) new StringReader("[chapter Outro capítulo]\n"
                + "Uma introdução com imagem: \n\n" + "[img basePngImage.png]")));
        Book imageBook = builder.build();

        generator.generate(imageBook, directory);

        File firstChapter = new File(directory, "um-capitulo/");
        File firstChaptersImage = new File(firstChapter, "baseJpgImage.jpg");
        File secondChapter = new File(directory, "outro-capitulo/");
        File secondChaptersImage = new File(secondChapter, "basePngImage.png");

        assertTrue(firstChapter.exists());
        assertTrue(firstChaptersImage.exists());
        assertTrue(secondChapter.exists());
        assertTrue(secondChaptersImage.exists());
    }

    @Test
    public void shouldNotCreateADirectoryChapterThatDoesntContainAnyImages() throws Exception {
        BookBuilder builder = new BookBuilder("Com Imagens");
        builder.addAllReaders(Arrays.asList((Reader) new StringReader("[chapter Um capítulo]\n"
                + "Uma introdução com imagem:\n")));
        builder.addAllReaders(Arrays.asList((Reader) new StringReader("[chapter Outro capítulo]\n"
                + "Uma introdução com imagem: \n\n" + "[img basePngImage.png]")));
        Book imageBook = builder.build();

        generator.generate(imageBook, directory);

        File firstChapter = new File(directory, "um-capitulo/");

        assertFalse(firstChapter.exists());
    }

    @Test
    public void testGeneratorWithCorrectImages() throws IOException {
        BookBuilder builder = new BookBuilder("Com imagens");
        builder.addAllReaders(Arrays.asList((Reader) new StringReader("[chapter qualquer um]\n"
                + "[img baseJpgImage.jpg]")));
        Book b = builder.build();

        generator.generate(b, directory);
        // testar se a imagem foi copiada pro diretorio images
        File chapterDir = new File(directory, "qualquer-um/");
        Assert.assertTrue(chapterDir.exists());

        Assert.assertEquals(1, chapterDir.list(new SuffixFileFilter(Arrays.asList("jpg"))).length);
        File copied = new File(chapterDir, "baseJpgImage.jpg");
        Assert.assertTrue(copied.exists());
    }

    @Test
    public void testGeneratorWithDoubledImage() throws TubainaException, IOException {
        BookBuilder builder = new BookBuilder("Com imagens");
        builder.addAllReaders(Arrays.asList((Reader) new StringReader("[chapter qualquer um]\n"
                + "[img baseJpgImage.jpg]\n[img baseJpgImage.jpg]")));

        Book b = builder.build();
        try {
            generator.generate(b, directory);
        } catch (TubainaException e) {
            Assert.fail("Should not complain if one uses twice the same image");
        }
    }

    @Test(expected = TubainaException.class)
    public void testGeneratorWithUnexistantImage() throws TubainaException, IOException {
        BookBuilder builder = new BookBuilder("Com imagens");
        builder.addAllReaders(Arrays.asList((Reader) new StringReader("[chapter qualquer um]\n"
                + "[img src/test/resources/someImage.gif]")));
        Book b = builder.build();
        generator.generate(b, directory);
    }
}
