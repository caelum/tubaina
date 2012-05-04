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
import br.com.caelum.tubaina.parser.html.desktop.Generator;
import br.com.caelum.tubaina.resources.ResourceLocator;

public class TubainaBuilder {

	public static final File DEFAULT_TEMPLATE_DIR = new File("templates");

	public static final Logger LOG = Logger.getLogger(TubainaBuilder.class);

	private static Integer codeLength = 93; // believe us... this is what
	// fits in Latex A4 templates.
	
	private static Integer maximumImageWidth = 175;

	private File inputDir = new File(".");

	private File outputDir = new File(".");

	private String bookName = "book";

	private final ParseType parseType;

	private boolean dontCare = false;

	private TubainaBuilderData data = new TubainaBuilderData(false, DEFAULT_TEMPLATE_DIR, false, false, "book.tex");

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
			b = builder.build(data.showNotes);
		} catch (TubainaException e) {
			if (dontCare) {
				LOG.warn(e);
			} else {
				e.printStackTrace();
				System.exit(-1);
			}
		}

		GeneratorFactory generatorFactory = new GeneratorFactory();
		Generator generator = generatorFactory.generatorFor(parseType, data);
		
		File file = new File(outputDir,parseType.getType());
		FileUtils.forceMkdir(file);
		File bibliography = new File(inputDir, data.outputFileName + ".bib");
		if(bibliography.exists()) {
			FileUtils.copyFileToDirectory(bibliography, file);
		}
		
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
		this.data.strictXhtml = true;
		return this;
	}

	public TubainaBuilder templateDir(File templateDir) {
		this.data.templateDir = templateDir;
		return this;
	}

	public TubainaBuilder showNotes() {
		this.data.showNotes = true;
		return this;
	}

	public TubainaBuilder dontCare() {
		this.dontCare = true;
		return this;
	}

	public TubainaBuilder noAnswer() {
		this.data.noAnswer = true;
		return this;
	}

	public TubainaBuilder outputFileName(String fileName) {
		this.data.outputFileName = fileName;
		return this;
	}
	
	public TubainaBuilder bookName(String bookName) {
		this.bookName = bookName;
		return this;
	}

	public TubainaBuilder codeLength(Integer length) {
		codeLength = length;
		return this;
	}
	
	public TubainaBuilder maximumImageWidth(Integer width) {
		maximumImageWidth = width;
		return this;
	}

	public static Integer getCodeLength() {
		return codeLength;
	}
	
	public static Integer getMaximumWidth() {
		return maximumImageWidth;
	}
}
