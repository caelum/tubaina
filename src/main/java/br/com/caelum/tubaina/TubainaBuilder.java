package br.com.caelum.tubaina;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.log4j.Logger;

import br.com.caelum.tubaina.builder.BookBuilder;
import br.com.caelum.tubaina.parser.TubainaModule;
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

	private TubainaBuilderData data = new TubainaBuilderData(false, DEFAULT_TEMPLATE_DIR, false, false, "book");

	public TubainaBuilder(ParseType type) {
		this.parseType = type;
	}

	public void build() throws IOException {
		TubainaModule module = parseType.getModule(data);
		SectionsManager sectionsManager = module.getSectionsManager();
		List<AfcFile> introductionAfcFiles = new ArrayList<AfcFile>();
		ResourceLocator.initialize(inputDir);
		List<AfcFile> afcFiles = getAfcsFrom(inputDir);
		File introductionChapterDirs = new File(inputDir, "introduction");
		if (introductionChapterDirs.exists()) {
			introductionAfcFiles = getAfcsFrom(introductionChapterDirs);
		}
		BookBuilder builder = new BookBuilder(bookName, sectionsManager);
		builder.addAllReaders(afcFiles, introductionAfcFiles);

		Book b = null;
		try {
			b = builder.build();
		} catch (TubainaException e) {
			if (dontCare) {
				LOG.warn(e);
			} else {
				throw e;
			}
		}
		module.inject(b);

		File file = new File(outputDir, parseType.getType());
		FileUtils.forceMkdir(file);
		File bibliographyFile = new File(inputDir, "bib.xml");
		if (bibliographyFile.exists()) {
			FileUtils.copyFileToDirectory(bibliographyFile, file);
		}

		Generator generator = parseType.getGenerator(parseType.getParser(), data);
		try {
			generator.generate(b, file);
		} catch (TubainaException e) {
			LOG.warn(e.getMessage());
		}
	}

	static List<AfcFile> getAfcsFrom(final File file) throws UnsupportedEncodingException, FileNotFoundException {
		List<AfcFile> afcFiles = new ArrayList<AfcFile>();
		List<String> files = new ArrayList<String>();
		Collections.addAll(files, file.list(new SuffixFileFilter(".afc")));
		Collections.sort(files);
		for (String fileName : files) {
			InputStreamReader reader = new InputStreamReader(new BOMInputStream(new FileInputStream(new File(file,
					fileName))), "UTF-8");
			afcFiles.add(new AfcFile(reader, fileName));
		}
		return afcFiles;
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
		this.data.setStrictXhtml(true);
		return this;
	}

	public TubainaBuilder templateDir(File templateDir) {
		this.data.setTemplateDir(templateDir);
		return this;
	}

	public TubainaBuilder showNotes() {
		this.data.setShowNotes(true);
		return this;
	}

	public TubainaBuilder dontCare() {
		this.dontCare = true;
		return this;
	}

	public TubainaBuilder noAnswer() {
		this.data.setNoAnswer(true);
		return this;
	}

	public TubainaBuilder outputFileName(String fileName) {
		this.data.setOutputFileName(fileName);
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

	public TubainaBuilder s3Path(String s3Path) {
		this.data.setS3Path(s3Path);
		return this;
	}
	
	public TubainaBuilder maximumImageWidth(Integer width) {
		maximumImageWidth = width;
		return this;
	}

	public TubainaBuilder withIfdefs(List<String> ifdefs) {
		this.data.setIfdefs(ifdefs);
		return this;
	}
	
	public static Integer getCodeLength() {
		return codeLength;
	}

	public static Integer getMaximumWidth() {
		return maximumImageWidth;
	}

	public void withLinkParameter(String linkParameter) {
		this.data.setLinkParameter(linkParameter);
	}

}
