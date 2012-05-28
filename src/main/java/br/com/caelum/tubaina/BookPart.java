package br.com.caelum.tubaina;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.tubaina.chunk.IntroductionChunk;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.resources.Resource;

public class BookPart {

    private List<Chapter> chapters;
    private String title;
    private boolean printable;
    private String introductionText;
    private Chunk introductionChunk;
    private final List<Resource> resources;

    public BookPart(String title, boolean printableType, String introduction,
            IntroductionChunk introductionChunk, List<Resource> resources) {
        printable = printableType;
        this.introductionText = introduction;
        this.introductionChunk = introductionChunk;
        this.resources = resources;
        this.chapters = new ArrayList<Chapter>();
        this.title = title;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public String getTitle() {
        return this.title;
    }

    public void addAllChapters(List<Chapter> chapters) {
        this.chapters.addAll(chapters);
    }

    public boolean isPrintable() {
        return printable;
    }

    public String getIntroduction(Parser p) {
        return introductionChunk == null ? "" : introductionChunk.getContent(p);
    }

    public String getIntroductionText() {
        return this.introductionText;
    }
    
    public List<Resource> getResources() {
        return resources;
    }
}
