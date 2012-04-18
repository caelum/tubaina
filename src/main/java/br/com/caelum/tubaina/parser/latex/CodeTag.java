package br.com.caelum.tubaina.parser.latex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;

public class CodeTag implements Tag {

    private final Indentator indentator;

    public static final String BEGIN = "\n\\begin{small}\n\\begin{minted}";
    public static final String END = "\n\\end{minted}\n\\end{small}";
    private static final String LATEXCODECOUNTER = "codecounter";
    public static final String CODEREFERENCE = "\n\\refstepcounter{" + LATEXCODECOUNTER
            + "}\\tubainacodecaption{\\the" + LATEXCODECOUNTER + "}";

    public CodeTag(Indentator indentator) {
        this.indentator = indentator;
    }

    public String parse(String string, String options) {
        StringBuilder latexOptionsBuilder = new StringBuilder("[");
        String optionsWithoutSharp = matchSharpOption(options, latexOptionsBuilder);
        String optionsWithoutHighlight = matchHighlightOption(optionsWithoutSharp,
                latexOptionsBuilder);
        latexOptionsBuilder.append("]");

        String optionsLeft = optionsWithoutHighlight;

        String latexOptionalParameters = latexOptionsBuilder.length() > 2 ? latexOptionsBuilder
                .toString() : "";

        StringBuilder labelBuilder = new StringBuilder();
        String optionsWithoutLabel = matchLabel(optionsLeft, labelBuilder);
        String label = labelBuilder.toString();
        String latexReference = latexReferenceFor(label);
        optionsLeft = optionsWithoutLabel;

        String chosenLanguage = matchLanguage(optionsLeft);
        String indentedString = this.indentator.indent(string);

        return latexReference + CodeTag.BEGIN + latexOptionalParameters + "{" + chosenLanguage
                + "}\n" + indentedString + CodeTag.END;
    }

    private String latexReferenceFor(String label) {
        if (label.isEmpty())
            return "";
        return CodeTag.CODEREFERENCE + "\\label{" + label + "}\n";
    }

    private String matchLabel(String options, StringBuilder labelBuilder) {
        String label = "";
        Matcher labelMatcher = Pattern.compile("label=(\\S+)").matcher(options);
        if (labelMatcher.find()) {
            label = labelMatcher.group(1);
            int indexOfLabel = options.indexOf("label");
            String optionsWithoutLabel = options.substring(0, indexOfLabel)
                    + options.subSequence(indexOfLabel + 6 + label.length(), options.length());
            options = optionsWithoutLabel;
        }
        
        labelBuilder.append(label);
        return options;
    }

    private String matchLanguage(String optionsLeft) {
        String chosenLanguage = optionsLeft == null ? "" : optionsLeft.trim().split(" ")[0].trim();
        if (chosenLanguage.isEmpty()) {
            chosenLanguage = "text";
        }
        return chosenLanguage;
    }

    private String matchHighlightOption(String options, StringBuilder optionsBuilder) {
        Matcher highlightPattern = Pattern.compile("h=(\\d+(,\\d+)*)").matcher(options);
        if (highlightPattern.find()) {
            if (optionsBuilder.length() > 1)
                optionsBuilder.append(", ");

            String highlights = highlightPattern.group();
            optionsBuilder.append(highlights);
            options = removeHighlights(options, highlights);
        }
        return options;
    }

    private String matchSharpOption(String options, StringBuilder optionsBuilder) {
        if (options.contains("#")) {
            optionsBuilder.append("linenos, numbersep=5pt");
            options = removeSharp(options);
        }
        return options;
    }

    private String removeHighlights(String options, String highlights) {
        return options.replace(highlights, "");
    }

    private String removeSharp(String options) {
        int lastSharp = options.lastIndexOf("#");
        return options.substring(0, lastSharp) + options.substring(lastSharp + 1);
    }

}
