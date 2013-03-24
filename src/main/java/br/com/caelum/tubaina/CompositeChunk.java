package br.com.caelum.tubaina;

import java.util.List;


public abstract class CompositeChunk<T extends CompositeChunk<T>> extends AbstractChunk<T> {

	protected final List<Chunk> body;

	public CompositeChunk(List<Chunk> body) {
		this.body = body;
	}

	public String getContent() {
		String content = "";
		for (Chunk c : body) {
			content += c.asString();
		}
		return content;
	}

}
