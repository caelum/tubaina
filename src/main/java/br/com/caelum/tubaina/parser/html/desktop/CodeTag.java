package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.HtmlCodeTag;

public class CodeTag implements Tag {

    private HtmlCodeTag htmlCodeTag;

    public CodeTag() {
        htmlCodeTag = new HtmlCodeTag();
    }
    
    public CodeTag(HtmlCodeTag htmlCodeTag) {
        this.htmlCodeTag = htmlCodeTag;
    }

    public String parse(String content, String options) {
        return htmlCodeTag.parse(content, options);
    }

}
