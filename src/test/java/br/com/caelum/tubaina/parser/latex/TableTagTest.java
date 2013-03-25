package br.com.caelum.tubaina.parser.latex;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.TubainaException;

public class TableTagTest {

	@Test
	public void testTable() {
		TableTag tag = new TableTag(false);
		String result = tag.parse(chunk);
		Assert.assertEquals(
				"\\begin{table}[!h]\n" +
				"\\caption{}\n" +
				"\\begin{center}\n" +
				"\\rowcolors[]{1}{gray!30}{gray!15}\n" +
				"\\begin{tabularx}{XX}\n" +
				"\\hline\n" +
				"texto da tabela\n" +
				"\\hline\n" +
				"\\end{tabularx}\n\\end{center}\n\\end{table}", result);
	}

	@Test
	public void testTableWithTitle() {
		TableTag tag = new TableTag(false);
		String result = tag.parse(chunk);
		Assert.assertEquals(
				"\\begin{table}[!h]\n" +
				"\\caption{titulo}\n" +
				"\\begin{center}\n" +
				"\\rowcolors[]{1}{gray!30}{gray!15}\n" +
				"\\begin{tabularx}{XX}\n" +
				"\\hline\n" +
				"texto da tabela\n" +
				"\\hline\n" +
				"\\end{tabularx}\n\\end{center}\n\\end{table}", result);
	}

	@Test
	public void testTableWithoutBorder() {
		TableTag tag = new TableTag(true);
		String result = tag.parse(chunk);
		Assert.assertEquals(
				"\\begin{table}[!h]\n" +
				"\\caption{}\n" +
				"\\begin{center}\n" +
				"\\begin{tabularx}{XX}\n" +
				"texto da tabela\n" +
				"\\end{tabularx}\n\\end{center}\n\\end{table}", result);
	}

	@Test
	public void testTableWithTitleAndWithoutBorder() {
		TableTag tag = new TableTag(true);
		String result = tag.parse(chunk);
		Assert.assertEquals(
				"\\begin{table}[!h]\n" +
				"\\caption{titulo}\n" +
				"\\begin{center}\n" +
				"\\begin{tabularx}{XX}\n" +
				"texto da tabela\n" +
				"\\end{tabularx}\n\\end{center}\n\\end{table}", result);
	}

	@Test
	public void testTableWithInvalidNumberOfColums() {
		TableTag tag = new TableTag(true);
		try {
			tag.parse(chunk);
			Assert.fail("Should raise an exception");
		} catch (TubainaException e) {
			// ok
		}
	}
}
