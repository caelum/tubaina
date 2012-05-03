package br.com.caelum.tubaina.parser.html.desktop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.parser.Tag;

public class TodoTag implements Tag {

	public String parse(String string, String options) {
		try {
			PrintStream stream = new PrintStream(new FileOutputStream(new File("todo.log"), true));
			stream.println("<==========================================================>");
			stream.println(string.trim());
		} catch (FileNotFoundException e) {
			throw new TubainaException("File could not be read", e);
		}
		return "";
	}

}
