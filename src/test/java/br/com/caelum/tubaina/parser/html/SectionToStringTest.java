package br.com.caelum.tubaina.parser.html;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.Tubaina;
import br.com.caelum.tubaina.builder.SectionBuilder;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.html.HtmlParser;
import br.com.caelum.tubaina.parser.html.SectionToString;
import br.com.caelum.tubaina.resources.Resource;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;


public class SectionToStringTest {
	private SectionToString sectionToString;

	@Before
	public void setUp() throws IOException {
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(Tubaina.DEFAULT_TEMPLATE_DIR);
		cfg.setObjectWrapper(new BeansWrapper());

		Parser parser = new HtmlParser(new RegexConfigurator().read("/regex.properties", "/html.properties"), false);
		ArrayList<String> dirTree = new ArrayList<String>();
		dirTree.add("livro");
		dirTree.add("livro/01-capitulo");
		dirTree.add("livro/01-capitulo/01-primeira");
		dirTree.add("livro/01-capitulo/02-segunda");
		sectionToString = new SectionToString(parser, cfg, dirTree);
	}
	
	private Section createSection(String sectionText) {
		return new SectionBuilder("Title", sectionText, new ArrayList<Resource>()).build();
	}

	private int countOccurrences(String text, String substring) {
		String[] tokens = text.split(substring);
		return tokens.length - 1;
	}
	
	@Test
	public void testSection() throws IOException {
		Section section = createSection("este é o texto da seção");
		String string = sectionToString.generateSection("capitulo", 7, section, 4, 2).toString();
		System.out.println(string);
		Assert.assertEquals(1, countOccurrences(string, "class=\"sectionChapter\">(\\s)*capitulo(\\s)*<"));
		Assert.assertEquals(1, countOccurrences(string, "7.4 - Title"));
		Assert.assertEquals(1, countOccurrences(string, "este &eacute; o texto da se&ccedil;&atilde;o"));
	}
	
	
}
