package br.com.caelum.tubaina.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.chunk.IntroductionChunk;
import br.com.caelum.tubaina.resources.Resource;

public class ChapterBuilder {

    private final String title;

    private final String content;

    private final Matcher matcher;

    private final String introduction;

    private final int chapterNumber;

    private static int LAST_CHAPTER = 0;

    private final boolean introductionChapter;

    private final String label;

	private final SectionsManager sectionsManager;

    public ChapterBuilder(String title, String label, String introduction, String content, int chapterNumber,
            boolean introductionChapter, SectionsManager sectionsManager) {
        this.title = title;
        this.label = label;
        this.content = content;
        this.introduction = introduction;
        this.chapterNumber = chapterNumber;
        this.introductionChapter = introductionChapter;
		this.sectionsManager = sectionsManager;
        Pattern pattern = Pattern.compile("(?i)(?s)(?m)^\\[section(.*?)\\](.*?)(\n\\[section|\\z)");
        this.matcher = pattern.matcher(content);
        if (!introductionChapter)
            LAST_CHAPTER = chapterNumber;
    }

    public ChapterBuilder(String title, String introduction, String content, int chapterNumber, SectionsManager sectionsManager) {
        this(title, "", introduction, content, chapterNumber, false, sectionsManager);
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
                    Section section = new SectionBuilder(sectionTitle, sectionContent, resources, sectionsManager)
                            .build();
                    sections.add(section);
                    increaseSectionCount();
                }
                offset = matcher.end(2);
            }
        }

        IntroductionChunk intro = new IntroductionChunk(new ChunkSplitter(resources, "all", sectionsManager).splitChunks(introduction));
        return new Chapter(title, label, intro, sections, resources, chapterNumber, introductionChapter);
    }

	private void increaseSectionCount() {
		if (!introductionChapter) {
			sectionsManager.nextSection();
		}
	}

    public static int getChaptersCount() {
        return LAST_CHAPTER;
    }

    /**
     * @deprecated exists only for testing purposes
     */
    public static void restartChapterCounter() {
    	LAST_CHAPTER = 0;
    }
}
