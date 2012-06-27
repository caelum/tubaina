package br.com.caelum.tubaina.parser.html.desktop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.builder.ChapterBuilder;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class ChapterToStringTest {

	private ChapterToString chapterToString;

	private String sectionIdentifier;

	@Before
	public void setUp() throws IOException {
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR, "html/"));
		cfg.setObjectWrapper(new BeansWrapper());

		Parser parser = new HtmlParser(new RegexConfigurator().read("/regex.properties", "/html.properties"), false);
		ArrayList<String> dirTree = new ArrayList<String>();
		dirTree.add("livro");
		dirTree.add("livro/01-capitulo");
		dirTree.add("livro/01-capitulo/01-primeira");
		dirTree.add("livro/01-capitulo/02-segunda");

		chapterToString = new ChapterToString(parser, cfg, dirTree);
		sectionIdentifier = "class=\"indexSection\"";
	}

	private Chapter createChapter(final String introduction, final String chapterText) {
		return new ChapterBuilder("Title", introduction, chapterText, 0).build();
	}

	private int countOccurrences(final String text, final String substring) {
		String[] tokens = text.split(substring);
		return tokens.length - 1;
	}

	
}
