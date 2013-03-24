package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.chunk.TableRowChunk;
import br.com.caelum.tubaina.parser.Tag;

public class TableRowTag implements Tag<TableRowChunk> {

	@Override
	public String parse(TableRowChunk chunk) {
		String string = chunk.getContent();
		// Remove the & of the last column (put by the TableColumnTag)
		int lastColumnBreak = string.lastIndexOf('&');
		// There IS a column break
		if (lastColumnBreak > 0)
			string = string.substring(0, lastColumnBreak);
		return string + "\\\\";
	}

}
