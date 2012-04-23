package br.com.caelum.tubaina.builder;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.TubainaSyntaxErrorsException;
import br.com.caelum.tubaina.builder.replacer.Replacer;

public class ChunksMaker {

	private final List<Replacer> replacers = new ArrayList<Replacer>();

	public List<Chunk> make(String text) {
        List<Exception> exceptions = new ArrayList<Exception>();
		List<Chunk> chunks = new ArrayList<Chunk>();
		while (text.trim().length() != 0) {
			boolean accepted = false;
			for (Replacer replacer : replacers) {
				if(replacer.accepts(text)) {
				    try {
				        text = replacer.execute(text, chunks);
                        accepted = true;
                    } catch (TubainaException e) {
                        exceptions.add(e);
                    }
				    break;
				}
			}
            if (!accepted) {
                exceptions.add(new TubainaException("There is a syntax error on chapter "+ Chapter.getChaptersCount() +
                        ": Probably, there is some text inside an exercise tag, but outside a question tag, or " +
                        "an unnacceptable tag at some place."));
                break;
            }
		}
		if (!exceptions.isEmpty()) {
		    throw new TubainaSyntaxErrorsException("There are syntax errors on a section of chapter "+ 
                    Chapter.getChaptersCount() + ". See the messages below.", exceptions);
		}
		return chunks;
	}

	public void register(Replacer replacer) {
		this.replacers.add(replacer);
	}

}
