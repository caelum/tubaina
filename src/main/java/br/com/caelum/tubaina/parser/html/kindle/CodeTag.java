package br.com.caelum.tubaina.parser.html.kindle;

import br.com.caelum.tubaina.parser.Tag;

public class CodeTag implements Tag {

    public String parse(String content, String options) {
        String indentedCode = content.replaceAll(" ", "&nbsp;");
        indentedCode = indentedCode.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        return "<pre class=\"" + detectLanguage(options) + "\">\n"
                + indentedCode + "\n</pre>";
    }

    private String detectLanguage(String options) {
        if (options != null) {
            String languageCandidate = options.trim().split(" ")[0];
            if (!languageCandidate.contains("#") && !languageCandidate.startsWith("h=")
                    && !languageCandidate.isEmpty())
                return languageCandidate;
        }
        return "text";
    }

}
