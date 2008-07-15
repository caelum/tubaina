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
		String tag =  "\\begin{table}[!h]\n\\caption{" + title + "}\n\\begin{tabular}{";
		if (!noborder)
			tag += "|";
		for (int i = 0; i < columns; i++) {
			tag += "c";
			if (!noborder)
				tag += "|";
		}
		tag += "}\n";
		if (!noborder)
			tag += "\\hline\n";
		tag += text;
		if (!noborder)
			tag += "\n\\hline";
		tag += "\n\\end{tabular}\n\\end{table}";
		return tag;
	}

}
