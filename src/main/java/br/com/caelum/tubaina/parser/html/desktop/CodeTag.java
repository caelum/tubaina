package br.com.caelum.tubaina.parser.html.desktop;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.util.CommandExecutor;

public class CodeTag implements Tag {

	public static final String START = "<div>";
	public static final String END = "\n</div>";
    private HtmlCodeHighlighter htmlCodeHighlighter;
    
    public CodeTag() {
        this.htmlCodeHighlighter = new HtmlCodeHighlighter(new CommandExecutor());
    }
    
    public CodeTag(HtmlCodeHighlighter htmlCodeHighlighter) {
        this.htmlCodeHighlighter = htmlCodeHighlighter;
    }

	public String parse(String content, String options) {
	    String language = detectLanguage(options);
	    List<Integer> highlights = detectHighlights(options);
	    boolean numbered = options.contains("#");
	    Matcher labelMatcher = Pattern.compile("label=(\\S+)").matcher(options);
	    
	    if (!highlights.isEmpty()) {
	        throw new TubainaException("Code highlights are not supported for html output yet");
	    }
	    if (labelMatcher.matches()) {
	        throw new TubainaException("Code labels are not supported for html output yet");
	    }
	    
	    String code = htmlCodeHighlighter.highlight(content, language, numbered);
	    
		return START + code + END;
	}
	
	private String detectLanguage(String options) {
		if (options != null) {
			String languageCandidate = options.trim().split(" ")[0];
			if (!languageCandidate.contains("#") && !languageCandidate.startsWith("h=") && !languageCandidate.isEmpty())
				return languageCandidate;
		}
		return "text";
	}
	
	private List<Integer> detectHighlights(String options) {
	    ArrayList<Integer> lines = new ArrayList<Integer>();
		Pattern pattern = Pattern.compile("h=([\\d+,]+)");
		Matcher matcher = pattern.matcher(options);
		if (matcher.find()) {
			String[] strings = matcher.group(1).split(",");
			for (String string : strings) {
                lines.add(Integer.parseInt(string));
            }
		}
		return lines;
	}
	
}
