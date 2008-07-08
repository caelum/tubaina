package br.com.caelum.tubaina.builder;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.chunk.BoxChunk;
import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.chunk.JavaChunk;
import br.com.caelum.tubaina.chunk.ListChunk;
import br.com.caelum.tubaina.chunk.ParagraphChunk;
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

		builder.add(new StringReader("[chapter     O que é java?   ]\n" + "texto da seção\n"
				+ "[section Primeira seção]\n" + "texto da prim seção\n" + "[section Segunda seção]\n"
				+ "texto da segunda seção\n\n"));

		builder.add(new StringReader("[chapter Introdução]\n" + "Algum texto de introdução\n"));
		Book book = builder.build();
		Assert.assertEquals("livro", book.getName());

		List<Chapter> chapters = book.getChapters();

		Assert.assertEquals(2, chapters.size());
		List<Section> sections1 = chapters.get(0).getSections();

		Assert.assertEquals("O que é java?", chapters.get(0).getTitle());
		Assert.assertEquals(2, sections1.size());

		Assert.assertEquals("Primeira seção", sections1.get(0).getTitle());
		Assert.assertEquals("texto da prim seção", sections1.get(0).getChunks().get(0).getContent(parser));

		Assert.assertEquals("Segunda seção", sections1.get(1).getTitle());
		Assert.assertEquals("texto da segunda seção", sections1.get(1).getChunks().get(0).getContent(parser));

		Assert.assertEquals("Algum texto de introdução", chapters.get(1).getIntroduction(parser));

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
		List<Chapter> chapters = getChapters("[chapter     O que é java?   ]\n" + "texto da introdução");

		List<Section> sections = chapters.get(0).getSections();

		Assert.assertEquals(1, chapters.size());
		Assert.assertEquals("O que é java?", chapters.get(0).getTitle());
		Assert.assertEquals(0, sections.size());
		Assert.assertEquals("texto da introdução", chapters.get(0).getIntroduction(parser));
	}

	@Test
	public void testChapterWithSectionsAndWithIntroduction() {
		List<Chapter> chapters = getChapters("[chapter     O que é java?   ]\n" + "texto da introdução\n"
				+ "[section Primeira seção]\n" + "texto da prim seção\n" + "[section Segunda seção]\n"
				+ "texto da segunda seção\n\n");

		Assert.assertEquals(1, chapters.size());
		List<Section> sections = chapters.get(0).getSections();
		Assert.assertEquals("O que é java?", chapters.get(0).getTitle());
		Assert.assertEquals(2, sections.size());

		Assert.assertEquals("texto da introdução", chapters.get(0).getIntroduction(parser));

		Assert.assertEquals("Primeira seção", sections.get(0).getTitle());
		Assert.assertEquals("texto da prim seção", sections.get(0).getChunks().get(0).getContent(parser));

		Assert.assertEquals("Segunda seção", sections.get(1).getTitle());
		Assert.assertEquals("texto da segunda seção", sections.get(1).getChunks().get(0).getContent(parser));
	}

	@Test
	public void testChapterWithSectionsAndWithoutIntroduction() {
		List<Chapter> chapters = getChapters("[chapter     O que é java?   ]\n" + "[section Primeira seção]\n"
				+ "texto da prim seção\n" + "[section Segunda seção]\n" + "texto da segunda seção\n\n");

		Assert.assertEquals(1, chapters.size());
		List<Section> sections = chapters.get(0).getSections();
		Assert.assertEquals("O que é java?", chapters.get(0).getTitle());
		Assert.assertEquals(2, sections.size());

		Assert.assertEquals("Primeira seção", sections.get(0).getTitle());
		Assert.assertEquals("texto da prim seção", sections.get(0).getChunks().get(0).getContent(parser));

		Assert.assertEquals("Segunda seção", sections.get(1).getTitle());
		Assert.assertEquals("texto da segunda seção", sections.get(1).getChunks().get(0).getContent(parser));
	}

	@Test
	public void testMultiChapters() {
		List<Chapter> chapters = getChapters("[chapter     O que é java?   ]\n" + "texto da introdução\n"
				+ "[section Primeira seção]\n" + "texto da prim seção\n" + "[section Segunda seção]\n"
				+ "texto da segunda seção\n\n" + "[chapter Introdução]\n" + "Algum texto de introdução\n");

		Assert.assertEquals(2, chapters.size());
		List<Section> sections1 = chapters.get(0).getSections();

		Assert.assertEquals("O que é java?", chapters.get(0).getTitle());
		Assert.assertEquals(2, sections1.size());

		Assert.assertEquals("texto da introdução", chapters.get(0).getIntroduction(parser));

		Assert.assertEquals("Primeira seção", sections1.get(0).getTitle());
		Assert.assertEquals("texto da prim seção", sections1.get(0).getChunks().get(0).getContent(parser));

		Assert.assertEquals("Segunda seção", sections1.get(1).getTitle());
		Assert.assertEquals("texto da segunda seção", sections1.get(1).getChunks().get(0).getContent(parser));

		Assert.assertEquals("Algum texto de introdução", chapters.get(1).getIntroduction(parser));
	}

	private List<Chapter> getChapters(final String string) {
		BookBuilder builder = new BookBuilder("livro");
		builder.add(new StringReader(string));
		Book b = builder.build();
		List<Chapter> chapters = b.getChapters();
		return chapters;
	}

	@Test
	public void testParagraphChunk() {
		List<Chapter> chapters = getChapters("[chapter  Capítulo cheio de Chunks]\n" + "[section secao]"
				+ "\n\nAlgum texto de parágrafo");

		List<Chunk> chunks = chapters.get(0).getSections().get(0).getChunks();
		Assert.assertEquals(1, chunks.size());

		Assert.assertEquals(ParagraphChunk.class, chunks.get(0).getClass());
		Assert.assertEquals("Algum texto de parágrafo", chunks.get(0).getContent(parser));
	}

	@Test
	public void testJavaChunk() {

		List<Chapter> chapters = getChapters("[chapter  Capítulo cheio de Chunks]\n" + "[section secao]"
				+ "\n\n[java]\n" + "public class AlgumCodigoJava\n" + "{\n}\n" + "[/java]\n\n");

		List<Chunk> chunks = chapters.get(0).getSections().get(0).getChunks();
		Assert.assertEquals(1, chunks.size());

		Assert.assertEquals(JavaChunk.class, chunks.get(0).getClass());
		Assert.assertEquals("\npublic class AlgumCodigoJava\n{\n}\n", chunks.get(0).getContent(parser));
	}

	@Test
	public void testBoxChunk() throws Exception {
		List<Chapter> chapters = getChapters("[chapter  Capítulo cheio de Chunks]\n" + "[section secao]"
				+ "\n\n[box Alguma coisa]\n" + "Algum corpo de texto\n\n" + "[/box]\n\n");

		List<Chunk> chunks = chapters.get(0).getSections().get(0).getChunks();
		Assert.assertEquals(1, chunks.size());

		Assert.assertEquals(BoxChunk.class, chunks.get(0).getClass());
		Assert.assertEquals("Algum corpo de texto", chunks.get(0).getContent(parser));

		Field field = BoxChunk.class.getDeclaredField("title");
		field.setAccessible(true);

		String title = (String) field.get(chunks.get(0));
		Assert.assertEquals("Alguma coisa", title);
	}

	@Test
	public void testCodeChunk() {
		List<Chapter> chapters = getChapters("[chapter  Capítulo cheio de Chunks]\n" + "[section secao]"
				+ "\n\n[code]\n" + "Algum corpo de texto\n" + "que é preformatado\n" + "[/code]\n\n");

		List<Chunk> chunks = chapters.get(0).getSections().get(0).getChunks();
		Assert.assertEquals(1, chunks.size());

		Assert.assertEquals(CodeChunk.class, chunks.get(0).getClass());
		Assert.assertEquals("\nAlgum corpo de texto\nque é preformatado\n", chunks.get(0).getContent(parser));
	}

	@Test
	public void testImageChunk() throws Exception {
		List<Chapter> chapters = getChapters("[chapter  Capítulo cheio de Chunks]\n" + "[section secao]"
				+ "\n\n[img   src/test/resources/baseJpgImage.jpg w=30 \"Descrição\"   ]\n\n");

		List<Chunk> chunks = chapters.get(0).getSections().get(0).getChunks();
		Assert.assertEquals(1, chunks.size());

		Assert.assertEquals(ImageChunk.class, chunks.get(0).getClass());
		Assert.assertEquals("src/test/resources/baseJpgImage.jpg", chunks.get(0).getContent(parser));

		Field field = ImageChunk.class.getDeclaredField("width");
		field.setAccessible(true);

		int width = field.getInt(chunks.get(0));
		Assert.assertEquals(627, width);

		Field field2 = ImageChunk.class.getDeclaredField("options");
		field2.setAccessible(true);

		String options = (String) field2.get(chunks.get(0));
		Assert.assertEquals("w=30 \"Descrição\"", options);
	}

	@Test
	public void testListChunk() {
		List<Chapter> chapters = getChapters("[chapter  Capítulo cheio de Chunks]\n" + "[section secao]"
				+ "\n\n[list]* uma lista\n\n* com alguns itens\n\n * pra ter certeza que funciona[/list]\n\n");

		List<Chunk> chunks = chapters.get(0).getSections().get(0).getChunks();
		Assert.assertEquals(1, chunks.size());

		Assert.assertEquals(ListChunk.class, chunks.get(0).getClass());
		Assert.assertEquals("uma listacom alguns itenspra ter certeza que funciona", chunks.get(0).getContent(parser));
	}

	@Test
	public void testChunkTypesTogether() throws Exception {
		List<Chapter> chapters = getChapters("[chapter  Capítulo cheio de Chunks]\n" + "[section secao]"
				+ "Um chunk de Paragrafo normal\n" + "Com um monte de coisas escritas\n" + "Em várias linhas\n\n"
				+ "[java] Agora um chunk com código java\n" + "Também multiline\n" + "\n\n" + "[/java]\n"
				+ "Mais algum texto que deveria ser chunk de parágrafo\n" + "[box Um chunk de box]\n"
				+ "Algo escrito dentro dele\n\n" + "Com pseudo-parágrafos [/box]\n\n"
				+ "[code] Um monte de código genérico \n[/code][list]* uma lista\n\n*"
				+ " com alguns itens\n\n * pra ter certeza que funciona[/list]\n\n");

		Assert.assertEquals(1, chapters.size());

		List<Section> sections = chapters.get(0).getSections();

		Assert.assertEquals("Capítulo cheio de Chunks", chapters.get(0).getTitle());
		Assert.assertEquals(1, sections.size());

		List<Chunk> chunks = sections.get(0).getChunks();

		Assert.assertEquals(6, chunks.size());

		// Primeiro Chunk
		Assert.assertEquals(ParagraphChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(
				"Um chunk de Paragrafo normal\n" + "Com um monte de coisas escritas\n" + "Em várias linhas", chunks
						.get(0).getContent(parser));

		// Segundo chunk
		Assert.assertEquals(JavaChunk.class, chunks.get(1).getClass());
		Assert.assertEquals("Agora um chunk com código java\n" + "Também multiline", chunks.get(1).getContent(parser)
				.trim());

		// Terceiro Chunk
		Assert.assertEquals(ParagraphChunk.class, chunks.get(2).getClass());
		Assert.assertEquals("Mais algum texto que deveria ser chunk de parágrafo", chunks.get(2).getContent(parser));

		// Quarto Chunk
		Assert.assertEquals(BoxChunk.class, chunks.get(3).getClass());
		Assert.assertEquals("Algo escrito dentro dele" + "Com pseudo-parágrafos", chunks.get(3).getContent(parser));

		Field field = BoxChunk.class.getDeclaredField("title");
		field.setAccessible(true);

		String title = (String) field.get(chunks.get(3));
		Assert.assertEquals("Um chunk de box", title);

		// Quinto Chunk
		Assert.assertEquals(CodeChunk.class, chunks.get(4).getClass());
		Assert.assertEquals(" Um monte de código genérico \n", chunks.get(4).getContent(parser));

		// Sexto Chunk
		Assert.assertEquals(ListChunk.class, chunks.get(5).getClass());
		Assert.assertEquals("uma listacom alguns itenspra ter certeza que funciona", chunks.get(5).getContent(parser));
	}

}
