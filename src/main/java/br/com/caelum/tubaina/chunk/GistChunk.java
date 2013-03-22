package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.AbstractChunk;

public class GistChunk extends AbstractChunk<GistChunk> {

	private final String options;

	public GistChunk(String options) {
		this.options = options;
	}

	public String getOptions() {
		return options;
	}

}
