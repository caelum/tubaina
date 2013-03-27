package br.com.caelum.tubaina.builder;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.BookPart;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.BoxChunk;
import br.com.caelum.tubaina.chunk.CenteredParagraphChunk;
import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.chunk.GistChunk;
import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.chunk.JavaChunk;
import br.com.caelum.tubaina.chunk.ListChunk;
import br.com.caelum.tubaina.chunk.ParagraphChunk;
import br.com.caelum.tubaina.chunk.RubyChunk;
import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.parser.MockedParser;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.resources.ResourceLocator;

public class BookBuilderTest {

    private Parser parser;

    @Before
    public void setUp() {
        parser = new MockedParser();
        ResourceLocator.initialize(".");
    }

    @Test
    public void testBuildSimpleBook() {

        BookBuilder builder = new BookBuilder("livro");

        String content = "[chapter     O que é java?   ]\n" + "texto da seção\n"
                + "[section Primeira seção]\n" + "texto da prim seção\n"
                + "[section Segunda seção]\n" + "texto da segunda seção\n\n";
        
        builder.addReaderFromString(content);
        builder.addReaderFromString("[chapter Introdução]\n"
                + "Algum texto de introdução\n");
        
        Book book = builder.build();
        Assert.assertEquals("livro", book.getName());

        List<Chapter> chapters = book.getChapters();

        Assert.assertEquals(2, chapters.size());
        List<Section> sections1 = chapters.get(0).getSections();

        Assert.assertEquals("O que é java?", chapters.get(0).getTitle());
        Assert.assertEquals(2, sections1.size());

        Assert.assertEquals("Primeira seção", sections1.get(0).getTitle());
        Assert.assertEquals("texto da prim seção", sections1.get(0).getChunks().get(0).asString());

        Assert.assertEquals("Segunda seção", sections1.get(1).getTitle());
        Assert.assertEquals("texto da segunda seção", sections1.get(1).getChunks().get(0)
                .asString());

        Assert.assertEquals("Algum texto de introdução", chapters.get(1).getIntroduction());

    }

    @Test
    public void testChapterWithoutSections() {
        List<Chapter> chapters = getChapters("[chapter     O que é java?   ]");

        Assert.assertEquals(1, chapters.size());
        Assert.assertEquals(0, chapters.get(0).getSections().size());
        Assert.assertEquals("O que é java?", chapters.get(0).getTitle());
    }

    @Test
    public void testChapterWithoutSectionsAndWithIntroduction() {
        List<Chapter> chapters = getChapters("[chapter     O que é java?   ]\n"
                + "texto da introdução");

        List<Section> sections = chapters.get(0).getSections();

        Assert.assertEquals(1, chapters.size());
        Assert.assertEquals("O que é java?", chapters.get(0).getTitle());
        Assert.assertEquals(0, sections.size());
        Assert.assertEquals("texto da introdução", chapters.get(0).getIntroduction());
    }

    @Test
    public void testChapterWithSectionsAndWithIntroduction() {
        List<Chapter> chapters = getChapters("[chapter     O que é java?   ]\n"
                + "texto da introdução\n" + "[section Primeira seção]\n" + "texto da prim seção\n"
                + "[section Segunda seção]\n" + "texto da segunda seção\n\n");

        Assert.assertEquals(1, chapters.size());
        List<Section> sections = chapters.get(0).getSections();
        Assert.assertEquals("O que é java?", chapters.get(0).getTitle());
        Assert.assertEquals(2, sections.size());

        Assert.assertEquals("texto da introdução", chapters.get(0).getIntroduction());

        Assert.assertEquals("Primeira seção", sections.get(0).getTitle());
        Assert.assertEquals("texto da prim seção", sections.get(0).getChunks().get(0).asString());

        Assert.assertEquals("Segunda seção", sections.get(1).getTitle());
        Assert.assertEquals("texto da segunda seção", sections.get(1).getChunks().get(0)
                .asString());
    }

