package br.com.caelum.tubaina.parser.latex;

import java.util.List;

import br.com.caelum.tubaina.gists.GistConnector;
import br.com.caelum.tubaina.gists.GistRequest;
import br.com.caelum.tubaina.gists.GistResultRetriever;
import br.com.caelum.tubaina.gists.JsonToGistResultConverter;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.SimpleIndentator;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.desktop.CodeCache;
import br.com.caelum.tubaina.parser.html.desktop.SyntaxHighlighter;
import br.com.caelum.tubaina.util.CommandExecutor;

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
        // TODO: remove eventual $1, $2 from the string so as not to be
        // interpreted
        string = new EscapeTag().parse(string, null);
        for (Tag tag : tags) {
            string = tag.parse(string, null);
        }
        return string;
    }

    public String parseBox(String text, String options) {
        String string = new BoxTag().parse(text, parse(options));
        return VSPACE + string + VSPACE;
    }

    public String parseImage(String text, String options) {
        return new ImageTag().parse(text, options);
    }

    public String parseJava(String text, String options) {
        String string = new JavaTag(new SimpleIndentator(4))
                .parse(text, options);
        return string;
    }

    public String parseParagraph(String text) {
        text = parse(text);
        return new ParagraphTag().parse(text, null);
    }

    public String parseCode(String text, String options) {
        String string = new CodeTag(new SimpleIndentator(4), new SyntaxHighlighter(new CommandExecutor(), SyntaxHighlighter.LATEX_OUTPUT, false, new CodeCache(SyntaxHighlighter.LATEX_OUTPUT)))
                .parse(text, options);
        return string + VSPACE;
    }

    public String parseGist(String options) {
    	
    	GistResultRetriever retriever = new GistResultRetriever(new GistConnector(
				new JsonToGistResultConverter(), new GistRequest()));
    	SimpleIndentator ident = new SimpleIndentator(4);
    	
		String string = new GistTag(ident, retriever).parse(null, options);
        return string + VSPACE;
    }

    public String parseList(String text, String options) {
        String string = new ListTag().parse(text, options);
        return string;
    }

    public String parseXml(String text, String options) {
        return new XmlTag(new SimpleIndentator(4)).parse(text, options)
                + VSPACE;
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

    public String parseNote(String text, String title) {
        if (this.showNotes)
            return new NoteTag().parse(text, title);
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

    public String parseColumn(String text) {
        return new TableColumnTag().parse(text, null);
    }

    public String parseRow(String text) {
        return new TableRowTag().parse(text, null);
    }

    public String parseTable(String text, String title, boolean noborder,
            int columns) {
        return new TableTag(noborder, columns).parse(text, title);
    }

    public String parseCenteredParagraph(String content) {
        return new CenteredParagraphTag().parse(content, null);
    }

    public String parseRuby(String content, String options) {
        String result = new RubyTag(new SimpleIndentator(4)).parse(content,
                options);
        return result + VSPACE;
    }

    public String parseParagraphInsideItem(String text) {
        return parse(text);
    }
}