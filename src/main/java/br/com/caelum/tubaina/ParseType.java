package br.com.caelum.tubaina;

import java.io.IOException;
import java.util.List;

import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.html.desktop.FlatHtmlGenerator;
import br.com.caelum.tubaina.parser.html.desktop.Generator;
import br.com.caelum.tubaina.parser.html.desktop.HtmlParser;
import br.com.caelum.tubaina.parser.html.desktop.SingleHtmlGenerator;
import br.com.caelum.tubaina.parser.html.kindle.KindleGenerator;
import br.com.caelum.tubaina.parser.html.kindle.KindleParser;
import br.com.caelum.tubaina.parser.latex.LatexGenerator;
import br.com.caelum.tubaina.parser.latex.LatexParser;
import br.com.caelum.tubaina.parser.latex.LinkTag;

public enum ParseType {

	LATEX {
		@Override
		public Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes, String linkParameter) throws IOException {
			List<Tag> tags = conf.read("/regex.properties", "/latex.properties");
			tags.add(new LinkTag("\\\\link{$1}$2"));
            return new LatexParser(tags, showNotes, noAnswer);
		}

		@Override
		public Generator getGenerator(Parser parser, TubainaBuilderData data) {
			return new LatexGenerator(parser, data);
		}

	},

	HTMLFLAT {
		@Override
		public Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes, String linkParameter) throws IOException {
		    List<Tag> tags = conf.read("/regex.properties", "/html.properties");
		    tags.add(new LinkTag("<a href=\"$1"+linkParameter+"\">$1</a>$2"));
            return new HtmlParser(tags, noAnswer, showNotes);
		}

		@Override
		protected Generator getGenerator(Parser parser, TubainaBuilderData data) {
			return new FlatHtmlGenerator(parser, data);
		}
	},
	HTML {
		@Override
		public Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes, String linkParameter) throws IOException {
		    List<Tag> tags = conf.read("/regex.properties", "/html.properties");
		    tags.add(new LinkTag("<a href=\"$1"+linkParameter+"\">$1</a>$2"));
            return new HtmlParser(tags, noAnswer, showNotes);
		}

		@Override
		protected Generator getGenerator(Parser parser, TubainaBuilderData data) {
			return new SingleHtmlGenerator(parser, data);
		}
	},

	KINDLE {
		@Override
		public Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes, String linkParameter) throws IOException {
			List<Tag> tags = conf.read("/regex.properties", "/kindle.properties");
			tags.add(new LinkTag("<a href=\"$1"+linkParameter+"\">$1</a>$2"));
            return new KindleParser(tags, noAnswer, showNotes);
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

	protected abstract Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes, String linkParameter) throws IOException;
}
