package br.com.caelum.tubaina;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;


public enum TubainaOption {
	INPUT_DIR('i', "input-dir", "directory where you have your .afc files") {
		@Override
		public void configure(TubainaBuilder builder, CommandLine cmd) {
			builder.inputDir(new File(cmd.getOptionValue(getShortName())));
		}
		@Override
		@SuppressWarnings("static-access")
		public Option buildOption() {
			return OptionBuilder
						.withArgName("inputDirectory")
						.withLongOpt(getLongName())
						.hasArg()
						.withDescription(getDescription())
						.create(getShortName());
		}
	},
	OUTPUT_DIR('o', "output-dir", "directory where you want the output files") {
		@Override
		public void configure(TubainaBuilder builder, CommandLine cmd) {
			builder.outputDir(new File(cmd.getOptionValue(getShortName())));
		}
		@Override
		@SuppressWarnings("static-access")
		public Option buildOption() {
			return OptionBuilder
						.withArgName("outputDirectory")
						.withLongOpt(getLongName())
						.hasArg()
						.withDescription(getDescription())
						.create(getShortName());
		}
	},
	TEMPLATE_DIR('t', "template-dir", "directory where you have your template files") {
		@Override
		public void configure(TubainaBuilder builder, CommandLine cmd) {
			builder.templateDir(new File(cmd.getOptionValue(getShortName())));
		}
		@Override
		@SuppressWarnings("static-access")
		public Option buildOption() {
			return OptionBuilder
						.withArgName("directory")
						.withLongOpt(getLongName())
						.hasArg()
						.withDescription(getDescription())
						.create(getShortName());
		}
	},
	STRICT_XHTML('x', "strict-xhtml", "perform an xhtml validation") {
		@Override
		public void configure(TubainaBuilder builder, CommandLine cmd) {
			builder.strictXhtml();
		}
	},
	SHOW_NOTES('s', "show-notes", "shows notes in Latex output") {
		@Override
		public void configure(TubainaBuilder builder, CommandLine cmd) {
			builder.showNotes();
		}
	},
	DONT_CARE('d', "dontcare", "ignore all errors") {
		@Override
		public void configure(TubainaBuilder builder, CommandLine cmd) {
			builder.dontCare();
		}
	},
	CODE_LENGHT('c', "codelength", "defines how many characters can be used at code blocks per line") {
		@Override
		public void configure(TubainaBuilder builder, CommandLine cmd) {
			try {
				String optionValue = cmd.getOptionValue(getShortName());
				Integer length = Integer.valueOf(optionValue);
				builder.codeLength(length);
			} catch (NumberFormatException e) {
				throw new TubainaException("Code Length (-c) argument wasn't a valid number", e);
			}
		}
		
		@Override
		@SuppressWarnings("static-access")
		public Option buildOption() {
			return OptionBuilder
						.withArgName("codelength")
						.withLongOpt(getLongName())
						.hasArg()
						.withDescription(getDescription())
						.create(getShortName());
		}
	},
	
	MAXIMUM_IMAGE_WIDTH('w', "maximum_image_width", "Defines the maximum width of images in milimeters") {
		@Override
		public void configure(TubainaBuilder builder, CommandLine cmd) {
			try {
				String optionValue = cmd.getOptionValue(getShortName());
				Integer width = Integer.valueOf(optionValue);
				builder.maximumImageWidth(width);
			} catch (NumberFormatException e) {
				throw new TubainaException("Maximum Image Width (-w) argument wasn't a valid number", e);
			}
		}
		
		@Override
		@SuppressWarnings("static-access")
		public Option buildOption() {
			return OptionBuilder
						.withArgName("maximum_image_width")
						.withLongOpt(getLongName())
						.hasArg()
						.withDescription(getDescription())
						.create(getShortName());
		}
	},
	
	NO_ANSWERS('a', "no-answers", "don't make the answers booklet") {
		@Override
		public void configure(TubainaBuilder builder, CommandLine cmd) {
			builder.noAnswer();
		}
	},
	OUTPUT_FILENAME('f', "output-file", "name for generated latex file (ignored for html output)") {
		@Override
		public void configure(TubainaBuilder builder, CommandLine cmd) {
			builder.outputFileName(cmd.getOptionValue(getShortName()));
		}
		@Override
		@SuppressWarnings("static-access")
		public Option buildOption() {
			return OptionBuilder
						.withArgName("outputFileName")
						.withLongOpt(getLongName())
						.hasArg()
						.withDescription(getDescription())
						.create(getShortName());
		}
	},
	BOOK_NAME('n', "name", "name of your book") {
		@Override
		public void configure(TubainaBuilder builder, CommandLine cmd) {
			builder.bookName(cmd.getOptionValue(getShortName()));
		}
		@Override
		@SuppressWarnings("static-access")
		public Option buildOption() {
			return OptionBuilder
						.withArgName("bookName")
						.withLongOpt(getLongName())
						.hasArg()
						.isRequired()
						.withDescription(getDescription())
						.create(getShortName());
		}
	},
	IFDEFS('e', "ifdef", "Defines boolean variables avaiable to freemarker templates") {
        @Override
        public void configure(TubainaBuilder builder, CommandLine cmd) {
            String[] optionValues = cmd.getOptionValues(getShortName());
            List<String> ifdefs = Arrays.asList(optionValues);
            builder.withIfdefs(ifdefs);
        }
        @Override
        @SuppressWarnings("static-access")
        public Option buildOption() {
            return OptionBuilder
                        .withArgName("ifdef")
                        .withLongOpt(getLongName())
                        .hasArgs()
                        .withDescription(getDescription())
                        .create(getShortName());
        }
    };
	
	private final String longName;
	private final char shortName;
	private final String description;

	private TubainaOption(char shortName, String longName, String description) {
		this.shortName = shortName;
		this.longName = longName;
		this.description = description;
	}
	
	char getShortName() {
		return shortName;
	}
	
	String getDescription() {
		return description;
	}
	
	String getLongName() {
		return longName;
	}
	
	public abstract void configure(TubainaBuilder builder, CommandLine cmd);
	
	public void configureIfPresent(TubainaBuilder builder, CommandLine cmd){
		if (cmd.hasOption(shortName)) {
			configure(builder, cmd);
		}
	}
	
	@SuppressWarnings("static-access")
	public Option buildOption() {
		return OptionBuilder.withLongOpt(longName)
					.withDescription(description)
					.create(shortName);
	}
}
