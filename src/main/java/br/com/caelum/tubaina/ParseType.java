package br.com.caelum.tubaina;

import java.io.IOException;
import java.util.List;

import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.RegexTag;
import br.com.caelum.tubaina.parser.TubainaModule;
import br.com.caelum.tubaina.parser.html.desktop.FlatHtmlGenerator;
import br.com.caelum.tubaina.parser.html.desktop.Generator;
import br.com.caelum.tubaina.parser.html.desktop.HtmlModule;
import br.com.caelum.tubaina.parser.html.desktop.HtmlParser;
import br.com.caelum.tubaina.parser.html.desktop.SingleHtmlGenerator;
import br.com.caelum.tubaina.parser.html.kindle.KindleGenerator;
import br.com.caelum.tubaina.parser.html.kindle.KindleModule;
import br.com.caelum.tubaina.parser.html.kindle.KindleParser;
import br.com.caelum.tubaina.parser.latex.LatexGenerator;
import br.com.caelum.tubaina.parser.latex.LatexModule;
import br.com.caelum.tubaina.parser.latex.LatexParser;

public enum ParseType {

	LATEX {
		@Override
		public Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes, String linkParameter) throws IOException {
			List<RegexTag> tags = conf.read("/regex.properties", "/latex.properties");
            return new LatexParser(tags);
		}

		@Override
		public Generator getGenerator(Parser parser, TubainaBuilderData data) {
			return new LatexGenerator(parser, data);
		}

		@Override
		public TubainaModule getModule(TubainaBuilderData data) {
			return new LatexModule(data.isShowNotes(), data.isNoAnswer());
		}

	},

	HTMLFLAT {
		@Override
		public Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes, String linkParameter) throws IOException {
		    List<RegexTag> tags = conf.read("/regex.properties", "/html.properties");
            return new HtmlParser(tags);
		}

		@Override
		protected Generator getGenerator(Parser parser, TubainaBuilderData data) {
			return new FlatHtmlGenerator(parser, data);
		}

		@Override
		public TubainaModule getModule(TubainaBuilderData data) {
			return new HtmlModule(data.isShowNotes(), data.isNoAnswer());
		}
	},
	HTML {
		@Override
		public Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes, String linkParameter) throws IOException {
		    List<RegexTag> tags = conf.read("/regex.properties", "/html.properties");
            return new HtmlParser(tags);
		}

		@Override
		protected Generator getGenerator(Parser parser, TubainaBuilderData data) {
			return new SingleHtmlGenerator(parser, data);
		}

		@Override
		public TubainaModule getModule(TubainaBuilderData data) {
			return new HtmlModule(data.isShowNotes(), data.isNoAnswer());
		}
	},

	KINDLE {
		@Override
		public Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes, String linkParameter) throws IOException {
			List<RegexTag> tags = conf.read("/regex.properties", "/kindle.properties");
            return new KindleParser(tags);
		}

		@Override
		protected Generator getGenerator(Parser parser, TubainaBuilderData data) {
			return new KindleGenerator(parser, data);
		}

		@Override
		public TubainaModule getModule(TubainaBuilderData data) {
			return new KindleModule(data.isShowNotes(), data.isNoAnswer());
		}
	};

	public String getType() {
		return this.toString().toLowerCase();
	}

	protected abstract Generator getGenerator(Parser parser, TubainaBuilderData data);

	protected abstract Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes, String linkParameter) throws IOException;

	public abstract TubainaModule getModule(TubainaBuilderData data);
	
}