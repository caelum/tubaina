package br.com.caelum.tubaina.parser.latex;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
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
import br.com.caelum.tubaina.resources.ResourceLocator;
import br.com.caelum.tubaina.util.FileUtilities;

public class LatexGeneratorTest {
    private LatexGenerator generator;
    private Book book;
    private File temp;
    private LatexParser parser;
    private TubainaBuilderData data;

    @Before
    public void setUp() throws IOException {
        RegexConfigurator configurator = new RegexConfigurator();
        List<Tag> tags = configurator.read("/regex.properties", "/html.properties");
        parser = new LatexParser(tags);

        File path = new File("src/test/resources");
        ResourceLocator.initialize(path);
        data = new TubainaBuilderData(false, TubainaBuilder.DEFAULT_TEMPLATE_DIR, false, false,
                "teste");

        generator = new LatexGenerator(parser, data);

        BookBuilder builder = new BookBuilder("livro");
        builder.addReaderFromString(
                "[chapter     O que é java?   ]\n" + "texto da seção\n"
                        + "[section Primeira seção]\n" + "texto da prim seção\n"
                        + "[section Segunda seção]\n" + "texto da segunda seção\n\n");
        builder.addReaderFromString("[chapter Introdução]\n"
                + "Algum texto de introdução\n");
        book = builder.build();
        temp = new File("tmp");
        temp.mkdir();
    }

    @After
    public void deleteTempFiles() throws IOException {
        // Probably not working on Windows. See HtmlGeneratorTest
        FileUtils.deleteDirectory(temp);
    }

    @Test
    public void testGenerator() throws IOException {
        generator.generate(book, temp);

        // Book LaTeX
        File texFile = new File(temp, "teste.tex");
        Assert.assertTrue("Book file should exist", texFile.exists());

        // Answer book LaTeX
        File answerFile = new File(temp, "answer.tex");
        Assert.assertFalse("Answer file should NOT exist", answerFile.exists());

        // Resources
        File styleFile = new File(temp, "tubaina.sty");
        Assert.assertTrue("Style file should exist", styleFile.exists());

        File xcolorStyleFile = new File(temp, "xcolor.sty");
        Assert.assertTrue("Xcolor style file should exist", xcolorStyleFile.exists());

        Assert.assertTrue(FileUtilities.contentEquals(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR,
                "latex"), temp, new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.contains(".png");
            }
        }));
    }

    @Test
    public void testGeneratorWithCorrectImages() throws IOException {
        BookBuilder builder = new BookBuilder("Com imagens");
        builder.addReaderFromString("[chapter qualquer um]\n"
                + "[img baseJpgImage.jpg]");
        Book b = builder.build();

        generator.generate(b, temp);

        File[] images = temp.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.contains(".png") || name.contains(".jpg");
            }
        });

        Assert.assertEquals(2, images.length);
        File copied = new File(temp, "baseJpgImage.jpg");
        Assert.assertTrue(copied.exists());
    }

    @Test
    public void testGeneratorWithDoubledImage() throws TubainaException, IOException {
        BookBuilder builder = new BookBuilder("Com imagens");
        builder.addReaderFromString("[chapter qualquer um]\n"
                + "[img baseJpgImage.jpg]\n[img baseJpgImage.jpg]");

        Book b = builder.build();
        try {
            generator.generate(b, temp);
        } catch (TubainaException t) {
            Assert.fail("Should not raise an exception");
        }
        // OK
    }

    @Test
    public void testGeneratorWithNonExistantImage() throws TubainaException, IOException {
        BookBuilder builder = new BookBuilder("Com imagens");
        builder.addReaderFromString("[chapter qualquer um]\n"
                + "[img src/test/resources/someImage.gif]");
        try {
            Book b = builder.build();
            generator.generate(b, temp);
            Assert.fail("Should raise an exception");
        } catch (TubainaException t) {
            // OK
        }
    }

    @Test
    public void testGeneratorForInstructorTextbook() throws IOException {
        RegexConfigurator configurator = new RegexConfigurator();
        List<Tag> tags = configurator.read("/regex.properties", "/html.properties");
        LatexParser parser = new LatexParser(tags, true, false);

        File path = new File("src/test/resources");
        ResourceLocator.initialize(path);
        LatexGenerator customGenerator = new LatexGenerator(parser, data);
        BookBuilder builder = new BookBuilder("Do Instrutor");
        builder.addReaderFromString("[chapter com notas]\n"
                + "[note]uma nota para o instrutor[/note]");
        Book b = builder.build(true);
        Assert.assertTrue(b.isInstructorBook());
        customGenerator.generate(b, temp);
        File texFile = new File(temp, "teste.tex");
        Assert.assertTrue("Book file should exist", texFile.exists());
        Assert.assertTrue("Should have INSTRUCTOR TEXTBOOK on the first page",
                containsText(texFile, "INSTRUCTOR TEXTBOOK"));
        Assert.assertTrue("Should display the note",
                containsText(texFile, "uma nota para o instrutor"));
    }

    @Test
    public void testGeneratorForStudentTextbook() throws IOException {
        BookBuilder builder = new BookBuilder("Do Aluno");
        builder.addReaderFromString("[chapter com notas]\n"
                + "[note]uma nota para o instrutor[/note]");
        Book b = builder.build(false);
        Assert.assertFalse(b.isInstructorBook());
        generator.generate(b, temp);
        File texFile = new File(temp, "teste.tex");
        Assert.assertTrue("Book file should exist", texFile.exists());
        Assert.assertFalse("Should not have INSTRUCTOR TEXTBOOK on the first page",
                containsText(texFile, "INSTRUCTOR TEXTBOOK"));
        Assert.assertFalse("Should not display the note",
                containsText(texFile, "uma nota para o instrutor"));
    }

    @SuppressWarnings("unchecked")
    private boolean containsText(File texFile, String text) throws IOException {
        List<String> lines = FileUtils.readLines(texFile);
        boolean containsText = false;
        for (String line : lines) {
            if (line.contains(text))
                containsText = true;
        }
        return containsText;
    }
}
