package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.CodeTagOptionsParser;
import br.com.caelum.tubaina.parser.html.desktop.SyntaxHighlighter;

import com.google.inject.Inject;

public class CodeTag implements Tag<CodeChunk> {

    private final Indentator indentator;

    public static final String BEGIN = "\n\n\\begin{small}\n";
    public static final String END = "\n\\end{small}";
    public static final String CODE_LABEL = "\\tubainaCodeLabel{";
    private static final String FILE_NAME = "\\tubainaCodeFileName";

    private CodeTagOptionsParser codeTagOptionsParser;

    private final SyntaxHighlighter syntaxHighlighter;

    @Inject
    public CodeTag(Indentator indentator, SyntaxHighlighter syntaxHighlighter) {
        this.indentator = indentator;
        this.syntaxHighlighter = syntaxHighlighter;
        this.codeTagOptionsParser = new CodeTagOptionsParser();
    }

    @Override
	public String parse(CodeChunk chunk) {

        String options = chunk.getOptions();
		String chosenLanguage = codeTagOptionsParser.parseLanguage(options);
        String latexFilename = latexFilenameFor(codeTagOptionsParser.parseFileName(options));
        String latexReference = latexLabelFor(codeTagOptionsParser.parseLabel(options));
        boolean numbered = options.contains(" #");
        
        String indentedCode = this.indentator.indent(chunk.getContent());
        
        String highlightedCode = syntaxHighlighter.highlight(indentedCode, chosenLanguage, numbered);

        return latexReference + latexFilename + CodeTag.BEGIN + highlightedCode + CodeTag.END;
    }

    private String latexFilenameFor(String filename) {
        return filename.isEmpty() ? "" : FILE_NAME + "{" + filename + "}\n";
    }

    private String latexLabelFor(String label) {
        if (label.isEmpty())
            return "";
        return CodeTag.CODE_LABEL + label + "}\n";
    }

}
