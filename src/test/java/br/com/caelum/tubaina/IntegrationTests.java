package br.com.caelum.tubaina;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class IntegrationTests {

	@Test
	public void completeIntegrationTestForLatex() throws Exception {
		Tubaina.main("--latex", "-i", "src/test/resources/", "-o", "bin/",
				"-n", "\"Meu teste master legal\"");
		String expected = leArquivo("src/test/resources/expected.tex").split("fancyhf")[1];
		String actual = leArquivo("bin/latex/book.tex").split("fancyhf")[1];
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void completeIntegrationTestForSingleHtml() throws Exception {
		Tubaina.main("--singlehtml", "-i", "src/test/resources/singlehtml", "-o", "bin/",
				"-n", "\"Meu teste singlehtml\"");
		String expected = leArquivo("src/test/resources/singlehtml/expected.html");
		String actual = leArquivo("bin/singlehtml/meu-teste-singlehtml/index.html");
		Assert.assertEquals(expected, actual);
	}
	
	@After
	public void cleanUp() throws IOException {
		FileUtils.deleteDirectory(new File("bin/"));
	}
	
	
	private String leArquivo(String nomeDoArquivo) throws FileNotFoundException {
		String expected;
		Scanner reading = new Scanner(new File(nomeDoArquivo));
		StringBuilder builder = new StringBuilder();
		while (reading.hasNextLine()) {
				builder.append(reading.nextLine() + "\n");
		}
		expected = builder.toString();
		return expected;
	}
}
