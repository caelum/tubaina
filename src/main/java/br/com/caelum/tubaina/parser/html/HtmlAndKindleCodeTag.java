package br.com.caelum.tubaina.parser.html;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import br.com.caelum.tubaina.parser.SimpleIndentator;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.desktop.HtmlSyntaxHighlighter;
import br.com.caelum.tubaina.util.CommandExecutor;

public class HtmlAndKindleCodeTag implements Tag {

    public static final String BEGIN_START = "<pre ";
    public static final String BEGIN_END = ">";
    public static final String END = "</pre>";
    private HtmlSyntaxHighlighter htmlCodeHighlighter;
    private static final Logger LOG = Logger.getLogger(HtmlAndKindleCodeTag.class);

    public HtmlAndKindleCodeTag() {
        this.htmlCodeHighlighter = new HtmlSyntaxHighlighter(new CommandExecutor());
    }

    public HtmlAndKindleCodeTag(HtmlSyntaxHighlighter htmlCodeHighlighter) {
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
        if (options != null) {
            String languageCandidate = options.trim().split(" ")[0];
            if (!languageCandidate.contains("#") && !languageCandidate.startsWith("h=")
                    && !languageCandidate.startsWith("label=") && !languageCandidate.isEmpty())
                return languageCandidate;
        }
        return "text";
    }

    private List<Integer> detectHighlights(String options) {
        return new CodeTagOptionsParser().parseHighlights(options);
    }

    private String matchLabel(String options) {
        Matcher labelMatcher = Pattern.compile("label=(\\S+)").matcher(options);
        if (labelMatcher.find())
            return labelMatcher.group(1);
        return "";
    }

}
