package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.util.CommandExecutor;

public class HtmlCodeHighlighter {

    private final CommandExecutor commandExecutor;

    public HtmlCodeHighlighter(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public String highlight(String code, String language, boolean numbered) {
        String options = "";
        if (numbered)
            options = "-P lineos=inline ";
        String command = "pygmentize " + options + "-f html -l " + language;
        return commandExecutor.execute(command, code);
    }

}
