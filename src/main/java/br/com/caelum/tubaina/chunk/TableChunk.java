package br.com.caelum.tubaina.chunk;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;
import br.com.caelum.tubaina.parser.Parser;

public class TableChunk implements CompositeChunk {

	private final List<Chunk> rows;
	private boolean noborder;
	private String title;

	public TableChunk(final String options, final List<Chunk> rows) {
		this.rows = rows;
		this.noborder = false;
		this.title = "";
		parseOptions(options);
	}
	
	private void parseOptions(String options) {
		Pattern noborderPattern = Pattern.compile("(\".+\")*noborder(\".+\")*");
		Matcher noborderMatcher = noborderPattern.matcher(options);
		if (noborderMatcher.find())
			this.noborder = true;
		
		Pattern titlePattern = Pattern.compile("\"(.+)\"");
		Matcher titleMatcher = titlePattern.matcher(options);
		if (titleMatcher.find()) {
			this.title = titleMatcher.group(1);
		}
	}

	public String getContent(Parser p) {
		String content = "";
		for (Chunk c : rows) {
			content += c.getContent(p);
		}
		return p.parseTable(content, title, noborder, this.getMaxNumberOfColumns());
	}

	public int getMaxNumberOfColumns() {
		int maxColumns = 0;
		for (Chunk chunk : rows) {
			if (chunk.getClass().equals(TableRowChunk.class)) {
				TableRowChunk row = (TableRowChunk) chunk;
				int columns = row.getNumberOfColumns();
				if (columns > maxColumns)
					maxColumns = columns;
			}
		}
		return maxColumns;
	}

}
