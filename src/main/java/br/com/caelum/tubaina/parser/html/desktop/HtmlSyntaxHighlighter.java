package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.util.CommandExecutor;

public class HtmlSyntaxHighlighter {

    private final CommandExecutor commandExecutor;

    public HtmlSyntaxHighlighter(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public String highlight(String code, String language, boolean numbered) {
        String options = "";
        if (numbered)
            options = "-P lineos=inline ";
        String command = "pygmentize -O encoding=ISO-8859-1 -O outencoding=UTF-8 " + options + "-f html -l " + language;
        return commandExecutor.execute(command, code);
    }

}
