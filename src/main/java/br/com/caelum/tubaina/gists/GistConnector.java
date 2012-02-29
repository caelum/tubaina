package br.com.caelum.tubaina.gists;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import br.com.caelum.tubaina.TubainaException;

public class GistConnector {

	private JsonToGistResultConverter converter;
	private GistRequest gist;
	
	public GistConnector(JsonToGistResultConverter converter, GistRequest gist) {
		this.converter = converter;
		this.gist = gist;
	}

	public GistResult get(long gistId) {
		try {
			String jsonResult = extractJson(gistId);

			return converter.convert(jsonResult);
		} catch (ClientProtocolException e) {
			throw new TubainaException(e);
		} catch (IOException e) {
			throw new TubainaException(e);
		}
	}

	private String extractJson(long gistId) throws IOException,
			ClientProtocolException {

		String jsonResult = gist.get(gistId);
		return jsonResult;
	}
	
}