    @Test
    public void testChapterWithSectionsAndWithoutIntroduction() {
        List<Chapter> chapters = getChapters("[chapter     O que é java?   ]\n"
                + "[section Primeira seção]\n" + "texto da prim seção\n"
                + "[section Segunda seção]\n" + "texto da segunda seção\n\n");

        Assert.assertEquals(1, chapters.size());
        List<Section> sections = chapters.get(0).getSections();
        Assert.assertEquals("O que é java?", chapters.get(0).getTitle());
        Assert.assertEquals(2, sections.size());

        Assert.assertEquals("Primeira seção", sections.get(0).getTitle());
        Assert.assertEquals("texto da prim seção", sections.get(0).getChunks().get(0).asString());

        Assert.assertEquals("Segunda seção", sections.get(1).getTitle());
        Assert.assertEquals("texto da segunda seção", sections.get(1).getChunks().get(0)
                .asString());
    }

    @Test(
            expected = TubainaException.class)
    public void testMultiChaptersMustThrowAnException() {
        getChapters("[chapter     O que é java?   ]\n" + "texto da introdução\n"
                + "[section Primeira seção]\n" + "texto da prim seção\n"
                + "[section Segunda seção]\n" + "texto da segunda seção\n\n"
                + "[chapter Introdução]\n" + "Algum texto de introdução\n");

    }

    private List<Chapter> getChapters(final String content) {
        BookBuilder builder = new BookBuilder("livro");
        builder.addReaderFromString(content);
        Book b = builder.build();
        List<Chapter> chapters = b.getChapters();
        return chapters;
    }

    @Test
    public void testParagraphChunk() {
        List<Chapter> chapters = getChapters("[chapter  Capítulo cheio de Chunks]\n"
                + "[section secao]" + "\n\nAlgum texto de parágrafo");

        List<Chunk> chunks = chapters.get(0).getSections().get(0).getChunks();
        Assert.assertEquals(1, chunks.size());

        Assert.assertEquals(ParagraphChunk.class, chunks.get(0).getClass());
        Assert.assertEquals("Algum texto de parágrafo", chunks.get(0).asString());
    }

    @Test
    public void testJavaChunk() {

        List<Chapter> chapters = getChapters("[chapter  Capítulo cheio de Chunks]\n"
                + "[section secao]" + "\n\n[java]\n" + "public class AlgumCodigoJava\n" + "{\n}\n"
                + "[/java]\n\n");

        List<Chunk> chunks = chapters.get(0).getSections().get(0).getChunks();
        Assert.assertEquals(1, chunks.size());

        Assert.assertEquals(JavaChunk.class, chunks.get(0).getClass());
        Assert.assertEquals("\npublic class AlgumCodigoJava\n{\n}\n", chunks.get(0).asString());
    }

    @Test
    public void testBoxChunk() throws Exception {
        List<Chapter> chapters = getChapters("[chapter  Capítulo cheio de Chunks]\n"
                + "[section secao]" + "\n\n[box Alguma coisa]\n" + "Algum corpo de texto\n\n"
                + "[/box]\n\n");

        List<Chunk> chunks = chapters.get(0).getSections().get(0).getChunks();
        Assert.assertEquals(1, chunks.size());

        Assert.assertEquals(BoxChunk.class, chunks.get(0).getClass());
        Assert.assertEquals("Algum corpo de texto", chunks.get(0).asString());

        Field field = BoxChunk.class.getDeclaredField("title");
        field.setAccessible(true);

        String title = (String) field.get(chunks.get(0));
        Assert.assertEquals("Alguma coisa", title);
    }

    @Test
    public void testCodeChunk() {
        List<Chapter> chapters = getChapters("[chapter  Capítulo cheio de Chunks]\n"
                + "[section secao]" + "\n\n[code]\n" + "Algum corpo de texto\n"
                + "que é preformatado\n" + "[/code]\n\n");

        List<Chunk> chunks = chapters.get(0).getSections().get(0).getChunks();
        Assert.assertEquals(1, chunks.size());

        Assert.assertEquals(CodeChunk.class, chunks.get(0).getClass());
        Assert.assertEquals("\nAlgum corpo de texto\nque é preformatado\n", chunks.get(0)
                .asString());
    }

