package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.gists.GistResult;
import br.com.caelum.tubaina.gists.GistResultRetriever;
import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.desktop.CodeCache;
import br.com.caelum.tubaina.parser.html.desktop.SyntaxHighlighter;
import br.com.caelum.tubaina.util.CommandExecutor;

public class GistTag implements Tag {

    private CodeTag code;
    private GistResultRetriever retriever;

    public GistTag(Indentator i, GistResultRetriever retriever) {
        this.code = new CodeTag(i, new SyntaxHighlighter(new CommandExecutor(), SyntaxHighlighter.LATEX_OUTPUT, false, new CodeCache(SyntaxHighlighter.LATEX_OUTPUT)));
        this.retriever = retriever;
    }

    public String parse(String string, String options) {
        String codeOptions = options.contains("#") ? " #" : "";
        
        long gistId = Long.parseLong(options.replaceAll("\\D", ""));
        
        GistResult result = retriever.retrieve(gistId, "");
        
        String content = result.getContent();
        String language = result.getLanguage();
        
        return code.parse(content, language + codeOptions);
    }
    
}
