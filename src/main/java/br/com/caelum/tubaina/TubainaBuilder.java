package br.com.caelum.tubaina;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.log4j.Logger;

import br.com.caelum.tubaina.builder.BookBuilder;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.FlatHtmlGenerator;
import br.com.caelum.tubaina.parser.html.HtmlGenerator;
import br.com.caelum.tubaina.parser.html.HtmlParser;
import br.com.caelum.tubaina.parser.latex.LatexGenerator;
import br.com.caelum.tubaina.parser.latex.LatexParser;
import br.com.caelum.tubaina.resources.ResourceLocator;

public class TubainaBuilder {

	public static final File DEFAULT_TEMPLATE_DIR = new File("templates");

	public static final Logger LOG = Logger.getLogger(TubainaBuilder.class);

	public static final int MAX_LINE_LENGTH = 93; // believe us... this is what
	// fits in Latex A4 templates.

	private File inputDir = new File(".");

	private File outputDir = new File(".");

	private final String bookName;

	private boolean strictXhtml = false;

	private File templateDir = DEFAULT_TEMPLATE_DIR;

	private final ParseTypes parseType;

	private boolean showNotes = false;

	private boolean dontCare = false;

	private boolean noAnswer = false;

	private String outputFileName = "book.tex";

	public TubainaBuilder(ParseTypes type, String bookName) {
		this.parseType = type;
		this.bookName = bookName;

	}

	public void build() throws IOException {

		ResourceLocator.initialize(inputDir);
		List<Reader> readers = getAfcsFrom(inputDir);
		BookBuilder builder = new BookBuilder(bookName);
		builder.addAll(readers);

		Book b = null;
		try {
			b = builder.build(showNotes);
		} catch (TubainaException e) {
			if (dontCare) {
				LOG.warn(e);
			} else {
				e.printStackTrace();
				System.exit(-1);
			}
		}

		RegexConfigurator conf = new RegexConfigurator();
		if (parseType.equals(ParseTypes.LATEX)) {
			List<Tag> tags = conf
					.read("/regex.properties", "/latex.properties");

			Parser parser = new LatexParser(tags, showNotes, noAnswer);
			LatexGenerator generator = new LatexGenerator(parser, templateDir,
					noAnswer);
			File file = new File(outputDir, "latex");
			FileUtils.forceMkdir(file);
			try {
				generator.generate(b, file, outputFileName);
			} catch (TubainaException e) {
				LOG.warn(e.getMessage());
			}
		}

		if (parseType.equals(ParseTypes.HTML)) {
			HtmlParser htmlParser = new HtmlParser(conf.read(
					"/regex.properties", "/html.properties"), noAnswer);
			HtmlGenerator generator = new HtmlGenerator(htmlParser,
					strictXhtml, templateDir);
			File file = new File(outputDir, "html");
			FileUtils.forceMkdir(file);
			try {
				generator.generate(b, file);
			} catch (TubainaException e) {
				LOG.warn(e.getMessage());
			}
		}

		if (parseType.equals(ParseTypes.HTMLFLAT)) {
			HtmlParser htmlParser = new HtmlParser(conf.read(
					"/regex.properties", "/html.properties"), noAnswer);
			FlatHtmlGenerator generator = new FlatHtmlGenerator(htmlParser,
					strictXhtml, templateDir);
			File file = new File(outputDir, "htmlflat");
			FileUtils.forceMkdir(file);
			try {
				generator.generate(b, file);
			} catch (TubainaException e) {
				LOG.warn(e.getMessage());
			}
		}

		
	}

	static List<Reader> getAfcsFrom(final File file)
			throws UnsupportedEncodingException, FileNotFoundException {
		List<Reader> readers = new ArrayList<Reader>();
		List<String> files = new ArrayList<String>();
		Collections.addAll(files, file.list(new SuffixFileFilter(".afc")));
		Collections.sort(files);
		for (String s : files) {
			readers.add(new InputStreamReader(new FileInputStream(new File(
					file, s)), "UTF-8"));
		}
		return readers;
	}

	public TubainaBuilder setInputDir(File inputDir) {
		this.inputDir = inputDir;
		return this;
	}

	public TubainaBuilder setOutputDir(File outputDir) {
		this.outputDir = outputDir;
		return this;
	}

	public TubainaBuilder strictXhtml() {
		this.strictXhtml = true;
		return this;
	}

	public TubainaBuilder setTemplateDir(File templateDir) {
		this.templateDir = templateDir;
		return this;
	}

	public TubainaBuilder showNotes() {
		this.showNotes = true;
		return this;
	}

	public TubainaBuilder dontCare() {
		this.dontCare = true;
		return this;
	}

	public TubainaBuilder noAnswer() {
		this.noAnswer = true;
		return this;
	}

	public TubainaBuilder setOutputFileName(String fileName) {
		this.outputFileName = fileName;
		return this;
	}

}
