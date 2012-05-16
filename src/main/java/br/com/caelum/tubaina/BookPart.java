package br.com.caelum.tubaina;

import java.util.ArrayList;
import java.util.List;

public class BookPart {
    
    private List<Chapter> chapters;
    private String title;
    private boolean printablePart;

    public BookPart(String title, boolean printableType) {
        printablePart = printableType;
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
    
    public boolean isPrintablePart() {
        return printablePart;
    }
}
