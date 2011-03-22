package br.com.caelum.tubaina;

import java.io.File;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class IntegrationTests {

	@Test
	public void verbatimShouldWorkHere() throws Exception {
		Tubaina.main("--latex", "-i", "src/test/resources/", "-o", "bin/",
				"-n", "\"Meu teste master legal\"");
		Process exec = Runtime.getRuntime().exec(
				"diff src/test/resources/expected.tex bin/latex/book.tex");

		if (exec.waitFor() != 0) {
			Scanner readExpected = new Scanner(new File("src/test/resources/expected.tex"));
			StringBuilder builder = new StringBuilder();
			while (readExpected.hasNextLine()) {
				builder.append(readExpected.nextLine() + "\n");
			}
			String expected = builder.toString();
			Scanner readReal = new Scanner(new File("bin/latex/book.tex"));
			StringBuilder builder2 = new StringBuilder();
			while (readReal.hasNextLine()) {
				builder2.append(readReal.nextLine() + "\n");
			}
			String actual = builder2.toString();
			Assert.assertEquals(expected, actual);
		}
	}
}
