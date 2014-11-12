package br.com.caelum.tubaina;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Logger;

public class Tubaina {

	public static final Logger LOG = Logger.getLogger(Tubaina.class);

	public static void main(String... args) throws IOException {
		
		args = new String[] {"-markdown", "-i", "/Users/mauricioaniche/textos/certificacao-java/book", "-o", "/Users/mauricioaniche/textos/certificacao-java/book/teste2", "-n", "chicao", "-3", "http://s3.amazonaws.com/caelum-online-public/certificacao-java/"};

		CommandLineParser commandLineParser = new PosixParser();

		Options options = registerOptions();

		TubainaBuilder tubainaBuilder = null;

		try {
			LOG.debug("Parsing arguments " + Arrays.toString(args));
			CommandLine commandLine = commandLineParser.parse(options, args);
			tubainaBuilder = parseOptions(commandLine, options);
		} catch (ParseException e) {
			System.err.println("Parsing failed.  Reason: " + e.getMessage());
			printUsage(options);
			System.exit(-1);
		}
		tubainaBuilder.build();
		
	}

	private static TubainaBuilder parseOptions(CommandLine cmd, Options options) {
		ParseType type = selectParseTypeFromCommandLine(cmd);
		TubainaBuilder builder = new TubainaBuilder(type);

		if (cmd.hasOption("h")) {
			printUsage(options);
		} else {
			for (TubainaOption tubainaOption : TubainaOption.values()) {
				tubainaOption.configureIfPresent(builder, cmd);
			}
		}
		
		return builder;
	}

	private static ParseType selectParseTypeFromCommandLine(CommandLine cmd) {
		for (ParseType type : ParseType.values()) {
			if (cmd.hasOption(type.getType())) {
				return type;
			}
		}
		throw new TubainaException("Argumento --html, --htmlflat, --kindle, --markdown ou --latex é obrigatório");
	}

	private static Options registerOptions() {
		Options options = new Options();

		options.addOption("markdown", "markdown", false,
				"generates an markdown output on given outputdir");
		options.addOption("latex", "latex", false,
				"generates an latex output on given outputdir");
		options.addOption("html", "html", false,
				"outputs the whole textbook in a single html5 file");
		options.addOption("htmlflat", "htmlflat", false,
				"generates an flat html output on given outputdir");
		options.addOption("kindle", "kindle", false,
				"generates an kindle html output on given outputdir");

		options.addOption("h", "help", false, "print this message");

		for (TubainaOption tubainaOption : TubainaOption.values()) {
			options.addOption(tubainaOption.buildOption());
		}
		
		return options;
	}

	private static void printUsage(final Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(
				"tubaina [-html|-latex|-htmlflat|-kindle] -i <inputdir> -o <outputdir> -n <bookname> -c <codelength> -w <maximum_image_width>",
				options);
	}

}
