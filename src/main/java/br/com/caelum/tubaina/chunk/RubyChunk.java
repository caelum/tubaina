package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.AbstractChunk;

public class RubyChunk extends AbstractChunk<RubyChunk> {

	private final String content;
	private final String options;

	public RubyChunk(String content, String options) {
		this.content = content;
		this.options = options;
	}

	public String getContent() {
		return content;
	}
	
	public String getOptions() {
		return options;
	}
}
