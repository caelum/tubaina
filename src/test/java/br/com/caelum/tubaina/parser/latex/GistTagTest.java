package br.com.caelum.tubaina.parser.latex;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.tubaina.gists.GistConnector;
import br.com.caelum.tubaina.gists.GistRequest;
import br.com.caelum.tubaina.gists.GistResultRetriever;
import br.com.caelum.tubaina.gists.JsonToGistResultConverter;
import br.com.caelum.tubaina.parser.SimpleIndentator;

public class GistTagTest {

	@Test
	public void gistedCodeIsRetrievedAndUsed() throws Exception {
		GistResultRetriever retriever = new GistResultRetriever(
				new GistConnector(new JsonToGistResultConverter(),
						new GistRequest()));

		String options = "417835";
		String gistedCode = "javascript:(function() {window.frames[3][0].document.getElementById('frameplugin').style.display='none'})()";

		String output = new GistTag(new SimpleIndentator(4), retriever).parse(
				"", options);

		Assert.assertEquals(CodeTag.BEGIN + "{text}\n" + gistedCode
				+ CodeTag.END, output);
	}

	@Test
	public void gistedCodeIsParsedWithLineNumbers() throws Exception {
		String gistedCode = "GivenCode";
		String options = "417835 #";
		long gistId = 417835;

		String json = new Scanner(
				JsonToGistResultConverter.class
						.getResourceAsStream("/gist.json")).useDelimiter("\\Z")
				.next();

		GistRequest mockedGistRequest = mock(GistRequest.class);
		when(mockedGistRequest.get(gistId)).thenReturn(json);

		GistConnector connector = new GistConnector(new JsonToGistResultConverter(), mockedGistRequest);

		GistResultRetriever retriever = new GistResultRetriever(connector);

		String output = new GistTag(new SimpleIndentator(4), retriever).parse(
				"", options);
		Assert.assertEquals(CodeTag.BEGIN + "[linenos, numbersep=5pt]{text}\n"
				+ gistedCode + CodeTag.END, output);
	}

}