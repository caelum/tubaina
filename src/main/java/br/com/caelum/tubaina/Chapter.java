package br.com.caelum.tubaina;

import java.util.List;

import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.resources.Resource;

public class Chapter {

	private static int COUNT = 1;
	
	private String title;

	private List<Section> sections;

	private List<Resource> resources;

	private Chunk introduction;
	
	private int chapterNumber;

	public Chapter(String title, Chunk introduction, List<Section> sections, List<Resource> resources) {
		this.title = title;
		this.sections = sections;
		this.resources = resources;
		this.introduction = introduction;
		chapterNumber = COUNT;
		COUNT++;
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
		return this.introduction.getContent(p);
	}
	
	public static int getChaptersCount() {
		return COUNT;
	}
	
	public int getChapterNumber() {
        return chapterNumber;
    }
}
