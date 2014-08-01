
package br.com.caelum.tubaina.builder;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import br.com.caelum.tubaina.AfcFile;
import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.util.TitleSlug;

public class BookBuilder {

    private final Logger LOG = Logger.getLogger(BookBuilder.class);
    private final String name;
    private final List<AfcFile> readers = new ArrayList<AfcFile>();
    private final List<AfcFile> introductionReaders = new ArrayList<AfcFile>();
    private BookPartsBuilder bookPartsBuilder;
    private int chapterNumber;
	private final SectionsManager sectionsManager;

    public BookBuilder(String name, SectionsManager sectionsManager) {
        this.name = name;
		this.sectionsManager = sectionsManager;
        this.bookPartsBuilder = new BookPartsBuilder(sectionsManager);
        this.chapterNumber = 1;
    }

    public void addAllReaders(List<AfcFile> chapterReaders, List<AfcFile> introductionReaders) {
        this.readers.addAll(chapterReaders);
        this.introductionReaders.addAll(introductionReaders);
    }
    
    public void addReaderFromString(String fileContent) {
        addAllReaders(Arrays.asList(new AfcFile(new StringReader(fileContent), "file from string")), 
                new ArrayList<AfcFile>());
    }
    
    public void addReaderFromStrings(List<String> chaptersContent) {
        for (String content : chaptersContent) {
            addReaderFromString(content);
        }
    }
    
    public void addAllReadersOfNonNumberedFromStrings(List<String> introductionChapters) {
        for (String content : introductionChapters) {     
            this.introductionReaders.add(new AfcFile(new StringReader(content), "intro chapter from string"));
        }
    }

    public Book build() {
        List<Chapter> introductionChapters = parseIntroductionChapters();
        parseBookChapters();
        return new Book(name, bookPartsBuilder.build(), introductionChapters);
    }

    private void parseBookChapters() {
        for (AfcFile afcFile : readers) {
            LOG.info("Parsing chapter " + chapterNumber + " - " + afcFile.getFileName());
            Scanner scanner = new Scanner(afcFile.getReader());
            scanner.useDelimiter("$$");
            if (scanner.hasNext()) {
                String text = scanner.next();
                bookPartsBuilder.addPartFrom(text);
                bookPartsBuilder.addChaptersToLastAddedPart(parseChapters(text, false));
            }
        }
    }

    private List<Chapter> parseIntroductionChapters() {
        List<Chapter> introductionChapters = new ArrayList<Chapter>();
        for (AfcFile afcFile: introductionReaders) {
        	LOG.info("Parsing introduction chapter - " + afcFile.getFileName());
            Scanner scanner = new Scanner(afcFile.getReader());
            scanner.useDelimiter("$$");
            introductionChapters.addAll(parseChapters(scanner.next(), true));
        }
        return introductionChapters;
    }

    private List<Chapter> parseChapters(String text, boolean introductionChapter) {  //[chapter nome do capi label="blablabla"]
        Pattern chapterPattern = Pattern
                .compile("(?i)(?s)(?m)^\\[chapter(.*?)(?:label=\"(.*?)\")?\\](.*?)(\n\\[chapter|\\z)");
        Matcher chapterMatcher = chapterPattern.matcher(text);

        List<Chapter> chapters = new ArrayList<Chapter>();

        Integer offset = 0;

        while (chapterMatcher.find(offset)) {
            String title = chapterMatcher.group(1).trim();
            String label = chapterMatcher.group(2) == null ? new TitleSlug(title).toString() : chapterMatcher.group(2);
            String content = chapterMatcher.group(3);
            offset = chapterMatcher.end(3);

            String introduction = extractIntroduction(content);

            content = content.substring(introduction.length());

            Chapter chapter = new ChapterBuilder(title, label, introduction, content, chapterNumber, introductionChapter, sectionsManager).build();
            chapters.add(chapter);
            sectionsManager.nextChapter();
            if (!introductionChapter)
                chapterNumber++;
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
