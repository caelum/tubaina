package br.com.caelum.tubaina.chunk;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;

public class TableChunk extends CompositeChunk<TableChunk> {

	private boolean noBorder;
	private String title;

	public TableChunk(String options, List<Chunk> rows) {
		super(rows);
		this.noBorder = false;
		this.title = "";
		parseOptions(options);
	}
	
	private void parseOptions(String options) {
		Pattern noborderPattern = Pattern.compile("(\".+\")*noborder(\".+\")*");
		Matcher noborderMatcher = noborderPattern.matcher(options);
		if (noborderMatcher.find())
			this.noBorder = true;
		
		Pattern titlePattern = Pattern.compile("\"(.+)\"");
		Matcher titleMatcher = titlePattern.matcher(options);
		if (titleMatcher.find()) {
			this.title = titleMatcher.group(1);
		}
	}

	public int getMaxNumberOfColumns() {
		int maxColumns = 0;
		for (Chunk chunk : body) {
			if (chunk.getClass().equals(TableRowChunk.class)) {
				TableRowChunk row = (TableRowChunk) chunk;
				int columns = row.getNumberOfColumns();
				if (columns > maxColumns)
					maxColumns = columns;
			}
		}
		return maxColumns;
	}

	public boolean hasBorder() {
		return !noBorder;
	}
	
	public String getTitle() {
		return title;
	}
}
