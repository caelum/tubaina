package br.com.caelum.tubaina.parser.latex;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.TubainaException;

public class TableTagTest {

	@Test
	public void testTable() {
		TableTag tag = new TableTag(false, 2);
		String result = tag.parse("texto da tabela", "");
		Assert.assertEquals(
				"\\begin{table}[!h]\n" +
				"\\begin{center}\n" +
				"\\rowcolors[]{1}{gray!30}{gray!15}\n" +
				"\\begin{tabular}{cc}\n" +
				"\\hline\n" +
				"texto da tabela\n" +
				"\\hline\n" +
				"\\caption{}\n" +
				"\\end{tabular}\n\\end{center}\n\\end{table}", result);
	}

	@Test
	public void testTableWithTitle() {
		TableTag tag = new TableTag(false, 2);
		String result = tag.parse("texto da tabela", "titulo");
		Assert.assertEquals(
				"\\begin{table}[!h]\n" +
				"\\begin{center}\n" +
				"\\rowcolors[]{1}{gray!30}{gray!15}\n" +
				"\\begin{tabular}{cc}\n" +
				"\\hline\n" +
				"texto da tabela\n" +
				"\\hline\n" +
				"\\caption{titulo}\n" +
				"\\end{tabular}\n\\end{center}\n\\end{table}", result);
	}

	@Test
	public void testTableWithoutBorder() {
		TableTag tag = new TableTag(true, 2);
		String result = tag.parse("texto da tabela", "");
		Assert.assertEquals(
				"\\begin{table}[!h]\n" +
				"\\begin{center}\n" +
				"\\begin{tabular}{cc}\n" +
				"texto da tabela\n" +
				"\\caption{}\n" +
				"\\end{tabular}\n\\end{center}\n\\end{table}", result);
	}

	@Test
	public void testTableWithTitleAndWithoutBorder() {
		TableTag tag = new TableTag(true, 2);
		String result = tag.parse("texto da tabela", "titulo");
		Assert.assertEquals(
				"\\begin{table}[!h]\n" +
				"\\begin{center}\n" +
				"\\begin{tabular}{cc}\n" +
				"texto da tabela\n" +
				"\\caption{titulo}\n" +
				"\\end{tabular}\n\\end{center}\n\\end{table}", result);
	}

	@Test
	public void testTableWithInvalidNumberOfColums() {
		TableTag tag = new TableTag(true, 0);
		try {
			tag.parse("texto", "");
			Assert.fail("Should raise an exception");
		} catch (TubainaException e) {
			// ok
		}
	}
}
