package br.com.caelum.tubaina.parser;

import br.com.caelum.tubaina.Chunk;

public interface Tag<T extends Chunk> {
	String parse(T chunk);
}
