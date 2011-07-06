package br.com.caelum.tubaina.parser.html;

import java.io.File;
import java.io.IOException;

import br.com.caelum.tubaina.Book;

public interface Generator {

	void generate(final Book b, final File directory) throws IOException;

}