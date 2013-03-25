package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.chunk.GistChunk;
import br.com.caelum.tubaina.gists.GistResult;
import br.com.caelum.tubaina.gists.GistResultRetriever;
import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.desktop.SyntaxHighlighter;
import br.com.caelum.tubaina.parser.pygments.CodeCache;
import br.com.caelum.tubaina.parser.pygments.CodeOutputType;
import br.com.caelum.tubaina.util.SimpleCommandExecutor;

public class GistTag implements Tag<GistChunk> {

    private CodeTag code;
    private GistResultRetriever retriever;

    public GistTag(Indentator i, GistResultRetriever retriever) {
        this.code = new CodeTag(i, new SyntaxHighlighter(new SimpleCommandExecutor(), CodeOutputType.LATEX, new CodeCache(CodeOutputType.LATEX)));
        this.retriever = retriever;
    }

    @Override
	public String parse(GistChunk chunk) {
        String options = chunk.getOptions();
		String codeOptions = options.contains("#") ? " #" : "";
        
        long gistId = Long.parseLong(options.replaceAll("\\D", ""));
        
        GistResult result = retriever.retrieve(gistId, "");
        
        String content = result.getContent();
        String language = result.getLanguage();
        
        CodeChunk codeChunk = new CodeChunk(content, language + codeOptions);
		return code.parse(codeChunk);
    }
    
}
