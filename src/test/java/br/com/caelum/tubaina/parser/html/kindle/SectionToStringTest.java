package br.com.caelum.tubaina.parser.html.kindle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.builder.ChapterBuilder;
import br.com.caelum.tubaina.builder.SectionBuilder;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.html.desktop.HtmlParser;
import br.com.caelum.tubaina.resources.Resource;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class SectionToStringTest {
    private SectionToString sectionToString;

    @Before
    public void setUp() throws IOException {
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR, "kindle/"));
        cfg.setObjectWrapper(new BeansWrapper());

        Parser parser = new HtmlParser(new RegexConfigurator().read("/regex.properties",
                "/kindle.properties"), false);
        ArrayList<String> dirTree = new ArrayList<String>();
        sectionToString = new SectionToString(parser, cfg, dirTree);
    }

    private Section createSection(final String sectionText, String sectionTitle) {
        return new SectionBuilder(sectionTitle, sectionText, new ArrayList<Resource>()).build();
    }

    private int countOccurrences(final String text, final String substring) {
        String[] tokens = text.split(substring);
        return tokens.length - 1;
    }

    @Test
    public void testSection() {
        String sectionTitle = "Section title";
        Section section = createSection("este é o texto da seção", sectionTitle);
        String generatedContent = sectionToString.generateKindleHtmlSection(section,
                new ChapterBuilder("titulo do capitulo", "intro", "content").build(), 1).toString();
        Assert.assertEquals(1,
                countOccurrences(generatedContent, "1.1 - " + sectionTitle));
    }

}
