package br.com.caelum.tubaina.bibliography;

import java.io.File;

import com.thoughtworks.xstream.XStream;

public class BibliographyFactory {

    private XStream xstream;

    public BibliographyFactory() {
        xstream = new XStream();
    }
    
    public Bibliography build(File xmlFile) {
        if (!xmlFile.exists()) {
            return new Bibliography();
        }
        setupXStream();

        Bibliography bibliography = (Bibliography) xstream.fromXML(xmlFile);
        return bibliography;
    }

    private void setupXStream() {
        xstream.addImplicitCollection(Bibliography.class, "entries");
        xstream.alias("bibliography", Bibliography.class);
        xstream.alias("bibliography-entry", BibliographyEntry.class);
    }
}
