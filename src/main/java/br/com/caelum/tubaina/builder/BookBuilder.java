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

    public void addAllReaders(List<Reader> readers) {
        this.readers.addAll(readers);
    }

    public Book build() {
        return this.build(false);
    }

    public Book build(boolean showNotes) {
        BookPartsBuilder bookPartsBuilder = new BookPartsBuilder();
        for (Reader reader : readers) {
            LOG.info("Parsing chapter " + Chapter.getChaptersCount());
            Scanner scanner = new Scanner(reader);
            scanner.useDelimiter("$$");
            if (scanner.hasNext()) {
                String text = scanner.next();
                bookPartsBuilder.addPartFrom(text);
                bookPartsBuilder.addChaptersToLastAddedPart(parseChapters(text));
            }
        }
        return new Book(name, bookPartsBuilder.build(), showNotes);
    }

    private List<Chapter> parseChapters(String text) {
        Pattern chapterPattern = Pattern
                .compile("(?i)(?s)(?m)^\\[chapter(.*?)\\](.*?)(\n\\[chapter|\\z)");
        Matcher chapterMatcher = chapterPattern.matcher(text);

        List<Chapter> chapters = new ArrayList<Chapter>();

        Integer offset = 0;

        while (chapterMatcher.find(offset)) {
            String title = chapterMatcher.group(1).trim();
            String content = chapterMatcher.group(2);
            offset = chapterMatcher.end(2);

            String introduction = extractIntroduction(content);

            content = content.substring(introduction.length());

            Chapter chapter = new ChapterBuilder(title, introduction, content).build();
            chapters.add(chapter);

        }

        // TODO : Refactoring
        if (chapters.size() > 1) {
            throw new TubainaException("Only one [chapter] element is allowed per file.");
        }

        return chapters;
    }

    private String extractIntroduction(String content) {
        Pattern introductionPattern = Pattern.compile("(?i)(?s)(.*?)(?:\\[section|\\z)");
        Matcher introductionMatcher = introductionPattern.matcher(content);

        String introduction = "";
        if (introductionMatcher.find())
            introduction = introductionMatcher.group(1);
        return introduction;
    }

}
