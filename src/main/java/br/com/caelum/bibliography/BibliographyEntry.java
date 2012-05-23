package br.com.caelum.bibliography;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("bibliography-entry")
public class BibliographyEntry {
    private String author;
    private String title;
    private String year;
    private String howPublished;
    private String type;
    private String label;

    public BibliographyEntry(String author, String title, String year, String howPublished,
            String type, String label) {
        this.author = author;
        this.title = title;
        this.year = year;
        this.howPublished = howPublished;
        this.type = type;
        this.label = label;
    }

    public String getLabel() {
        return label;
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

    @Override
    public String toString() {
        return "BibliographyEntry [label=" + label + "]";
    }

}
