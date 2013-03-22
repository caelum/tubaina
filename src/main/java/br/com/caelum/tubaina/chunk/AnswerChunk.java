package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;

public class AnswerChunk extends CompositeChunk<AnswerChunk> {

	private static int ANSWER_ID = 0;
	private int id;

	public AnswerChunk(List<Chunk> body) {
		super(body);
		this.id = ANSWER_ID++;
	}

	public int getId() {
		return id;
	}

}
