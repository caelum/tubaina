package br.com.caelum.tubaina;

import java.io.IOException;

import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.html.desktop.Generator;

public class GeneratorFactory {

	public Generator generatorFor(ParseType parseType, TubainaBuilderData data) throws IOException {
		Parser parser = parseType.getParser(new RegexConfigurator(), data.isNoAnswer(), data.isShowNotes());
		return parseType.getGenerator(parser, data);

	}

}
