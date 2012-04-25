package br.com.caelum.tubaina.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.TubainaSyntaxErrorsException;
import br.com.caelum.tubaina.chunk.IntroductionChunk;
import br.com.caelum.tubaina.resources.Resource;

public class ChapterBuilder {

	private final String title;

	private final String content;

	private final Matcher matcher;

	private final String introduction;

	public ChapterBuilder(String title, String introduction, String content) {
		this.title = title;
		this.content = content;
		this.introduction = introduction;
		Pattern pattern = Pattern.compile("(?i)(?s)(?m)^\\[section(.*?)\\](.*?)(\n\\[section|\\z)");
		this.matcher = pattern.matcher(content);
	}

	public Chapter build() {

		List<Section> sections = new ArrayList<Section>();
		List<Resource> resources = new ArrayList<Resource>();
		List<TubainaException> exceptions = new ArrayList<TubainaException>();
		if (content != null && content.trim().length() > 0) {

			Integer offset = 0;

			while (matcher.find(offset)) {
				String sectionTitle = matcher.group(1);
				if (sectionTitle != null) {
					sectionTitle = sectionTitle.trim();
				}
				String sectionContent = matcher.group(2);
				if (sectionTitle != null || sectionContent.trim().length() > 0) {
					try {
					    Section section = new SectionBuilder(sectionTitle, sectionContent, resources).build();
					    sections.add(section);
					} catch (TubainaSyntaxErrorsException e) {
					    exceptions.add(new TubainaSyntaxErrorsException("There are syntax errors on section named \"" 
					                + sectionTitle + "\"", e));
					}
				}
				offset = matcher.end(2);
			}
			if (!exceptions.isEmpty()) {
	            throw new TubainaSyntaxErrorsException("There are syntax errors on a chapter. See the messages below.", exceptions);
	        }
		}

		IntroductionChunk intro = new IntroductionChunk(new ChunkSplitter(resources, "all").splitChunks(introduction));

		return new Chapter(title, intro, sections, resources);
	}

}
