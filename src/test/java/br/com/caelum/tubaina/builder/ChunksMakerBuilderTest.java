package br.com.caelum.tubaina.builder;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.AnswerChunk;
import br.com.caelum.tubaina.chunk.BoxChunk;
import br.com.caelum.tubaina.chunk.CenteredParagraphChunk;
import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.chunk.ExerciseChunk;
import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.chunk.JavaChunk;
import br.com.caelum.tubaina.chunk.ListChunk;
import br.com.caelum.tubaina.chunk.NoteChunk;
import br.com.caelum.tubaina.chunk.ParagraphChunk;
import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.chunk.XmlChunk;
import br.com.caelum.tubaina.resources.Resource;
import br.com.caelum.tubaina.resources.ResourceLocator;

public class ChunksMakerBuilderTest {

	private String chunkString;

	private List<Resource> resources;

	@Before
	public void setUp() {
		chunkString = "[box title]conteudo[/box]" + "[code]codigo[/code]"
				+ "[exercise][question]pergunta[answer]resposta[/answer][/question][/exercise]"
				+ "[img src/test/resources/baseJpgImage.jpg]" + "[java] class [/java]"
				+ "[list]*texto\n* blablabla\n[/list]" + "[note]nota[/note]" + "[xml]<xml>[/xml]\n\n"
				+ "finalmente um paragrafo" + "[ruby]algum codigo ruby[/ruby]"
				+ "[table][row][col]uma celula[/col][/row][/table]"
				+ "[center]um texto centralizado[/center]";
		resources = new ArrayList<Resource>();
		ResourceLocator.initialize(".");

	}

