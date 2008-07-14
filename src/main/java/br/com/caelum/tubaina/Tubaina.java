package br.com.caelum.tubaina;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.log4j.Logger;

import br.com.caelum.tubaina.builder.BookBuilder;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.HtmlGenerator;
import br.com.caelum.tubaina.parser.html.HtmlParser;
import br.com.caelum.tubaina.parser.latex.LatexGenerator;
import br.com.caelum.tubaina.parser.latex.LatexParser;
import br.com.caelum.tubaina.resources.ResourceLocator;

public class Tubaina {

	public static final File DEFAULT_TEMPLATE_DIR = new File("src/templates");

	public static final Logger LOG = Logger.getLogger(Tubaina.class);

	public static final int MAX_LINE_LENGTH = 93; // believe us... this is what

	// fits in Latex A4
	// templates.

	private static File inputDir = new File(".");

	private static File outputDir = new File(".");

	private static String bookName;

	// TODO listar todos
	private static boolean listTodos;

	private static boolean strictXhtml;

	private static File templateDir = DEFAULT_TEMPLATE_DIR;

	private static boolean html;

	private static boolean latex;

	private static boolean showNotes;

	private static boolean dontCare;

	private static boolean noAnswer;

	private static String outputFileName;

	public static void main(final String[] args) throws IOException {

		CommandLineParser parser = new PosixParser();

		Options options = registerOptions();

		try {
			LOG.debug("Parsing arguments " + Arrays.toString(args));
			CommandLine cmd = parser.parse(options, args);
			parseOptions(cmd, options);
		} catch (ParseException e) {
			System.err.println("Parsing failed.  Reason: " + e.getMessage());
			printUsage(options);
			System.exit(-1);
		}

		generate();

	}

	private static void generate() throws IOException {

		ResourceLocator.initialize(inputDir);
		List<Reader> readers = getAfcsFrom(inputDir);
		BookBuilder builder = new BookBuilder(bookName);
		builder.addAll(readers);

		Book b = null;
		try {
			b = builder.build();
		} catch (TubainaException e) {
			if (dontCare) {
				LOG.warn(e);
			} else {
				e.printStackTrace();
				System.exit(-1);
			}
		}

		RegexConfigurator conf = new RegexConfigurator();
		if (latex) {
			List<Tag> tags = conf.read("/regex.properties", "/latex.properties");

			Parser parser = new LatexParser(tags, showNotes, noAnswer);
			LatexGenerator generator = new LatexGenerator(parser, templateDir, noAnswer);
			File file = new File(outputDir, "latex");
			FileUtils.forceMkdir(file);
			try {
				generator.generate(b, file, outputFileName);
			} catch (TubainaException e) {
				LOG.warn(e.getMessage());
			}
		}

		System.out.println("html content " + html);
		if (html) {
			HtmlParser htmlParser = new HtmlParser(conf.read("/regex.properties", "/html.properties"), noAnswer);
			HtmlGenerator generator = new HtmlGenerator(htmlParser, strictXhtml, templateDir);
			File file = new File(outputDir, "html");
			FileUtils.forceMkdir(file);
			try {
				generator.generate(b, file);
			} catch (TubainaException e) {
				LOG.warn(e.getMessage());
			}
		}

	}

	private static void parseOptions(final CommandLine cmd, final Options options) {
		if (cmd.hasOption("h")) {
			printUsage(options);
		} else {
			if (cmd.hasOption('i')) {
				inputDir = new File(cmd.getOptionValue('i'));
			}

			if (cmd.hasOption('o')) {
				outputDir = new File(cmd.getOptionValue('o'));
			}

			if (cmd.hasOption('t')) {
				templateDir = new File(cmd.getOptionValue('t'));
			}

			bookName = cmd.getOptionValue('n');

			listTodos = cmd.hasOption('l');

			strictXhtml = cmd.hasOption('x');

			showNotes = cmd.hasOption('s');

			html = cmd.hasOption("html");
			latex = cmd.hasOption("latex");
			dontCare = cmd.hasOption("d");
			noAnswer = cmd.hasOption("a");

			if (cmd.hasOption('f')) {
				outputFileName = cmd.getOptionValue('f');
			} else {
				outputFileName = "book.tex";
			}
		}

	}

	@SuppressWarnings("static-access")
	private static Options registerOptions() {
		Options options = new Options();

		options.addOption("latex", "latex", false, "generates an latex output on given outputdir");
		options.addOption("html", "html", false, "generates an html output on given outputdir");

		options.addOption("h", "help", false, "print this message");
		// inputdir
		options.addOption(OptionBuilder.withArgName("inputDirectory").withLongOpt("input-dir").hasArg()
				.withDescription("directory where you have your .afc files").create('i'));
		// outputdir
		options.addOption(OptionBuilder.withArgName("outputDirectory").withLongOpt("output-dir").hasArg().isRequired()
				.withDescription("directory where you want the output files").create('o'));

		// name
		options.addOption(OptionBuilder.withArgName("bookName").withLongOpt("name").hasArg().isRequired()
				.withDescription("name of your book").create('n'));
		// optional
		options.addOption("l", "list-todos", false, "lists todos from all files");
		options.addOption("x", "strict-xhtml", false, "perform an xhtml validation");
		options.addOption("s", "show-notes", false, "shows notes in Latex output");
		options.addOption("d", "dontcare", false, "ignore all errors");
		options.addOption("a", "no-answers", false, "don't make the answers booklet");

		options.addOption(OptionBuilder.withArgName("directory").withLongOpt("template-dir").hasArg().withDescription(
				"directory where you have your template files").create('t'));

		options.addOption(OptionBuilder.withArgName("outputFileName").withLongOpt("output-file").hasArg()
				.withDescription("name for generated latex file (ignored for html output)").create('f'));
		return options;
	}

	private static void printUsage(final Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("tubaina [-html|-latex] -i <inputdir> -o <outputdir> -n <bookname>", options);
	}

	static List<Reader> getAfcsFrom(final File file) throws UnsupportedEncodingException, FileNotFoundException {
		List<Reader> readers = new ArrayList<Reader>();
		List<String> files = new ArrayList<String>();
		Collections.addAll(files, file.list(new SuffixFileFilter(".afc")));
		Collections.sort(files);
		for (String s : files) {
			readers.add(new InputStreamReader(new FileInputStream(new File(file, s)), "UTF-8"));
		}
		return readers;
	}

}
