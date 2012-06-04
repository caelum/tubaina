package br.com.caelum.tubaina.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import br.com.caelum.tubaina.BookPart;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.chunk.IntroductionChunk;
import br.com.caelum.tubaina.resources.Resource;

public class BookPartsBuilder {

    private final Logger LOG = Logger.getLogger(BookPartsBuilder.class);

    List<BookPart> bookParts;

    private Pattern bookPartPattern;

    public BookPartsBuilder() {
        bookParts = new ArrayList<BookPart>();
        bookPartPattern = Pattern.compile("(?i)(?s)(?m)^\\[part(.*?)\\].*?");
    }

    public BookPartsBuilder addPartFrom(String text) {
        if (containsPartTag(text)) {
            String bookPartTitle = extractPartBookTitle(text);

            String introductionText = extractIntroduction(text);
            List<Resource> partResources = new ArrayList<Resource>();
            IntroductionChunk introChunk = new IntroductionChunk(new ChunkSplitter(partResources, "all")
                    .splitChunks(introductionText));

            bookParts.add(new BookPart(bookPartTitle, true, introductionText, introChunk, partResources));
            LOG.info("Parsing part: " + bookPartTitle);
        }
        return this;
    }

    private String extractIntroduction(String text) {
        Pattern introductionPattern = Pattern.compile("(?i)(?s)\\[part.*?\\](.*?)(\\[chapter|\\z)");
        Matcher introductionMatcher = introductionPattern.matcher(text);

        String introduction = "";
        if (introductionMatcher.find())
            introduction = introductionMatcher.group(1);
        introduction = introduction.trim();
        return introduction;
    }

    public List<BookPart> build() {
        return bookParts;
    }

    public BookPartsBuilder addChaptersToLastAddedPart(List<Chapter> parsedChapters) {
        if (bookParts.isEmpty()) {
            bookParts.add(new BookPart("", false, "", null, new ArrayList<Resource>()));
        }
        int lastPartIndex = bookParts.size() - 1;
        bookParts.get(lastPartIndex).addAllChapters(parsedChapters);
        return this;
    }

    private String extractPartBookTitle(String text) {
        Pattern titlePattern = Pattern.compile("(?i)(?s)(?m)^\\[part\\s+\"(.*?)\"\\].*?");
        Matcher titleMatcher = titlePattern.matcher(text);
        titleMatcher.find();
        return titleMatcher.group(1).trim();
    }

    private boolean containsPartTag(String text) {
        Matcher chapterMatcher = bookPartPattern.matcher(text);
        return chapterMatcher.matches();
    }
}
