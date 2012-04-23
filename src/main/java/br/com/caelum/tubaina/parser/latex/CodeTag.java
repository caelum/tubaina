package br.com.caelum.tubaina.parser.latex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.parser.Indentator;
import br.com.caelum.tubaina.parser.Tag;

public class CodeTag implements Tag {

    private final Indentator indentator;

    public static final String BEGIN = "\n\\begin{small}\n\\begin{minted}";
    public static final String END = "\n\\end{minted}\n\\end{small}";
    public static final String CODE_LABEL = "\\tubainaCodeLabel{";
    private static final String FILE_NAME = "\\tubainaCodeFileName";

    public CodeTag(Indentator indentator) {
        this.indentator = indentator;
    }

    public String parse(String string, String options) {
        StringBuilder mintedOptionsBuilder = new StringBuilder("[");

        String optionsWithoutSharp = matchSharpOption(options, mintedOptionsBuilder);
        String optionsWithoutHighlight = matchHighlightOption(optionsWithoutSharp,
                mintedOptionsBuilder);

        String optionsLeft = optionsWithoutHighlight;

        String mintedOptionalParameters = mintedOptionsBuilder.length() > 2 ? mintedOptionsBuilder
                .toString() : "";

        StringBuilder labelBuilder = new StringBuilder();
        String optionsWithoutLabel = matchLabel(optionsLeft, labelBuilder);
        String label = labelBuilder.toString();
        String latexReference = latexLabelFor(label);
        optionsLeft = optionsWithoutLabel;

        StringBuilder filenameBuilder = new StringBuilder();
        String optionsWithoutFileName = matchFilename(optionsLeft, filenameBuilder);
        optionsLeft = optionsWithoutFileName;
        String filename = filenameBuilder.toString();
        String latexFilename = latexFilenameFor(filename);

        String chosenLanguage = matchLanguage(optionsLeft);
        String indentedString = this.indentator.indent(string);

        return latexReference + latexFilename + CodeTag.BEGIN + mintedOptionalParameters + "{"
                + chosenLanguage + "}\n" + indentedString + CodeTag.END;
    }

    private String latexFilenameFor(String filename) {
        return filename.isEmpty() ? "" : FILE_NAME + "{" + filename + "}\n";
    }

    private String matchFilename(String options, StringBuilder filenameBuilder) {
        String filename = "";
        Matcher filenameMatcher = Pattern.compile("filename=(\\S+)").matcher(options);
        if (filenameMatcher.find()) {
            filename = filenameMatcher.group(1);
            int indexOfFilename = options.indexOf("filename");
            String optionsFilename = options.substring(0, indexOfFilename)
                    + options.subSequence(indexOfFilename + 9 + filename.length(), options.length());
            options = optionsFilename;
        }

        filenameBuilder.append(filename);
        return options;
    }

    private String latexLabelFor(String label) {
        if (label.isEmpty())
            return "";
        return CodeTag.CODE_LABEL + label + "}\n";
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
        optionsBuilder.append("]");
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
