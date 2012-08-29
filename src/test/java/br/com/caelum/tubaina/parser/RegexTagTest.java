package br.com.caelum.tubaina.parser;

import java.util.List;

public class RegexTagTest {
    protected List<Tag> regexTags;
    
    protected String parseWithRegexps(String text) {
        for (Tag tag : regexTags) {
            text = tag.parse(text, "");
        }
        return text;
    }

}
