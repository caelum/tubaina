package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.AbstractChunk;

public class SubsectionChunk extends AbstractChunk<SubsectionChunk> {

	private final String title;

	public SubsectionChunk(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

}
