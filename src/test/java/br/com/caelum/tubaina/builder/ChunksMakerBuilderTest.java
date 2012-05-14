package br.com.caelum.tubaina.builder;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.builder.replacer.ReplacerType;
import br.com.caelum.tubaina.chunk.AnswerChunk;
import br.com.caelum.tubaina.chunk.BoxChunk;
import br.com.caelum.tubaina.chunk.CenteredParagraphChunk;
import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.chunk.ExerciseChunk;
import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.chunk.IndexChunk;
import br.com.caelum.tubaina.chunk.ItemChunk;
import br.com.caelum.tubaina.chunk.JavaChunk;
import br.com.caelum.tubaina.chunk.ListChunk;
import br.com.caelum.tubaina.chunk.NoteChunk;
import br.com.caelum.tubaina.chunk.ParagraphChunk;
import br.com.caelum.tubaina.chunk.ParagraphInsideItemChunk;
import br.com.caelum.tubaina.chunk.QuestionChunk;
import br.com.caelum.tubaina.chunk.RubyChunk;
import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.chunk.TableColumnChunk;
import br.com.caelum.tubaina.chunk.TableRowChunk;
import br.com.caelum.tubaina.chunk.TodoChunk;
import br.com.caelum.tubaina.chunk.XmlChunk;
import br.com.caelum.tubaina.resources.Resource;
import br.com.caelum.tubaina.resources.ResourceLocator;

public class ChunksMakerBuilderTest {


	private List<Resource> resources;

	private String exampleBox = "[box title]a box[/box]\n";
	private String exampleParagraph = "some text\n\n";
	private String exampleCode = "[code]some code[/code]\n";
	private String exampleJava = "[java]\nSystem.out.println(\"some java code\");\n[/java]\n";
	private String exampleListItem = "* an item\n";
	private String exampleList = "[list]\n" + exampleListItem + exampleListItem
			+ "[/list]\n";
	private String exampleNote = "[note]a note to the instructor[/note]\n";
	private String exampleXml = "[xml]<tag>xml</tag>[/xml]\n";
	private String exampleIndex = "[index an index]\n";
	private String exampleTodo = "[todo something to do]\n";
	private String exampleRuby = "[ruby]\nputs 'some ruby code'\n[/ruby]\n";
	private String exampleTableColumn = "[col]a column[/col]\n";
	private String exampleTableRow = "[row]\n" + exampleTableColumn
			+ exampleTableColumn + "[/row]\n";
	private String exampleTable = "[table]" + exampleTableRow + exampleTableRow
			+ "[/table]\n";
	private String exampleCenteredText = "[center]some centered text[/center]\n";
	private String exampleAnswer = "[answer]42[/answer]\n";
	private String exampleQuestion = "[question]\na question\n" + exampleAnswer
			+ "[/question]\n";
	private String exampleExercise = "[exercise]\n" + exampleQuestion
			+ "[/exercise]\n";
	private String exampleImage = "[img src/test/resources/baseJpgImage.jpg]\n";

	@Before
	public void setUp() {
		resources = new ArrayList<Resource>();
		ResourceLocator.initialize(".");
	}

