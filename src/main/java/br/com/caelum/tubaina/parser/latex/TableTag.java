package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.parser.Tag;

public class TableTag implements Tag<TableChunk> {

	private final boolean noborder;

	public TableTag() {
		this(false);
	}
	
	public TableTag(boolean noborder) {
		this.noborder = noborder;
	}

	@Override
	public String parse(TableChunk chunk) {
		String title = chunk.getTitle();
		int numberOfColumns = chunk.getMaxNumberOfColumns();
		if (numberOfColumns <= 0)
			throw new TubainaException("There are no columns inside table " + title);
		String tag = "\\begin{table}[!h]\n\\caption{" + title + "}\n\\begin{center}\n";
		if (!noborder)
			tag += "\\rowcolors[]{1}{gray!30}{gray!15}\n";
		tag += "\\begin{tabularx}{";
		for (int i = 0; i < numberOfColumns; i++)
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
