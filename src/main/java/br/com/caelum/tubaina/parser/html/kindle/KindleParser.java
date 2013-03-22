package br.com.caelum.tubaina.parser.html.kindle;

import java.util.List;

import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.util.HtmlSanitizer;

public class KindleParser implements Parser {

    public static final int MAX_LINE_LENGTH = 80;

    private final List<Tag> tags;

    private final HtmlSanitizer sanitizer = new HtmlSanitizer();

    private final boolean noAnswer;

    private final boolean showNotes;

    public KindleParser(List<Tag> tags, boolean noAnswer, boolean showNotes) {
        this.tags = tags;
        this.noAnswer = noAnswer;
        this.showNotes = showNotes;
    }

    public String parse(String string) {
        // TODO: remove eventual $1, $2 from the string so as not to be
        // interpreted

        for (Tag tag : tags) {
            string = tag.parse(chunk);
        }
        return string;
    }

    public String parseBox(String text, String options) {
        String title = this.sanitizer.sanitize(options);
        return new BoxTag().parse(chunk);

    }

    public String parseImage(String text, String options) {
        return new ImageTag().parse(chunk);
    }

    public String parseJava(String text, String options) {
        return null;
    }

    public String parseParagraph(String text) {
        String string = this.sanitizer.sanitize(text);
        string = new ParagraphTag().parse(chunk);
        string = this.parse(string);
        return string;
    }
    
    public String parseParagraphInsideItem(String text) {
        String string = this.sanitizer.sanitize(text);
        string = this.parse(string);
        return string;
    }

    public String parseCode(String text, String options) {
        return new CodeTag().parse(chunk);
    }

    public String parseGist(String options) {
    	return new GistTag().parse(chunk);
    }

    public String parseList(String text, String options) {
        return new ListTag().parse(chunk);
    }

    public String parseXml(String text, String options) {
        return null;
    }

    public String parseExercise(String text, int id) {
        String string = new ExerciseTag().parse(chunk);
        return string;
    }

    public String parseAnswer(String text, int id) {
        if (!noAnswer)
            return new AnswerTag().parse(chunk);
        return "";
    }

    public String parseQuestion(String text) {
        String string = new QuestionTag().parse(chunk);
        return string;
    }

    public String parseNote(String text, String title) {
        if (!showNotes)
            return "";
        return new NoteTag().parse(chunk);
    }

    public String parseItem(String text) {
        return new ItemTag().parse(chunk);
    }

    public String parseTodo(String text) {
        return new TodoTag().parse(chunk);
    }

    public String parseIndex(String name) {
        name = this.sanitizer.sanitize(name);
        return new IndexTag().parse(chunk);
    }

    public String parseColumn(String text) {
        return new TableColumnTag().parse(chunk);
    }

    public String parseRow(String text) {
        return new TableRowTag().parse(chunk);
    }

    public String parseTable(String text, String title, boolean noborder,
            int columns) {
        title = this.sanitizer.sanitize(title);
        return new TableTag(noborder).parse(chunk);
    }

    public String parseCenteredParagraph(String content) {
        return new CenteredParagraphTag().parse(chunk);
    }

    public String parseRuby(String content, String options) {
    	return null;
    }


}