package br.com.caelum.tubaina.chunk;

import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;
import br.com.caelum.tubaina.parser.Parser;

public class ExerciseChunk implements CompositeChunk {

	private static int COUNT = 1;
	private List<Chunk> body;
	private int id;

	public ExerciseChunk(List<Chunk> body) {
		this.body = body;
		this.id = COUNT++;
	}

	public String getContent(Parser p) {
		String content = "";
		for (Chunk c : body) {
			content += c.getContent(p);
		}
		return p.parseExercise(content, id);
	}
	
	public static int getExerciseCount() {
		return COUNT;
	}
}
