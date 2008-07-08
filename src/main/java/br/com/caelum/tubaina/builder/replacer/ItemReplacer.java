package br.com.caelum.tubaina.builder.replacer;

import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.builder.ChunksMakerBuilder;
import br.com.caelum.tubaina.chunk.ItemChunk;
import br.com.caelum.tubaina.resources.Resource;

public class ItemReplacer implements Replacer {

	private final List<Resource> resources;

	private final Pattern pattern;

	public ItemReplacer(final List<Resource> resources) {
		pattern = Pattern.compile("(?m)^\\s*\\*([^\\*])");
		this.resources = resources;
	}

	public Chunk createChunk(final String content) {
		return new ItemChunk(new ChunkSplitter(resources, "item").splitChunks(content));
	}

	public String execute(final String text, final List<Chunk> chunks) {
		Pattern tagPattern = Pattern.compile("(?s)(?i)(?m)(^\\s*\\*([^\\*])|\\[(/)?(\\w+)(.*?)\\])");
		Matcher matcher = tagPattern.matcher(text);
		Stack<String> stack = new Stack<String>();
		int chunkEnd = -1;
		int chunkStart = -1;
		while (matcher.find()) {
			if (matcher.group(1).trim().charAt(0) == '*') { // item found
				if (stack.isEmpty()) {
					if (chunkStart < 0) {
						chunkStart = matcher.start(2);
					} else {
						chunkEnd = matcher.start();
						break;
					}
				}
			} else if (matcher.group(3) == null) { // open tag found
				if (ChunksMakerBuilder.isClosableTag(matcher.group(4))) {
					stack.push(matcher.group(4));
				}
			} else { // close tag found
				if (!stack.isEmpty() && stack.peek().equalsIgnoreCase(matcher.group(4))) {
					stack.pop();
				} else {
					throw new TubainaException("Tag " + matcher.group(4) + " closed unproperly");
				}
			}
		}

		if (!stack.isEmpty()) {
			throw new TubainaException("There is(are) unclosed tag(s) : " + stack.toString());
		}

		if (chunkStart < 0) { // if item was not found
			throw new TubainaException("There is no item inside a list tag on chapter " + Chapter.getChaptersCount());
			// chunks.add(new ParagraphChunk(text));
			// return "";
		}
		String textBefore = text.substring(0, chunkStart - 1).trim();
		if (textBefore.length() > 0) {
			throw new TubainaException("There is some inside a list tag, but outside an item on chapter "
					+ Chapter.getChaptersCount());
			// chunks.add(new ParagraphChunk(textBefore));
		}

		if (chunkEnd < 0) {
			chunks.add(createChunk(text.substring(chunkStart).trim()));
			return "";
		}
		chunks.add(createChunk(text.substring(chunkStart, chunkEnd)));
		return text.substring(chunkEnd);

	}

	public boolean accepts(final String text) {
		Matcher matcher = pattern.matcher(text);
		return matcher.find();
	}
}
