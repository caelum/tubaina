package br.com.caelum.tubaina.parser.html.kindle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.chunk.TodoChunk;
import br.com.caelum.tubaina.parser.Tag;

public class TodoTag implements Tag<TodoChunk> {

	@Override
	public String parse(TodoChunk chunk) {
		try {
			PrintStream stream = new PrintStream(new FileOutputStream(new File("todo.log"), true));
			stream.println("<==========================================================>");
			stream.println(chunk.getContent().trim());
		} catch (FileNotFoundException e) {
			throw new TubainaException("File could not be read", e);
		}
		return "";
	}

}
