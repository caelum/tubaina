package br.com.caelum.tubaina.parser.markdown;

import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.parser.Tag;

public class CodeTag implements Tag<CodeChunk> {

    @Override
	public String parse(CodeChunk chunk) {
    	return "\n\n```\n" + chunk.getContent() + "\n```\n\n";
    }

}
