package br.com.caelum.tubaina.parser.markdown;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.TubainaBuilderData;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.html.desktop.Generator;

public class MarkdownGenerator implements Generator {

    private final Parser parser;

    public MarkdownGenerator(final Parser parser, TubainaBuilderData data) {
        this.parser = parser;
    }

    public void generate(Book book, File directory) throws IOException {
    	
    	int number = 1;
    	for(Chapter chapter : book.getChapters()) {
    		
    		PrintStream ps = new PrintStream(new File(directory, "cap" + number++ + ".md"));
    		
    		String chapterTitle = parser.parse(chapter.getTitle());
			ps.println("# " + chapterTitle);
			ps.println();
			
			ps.println(chapter.getIntroduction());

			for(Section section : chapter.getSections()) {
				ps.println("## " + parser.parse(section.getTitle()));
				ps.println();
				
				for(Chunk chunk : section.getChunks()) {
					ps.println(chunk.asString());
				}
			}
			
			ps.flush();
			ps.close();
    	}

    }
    
}