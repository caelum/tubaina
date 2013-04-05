package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.HtmlAndKindleCodeTag;

import com.google.inject.Inject;

public class CodeTag implements Tag<CodeChunk> {

    private final HtmlAndKindleCodeTag htmlCodeTag;

    @Inject
    public CodeTag(HtmlAndKindleCodeTag htmlCodeTag) {
        this.htmlCodeTag = htmlCodeTag;
    }

    @Override
	public String parse(CodeChunk chunk) {
        return htmlCodeTag.parse(chunk);
    }

}