    @Test
    public void testGistsChunk() {
        List<Chapter> chapters = getChapters("[chapter  Capítulo cheio de Chunks]\n"
                + "[section secao]" + "\n\n[gist 1940936]\n\n");

        List<Chunk> chunks = chapters.get(0).getSections().get(0).getChunks();
        Assert.assertEquals(1, chunks.size());

        Assert.assertEquals(GistChunk.class, chunks.get(0).getClass());
        Assert.assertEquals("1940936", chunks.get(0).asString());
    }

    @Test
    public void testRubyChunk() {
        List<Chapter> chapters = getChapters("[chapter  Capítulo cheio de Chunks]\n"
                + "[section secao]" + "\n\n[ruby]\n" + "Algum corpo de texto\n"
                + "que é preformatado\n" + "[/ruby]\n\n");

        List<Chunk> chunks = chapters.get(0).getSections().get(0).getChunks();
        Assert.assertEquals(1, chunks.size());

        Assert.assertEquals(RubyChunk.class, chunks.get(0).getClass());
        Assert.assertEquals("\nAlgum corpo de texto\nque é preformatado\n", chunks.get(0)
                .asString());
    }

    @Test
    public void testImageChunk() throws Exception {
        List<Chapter> chapters = getChapters("[chapter  Capítulo cheio de Chunks]\n"
                + "[section secao]"
                + "\n\n[img   src/test/resources/baseJpgImage.jpg w=30 \"Descrição\"   ]\n\n");

        List<Chunk> chunks = chapters.get(0).getSections().get(0).getChunks();
        Assert.assertEquals(1, chunks.size());

        Assert.assertEquals(ImageChunk.class, chunks.get(0).getClass());
        Assert.assertEquals("src/test/resources/baseJpgImage.jpg", chunks.get(0).asString());

        Field field = ImageChunk.class.getDeclaredField("width");
        field.setAccessible(true);

        double width = field.getDouble(chunks.get(0));
        Assert.assertEquals(627, width, 0.0001);

        Field field2 = ImageChunk.class.getDeclaredField("options");
        field2.setAccessible(true);

        String options = (String) field2.get(chunks.get(0));
        Assert.assertEquals("w=30 \"Descrição\"", options);
    }

    @Test
    public void testListChunk() {
        List<Chapter> chapters = getChapters("[chapter  Capítulo cheio de Chunks]\n"
                + "[section secao]"
                + "\n\n[list]* uma lista\n\n* com alguns itens\n\n * pra ter certeza que funciona[/list]\n\n");

        List<Chunk> chunks = chapters.get(0).getSections().get(0).getChunks();
        Assert.assertEquals(1, chunks.size());

        Assert.assertEquals(ListChunk.class, chunks.get(0).getClass());
        Assert.assertEquals("uma listacom alguns itenspra ter certeza que funciona", chunks.get(0)
                .asString());
    }

    @Test
    public void testTableChunk() {
        List<Chapter> chapters = getChapters("[chapter  Capítulo com tabela]\n"
                + "[section secao]"
                + "\n\n[table][row]\n[col]uma tabela[/col]\n[col]com várias colunas[/col]\n[/row]\n"
                + "[row]\n[col]e várias[/col]\n[col]linhas também[/col]\n[/row]\n[/table]\n");

        List<Chunk> chunks = chapters.get(0).getSections().get(0).getChunks();
        Assert.assertEquals(1, chunks.size());

        Assert.assertEquals(TableChunk.class, chunks.get(0).getClass());
        Assert.assertEquals("uma tabelacom várias colunase váriaslinhas também", chunks.get(0)
                .asString());
    }

