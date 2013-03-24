package br.com.caelum.tubaina.chunk;

import br.com.caelum.tubaina.AbstractChunk;

public class JavaChunk extends AbstractChunk<JavaChunk> {

	private String content;
	private final String options;

	public JavaChunk(String options, String content) {
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
