package br.com.caelum.tubaina.parser.html;

import java.util.Scanner;

import br.com.caelum.tubaina.chunk.ListChunk;
import br.com.caelum.tubaina.parser.Tag;

public class KindleListTagTemplate implements Tag<ListChunk> {

	private static final String ITEM_DELIMITER = "<p class=\"item\">";

	@Override
	public String parse(ListChunk chunk) {
		String options = chunk.getType();
		String content = chunk.getContent();
		if (options.contains("number"))
			return "<ol>" + content + "</ol>";
		if (options.contains("letter")) {
			content = sanitizeLetterListForEpubAndMobi(content);
			return "<div>" + content + "</div>";
		
		} if (options.contains("roman"))
			return "<ol class=\"roman\">" + content + "</ol>";
		
		return "<ul>" + content.trim() + "</ul>";

	}

	private String sanitizeLetterListForEpubAndMobi(String content) {
		content = content.replaceAll("<li>", "<p class=\"item\">####) ").replaceAll("</li>", "</p>");
		
		String newContent = "";
		Scanner sc = new Scanner(content);
		sc.useDelimiter(ITEM_DELIMITER);
		char indexLetter = 'a';
		while(sc.hasNext()){
			String line = sc.next();
			newContent += line.replaceAll("####", ITEM_DELIMITER + indexLetter + "");
			indexLetter++;
		}
		
		return newContent;
	}

}
