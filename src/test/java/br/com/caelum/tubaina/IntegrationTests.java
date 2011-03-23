package br.com.caelum.tubaina;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class IntegrationTests {

	@Test
	public void verbatimShouldWorkHere() throws Exception {
		Tubaina.main("--latex", "-i", "src/test/resources/", "-o", "bin/",
				"-n", "\"Meu teste master legal\"");
		String expected = leArquivo("src/test/resources/expected.tex");
		String actual = leArquivo("bin/latex/book.tex");		
		Assert.assertEquals(expected, actual);
	}

	
	private String leArquivo(String nomeDoArquivo) throws FileNotFoundException {
		String expected;
		Scanner reading = new Scanner(new File(nomeDoArquivo));
		StringBuilder builder = new StringBuilder();
		int i = 0;
		while (reading.hasNextLine()) {
			if(i<100) {
				i++;
				reading.nextLine();
			}
			else
				builder.append(reading.nextLine() + "\n");
		}
		expected = builder.toString();
		return expected;
	}
}
