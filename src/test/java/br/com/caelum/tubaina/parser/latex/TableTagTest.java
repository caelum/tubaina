package br.com.caelum.tubaina.parser.latex;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.chunk.TableColumnChunk;
import br.com.caelum.tubaina.chunk.TableRowChunk;

public class TableTagTest extends AbstractTagTest {

	private List<Chunk> rows;

	@Before
	public void setUp() {
		TableColumnChunk tableColumnChunk = new TableColumnChunk(text("texto da tabela"));
		List<Chunk> colunas = Arrays.<Chunk>asList(tableColumnChunk);
		rows = Arrays.<Chunk>asList(new TableRowChunk(colunas));
	}

	@Test
	public void testTable() {
		TableChunk chunk = new TableChunk("", rows);
		String result = getContent(chunk);
		Assert.assertEquals("\\begin{table}[!h]\n" + 
								"\\caption{}\n" + 
								"\\begin{center}\n" + 
									"\\rowcolors[]{1}{gray!30}{gray!15}\n" + 
									"\\begin{tabularx}{X}\n" + 
										"\\hline\n" + 
										"texto da tabela\\\\\n" + 
										"\\hline\n" + 
									"\\end{tabularx}\n" +
								"\\end{center}\n" +
							"\\end{table}", result);
	}


	@Test
	public void testTableWithTitle() {
		TableChunk chunk = new TableChunk("\"titulo\"", rows);
		String result = getContent(chunk);
		Assert.assertEquals("\\begin{table}[!h]\n" + 
								"\\caption{titulo}\n" + 
								"\\begin{center}\n" + 
									"\\rowcolors[]{1}{gray!30}{gray!15}\n" + 
									"\\begin{tabularx}{X}\n" + 
										"\\hline\n" + 
										"texto da tabela\\\\\n" + 
										"\\hline\n" + 
									"\\end{tabularx}\n" +
								"\\end{center}\n" +
							"\\end{table}", result);
	}

	@Test
	public void testTableWithoutBorder() {
		TableChunk chunk = new TableChunk("noborder", rows);
		String result = getContent(chunk);
		Assert.assertEquals("\\begin{table}[!h]\n" + 
								"\\caption{}\n" + 
								"\\begin{center}\n" + 
									"\\begin{tabularx}{X}\n" + 
										"texto da tabela\\\\\n" + 
									"\\end{tabularx}\n" +
								"\\end{center}\n" +
							"\\end{table}", result);
	}

	@Test
	public void testTableWithTitleAndWithoutBorder() {
		TableChunk chunk = new TableChunk("noborder \"titulo\"", rows);
		String result = getContent(chunk);
		Assert.assertEquals("\\begin{table}[!h]\n" + 
								"\\caption{titulo}\n" + 
								"\\begin{center}\n" + 
									"\\begin{tabularx}{X}\n" + 
										"texto da tabela\\\\\n" + 
									"\\end{tabularx}\n" +
								"\\end{center}\n" +
							"\\end{table}", result);
	}

	@Test(expected = TubainaException.class)
	public void testTableWithInvalidNumberOfColums() {
		TableChunk chunk = new TableChunk("", text("texto da tabela"));
		getContent(chunk);
	}
}
