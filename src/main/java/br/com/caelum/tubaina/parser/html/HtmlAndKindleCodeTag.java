package br.com.caelum.tubaina.parser.html;

import java.util.List;

import com.google.inject.Inject;

import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.parser.SimpleIndentator;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.desktop.SyntaxHighlighter;

public class HtmlAndKindleCodeTag implements Tag<CodeChunk> {

	public static final String BEGIN_START = "<pre ";
	public static final String BEGIN_END = ">";
	public static final String END = "</pre>";
	private SyntaxHighlighter htmlCodeHighlighter;
	private CodeTagOptionsParser codeTagOptionsParser;

	@Inject
	public HtmlAndKindleCodeTag(SyntaxHighlighter htmlCodeHighlighter) {
		codeTagOptionsParser = new CodeTagOptionsParser();
		this.htmlCodeHighlighter = htmlCodeHighlighter;
	}

	@Override
	public String parse(CodeChunk chunk) {
		String options = chunk.getOptions();
		String language = detectLanguage(options);
		List<Integer> highlights = detectHighlights(options);
		boolean numbered = options.contains("#");
		SimpleIndentator simpleIndentator = new SimpleIndentator(2);
		String indentedCode = simpleIndentator.indent(chunk.getContent());
		String label = matchLabel(options);

		String code = htmlCodeHighlighter.highlight(indentedCode, language, numbered, highlights);

		String result = "";
		if (!label.isEmpty()) {
			result = BEGIN_START + "id='" + label + "'" + BEGIN_END + code + END;
		} else {
			result = BEGIN_START + BEGIN_END + code + END;
		}
		return result;
	}

	private String detectLanguage(String options) {
		return codeTagOptionsParser.parseLanguage(options);
	}

	private List<Integer> detectHighlights(String options) {
		return codeTagOptionsParser.parseHighlights(options);
	}

	private String matchLabel(String options) {
		return codeTagOptionsParser.parseLabel(options);
	}

}
