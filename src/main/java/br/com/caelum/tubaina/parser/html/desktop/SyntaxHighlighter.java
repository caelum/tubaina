package br.com.caelum.tubaina.parser.html.desktop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.caelum.tubaina.parser.pygments.CodeCache;
import br.com.caelum.tubaina.parser.pygments.CodeOutputType;
import br.com.caelum.tubaina.util.CommandExecutor;

public class SyntaxHighlighter {

    private final CommandExecutor commandExecutor;
    private boolean allLinesNumbered;
    private CodeOutputType output;
    
    public static final String HTML_OUTPUT = "html";
    public static final String LATEX_OUTPUT = "latex";
    
    private CodeCache codeCache;

    public SyntaxHighlighter(CommandExecutor commandExecutor, CodeOutputType outputType, boolean allLinesNumbered, CodeCache codeCache) {
        this.commandExecutor = commandExecutor;
        this.output = outputType;
        this.allLinesNumbered = allLinesNumbered;
        this.codeCache = codeCache;
    }

    public String highlight(String code, String language, boolean numbered, List<Integer> lines) {
        if (codeCache.exists(code)) {
            return codeCache.find(code);
        }
        ArrayList<String> commandWithArgs = buildCommand(language, numbered, lines);
        String codeHighlighted = commandExecutor.execute(commandWithArgs, code);
        
        codeCache.write(code, codeHighlighted);
        return codeHighlighted;
    }

    private ArrayList<String> buildCommand(String language, boolean numbered, List<Integer> lines) {
        StringBuilder options = new StringBuilder();
        if (numbered || allLinesNumbered) { // for kindle output all lines are numbered
            appendLineNumberingOption(options);
        }
        
        addLineHighlightOption(lines, options);
        
        String encoding = System.getProperty("file.encoding");
        
        ArrayList<String> commands = new ArrayList<String>();
        commands.add("pygmentize");
        commands.add("-O");
        commands.add("encoding=" + encoding + ",outencoding=UTF-8" + options);
        if (output.equals(CodeOutputType.LATEX)) {
            commands.add("-P");
            commands.add("verboptions=numbersep=5pt");
        }
        commands.add("-f");
        commands.add(output.pygmentsFormatterName());
        commands.add("-l");
        commands.add(language);
        return commands;
    }

    private void appendLineNumberingOption(StringBuilder options) {
        if (output.equals(CodeOutputType.HTML) || output.equals(CodeOutputType.KINDLE_HTML)) {
            options.append(",linenos=inline");
        } else {
            options.append(",linenos=yes");
        }
        
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
