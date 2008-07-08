package br.com.caelum.tubaina.builder.replacer;

import java.util.List;
import java.util.regex.Matcher;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.chunk.IndexChunk;
import br.com.caelum.tubaina.resources.IndexResource;
import br.com.caelum.tubaina.resources.Resource;

public class IndexReplacer extends PatternReplacer {

	private final List<Resource> resources;

	public IndexReplacer(List<Resource> resources) {
		super("(?s)(?i)\\A\\s*\\[index \\s*(.*?)\\s*\\]");
		this.resources = resources;
	}

	@Override
	public Chunk createChunk(Matcher matcher) {
		int dirNumber = Chapter.getChaptersCount() + Section.getSectionsCount();
		IndexResource resource = new IndexResource(matcher.group(1), dirNumber);
		resources.add(resource);
		return new IndexChunk(matcher.group(1));
	}

}
