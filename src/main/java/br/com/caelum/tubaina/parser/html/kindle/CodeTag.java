package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.HtmlAndKindleCodeTag;
import br.com.caelum.tubaina.parser.html.desktop.SyntaxHighlighter;
import br.com.caelum.tubaina.parser.pygments.CodeCache;
import br.com.caelum.tubaina.parser.pygments.CodeOutputType;
import br.com.caelum.tubaina.util.CommandExecutor;

public class CodeTag implements Tag {

    private final HtmlAndKindleCodeTag htmlCodeTag;

    public CodeTag() {
        SyntaxHighlighter syntaxHighlighter = new SyntaxHighlighter(new CommandExecutor(),
                CodeOutputType.KINDLE_HTML, true, new CodeCache(CodeOutputType.KINDLE_HTML));
        this.htmlCodeTag = new HtmlAndKindleCodeTag(syntaxHighlighter);
    }

    public CodeTag(HtmlAndKindleCodeTag htmlCodeTag) {
        this.htmlCodeTag = htmlCodeTag;
    }

    public String parse(String content, String options) {
        return htmlCodeTag.parse(content, options);
    }

}
