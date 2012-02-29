package br.com.caelum.tubaina.gists;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class GistConnectorTest {

	@Test
	public void useGivenGistIdToRetrieveGistResult() throws Exception {
		int gistId = 1940936;
		String simpleJson = "{simpleJson: true}";
		
		JsonToGistResultConverter converter = mock(JsonToGistResultConverter.class);
		GistRequest gist = mock(GistRequest.class);
		
		when(gist.get(gistId)).thenReturn(simpleJson);
		
		new GistConnector(converter, gist).get(gistId);
		
		verify(converter).convert(simpleJson);
	}

}
