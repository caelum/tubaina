package br.com.caelum.tubaina.parser;


import java.io.File;
import java.io.IOException;

import br.com.caelum.tubaina.Book;

public interface Generator {

	void generate(Book book, File directory) throws IOException;
	
}
