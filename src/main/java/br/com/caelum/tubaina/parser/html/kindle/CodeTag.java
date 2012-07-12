package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.HtmlAndKindleCodeTag;
import br.com.caelum.tubaina.parser.html.desktop.HtmlSyntaxHighlighter;
import br.com.caelum.tubaina.util.CommandExecutor;

public class CodeTag implements Tag {

    private final HtmlAndKindleCodeTag htmlCodeTag;
    
    public CodeTag() {
        this.htmlCodeTag = new HtmlAndKindleCodeTag(new HtmlSyntaxHighlighter(new CommandExecutor(), true));
    }

    public CodeTag(HtmlAndKindleCodeTag htmlCodeTag) {
        this.htmlCodeTag = htmlCodeTag;
    }

    public String parse(String content, String options) {
        return htmlCodeTag.parse(content, options);
    }

}