    @Test
    public void testCenteredParagraphChunk() {
        List<Chapter> chapters = getChapters("[chapter  Capítulo com texto centralizado]\n"
                + "[section secao]"
                + "\n\n[center]Algum texto centralizado\n\nCom várias linhas[/center]\n\n");

        List<Chunk> chunks = chapters.get(0).getSections().get(0).getChunks();
        Assert.assertEquals(1, chunks.size());

        Assert.assertEquals(CenteredParagraphChunk.class, chunks.get(0).getClass());
        Assert.assertEquals("Algum texto centralizado\n\nCom várias linhas", chunks.get(0)
                .asString());
    }

    @Test
    public void testChunkTypesTogether() throws Exception {
        List<Chapter> chapters = getChapters("[chapter  Capítulo cheio de Chunks]\n"
                + "[section secao]" + "Um chunk de Paragrafo normal\n"
                + "Com um monte de coisas escritas\n" + "Em várias linhas\n\n"
                + "[java] Agora um chunk com código java\n" + "Também multiline\n" + "\n\n"
                + "[/java]\n" + "Mais algum texto que deveria ser chunk de parágrafo\n"
                + "[box Um chunk de box]\n" + "Algo escrito dentro dele\n\n"
                + "Com pseudo-parágrafos [/box]\n\n"
                + "[code] Um monte de código genérico \n[/code][list]* uma lista\n\n*"
                + " com alguns itens\n\n * pra ter certeza que funciona[/list]\n\n"
                + "[table][row]\n[col]uma tabela[/col]\n[col]com várias colunas[/col]\n[/row]\n"
                + "[row]\n[col]e várias[/col]\n[col]linhas também[/col]\n[/row]\n[/table]\n\n"
                + "[center]Algum texto centralizado\n\nCom várias linhas[/center]\n\n");

        Assert.assertEquals(1, chapters.size());

        List<Section> sections = chapters.get(0).getSections();

        Assert.assertEquals("Capítulo cheio de Chunks", chapters.get(0).getTitle());
        Assert.assertEquals(1, sections.size());

        List<Chunk> chunks = sections.get(0).getChunks();

        Assert.assertEquals(8, chunks.size());

        // Primeiro Chunk
        Assert.assertEquals(ParagraphChunk.class, chunks.get(0).getClass());
        Assert.assertEquals("Um chunk de Paragrafo normal\n" + "Com um monte de coisas escritas\n"
                + "Em várias linhas", chunks.get(0).asString());

        // Segundo chunk
        Assert.assertEquals(JavaChunk.class, chunks.get(1).getClass());
        Assert.assertEquals("Agora um chunk com código java\n" + "Também multiline", chunks.get(1)
                .asString().trim());

        // Terceiro Chunk
        Assert.assertEquals(ParagraphChunk.class, chunks.get(2).getClass());
        Assert.assertEquals("Mais algum texto que deveria ser chunk de parágrafo", chunks.get(2)
                .asString());

        // Quarto Chunk
        Assert.assertEquals(BoxChunk.class, chunks.get(3).getClass());
        Assert.assertEquals("Algo escrito dentro dele" + "Com pseudo-parágrafos", chunks.get(3)
                .asString());

        Field field = BoxChunk.class.getDeclaredField("title");
        field.setAccessible(true);

        String title = (String) field.get(chunks.get(3));
        Assert.assertEquals("Um chunk de box", title);

        // Quinto Chunk
        Assert.assertEquals(CodeChunk.class, chunks.get(4).getClass());
        Assert.assertEquals(" Um monte de código genérico \n", chunks.get(4).asString());

        // Sexto Chunk
        Assert.assertEquals(ListChunk.class, chunks.get(5).getClass());
        Assert.assertEquals("uma listacom alguns itenspra ter certeza que funciona", chunks.get(5)
                .asString());

        // Sétimo Chunk
        Assert.assertEquals(TableChunk.class, chunks.get(6).getClass());
        Assert.assertEquals("uma tabelacom várias colunase váriaslinhas também", chunks.get(6)
                .asString());

        // Oitavo Chunk
        Assert.assertEquals(CenteredParagraphChunk.class, chunks.get(7).getClass());
        Assert.assertEquals("Algum texto centralizado\n\nCom várias linhas", chunks.get(7)
                .asString());
    }

