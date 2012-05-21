package br.com.caelum.tubaina;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.chunk.IntroductionChunk;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.resources.Resource;

public class BookPart {
    
    private List<Chapter> chapters;
    private String title;
    private boolean printable;
    private String introduction;

    public BookPart(String title, boolean printableType, String introduction) {
        printable = printableType;
        this.introduction = introduction;
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
        List<Resource> resources = new ArrayList<Resource>();
        IntroductionChunk intro = new IntroductionChunk(new ChunkSplitter(resources, "all").splitChunks(introduction));
        return intro.getContent(p);
    }
    
    public String getIntroduction() {
        return this.introduction;
    }
}
