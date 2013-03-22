package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;

public class IndexTag implements Tag {

    public String parse(Chunk chunk) {
        return "\n\\index{" + escapeUnderscores(content) + "}\n";
    }

    public String escapeUnderscores(String content) {
        return content.replaceAll("\\_", "\\\\_");
    }

}
