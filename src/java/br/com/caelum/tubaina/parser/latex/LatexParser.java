package br.com.caelum.tubaina.parser.latex;

import java.util.List;

import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.SimpleIndentator;
import br.com.caelum.tubaina.parser.Tag;

public class LatexParser implements Parser {

	private static final String VSPACE = "\n\\\\[1em]\n";
	public static final int MAX_LINE_LENGTH = 93;

	private final List<Tag> tags;

	private boolean showNotes;
	private final boolean noAnswer;

	public LatexParser(List<Tag> tags, boolean showNotes, boolean noAnswer) {
		this.tags = tags;
		this.showNotes = showNotes;
		this.noAnswer = noAnswer;
	}
	
	public LatexParser(List<Tag> tags) {
		this.tags = tags;
		this.showNotes = false;
		this.noAnswer = false;
	}

	public String parse(String string) {
		// TODO: remove eventual $1, $2 from the string so as not to be interpreted

		string = new EscapeTag().parse(string, null);
		for (Tag tag : tags) {
			string = tag.parse(string, null);
		}
		return string;
	}

	public String parseBox(String text, String options) {
		String string = new BoxTag().parse(text, parse(options));
		return VSPACE + string  + VSPACE;
	}

	public String parseImage(String text, String options) {
		return new ImageTag().parse(text, options);
	}

	public String parseJava(String text, String options) {
		String string = new JavaTag(new SimpleIndentator()).parse(text, options);
		return VSPACE + string;
	}

	public String parseParagraph(String text) {
		text = parse(text);
		return new ParagraphTag().parse(text, null);
	}

	public String parseCode(String text, String options) {
		String string = new CodeTag(new SimpleIndentator()).parse(text, options);
		return VSPACE + string + VSPACE;
	}

	public String parseList(String text, String options) {
		String string = new ListTag().parse(text, options);
		return string;
	}

	public String parseXml(String text, String options) {
		return VSPACE + new XmlTag(new SimpleIndentator()).parse(text, options) + VSPACE;
	}

	public String parseExercise(String text, int id) {
		return new ExerciseTag().parse(text, "" + id);
	}

	public String parseAnswer(String text, int id) {
		if (!noAnswer)
			return new AnswerTag().parse(text, "" + id);
		return "";
	}

	public String parseQuestion(String text) {
		return new QuestionTag().parse(text, null);
	}

	public String parseNote(String text) {
		if (this.showNotes)
			return new NoteTag().parse(text, null);
		return "";
	}

	public String parseItem(String text) {
		return new ItemTag().parse(text, null);
	}

	public String parseTodo(String text) {
		return new TodoTag().parse(text, null);
	}

	public String parseIndex(String name) {
		return new IndexTag().parse(name, null);
	}
}