	@Test
	public void testChunksMakerBuilderForAnswer() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build("answer");
		List<Chunk> chunks = maker.make(chunkString);
		Assert.assertEquals(14, chunks.size());
		Assert.assertEquals(BoxChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(1).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(2).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(3).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(4).getClass());
		Assert.assertEquals(ImageChunk.class, chunks.get(5).getClass());
		Assert.assertEquals(JavaChunk.class, chunks.get(6).getClass());
		Assert.assertEquals(ListChunk.class, chunks.get(7).getClass());
		Assert.assertEquals(NoteChunk.class, chunks.get(8).getClass());
		Assert.assertEquals(XmlChunk.class, chunks.get(9).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(10).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(11).getClass());
		Assert.assertEquals(TableChunk.class, chunks.get(12).getClass());
		Assert.assertEquals(CenteredParagraphChunk.class, chunks.get(13).getClass());
	}

	@Test
	public void testChunksMakerBuilderForBox() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build("box");
		List<Chunk> chunks = maker.make(chunkString);
		Assert.assertEquals(14, chunks.size());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(1).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(2).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(3).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(4).getClass());
		Assert.assertEquals(ImageChunk.class, chunks.get(5).getClass());
		Assert.assertEquals(JavaChunk.class, chunks.get(6).getClass());
		Assert.assertEquals(ListChunk.class, chunks.get(7).getClass());
		Assert.assertEquals(NoteChunk.class, chunks.get(8).getClass());
		Assert.assertEquals(XmlChunk.class, chunks.get(9).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(10).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(11).getClass());
		Assert.assertEquals(TableChunk.class, chunks.get(12).getClass());
		Assert.assertEquals(CenteredParagraphChunk.class, chunks.get(13).getClass());
	}

	@Test
	public void testChunksMakerBuilderForExercise() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build("exercise");
		try {
			maker.make(chunkString);
			Assert.fail();
		} catch (TubainaException e) {
			// OK
		}

		// Assert.assertEquals(11, chunks.size());
		// Assert.assertEquals(ParagraphChunk.class, chunks.get(0).getClass());
		// Assert.assertEquals(ParagraphChunk.class, chunks.get(1).getClass());
		// Assert.assertEquals(ParagraphChunk.class, chunks.get(2).getClass());
		// Assert.assertEquals(QuestionChunk.class, chunks.get(3).getClass());
		// Assert.assertEquals(ParagraphChunk.class, chunks.get(4).getClass());
		// Assert.assertEquals(ParagraphChunk.class, chunks.get(5).getClass());
		// Assert.assertEquals(ParagraphChunk.class, chunks.get(6).getClass());
		// Assert.assertEquals(ParagraphChunk.class, chunks.get(7).getClass());
		// Assert.assertEquals(NoteChunk.class, chunks.get(8).getClass());
		// Assert.assertEquals(ParagraphChunk.class, chunks.get(9).getClass());
		// Assert.assertEquals(ParagraphChunk.class, chunks.get(10).getClass());
	}

	@Test
	public void testChunksMakerBuilderForItem() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build("item");
		List<Chunk> chunks = maker.make(chunkString);

		Assert.assertEquals(13, chunks.size());
		Assert.assertEquals(BoxChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(1).getClass());
		Assert.assertEquals(ExerciseChunk.class, chunks.get(2).getClass());
		Assert.assertEquals(ImageChunk.class, chunks.get(3).getClass());
		Assert.assertEquals(JavaChunk.class, chunks.get(4).getClass());
		Assert.assertEquals(ListChunk.class, chunks.get(5).getClass());
		Assert.assertEquals(NoteChunk.class, chunks.get(6).getClass());
		Assert.assertEquals(XmlChunk.class, chunks.get(7).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(8).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(9).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(10).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(11).getClass());
		Assert.assertEquals(CenteredParagraphChunk.class, chunks.get(12).getClass());
	}

	@Test
	public void testChunksMakerBuilderForList() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build("list");
		try {
			maker.make(chunkString);
			Assert.fail();
		} catch (TubainaException e) {
			// OK
		}

		// Assert.assertEquals(1, chunks.size());
		// Assert.assertEquals(ParagraphChunk.class, chunks.get(0).getClass());
	}

	@Test
	public void testChunksMakerBuilderForNote() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build("note");
		List<Chunk> chunks = maker.make(chunkString);
		Assert.assertEquals(14, chunks.size());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(1).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(2).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(3).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(4).getClass());
		Assert.assertEquals(ImageChunk.class, chunks.get(5).getClass());
		Assert.assertEquals(JavaChunk.class, chunks.get(6).getClass());
		Assert.assertEquals(ListChunk.class, chunks.get(7).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(8).getClass());
		Assert.assertEquals(XmlChunk.class, chunks.get(9).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(10).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(11).getClass());
		Assert.assertEquals(TableChunk.class, chunks.get(12).getClass());
		Assert.assertEquals(CenteredParagraphChunk.class, chunks.get(13).getClass());
	}

	@Test
	public void testChunksMakerBuilderForQuestion() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build("question");
		List<Chunk> chunks = maker.make(chunkString);

		Assert.assertEquals(15, chunks.size());
		Assert.assertEquals(BoxChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(1).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(2).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(3).getClass());
		Assert.assertEquals(AnswerChunk.class, chunks.get(4).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(5).getClass());
		Assert.assertEquals(ImageChunk.class, chunks.get(6).getClass());
		Assert.assertEquals(JavaChunk.class, chunks.get(7).getClass());
		Assert.assertEquals(ListChunk.class, chunks.get(8).getClass());
		Assert.assertEquals(NoteChunk.class, chunks.get(9).getClass());
		Assert.assertEquals(XmlChunk.class, chunks.get(10).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(11).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(12).getClass());
		Assert.assertEquals(TableChunk.class, chunks.get(13).getClass());
		Assert.assertEquals(CenteredParagraphChunk.class, chunks.get(14).getClass());
	}

	@Test
	public void testChunksMakerBuilderForAll() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build("all");
		List<Chunk> chunks = maker.make(chunkString);

		// for (Chunk chunk : chunks) {
		// System.out.println(chunk.getClass());
		// }

		Assert.assertEquals(12, chunks.size());
		Assert.assertEquals(BoxChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(1).getClass());
		Assert.assertEquals(ExerciseChunk.class, chunks.get(2).getClass());
		Assert.assertEquals(ImageChunk.class, chunks.get(3).getClass());
		Assert.assertEquals(JavaChunk.class, chunks.get(4).getClass());
		Assert.assertEquals(ListChunk.class, chunks.get(5).getClass());
		Assert.assertEquals(NoteChunk.class, chunks.get(6).getClass());
		Assert.assertEquals(XmlChunk.class, chunks.get(7).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(8).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(9).getClass());
		Assert.assertEquals(TableChunk.class, chunks.get(10).getClass());
		Assert.assertEquals(CenteredParagraphChunk.class, chunks.get(11).getClass());
	}
}
