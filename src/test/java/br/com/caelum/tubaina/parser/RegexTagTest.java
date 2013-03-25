package br.com.caelum.tubaina.parser;

import java.util.List;

public class RegexTagTest {
    protected List<RegexTag> regexTags;
    
    protected String parseWithRegexps(String text) {
        for (RegexTag tag : regexTags) {
            text = tag.parse(text);
        }
        return text;
    }

}
