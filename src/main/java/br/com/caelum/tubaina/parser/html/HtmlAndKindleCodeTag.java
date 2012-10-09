package br.com.caelum.tubaina.parser.html;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.caelum.tubaina.parser.SimpleIndentator;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.desktop.CodeCache;
import br.com.caelum.tubaina.parser.html.desktop.SyntaxHighlighter;
import br.com.caelum.tubaina.util.CommandExecutor;

public class HtmlAndKindleCodeTag implements Tag {

    public static final String BEGIN_START = "<pre ";
    public static final String BEGIN_END = ">";
    public static final String END = "</pre>";
    private SyntaxHighlighter htmlCodeHighlighter;
    private static final Logger LOG = Logger.getLogger(HtmlAndKindleCodeTag.class);
    private CodeTagOptionsParser codeTagOptionsParser;

    public HtmlAndKindleCodeTag() {
        codeTagOptionsParser = new CodeTagOptionsParser();
        this.htmlCodeHighlighter = new SyntaxHighlighter(new CommandExecutor(), 
                SyntaxHighlighter.HTML_OUTPUT, false, new CodeCache(SyntaxHighlighter.HTML_OUTPUT));
    }

    public HtmlAndKindleCodeTag(SyntaxHighlighter htmlCodeHighlighter) {
        codeTagOptionsParser = new CodeTagOptionsParser();
        this.htmlCodeHighlighter = htmlCodeHighlighter;
    }

    public String parse(String content, String options) {
        String language = detectLanguage(options);
        List<Integer> highlights = detectHighlights(options);
        boolean numbered = options.contains("#");
        SimpleIndentator simpleIndentator = new SimpleIndentator(2);
        String indentedCode = simpleIndentator.indent(content);
        String label = matchLabel(options);
        
        String code = htmlCodeHighlighter.highlight(indentedCode, language, numbered, highlights);
        
        String result = "";
        if (!label.isEmpty()) {
            result = BEGIN_START + "id='" + label + "'" + BEGIN_END + code + END;
        } else {
            result = BEGIN_START + BEGIN_END + code + END;
        }
        return result;
    }

    private String detectLanguage(String options) {
        return codeTagOptionsParser.parseLanguage(options);
    }

    private List<Integer> detectHighlights(String options) {
        return codeTagOptionsParser.parseHighlights(options);
    }

    private String matchLabel(String options) {
        return codeTagOptionsParser.parseLabel(options);
    }

}
