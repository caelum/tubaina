package br.com.caelum.tubaina.gists;

public class GistResultRetriever {

	private GistConnector connector;
	
	public GistResultRetriever(GistConnector connector) {
		this.connector = connector;
	}

	public GistResult retrieve(long gistId, String filename) {
		return connector.get(gistId);
	}
}
