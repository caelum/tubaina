package br.com.caelum.tubaina.builder.replacer;

import java.util.List;

import br.com.caelum.tubaina.Chunk;

public interface Replacer {

	String execute(String text, List<Chunk> chunks);

	boolean accepts(String text);

}
