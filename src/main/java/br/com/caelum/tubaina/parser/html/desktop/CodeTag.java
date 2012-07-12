package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.HtmlAndKindleCodeTag;

public class CodeTag implements Tag {

    private HtmlAndKindleCodeTag htmlCodeTag;

    public CodeTag() {
        htmlCodeTag = new HtmlAndKindleCodeTag();
    }
    
    public CodeTag(HtmlAndKindleCodeTag htmlCodeTag) {
        this.htmlCodeTag = htmlCodeTag;
    }

    public String parse(String content, String options) {
        return htmlCodeTag.parse(content, options);
    }

}
