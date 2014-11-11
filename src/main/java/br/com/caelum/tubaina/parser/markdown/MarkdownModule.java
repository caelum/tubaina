package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.ParseType;
import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.chunk.AnswerChunk;
import br.com.caelum.tubaina.chunk.BoxChunk;
import br.com.caelum.tubaina.chunk.CenteredParagraphChunk;
import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.chunk.ExerciseChunk;
import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.chunk.IndexChunk;
import br.com.caelum.tubaina.chunk.IntroductionChunk;
import br.com.caelum.tubaina.chunk.ItemChunk;
import br.com.caelum.tubaina.chunk.ListChunk;
import br.com.caelum.tubaina.chunk.NoteChunk;
import br.com.caelum.tubaina.chunk.ParagraphChunk;
import br.com.caelum.tubaina.chunk.QuestionChunk;
import br.com.caelum.tubaina.chunk.SubsectionChunk;
import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.chunk.TableColumnChunk;
import br.com.caelum.tubaina.chunk.TableRowChunk;
import br.com.caelum.tubaina.chunk.TodoChunk;
import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.IntroductionTag;
import br.com.caelum.tubaina.parser.NullAnswerTag;
import br.com.caelum.tubaina.parser.NullNoteTag;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.SimpleIndentator;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.TubainaModule;
import br.com.caelum.tubaina.parser.pygments.CodeOutputType;
import br.com.caelum.tubaina.util.CommandExecutor;
import br.com.caelum.tubaina.util.SimpleCommandExecutor;

import com.google.inject.TypeLiteral;

public class MarkdownModule extends TubainaModule {

	private final boolean showNotes;
	private final boolean noAnswer;
	private final S3Path s3Path;
	
	public MarkdownModule(String s3Path, boolean showNotes, boolean noAnswer) {
		this.s3Path = new S3Path(s3Path);
		this.showNotes = showNotes;
		this.noAnswer = noAnswer;
	}

	public MarkdownModule() {
		this("", true, false);
	}
	
	@Override
	protected void configure() {
		bind(new TypeLiteral<S3Path>() {}).toInstance(s3Path);
		bind(new TypeLiteral<Tag<AnswerChunk>>() {}).to(noAnswer ? NullAnswerTag.class : AnswerTag.class);
		bind(new TypeLiteral<Tag<BoxChunk>>() {}).to(BoxTag.class);
		bind(new TypeLiteral<Tag<CenteredParagraphChunk>>() {}).to(CenteredParagraphTag.class);
		bind(new TypeLiteral<Tag<CodeChunk>>() {}).to(CodeTag.class);
		bind(new TypeLiteral<Tag<ExerciseChunk>>() {}).to(ExerciseTag.class);
		bind(new TypeLiteral<Tag<ImageChunk>>() {}).to(ImageTag.class);
		bind(new TypeLiteral<Tag<IndexChunk>>() {}).to(IndexTag.class);
		bind(new TypeLiteral<Tag<IntroductionChunk>>() {}).to(IntroductionTag.class);
		bind(new TypeLiteral<Tag<ItemChunk>>() {}).to(ItemTag.class);
		bind(new TypeLiteral<Tag<ListChunk>>() {}).to(ListTag.class);
		bind(new TypeLiteral<Tag<NoteChunk>>() {}).to(showNotes ? NoteTag.class : NullNoteTag.class);
		bind(new TypeLiteral<Tag<ParagraphChunk>>() {}).to(ParagraphTag.class);
		bind(new TypeLiteral<Tag<QuestionChunk>>() {}).to(QuestionTag.class);
		bind(new TypeLiteral<Tag<TableColumnChunk>>() {}).to(TableColumnTag.class);
		bind(new TypeLiteral<Tag<TableRowChunk>>() {}).to(TableRowTag.class);
		bind(new TypeLiteral<Tag<TableChunk>>() {}).to(TableTag.class);
		bind(new TypeLiteral<Tag<TodoChunk>>() {}).to(TodoTag.class);
		bind(SectionsManager.class).toInstance(getSectionsManager());
		bind(new TypeLiteral<Tag<SubsectionChunk>>() {}).to(SubsectionTag.class);

		bind(Parser.class).toInstance(ParseType.LATEX.getParser());
		bind(Indentator.class).to(SimpleIndentator.class);
		bind(CommandExecutor.class).to(SimpleCommandExecutor.class);
		bind(CodeOutputType.class).toInstance(CodeOutputType.LATEX);
	}
}
