package br.com.caelum.tubaina.parser.html.desktop;

import java.util.Collections;
import java.util.List;

import br.com.caelum.tubaina.util.CommandExecutor;

public class HtmlSyntaxHighlighter {

    private final CommandExecutor commandExecutor;
    private boolean allLinesNumbered;

    public HtmlSyntaxHighlighter(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public HtmlSyntaxHighlighter(CommandExecutor commandExecutor, boolean allLinesNumbered) {
        this(commandExecutor);
        this.allLinesNumbered = allLinesNumbered;
    }

    public String highlight(String code, String language, boolean numbered, List<Integer> lines) {
        StringBuilder options = new StringBuilder();
        if (numbered || allLinesNumbered)
            options.append(",linenos=inline");
        
        addLineHighlightOption(lines, options);
        
        String encoding = System.getProperty("file.encoding");
        String command = "pygmentize -O encoding=" + encoding + ",outencoding=UTF-8" + options
                + " -f html -l " + language;
        return commandExecutor.execute(command, code);
    }

    private void addLineHighlightOption(List<Integer> lines, StringBuilder options) {
        if (!lines.isEmpty()) {
            options.append(",hl_lines='");
            for (Integer line : lines) {
                options.append(line.toString());
                options.append(" ");
            }
            options.append("'");
        }
    }

    public String highlight(String code, String language, boolean numbered) {
        List<Integer> list = Collections.emptyList();
        return highlight(code, language, numbered, list);
    }

}