	@Test
	public void testChunksMakerBuilderForAnswer() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build(ReplacerType.ANSWER);
		String text = exampleBox + exampleCode + exampleImage + exampleJava
				+ exampleList + exampleNote + exampleXml + exampleIndex
				+ exampleTodo + exampleRuby + exampleTable
				+ exampleCenteredText + exampleParagraph;
		List<Chunk> chunks = maker.make(text);
		Assert.assertEquals(13, chunks.size());
		Assert.assertEquals(BoxChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(1).getClass());
		Assert.assertEquals(ImageChunk.class, chunks.get(2).getClass());
		Assert.assertEquals(JavaChunk.class, chunks.get(3).getClass());
		Assert.assertEquals(ListChunk.class, chunks.get(4).getClass());
		Assert.assertEquals(NoteChunk.class, chunks.get(5).getClass());
		Assert.assertEquals(XmlChunk.class, chunks.get(6).getClass());
		Assert.assertEquals(IndexChunk.class, chunks.get(7).getClass());
		Assert.assertEquals(TodoChunk.class, chunks.get(8).getClass());
		Assert.assertEquals(RubyChunk.class, chunks.get(9).getClass());
		Assert.assertEquals(TableChunk.class, chunks.get(10).getClass());
		Assert.assertEquals(CenteredParagraphChunk.class, chunks.get(11)
				.getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(12).getClass());
	}

	@Test
	public void testChunksMakerBuilderForBox() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build(ReplacerType.BOX);
		String text = exampleCode + exampleImage + exampleJava + exampleList
				+ exampleNote + exampleXml + exampleIndex + exampleTodo
				+ exampleRuby + exampleTable + exampleCenteredText
				+ exampleParagraph;
		List<Chunk> chunks = maker.make(text);
		Assert.assertEquals(12, chunks.size());
		Assert.assertEquals(CodeChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(ImageChunk.class, chunks.get(1).getClass());
		Assert.assertEquals(JavaChunk.class, chunks.get(2).getClass());
		Assert.assertEquals(ListChunk.class, chunks.get(3).getClass());
		Assert.assertEquals(NoteChunk.class, chunks.get(4).getClass());
		Assert.assertEquals(XmlChunk.class, chunks.get(5).getClass());
		Assert.assertEquals(IndexChunk.class, chunks.get(6).getClass());
		Assert.assertEquals(TodoChunk.class, chunks.get(7).getClass());
		Assert.assertEquals(RubyChunk.class, chunks.get(8).getClass());
		Assert.assertEquals(TableChunk.class, chunks.get(9).getClass());
		Assert.assertEquals(CenteredParagraphChunk.class, chunks.get(10)
				.getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(11).getClass());
	}

	@Test
	public void testChunksMakerBuilderForExercise() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build(ReplacerType.EXERCISE);
		String text = exampleTodo + exampleQuestion;
		List<Chunk> chunks = maker.make(text);
		Assert.assertEquals(2, chunks.size());
		Assert.assertEquals(TodoChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(QuestionChunk.class, chunks.get(1).getClass());
	}

	@Test
	public void testChunksMakerBuilderForItem() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build(ReplacerType.ITEM);
		String text = exampleBox + exampleCode + exampleExercise + exampleImage
				+ exampleJava + exampleList + exampleNote + exampleXml
				+ exampleIndex + exampleTodo + exampleRuby
				+ exampleCenteredText + exampleParagraph;
		List<Chunk> chunks = maker.make(text);
		Assert.assertEquals(13, chunks.size());
		Assert.assertEquals(BoxChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(1).getClass());
		Assert.assertEquals(ExerciseChunk.class, chunks.get(2).getClass());
		Assert.assertEquals(ImageChunk.class, chunks.get(3).getClass());
		Assert.assertEquals(JavaChunk.class, chunks.get(4).getClass());
		Assert.assertEquals(ListChunk.class, chunks.get(5).getClass());
		Assert.assertEquals(NoteChunk.class, chunks.get(6).getClass());
		Assert.assertEquals(XmlChunk.class, chunks.get(7).getClass());
		Assert.assertEquals(IndexChunk.class, chunks.get(8).getClass());
		Assert.assertEquals(TodoChunk.class, chunks.get(9).getClass());
		Assert.assertEquals(RubyChunk.class, chunks.get(10).getClass());
		Assert.assertEquals(CenteredParagraphChunk.class, chunks.get(11)
				.getClass());
		Assert.assertEquals(ParagraphInsideItemChunk.class, chunks.get(12).getClass());
	}

	@Test
	public void testChunksMakerBuilderForList() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build(ReplacerType.LIST);
		String text = exampleListItem;
		List<Chunk> chunks = maker.make(text);
		Assert.assertEquals(1, chunks.size());
		Assert.assertEquals(ItemChunk.class, chunks.get(0).getClass());
	}

	@Test
	public void testChunksMakerBuilderForNote() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build(ReplacerType.NOTE);
		String text = exampleCode + exampleImage + exampleJava + exampleList
				+ exampleXml + exampleIndex + exampleTodo + exampleRuby
				+ exampleTable + exampleCenteredText + exampleParagraph;
		List<Chunk> chunks = maker.make(text);
		Assert.assertEquals(11, chunks.size());
		Assert.assertEquals(CodeChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(ImageChunk.class, chunks.get(1).getClass());
		Assert.assertEquals(JavaChunk.class, chunks.get(2).getClass());
		Assert.assertEquals(ListChunk.class, chunks.get(3).getClass());
		Assert.assertEquals(XmlChunk.class, chunks.get(4).getClass());
		Assert.assertEquals(IndexChunk.class, chunks.get(5).getClass());
		Assert.assertEquals(TodoChunk.class, chunks.get(6).getClass());
		Assert.assertEquals(RubyChunk.class, chunks.get(7).getClass());
		Assert.assertEquals(TableChunk.class, chunks.get(8).getClass());
		Assert.assertEquals(CenteredParagraphChunk.class, chunks.get(9)
				.getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(10).getClass());
	}

	@Test
	public void testChunksMakerBuilderForQuestion() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build(ReplacerType.QUESTION);
		String text = exampleAnswer + exampleBox + exampleCode + exampleImage
				+ exampleJava + exampleList + exampleNote + exampleXml
				+ exampleIndex + exampleTodo + exampleRuby + exampleTable
				+ exampleCenteredText + exampleParagraph;
		List<Chunk> chunks = maker.make(text);
		Assert.assertEquals(14, chunks.size());
		Assert.assertEquals(AnswerChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(BoxChunk.class, chunks.get(1).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(2).getClass());
		Assert.assertEquals(ImageChunk.class, chunks.get(3).getClass());
		Assert.assertEquals(JavaChunk.class, chunks.get(4).getClass());
		Assert.assertEquals(ListChunk.class, chunks.get(5).getClass());
		Assert.assertEquals(NoteChunk.class, chunks.get(6).getClass());
		Assert.assertEquals(XmlChunk.class, chunks.get(7).getClass());
		Assert.assertEquals(IndexChunk.class, chunks.get(8).getClass());
		Assert.assertEquals(TodoChunk.class, chunks.get(9).getClass());
		Assert.assertEquals(RubyChunk.class, chunks.get(10).getClass());
		Assert.assertEquals(TableChunk.class, chunks.get(11).getClass());
		Assert.assertEquals(CenteredParagraphChunk.class, chunks.get(12)
				.getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(13).getClass());
	}

	@Test
	public void testChunksMakerBuilderForTable() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build(ReplacerType.TABLE);
		String text = exampleTableRow + exampleTodo;
		List<Chunk> chunks = maker.make(text);
		Assert.assertEquals(2, chunks.size());
		Assert.assertEquals(TableRowChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(TodoChunk.class, chunks.get(1).getClass());
	}

	@Test
	public void testChunksMakerBuilderForTableRow() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build(ReplacerType.ROW);
		String text = exampleTableColumn + exampleTodo;
		List<Chunk> chunks = maker.make(text);
		Assert.assertEquals(2, chunks.size());
		Assert.assertEquals(TableColumnChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(TodoChunk.class, chunks.get(1).getClass());
	}

	@Test
	public void testChunksMakerBuilderForTableColumn() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build(ReplacerType.COLUMN);
		String text = exampleBox + exampleCode + exampleExercise + exampleImage
				+ exampleJava + exampleList + exampleNote + exampleXml
				+ exampleTodo + exampleRuby + exampleParagraph;
		List<Chunk> chunks = maker.make(text);
		Assert.assertEquals(11, chunks.size());
		Assert.assertEquals(BoxChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(1).getClass());
		Assert.assertEquals(ExerciseChunk.class, chunks.get(2).getClass());
		Assert.assertEquals(ImageChunk.class, chunks.get(3).getClass());
		Assert.assertEquals(JavaChunk.class, chunks.get(4).getClass());
		Assert.assertEquals(ListChunk.class, chunks.get(5).getClass());
		Assert.assertEquals(NoteChunk.class, chunks.get(6).getClass());
		Assert.assertEquals(XmlChunk.class, chunks.get(7).getClass());
		Assert.assertEquals(TodoChunk.class, chunks.get(8).getClass());
		Assert.assertEquals(RubyChunk.class, chunks.get(9).getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(10).getClass());
	}

	@Test
	public void testChunksMakerBuilderForAll() {
		ChunksMaker maker = new ChunksMakerBuilder(resources).build(ReplacerType.ALL);
		String text = exampleBox + exampleCode + exampleExercise + exampleImage
				+ exampleJava + exampleList + exampleNote + exampleXml
				+ exampleIndex + exampleTodo + exampleRuby + exampleTable
				+ exampleCenteredText + exampleParagraph;
		List<Chunk> chunks = maker.make(text);
		Assert.assertEquals(14, chunks.size());
		Assert.assertEquals(BoxChunk.class, chunks.get(0).getClass());
		Assert.assertEquals(CodeChunk.class, chunks.get(1).getClass());
		Assert.assertEquals(ExerciseChunk.class, chunks.get(2).getClass());
		Assert.assertEquals(ImageChunk.class, chunks.get(3).getClass());
		Assert.assertEquals(JavaChunk.class, chunks.get(4).getClass());
		Assert.assertEquals(ListChunk.class, chunks.get(5).getClass());
		Assert.assertEquals(NoteChunk.class, chunks.get(6).getClass());
		Assert.assertEquals(XmlChunk.class, chunks.get(7).getClass());
		Assert.assertEquals(IndexChunk.class, chunks.get(8).getClass());
		Assert.assertEquals(TodoChunk.class, chunks.get(9).getClass());
		Assert.assertEquals(RubyChunk.class, chunks.get(10).getClass());
		Assert.assertEquals(TableChunk.class, chunks.get(11).getClass());
		Assert.assertEquals(CenteredParagraphChunk.class, chunks.get(12)
				.getClass());
		Assert.assertEquals(ParagraphChunk.class, chunks.get(13).getClass());
	}

	@Test(expected=TubainaException.class)
	public void testChunksMakerBuilderForAllDoesntAcceptQuestionTagOutsideExercise() {
		ChunksMaker chunksMaker = new ChunksMakerBuilder(resources)
				.build(ReplacerType.ALL);
		String exercise = exampleQuestion;
		List<Chunk> list = chunksMaker.make(exercise);
		for (Chunk chunk : list) {
			System.out.println(chunk.getClass().getName());
		}
		Assert.fail("Should not accept question tag outside exercise tag");
	}

	@Test(expected=TubainaException.class)
	public void testChunksMakerBuilderDoesntAcceptNoteInsideExerciseOutsideQuestion() {
		ChunksMaker chunksMaker = new ChunksMakerBuilder(resources)
				.build(ReplacerType.EXERCISE);
		String exercise = exampleNote + exampleQuestion;
		chunksMaker.make(exercise);
		Assert.fail("Should not accept notes inside exercise tag but outside question tag");
	}
}
