package br.com.caelum.tubaina.parser.latex;

import java.util.List;

import br.com.caelum.tubaina.gists.GistConnector;
import br.com.caelum.tubaina.gists.GistRequest;
import br.com.caelum.tubaina.gists.GistResultRetriever;
import br.com.caelum.tubaina.gists.JsonToGistResultConverter;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.SimpleIndentator;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.desktop.SyntaxHighlighter;
import br.com.caelum.tubaina.parser.pygments.CodeCache;
import br.com.caelum.tubaina.parser.pygments.CodeOutputType;
import br.com.caelum.tubaina.util.SimpleCommandExecutor;

public class LatexParser implements Parser {

    private static final String VSPACE = "\n\\\\[1em]\n";
    public static final int MAX_LINE_LENGTH = 93;

    private final List<Tag> tags;

    private boolean showNotes;
    private final boolean noAnswer;
    private SyntaxHighlighter syntaxHighlighter;
    private CodeCache codeCache;

    public LatexParser(List<Tag> tags, boolean showNotes, boolean noAnswer) {
        codeCache = new CodeCache(CodeOutputType.LATEX);
        syntaxHighlighter = new SyntaxHighlighter(new SimpleCommandExecutor(), CodeOutputType.LATEX, codeCache);
        this.tags = tags;
        this.showNotes = showNotes;
        this.noAnswer = noAnswer;
    }

    public LatexParser(List<Tag> tags) {
        codeCache = new CodeCache(CodeOutputType.LATEX);
        syntaxHighlighter = new SyntaxHighlighter(new SimpleCommandExecutor(), CodeOutputType.LATEX, codeCache);
        this.tags = tags;
        this.showNotes = false;
        this.noAnswer = false;
    }

    @Override
	public String parse(String string) {
        // TODO: remove eventual $1, $2 from the string so as not to be
        // interpreted
        string = new EscapeTag().parse(chunk);
        for (Tag tag : tags) {
            string = tag.parse(chunk);
        }
        return string;
    }

    @Override
	public String parseBox(String text, String options) {
        String string = new BoxTag().parse(chunk);
        return VSPACE + string + VSPACE;
    }

    @Override
	public String parseImage(String text, String options) {
        return new ImageTag().parse(chunk);
    }

    @Override
	@Deprecated
    public String parseJava(String text, String options) {
        String string = new JavaTag(new SimpleIndentator(4))
                .parse(chunk);
        return string;
    }

    @Override
	public String parseParagraph(String text) {
        text = parse(text);
        return new ParagraphTag().parse(chunk);
    }

    @Override
	public String parseCode(String text, String options) {
        String string = new CodeTag(new SimpleIndentator(4), syntaxHighlighter)
                .parse(chunk);
        return string + VSPACE;
    }

    @Override
	public String parseGist(String options) {
    	
    	GistResultRetriever retriever = new GistResultRetriever(new GistConnector(
				new JsonToGistResultConverter(), new GistRequest()));
    	SimpleIndentator ident = new SimpleIndentator(4);
    	
		String string = new GistTag(ident, retriever).parse(chunk);
        return string + VSPACE;
    }

    @Override
	public String parseList(String text, String options) {
        String string = new ListTag().parse(chunk);
        return string;
    }

    @Override
	@Deprecated
    public String parseXml(String text, String options) {
        return new XmlTag(new SimpleIndentator(4)).parse(chunk)
                + VSPACE;
    }

    @Override
	public String parseExercise(String text, int id) {
        return new ExerciseTag().parse(chunk);
    }

    @Override
	public String parseAnswer(String text, int id) {
        if (!noAnswer)
            return new AnswerTag().parse(chunk);
        return "";
    }

    @Override
	public String parseQuestion(String text) {
        return new QuestionTag().parse(chunk);
    }

    @Override
	public String parseNote(String text, String title) {
        if (this.showNotes)
            return new NoteTag().parse(chunk);
        return "";
    }

    @Override
	public String parseItem(String text) {
        return new ItemTag().parse(chunk);
    }

    @Override
	public String parseTodo(String text) {
        return new TodoTag().parse(chunk);
    }

    @Override
	public String parseIndex(String name) {
        return new IndexTag().parse(chunk);
    }

    @Override
	public String parseColumn(String text) {
        return new TableColumnTag().parse(chunk);
    }

    @Override
	public String parseRow(String text) {
        return new TableRowTag().parse(chunk);
    }

    @Override
	public String parseTable(String text, String title, boolean noborder,
            int columns) {
        return new TableTag(noborder).parse(chunk);
    }

    @Override
	public String parseCenteredParagraph(String content) {
        return new CenteredParagraphTag().parse(chunk);
    }

    @Override
	@Deprecated
    public String parseRuby(String content, String options) {
        String result = new RubyTag(new SimpleIndentator(4)).parse(chunk);
        return result + VSPACE;
    }

    @Override
	public String parseParagraphInsideItem(String text) {
        return parse(text);
    }
}