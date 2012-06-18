package br.com.caelum.tubaina.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.chunk.IntroductionChunk;
import br.com.caelum.tubaina.resources.Resource;

public class ChapterBuilder {

	private final String title;

	private final String content;

	private final Matcher matcher;

	private final String introduction;

    private final int chapterNumber;
    
    private static int LAST_CHAPTER = 0; 

	public ChapterBuilder(String title, String introduction, String content, int chapterNumber) {
		this.title = title;
		this.content = content;
		this.introduction = introduction;
        this.chapterNumber = chapterNumber;
		Pattern pattern = Pattern.compile("(?i)(?s)(?m)^\\[section(.*?)\\](.*?)(\n\\[section|\\z)");
		this.matcher = pattern.matcher(content);
		LAST_CHAPTER = chapterNumber;
	}

	public Chapter build() {

		List<Section> sections = new ArrayList<Section>();
		List<Resource> resources = new ArrayList<Resource>();
		if (content != null && content.trim().length() > 0) {

			Integer offset = 0;

			while (matcher.find(offset)) {
				String sectionTitle = matcher.group(1);
				if (sectionTitle != null) {
					sectionTitle = sectionTitle.trim();
				}
				String sectionContent = matcher.group(2);
				if (sectionTitle != null || sectionContent.trim().length() > 0) {
					Section section = new SectionBuilder(sectionTitle, sectionContent, resources).build();
					sections.add(section);
				}
				offset = matcher.end(2);
			}
		}

		IntroductionChunk intro = new IntroductionChunk(new ChunkSplitter(resources, "all").splitChunks(introduction));

		return new Chapter(title, intro, sections, resources, chapterNumber);
	}
	
	public static int getChaptersCount() {
        return LAST_CHAPTER;
    }

}
