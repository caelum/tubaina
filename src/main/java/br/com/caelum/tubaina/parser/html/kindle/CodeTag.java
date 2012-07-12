package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.HtmlCodeTag;

public class CodeTag implements Tag {

    private final HtmlCodeTag htmlCodeTag;
    
    public CodeTag() {
        this.htmlCodeTag = new HtmlCodeTag();
    }

    public CodeTag(HtmlCodeTag htmlCodeTag) {
        this.htmlCodeTag = htmlCodeTag;
    }

    public String parse(String content, String options) {
        return htmlCodeTag.parse(content, options);
    }

}
