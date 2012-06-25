package br.com.caelum.tubaina.parser.html.desktop;

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
        // TODO: remove eventual $1, $2 from the string so as not to be
        // interpreted

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
        String string = new JavaTag(new SimpleIndentator())
                .parse(text, options);
        return string;
    }

    public String parseParagraph(String text) {
        String string = this.sanitizer.sanitize(text);
        string = new ParagraphTag().parse(string, null);
        string = this.parse(string);
        return string;
    }

    public String parseCode(String text, String options) {
        return new CodeTag().parse(text, options);
    }

    public String parseGist(String options) {
    	return new GistTag().parse(null, options);
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

    public String parseNote(String text, String title) {
        return new NoteTag().parse(text, title);
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

    public String parseColumn(String text) {
        return new TableColumnTag().parse(text, null);
    }

    public String parseRow(String text) {
        return new TableRowTag().parse(text, null);
    }

    public String parseTable(String text, String title, boolean noborder,
            int columns) {
        title = this.sanitizer.sanitize(title);
        return new TableTag(noborder).parse(text, title);
    }

    public String parseCenteredParagraph(String content) {
        return new CenteredParagraphTag().parse(content, null);
    }

    public String parseRuby(String content, String options) {
        return new RubyTag(new SimpleIndentator()).parse(content, options);
    }

    public String parseParagraphInsideItem(String text) {
        String string = this.sanitizer.sanitize(text);
        string = this.parse(string);
        return string;
    }

}