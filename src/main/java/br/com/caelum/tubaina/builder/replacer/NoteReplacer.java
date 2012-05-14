package br.com.caelum.tubaina.builder.replacer;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.builder.ChunkSplitter;
import br.com.caelum.tubaina.chunk.MockedChunk;
import br.com.caelum.tubaina.chunk.NoteChunk;
import br.com.caelum.tubaina.resources.Resource;

public class NoteReplacer extends AbstractReplacer {

	private static final String INSTRUCTOR_NOTE = "\\instructornote";
	private List<Resource> resources;

	public NoteReplacer(List<Resource> resources) {
		super("note");
		this.resources = resources;
	}

	@Override
	public Chunk createChunk(String options, String content) {
		ChunkSplitter splitter = new ChunkSplitter(resources, ReplacerType.NOTE);
		List<Chunk> list = new ArrayList<Chunk>();
		list.add(new MockedChunk(INSTRUCTOR_NOTE));
		return new NoteChunk(list, splitter.splitChunks(content));
	}

}
