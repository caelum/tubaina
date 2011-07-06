package br.com.caelum.tubaina;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Logger;

public class Tubaina {

	public static final Logger LOG = Logger.getLogger(Tubaina.class);

	public static void main(String... args) throws IOException {
		CommandLineParser parser = new PosixParser();

		Options options = registerOptions();

		TubainaBuilder tubainaBuilder = null;

		try {
			LOG.debug("Parsing arguments " + Arrays.toString(args));
			CommandLine cmd = parser.parse(options, args);
			tubainaBuilder = parseOptions(cmd, options);
		} catch (ParseException e) {
			System.err.println("Parsing failed.  Reason: " + e.getMessage());
			printUsage(options);
			System.exit(-1);
		}
		tubainaBuilder.build();
	}

	private static TubainaBuilder parseOptions(final CommandLine cmd,
			final Options options) {
		ParseTypes type;

		if (cmd.hasOption("html")) {
			type = ParseTypes.HTML;
		} else if (cmd.hasOption("htmlflat")) {
			type = ParseTypes.HTMLFLAT;
		} else if (cmd.hasOption("latex")) {
			type = ParseTypes.LATEX;
		} else {
			throw new TubainaException(
					"Argumento --html, --htmlflat ou --latex é obrigatório");
		}
		TubainaBuilder builder = new TubainaBuilder(type, cmd
				.getOptionValue('n'));

		if (cmd.hasOption("h")) {
			printUsage(options);
		} else {
			if (cmd.hasOption('i')) {
				builder.setInputDir(new File(cmd.getOptionValue('i')));
			}
			if (cmd.hasOption('o')) {
				builder.setOutputDir(new File(cmd.getOptionValue('o')));
			}

			if (cmd.hasOption('t')) {
				builder.setTemplateDir(new File(cmd.getOptionValue('t')));
			}

			if (cmd.hasOption('x')) {
				builder.strictXhtml();
			}

			if (cmd.hasOption('s')) {
				builder.showNotes();
			}

			if (cmd.hasOption('d')) {
				builder.dontCare();
			}

			if (cmd.hasOption('a')) {
				builder.noAnswer();
			}

			if (cmd.hasOption('f')) {
				builder.setOutputFileName(cmd.getOptionValue('f'));
			}
		}

		return builder;

	}

	@SuppressWarnings("static-access")
	private static Options registerOptions() {
		Options options = new Options();

		options.addOption("latex", "latex", false,
				"generates an latex output on given outputdir");
		options.addOption("html", "html", false,
				"generates an html output on given outputdir");
		options.addOption("htmlflat", "htmlflat", false,
		"generates an flat html output on given outputdir");

		options.addOption("h", "help", false, "print this message");
		// inputdir
		options.addOption(OptionBuilder.withArgName("inputDirectory")
						.withLongOpt("input-dir").hasArg().withDescription(
								"directory where you have your .afc files")
						.create('i'));
		// outputdir
		options.addOption(OptionBuilder.withArgName("outputDirectory")
				.withLongOpt("output-dir").hasArg().withDescription(
						"directory where you want the output files")
				.create('o'));

		// name
		options.addOption(OptionBuilder.withArgName("bookName").withLongOpt(
				"name").hasArg().isRequired().withDescription(
				"name of your book").create('n'));
		// optional
		options.addOption("x", "strict-xhtml", false,
				"perform an xhtml validation");
		options.addOption("s", "show-notes", false,
				"shows notes in Latex output");
		options.addOption("d", "dontcare", false, "ignore all errors");
		options.addOption("a", "no-answers", false,
				"don't make the answers booklet");

		options.addOption(OptionBuilder.withArgName("directory").withLongOpt(
				"template-dir").hasArg().withDescription(
				"directory where you have your template files").create('t'));

		options
				.addOption(OptionBuilder
						.withArgName("outputFileName")
						.withLongOpt("output-file")
						.hasArg()
						.withDescription(
								"name for generated latex file (ignored for html output)")
						.create('f'));
		return options;
	}

	private static void printUsage(final Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		formatter
				.printHelp(
						"tubaina [-html|-latex|-htmlflat] -i <inputdir> -o <outputdir> -n <bookname>",
						options);
	}

}
