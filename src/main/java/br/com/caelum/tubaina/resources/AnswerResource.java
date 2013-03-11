package br.com.caelum.tubaina.resources;

import br.com.caelum.tubaina.chunk.AnswerChunk;

public class AnswerResource implements Resource {

	private final AnswerChunk chunk;

	public AnswerResource(AnswerChunk chunk) {
		this.chunk = chunk;
	}

	@Override
	public void copyTo(ResourceManipulator manipulator) {
		manipulator.copyAnswer(chunk);
	}

}
