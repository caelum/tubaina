package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.parser.Tag;

public class TableTag implements Tag<TableChunk> {

	private final boolean noborder;
	private final int columns;

	public TableTag(boolean noborder, int columns) {
		this.noborder = noborder;
		this.columns = columns;
	}

	@Override
	public String parse(TableChunk chunk) {
		String title = chunk.getTitle();
		if (this.columns <= 0)
			throw new TubainaException("There are no columns inside table " + title);
		String tag = "\\begin{table}[!h]\n\\caption{" + title + "}\n\\begin{center}\n";
		if (!noborder)
			tag += "\\rowcolors[]{1}{gray!30}{gray!15}\n";
		tag += "\\begin{tabularx}{";
		for (int i = 0; i < columns; i++)
			tag += "X";
		tag += "}\n";
		if (!noborder)
			tag += "\\hline\n";
		tag += chunk.getContent();
		if (!noborder)
			tag += "\n\\hline";
		tag += "\n\\end{tabularx}\n\\end{center}\n\\end{table}";
		return tag;
	}

}
