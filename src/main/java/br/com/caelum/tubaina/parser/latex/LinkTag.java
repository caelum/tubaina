package br.com.caelum.tubaina.parser.latex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;

public class LinkTag implements Tag {
    
    private final String replace;

    public LinkTag(String replace) {
        this.replace = replace;
    }

    @Override
    public String parse(Chunk chunk) {
        String r = "(?s)(?i)((?:https?|ftp|file)://[-a-zA-Z0-9+&@#}{/?=~_|!:,.;\\\\]*[-a-zA-Z0-9+&@#/}{%=~_|])(:|;|,|\\.|\"|'|\\(|\\)|<|>|\\s|%%|$$)";
        Pattern regex = Pattern.compile(r);
        Matcher matcher = regex.matcher(content);
        String text = matcher.replaceAll(replace);
        return text;
    }

}
