package br.com.caelum.tubaina.builder;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.TubainaException;

public class BookBuilder {

	private final Logger LOG = Logger.getLogger(BookBuilder.class);
	private final String name;
	
	private final List<Reader> readers = new ArrayList<Reader>();
	
	public BookBuilder(String name) {
		this.name = name;
	}

	public void add(Reader fileReader) {
		this.readers.add(fileReader);
	}

	public void addAll(List<Reader> readers) {
		this.readers.addAll(readers);
	}
	
	public Book build() {
		return this.build(false);
	}
	
	public Book build(boolean showNotes) {
		List<Chapter> chapters = new ArrayList<Chapter>();
		for (Reader reader : readers) {
			LOG.info("Parsing chapter " + Chapter.getChaptersCount());
			Scanner scanner = new Scanner(reader);
			scanner.useDelimiter("$$");
			if (scanner.hasNext())
				chapters.addAll(parse(scanner.next()));
		}
		return new Book(name, chapters, showNotes);
	}

	private List<Chapter> parse(String text) {
		Pattern pattern = Pattern.compile("(?i)(?s)(?m)^\\[chapter(.*?)\\](.*?)(\n\\[chapter|\\z)");
		Matcher matcher = pattern.matcher(text);

		List<Chapter> chapters = new ArrayList<Chapter>();

		Integer offset = 0;

		while (matcher.find(offset)) {
			
			
			String title = matcher.group(1).trim();
			String content = matcher.group(2);
			offset = matcher.end(2);

			
			Pattern introductionPattern = Pattern.compile("(?i)(?s)(.*?)(?:\\[section|\\z)");
			Matcher introductionMatcher = introductionPattern.matcher(content);
			
			String introduction = "";
			if (introductionMatcher.find())
				introduction = introductionMatcher.group(1);
			
			content = content.substring(introduction.length());

			Chapter chapter = new ChapterBuilder(title, introduction, content).build();
			chapters.add(chapter);
			
		}
		
		//TODO : Refactoring
		if(chapters.size() > 1) {
			throw new TubainaException("Only one [chapter] element is allowed per file.");
		}
		
		return chapters;
	}


}
