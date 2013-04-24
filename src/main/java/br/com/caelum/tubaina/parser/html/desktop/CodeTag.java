package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.HtmlAndKindleCodeTag;
import br.com.caelum.tubaina.parser.pygments.CodeCache;
import br.com.caelum.tubaina.parser.pygments.CodeOutputType;
import br.com.caelum.tubaina.util.SimpleCommandExecutor;

public class CodeTag implements Tag<CodeChunk> {

    private HtmlAndKindleCodeTag htmlCodeTag;

    public CodeTag() {
        SyntaxHighlighter syntaxHighlighter = new SyntaxHighlighter(new SimpleCommandExecutor(),
                CodeOutputType.HTML, new CodeCache(CodeOutputType.HTML));
        htmlCodeTag = new HtmlAndKindleCodeTag(syntaxHighlighter);
    }
    
    public CodeTag(HtmlAndKindleCodeTag htmlCodeTag) {
        this.htmlCodeTag = htmlCodeTag;
    }

    @Override
	public String parse(CodeChunk chunk) {
        return htmlCodeTag.parse(chunk);
    }

}
