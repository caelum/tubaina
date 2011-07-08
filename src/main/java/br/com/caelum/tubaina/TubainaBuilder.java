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
import br.com.caelum.tubaina.parser.html.FlatHtmlGenerator;
import br.com.caelum.tubaina.parser.html.Generator;
import br.com.caelum.tubaina.parser.html.HtmlGenerator;
import br.com.caelum.tubaina.parser.html.SingleHtmlGenerator;
import br.com.caelum.tubaina.parser.latex.LatexGenerator;
import br.com.caelum.tubaina.resources.ResourceLocator;

public class TubainaBuilder {

	public static final File DEFAULT_TEMPLATE_DIR = new File("templates");

	public static final Logger LOG = Logger.getLogger(TubainaBuilder.class);

	public static final int MAX_LINE_LENGTH = 93; // believe us... this is what
	// fits in Latex A4 templates.

	private File inputDir = new File(".");

	private File outputDir = new File(".");

	private String bookName = "book";

	private boolean strictXhtml = false;

	private File templateDir = DEFAULT_TEMPLATE_DIR;

	private final ParseType parseType;

	private boolean showNotes = false;

	private boolean dontCare = false;

	private boolean noAnswer = false;

	private String outputFileName = "book.tex";

	public TubainaBuilder(ParseType type) {
		this.parseType = type;
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
		
		Parser parser = parseType.getParser(conf, noAnswer, showNotes);
		
		Generator generator = null;
		if (parseType.equals(ParseType.LATEX)) {
			generator = new LatexGenerator(parser, templateDir, noAnswer, outputFileName);
		}

		if (parseType.equals(ParseType.HTML)) {
			generator = new HtmlGenerator(parser, strictXhtml, templateDir);
		}

		if (parseType.equals(ParseType.HTMLFLAT)) {
			generator = new FlatHtmlGenerator(parser, strictXhtml, templateDir);
		}
		if (parseType.equals(ParseType.SINGLEHTML)) {
			generator = new SingleHtmlGenerator(parser, templateDir);
		}
		
		File file = new File(outputDir,parseType.getType());
		FileUtils.forceMkdir(file);
		try {
			generator.generate(b, file);
		} catch (TubainaException e) {
			LOG.warn(e.getMessage());
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

	public TubainaBuilder inputDir(File inputDir) {
		this.inputDir = inputDir;
		return this;
	}

	public TubainaBuilder outputDir(File outputDir) {
		this.outputDir = outputDir;
		return this;
	}

	public TubainaBuilder strictXhtml() {
		this.strictXhtml = true;
		return this;
	}

	public TubainaBuilder templateDir(File templateDir) {
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

	public TubainaBuilder outputFileName(String fileName) {
		this.outputFileName = fileName;
		return this;
	}
	
	public TubainaBuilder bookName(String bookName) {
		this.bookName = bookName;
		return this;
	}

}
