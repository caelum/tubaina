package br.com.caelum.tubaina.builder.replacer;

import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.builder.ChunksMakerBuilder;

public abstract class AbstractReplacer implements Replacer {

	private final Pattern openPattern;
	private Pattern tagPattern;
	private final String tag;

	public AbstractReplacer(String tag) {
		this.tag = tag;
		this.openPattern = Pattern.compile("(?s)(?i)\\A\\s*\\["+tag+"(.*?)\\]");
		this.tagPattern = Pattern.compile("(?s)(?i)\\[(/)?(\\w+)(.*?)\\]");
	}

	public String execute(String text, List<Chunk> chunks) {
		Matcher matcher = openPattern.matcher(text);
		matcher.find();
		String options = matcher.group(1);
		
		int contentStart = matcher.end();
		
		Matcher tagMatcher = tagPattern.matcher(text.substring(contentStart));
		
		Stack<String> stack = new Stack<String>();
		stack.push(tag);
		while (!stack.isEmpty() && tagMatcher.find()) {
			String tagName = tagMatcher.group(2);
			if (tagMatcher.group(1) == null) { //is openTag ([tag])
				if (ChunksMakerBuilder.isClosableTag(tagName)) {
					stack.push(tagName);					
				}
			} else { //is closeTag ([/tag])
				if (stack.peek().equalsIgnoreCase(tagName)) {
					stack.pop();
				} else {
					throw new TubainaException("Tag " + tagName + " was closed unproperly");
				}
			}
		}
		if (!stack.isEmpty()) { //tag was not closed properly
			throw new TubainaException("There is(are) unclosed tag(s) : " + stack.toString());
		}
		String content = text.substring(contentStart, contentStart + tagMatcher.start());
		
		chunks.add(createChunk(options, content));
		return text.substring(contentStart + tagMatcher.end());
	}

	protected abstract Chunk createChunk(String options, String content);

	public boolean accepts(String text) {
		Matcher matcher = openPattern.matcher(text);
		return matcher.find();
	}

}
