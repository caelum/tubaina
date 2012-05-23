package br.com.caelum.bibliography;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("bibliography")
public class Bibliography {
    private List<BibliographyEntry> entries;

    public Bibliography(List<BibliographyEntry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "Bibliography [entries=" + entries + "]";
    }

    public List<BibliographyEntry> getEntries() {
        return entries;
    }

}
