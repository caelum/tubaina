package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.parser.Tag;

public class IndexTag implements Tag {

    public String parse(String content, String options) {
        return "\n\\index{" + escapeUnderscores(content) + "}\n";
    }

    public String escapeUnderscores(String content) {
        return content.replaceAll("\\_", "\\\\_");
    }

}
