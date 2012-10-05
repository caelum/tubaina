package br.com.caelum.tubaina.parser.latex;

import java.util.List;

import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.CodeTagOptionsParser;

public class CodeTag implements Tag {

    private final Indentator indentator;

    public static final String BEGIN = "\n\\begin{small}\n\\begin{minted}";
    public static final String END = "\n\\end{minted}\n\\end{small}";
    public static final String CODE_LABEL = "\\tubainaCodeLabel{";
    private static final String FILE_NAME = "\\tubainaCodeFileName";

    private CodeTagOptionsParser codeTagOptionsParser;

    public CodeTag(Indentator indentator) {
        this.indentator = indentator;
        codeTagOptionsParser = new CodeTagOptionsParser();
    }

    public String parse(String string, String options) {

        String mintedOptionalParameters = parseOptinals(options);

        String chosenLanguage = codeTagOptionsParser.parseLanguage(options);
        String latexFilename = latexFilenameFor(codeTagOptionsParser.parseFileName(options));
        String latexReference = latexLabelFor(codeTagOptionsParser.parseLabel(options));

        String indentedString = this.indentator.indent(string);

        return latexReference + latexFilename + CodeTag.BEGIN + mintedOptionalParameters + "{"
                + chosenLanguage + "}\n" + indentedString + CodeTag.END;
    }

    private String parseOptinals(String options) {
        StringBuilder optionals = new StringBuilder("[");
        if (options.contains("#")) {
            optionals.append("linenos, numbersep=5pt");
        }
        List<Integer> lines = codeTagOptionsParser.parseHighlights(options);
        addLineHighlights(lines, optionals);
        String finalOptionals = optionals.append("]").toString();
        return finalOptionals.equals("[]")? "" : finalOptionals;
    }

    private void addLineHighlights(List<Integer> lines, StringBuilder options) {
        if (!lines.isEmpty()) {
            options.append(", h=");
            for (Integer line : lines) {
                options.append(line.toString());
                options.append(",");
            }
            options.deleteCharAt(options.length() - 1);
            options.append("");
        }
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
