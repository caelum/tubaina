package br.com.caelum.tubaina;

import java.io.IOException;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.html.desktop.HtmlParser;
import br.com.caelum.tubaina.parser.html.kindle.KindleParser;
import br.com.caelum.tubaina.parser.latex.LatexParser;

public enum ParseType {
	
	LATEX{
		@Override
		public Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes) throws IOException {
			return new LatexParser(conf.read("/regex.properties", "/latex.properties"), showNotes, noAnswer);
		}
	},
	
	HTMLFLAT, HTML, KINDLE;
	
	public String getType() {
		return this.toString().toLowerCase();
	}
	
	public Parser getParser(RegexConfigurator conf, boolean noAnswer, boolean showNotes) throws IOException {
		return new HtmlParser(conf.read(
				"/regex.properties", "/html.properties"), noAnswer);
	}
	
}
