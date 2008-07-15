package br.com.caelum.tubaina.parser.latex;

import junit.framework.Assert;

import org.junit.Test;

import br.com.caelum.tubaina.TubainaException;

public class TableTagTest {

	@Test
	public void testTable() {
		TableTag tag = new TableTag(false, 2);
		String result = tag.parse("texto da tabela", "");
		Assert
				.assertEquals(
						"\\begin{table}[!h]\n\\caption{}\n\\begin{tabular}{|c|c|}\n\\hline\ntexto da tabela\n\\hline\n\\end{tabular}\n\\end{table}",
						result);
	}

	@Test
	public void testTableWithTitle() {
		TableTag tag = new TableTag(false, 2);
		String result = tag.parse("texto da tabela", "titulo");
		Assert
				.assertEquals(
						"\\begin{table}[!h]\n\\caption{titulo}\n\\begin{tabular}{|c|c|}\n\\hline\ntexto da tabela\n\\hline\n\\end{tabular}\n\\end{table}",
						result);
	}

	@Test
	public void testTableWithoutBorder() {
		TableTag tag = new TableTag(true, 2);
		String result = tag.parse("texto da tabela", "");
		Assert
				.assertEquals(
						"\\begin{table}[!h]\n\\caption{}\n\\begin{tabular}{cc}\ntexto da tabela\n\\end{tabular}\n\\end{table}",
						result);
	}

	@Test
	public void testTableWithTitleAndWithoutBorder() {
		TableTag tag = new TableTag(true, 2);
		String result = tag.parse("texto da tabela", "titulo");
		Assert
				.assertEquals(
						"\\begin{table}[!h]\n\\caption{titulo}\n\\begin{tabular}{cc}\ntexto da tabela\n\\end{tabular}\n\\end{table}",
						result);
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
