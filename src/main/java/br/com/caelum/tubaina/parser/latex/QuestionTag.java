package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.parser.Tag;

public class QuestionTag implements Tag {

	public String parse(Chunk chunk) {
		return "\\item{" + string + "}";
	}

}
