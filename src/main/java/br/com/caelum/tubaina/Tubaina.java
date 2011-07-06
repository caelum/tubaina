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

	private static TubainaBuilder parseOptions(CommandLine cmd, Options options) {
		ParseTypes type = selectParseTypeFromCommandLine(cmd);
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

	private static ParseTypes selectParseTypeFromCommandLine(CommandLine cmd) {
		for (ParseTypes type : ParseTypes.values()) {
			if (cmd.hasOption(type.getType())) {
				return type;
			}
		}
		throw new TubainaException("Argumento --html, --htmlflat ou --latex é obrigatório");
	}

	private static Options registerOptions() {
		Options options = new Options();

		options.addOption("latex", "latex", false,
				"generates an latex output on given outputdir");
		options.addOption("html", "html", false,
				"generates an html output on given outputdir");
		options.addOption("htmlflat", "htmlflat", false,
				"generates an flat html output on given outputdir");
		options.addOption("singlehtml", "singlehtml", false,
				"outputs the whole textbook in a single html file");

		options.addOption("h", "help", false, "print this message");

		for (TubainaOption tubainaOption : TubainaOption.values()) {
			options.addOption(tubainaOption.buildOption());
		}
		
		return options;
	}

	private static void printUsage(final Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		formatter
				.printHelp(
						"tubaina [-html|-latex|-htmlflat|-singlehtml] -i <inputdir> -o <outputdir> -n <bookname>",
						options);
	}

}
