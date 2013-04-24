package br.com.caelum.tubaina.gists;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import org.junit.Test;

public class JsonToGistResultConverterTest {

	@Test
	public void convertAGivenJsonToGistResult() {
		JsonToGistResultConverter jsonTo = new JsonToGistResultConverter();
		
		String json = new Scanner(JsonToGistResultConverter.class.getResourceAsStream("/gist.json")).useDelimiter("\\Z").next();
		GistResult gist = jsonTo.convert(json);
		
		assertEquals(gist.getContent(), "GivenCode");
		assertEquals(gist.getLanguage(), "");
		assertEquals(gist.getFileName(), "GivenFile");
		
	}

}
