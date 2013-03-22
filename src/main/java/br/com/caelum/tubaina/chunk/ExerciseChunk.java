package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;

public class ExerciseChunk extends CompositeChunk<ExerciseChunk> {

	private static int COUNT = 1;
	private int id;

	public ExerciseChunk(List<Chunk> body) {
		super(body);
		this.id = COUNT++;
	}

	public int getId() {
		return id;
	}
	
	public static int getExerciseCount() {
		return COUNT;
	}
}
