package br.com.caelum.tubaina.parser.html.desktop;

import java.util.ArrayList;
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
        
        ArrayList<String> commands = new ArrayList<String>();
        commands.add("pygmentize");
        commands.add("-O");
        commands.add("encoding=" + encoding + ",outencoding=UTF-8" + options);
        commands.add("-f");
        commands.add("html");
        commands.add("-l");
        commands.add(language);
        
        return commandExecutor.execute(commands, code);
    }

    private void addLineHighlightOption(List<Integer> lines, StringBuilder options) {
        if (!lines.isEmpty()) {
            options.append(",hl_lines=");
            for (Integer line : lines) {
                options.append(line.toString());
                options.append(" ");
            }
            options.deleteCharAt(options.length() - 1);
            options.append("");
        }
    }

    public String highlight(String code, String language, boolean numbered) {
        List<Integer> list = Collections.emptyList();
        return highlight(code, language, numbered, list);
    }

}
