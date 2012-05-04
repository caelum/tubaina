package br.com.caelum.tubaina;

import java.io.IOException;

import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.html.desktop.FlatHtmlGenerator;
import br.com.caelum.tubaina.parser.html.desktop.Generator;
import br.com.caelum.tubaina.parser.html.desktop.HtmlParser;
import br.com.caelum.tubaina.parser.html.desktop.SingleHtmlGenerator;
import br.com.caelum.tubaina.parser.html.kindle.KindleGenerator;
import br.com.caelum.tubaina.parser.html.kindle.KindleParser;
import br.com.caelum.tubaina.parser.latex.LatexGenerator;
import br.com.caelum.tubaina.parser.latex.LatexParser;

public enum ParseType {

	LATEX {
		@Override
		public Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes) throws IOException {
			return new LatexParser(conf.read("/regex.properties", "/latex.properties"), showNotes, noAnswer);
		}

		@Override
		public Generator getGenerator(Parser parser, TubainaBuilderData data) {
			return new LatexGenerator(parser, data);
		}

	},

	HTMLFLAT {
		@Override
		public Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes) throws IOException {
			return new HtmlParser(conf.read("/regex.properties", "/html.properties"), noAnswer);
		}

		@Override
		protected Generator getGenerator(Parser parser, TubainaBuilderData data) {
			return new FlatHtmlGenerator(parser, data);
		}
	},
	HTML {

		@Override
		public Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes) throws IOException {
			return new HtmlParser(conf.read("/regex.properties", "/html.properties"), noAnswer);
		}

		@Override
		protected Generator getGenerator(Parser parser, TubainaBuilderData data) {
			return new SingleHtmlGenerator(parser, data);
		}
	},

	KINDLE {

		@Override
		public Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes) throws IOException {
			return new KindleParser(conf.read("/regex.properties", "/html.properties"), noAnswer);
		}

		@Override
		protected Generator getGenerator(Parser parser, TubainaBuilderData data) {
			return new KindleGenerator(parser, data);
		}
	};

	public String getType() {
		return this.toString().toLowerCase();
	}

	protected abstract Generator getGenerator(Parser parser, TubainaBuilderData data);

	protected abstract Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes) throws IOException;
}
