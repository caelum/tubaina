package br.com.caelum.tubaina;

import com.google.inject.Inject;

import br.com.caelum.tubaina.parser.Tag;

public abstract class AbstractChunk <T extends AbstractChunk<T>> implements Chunk{

	@Inject
	private Tag<T> tag;
	
	@Override
	@SuppressWarnings("unchecked")
	public String asString() {
		return tag.parse((T) this);
	}
}
