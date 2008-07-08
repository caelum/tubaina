package br.com.caelum.tubaina.parser.html;

import java.util.List;

import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.SimpleIndentator;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.util.HtmlSanitizer;

public class HtmlParser implements Parser {

	public static final int MAX_LINE_LENGTH = 80;

	private final List<Tag> tags;

	private final HtmlSanitizer sanitizer = new HtmlSanitizer();

	private final boolean noAnswer;

	public HtmlParser(List<Tag> tags, boolean noAnswer) {
		this.tags = tags;
		this.noAnswer = noAnswer;
	}

	public String parse(String string) {
		// TODO: remove eventual $1, $2 from the string so as not to be interpreted

		for (Tag tag : tags) {
			string = tag.parse(string, null);
		}
		return string;
	}

	public String parseBox(String text, String options) {
		String title = this.sanitizer.sanitize(options);
		return new BoxTag().parse(text, title);

	}

	public String parseImage(String text, String options) {
		return new ImageTag().parse(text, options);
	}

	public String parseJava(String text, String options) {
		String string = new JavaTag(new SimpleIndentator()).parse(text, options);
		return string;
	}

	public String parseParagraph(String text) {
		text = this.sanitizer.sanitize(text);
		String string = new ParagraphTag().parse(text, null);
		return this.parse(string);
	}

	public String parseCode(String text, String options) {
		text = this.sanitizer.sanitize(text);
		return new CodeTag(new SimpleIndentator()).parse(text, options);
	}

	public String parseList(String text, String options) {
		return new ListTag().parse(text, options);
	}

	public String parseXml(String text, String options) {
		text = this.sanitizer.sanitize(text);
		String string = new XmlTag(new SimpleIndentator()).parse(text, options);
		return string;
	}

	public String parseExercise(String text, int id) {
		String string = new ExerciseTag().parse(text, "" + id);
		return string;
	}

	public String parseAnswer(String text, int id) {
		if (!noAnswer)
			return new AnswerTag().parse(text, "" + id);
		return "";
	}

	public String parseQuestion(String text) {
		String string = new QuestionTag().parse(text, null);
		return string;
	}

	public String parseNote(String text) {
		return new NoteTag().parse(text, null);
	}

	public String parseItem(String text) {
		return new ItemTag().parse(text, null);
	}

	public String parseTodo(String text) {
		return new TodoTag().parse(text, null);
	}

	public String parseIndex(String name) {
		name = this.sanitizer.sanitize(name);
		return new IndexTag().parse(name, null);
	}
}