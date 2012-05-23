package br.com.caelum.bibliography;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class BibliographyEntryTest {

    @Test
    public void shouldParseABibliographyFromXML() throws Exception {

        XStream xstream = new XStream();
        xstream.addImplicitCollection(Bibliography.class, "entries");
        xstream.alias("bibliography", Bibliography.class);
        xstream.alias("bibliography-entry", BibliographyEntry.class);

        Bibliography bibliography = (Bibliography) xstream.fromXML(new File(
                "src/test/resources/bib.xml"));
        
        assertEquals("livro-jose", bibliography.getEntries().get(0).getLabel());
        assertEquals("artigo-jose", bibliography.getEntries().get(1).getLabel());
        
        
        
    }

}
