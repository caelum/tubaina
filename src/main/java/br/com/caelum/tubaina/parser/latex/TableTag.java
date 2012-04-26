package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.parser.Tag;

public class TableTag implements Tag {

	private final boolean noborder;
	private final int columns;

	public TableTag(boolean noborder, int columns) {
		this.noborder = noborder;
		this.columns = columns;
	}

	public String parse(String text, String title) {
		if (this.columns <= 0)
			throw new TubainaException("There are no columns inside table " + title);
		String tag =  "\\begin{table}[!h]\n\\begin{center}\n";
		if (!noborder)
			tag += "\\rowcolors[]{1}{gray!30}{gray!15}\n";
		tag += "\\begin{tabular}{";
		for (int i = 0; i < columns; i++)
			tag += "c";
		tag += "}\n";
		if (!noborder)
			tag += "\\hline\n";
		tag += text;
		if (!noborder)
			tag += "\n\\hline";
		tag += "\n\\caption{" + title + "}\n\\end{tabular}\n\\end{center}\n\\end{table}";
		return tag;
	}

}
