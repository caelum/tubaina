package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.util.CommandExecutor;

public class HtmlCodeHighlighter {

    private final CommandExecutor commandExecutor;

    public HtmlCodeHighlighter(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public String highlight(String code) {
        return commandExecutor.execute("pygmentize", code);
    }

}
