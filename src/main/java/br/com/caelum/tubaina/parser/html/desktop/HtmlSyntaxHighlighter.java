package br.com.caelum.tubaina.parser.html.desktop;

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

    public String highlight(String code, String language, boolean numbered) {
        String options = "";
        if (numbered || allLinesNumbered)
            options = ",linenos=inline";
        String encoding = System.getProperty("file.encoding");
        String command = "pygmentize -O encoding=" + encoding + ",outencoding=UTF-8" + options
                + " -f html -l " + language;
        return commandExecutor.execute(command, code);
    }

}
