package br.com.caelum.tubaina.builder;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.builder.replacer.Replacer;

public class ChunksMaker {

	private final List<Replacer> replacers = new ArrayList<Replacer>();

	public List<Chunk> make(String text) {
		List<Chunk> chunks = new ArrayList<Chunk>();
		while (text.trim().length() != 0) {
			boolean accepted = false;
			for (Replacer replacer : replacers) {
				if (replacer.accepts(text)) {
					text = replacer.execute(text, chunks);
					accepted = true;
					break;
				}
			}
			if (!accepted)
				throw new TubainaException("There is a syntax error on chapter "+ ChapterBuilder.getChaptersCount() +
					": Probably, there is some text inside an exercise tag, but outside a question tag, or " +
					"an unnacceptable tag at some place.");
		}
		return chunks;
	}

	public void register(Replacer replacer) {
		this.replacers.add(replacer);
	}

}
