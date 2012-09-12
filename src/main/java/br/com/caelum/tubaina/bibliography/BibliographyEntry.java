package br.com.caelum.tubaina.bibliography;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("bibliography-entry")
public class BibliographyEntry {
    public String author;
    public String title;
    public String year;
    public String howPublished;
    public String type;
    public String label;
    public String publisher;
    private final String journal;

    public BibliographyEntry(String author, String title, String year, String howPublished,
            String type, String label, String journal) {
        this.author = author;
        this.title = title;
        this.year = year;
        this.howPublished = howPublished;
        this.type = type;
        this.label = label;
        this.journal = journal;
    }

    @Override
    public String toString() {
        return "BibliographyEntry [label=" + label + "]";
    }
    
    //We still need these getters to use bibliography.ftl
    
    public String getJournal() {
        return journal;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getHowPublished() {
        return howPublished;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public String getPublisher() {
        return publisher;
    }

}
