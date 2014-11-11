package br.com.caelum.tubaina.parser.html.desktop;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.chunk.MockedChunk;
import org.junit.Before;

public class AbstractTagTest {

	@Before
	public final void cleanCodeCache() {
		String dir = System.getProperty("java.io.tmpdir");
		File tempdir = new File(dir);
		File[] files = tempdir.listFiles();
		for (File file : files) {
			file.delete();
		}
	}

	protected String getContent(Chunk chunk) {
		new HtmlModule().inject(chunk);
		return chunk.asString();
	}

	protected List<Chunk> text(String text) {
		return Arrays.<Chunk>asList(new MockedChunk(text));
	}
}