    @Test
    public void testBookWithMultipleParts() throws Exception {
        BookBuilder builder = new BookBuilder("livro");
        String content = "[part \"parte um\"]\n" + "[chapter capitulo um]\n"
                + "introducao do capitulo um\n" + "[section secao um]\n" + "conteudo da secao um";
        builder.addReaderFromString(content);
        Book b = builder.build();
        BookPart bookPart = b.getParts().get(0);
        Chapter chapter = bookPart.getChapters().get(0);
        assertEquals("capitulo um", chapter.getTitle());
        assertEquals("secao um", chapter.getSections().get(0).getTitle());

        assertEquals("parte um", bookPart.getTitle());
        assertEquals(true, bookPart.isPrintable());
    }

    @Test
    public void testBookWithChapterOutsideParts() throws Exception {
        BookBuilder builder = new BookBuilder("livro");
        String chapter0 = "[chapter capitulo zero]";
        String chapter1 = "[part \"parte um\"]\n" + "[chapter capitulo um]\n"
                + "introducao do capitulo um\n" + "[section secao um]\n" + "conteudo da secao um";
        builder.addReaderFromStrings(Arrays.asList(chapter0, chapter1));
        Book b = builder.build();
        BookPart bookPart = b.getParts().get(1);
        Chapter chapter = bookPart.getChapters().get(0);
        assertEquals("capitulo um", chapter.getTitle());
        assertEquals("secao um", chapter.getSections().get(0).getTitle());

        assertEquals("parte um", bookPart.getTitle());
        assertEquals(true, bookPart.isPrintable());

        assertEquals(false, b.getParts().get(0).isPrintable());
    }

    @Test
    public void testBookWithIntroductionChapters() throws Exception {
        BookBuilder builder = new BookBuilder("livro");
        String chapter = "[chapter chatper zero]\n" + "Some text";
        String preface = "[chapter preface]\n" + "Some preface text";
        String about = "[chapter about]\n" + "About the authors";
        List<String> chapters = Arrays.asList(chapter);
        List<String> introductionChapters = Arrays.asList(preface, about);
        builder.addReaderFromStrings(chapters);
        builder.addAllReadersOfNonNumberedFromStrings(introductionChapters);

        Book book = builder.build();
        assertEquals(2, book.getIntroductionChapters().size());
        assertEquals("preface", book.getIntroductionChapters().get(0).getTitle());
        assertEquals("about", book.getIntroductionChapters().get(1).getTitle());
    }

    @Test
    public void testBookWithPartsWithIllustrations() throws Exception {
        BookBuilder builder = new BookBuilder("livro");
        String content = "[part \"parte um\" illustration=resources/image.png]\n" + "[chapter capitulo um]\n"
                + "introducao do capitulo um\n" + "[section secao um]\n" + "conteudo da secao um";
        builder.addReaderFromString(content);
        Book b = builder.build();
        BookPart bookPart = b.getParts().get(0);
        Chapter chapter = bookPart.getChapters().get(0);
        assertEquals("capitulo um", chapter.getTitle());
        assertEquals("secao um", chapter.getSections().get(0).getTitle());

        assertEquals("parte um", bookPart.getTitle());
        assertEquals(true, bookPart.isPrintable());
    }
    
    @Test
    public void testBookWithChapterWithLabel() throws Exception {
        BookBuilder builder = new BookBuilder("livro");
        String content = "[chapter capitulo um label=\"label of this chapter\"]\n"
                + "introducao do capitulo um\n" + "[section secao um]\n" + "conteudo da secao um";
        builder.addReaderFromString(content);
        Book b = builder.build();
        BookPart bookPart = b.getParts().get(0);
        Chapter chapter = bookPart.getChapters().get(0);
        assertEquals("capitulo um", chapter.getTitle());
        assertEquals("label of this chapter", chapter.getLabel());
    }
    
}
