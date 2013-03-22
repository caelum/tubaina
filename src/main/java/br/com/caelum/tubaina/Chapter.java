package br.com.caelum.tubaina;

import java.util.List;

import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.resources.Resource;
import br.com.caelum.tubaina.util.TitleShortener;
import br.com.caelum.tubaina.util.TitleSlug;

public class Chapter {
	
	private String title;

	private List<Section> sections;

	private List<Resource> resources;

	private Chunk introduction;
	
	private int chapterNumber;

    private final boolean introductionChapter;

    private String titleSlug;

    private final String label;

	public Chapter(String title, String label, Chunk introduction, List<Section> sections, List<Resource> resources, int chapterNumber, boolean introductionChapter) {
		this.title = title;
        this.label = label;
		this.sections = sections;
		this.resources = resources;
		this.introduction = introduction;
        this.chapterNumber = chapterNumber;
        this.introductionChapter = introductionChapter;
        this.titleSlug = new TitleSlug(title).toString();
	}

	public List<Section> getSections() {
		return sections;
	}

	public String getTitle() {
		return title;
	}

	public List<Resource> getResources() {
		return resources;
	}
	
	public String getIntroduction(Parser p){
		return this.introduction.asString();
	}
	
	public int getChapterNumber() {
        return chapterNumber;
    }
	
	public boolean isIntroductionChapter() {
        return introductionChapter;
    }
	
	public String getTitleSlug() {
        return titleSlug;
    }
	
	public String getShortTitle(int maxLength) {
	    return new TitleShortener(maxLength).shortenTitle(title);
	}
	
	public String getLabel() {
        return label;
    }
}
