package br.com.caelum.tubaina.resources;

import java.io.File;

import br.com.caelum.tubaina.chunk.AnswerChunk;

public interface ResourceManipulator {
	
	public void copyAndScaleImage(File image, String scale);
	public void copyAnswer(AnswerChunk chunk);
	public void copyExercise(int id);
	public void copyIndex(String name, int dirNumber);
}
