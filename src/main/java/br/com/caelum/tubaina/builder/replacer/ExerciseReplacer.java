package br.com.caelum.tubaina.builder.replacer;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.chunk.ExerciseChunk;
import br.com.caelum.tubaina.resources.AnswerResource;
import br.com.caelum.tubaina.resources.ExerciseResource;
import br.com.caelum.tubaina.resources.Resource;

public class ExerciseReplacer extends AbstractReplacer {

	private List<Resource> resources;

	
	public ExerciseReplacer(List<Resource> resources) {
		super("exercise");
		this.resources = resources;
	}

	@Override
	public Chunk createChunk(String options, String content) {
		ArrayList<Resource> subResources = new ArrayList<Resource>();
		ChunkSplitter splitter = new ChunkSplitter(subResources, ReplacerType.EXERCISE);
		List<Chunk> chunks = splitter.splitChunks(content);
		if (hasAnswer(subResources))
			resources.add(new ExerciseResource(ExerciseChunk.getExerciseCount()));
		resources.addAll(subResources);
		return new ExerciseChunk(chunks);
	}

	private boolean hasAnswer(ArrayList<Resource> subResources) {
		for (Resource resource : subResources) {
			if (resource instanceof AnswerResource) {
				return true;
			}
		}
		return false;
	}

}
