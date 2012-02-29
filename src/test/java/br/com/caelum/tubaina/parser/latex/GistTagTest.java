package br.com.caelum.tubaina.parser.latex;

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
		GistResultRetriever retriever = new GistResultRetriever(new GistConnector(
				new JsonToGistResultConverter(), new GistRequest()));

		String options = "417835";
		String gistedCode = "javascript:(function() {window.frames[3][0].document.getElementById('frameplugin').style.display='none'})()";

		String output = new GistTag(new SimpleIndentator(), retriever).parse("", options);

		Assert.assertEquals(CodeTag.BEGIN + "{text}\n" + gistedCode + CodeTag.END, output);
	}
}
