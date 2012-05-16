package br.com.caelum.tubaina;

import java.util.List;

public class BookPart {
    
    private List<Chapter> chapters;

    public BookPart(List<Chapter> chapters) {
        this.chapters = chapters;
    }
    
    public List<Chapter> getChapters() {
        return chapters;
    }
}
