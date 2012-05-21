package br.com.caelum.tubaina.builder;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.com.caelum.tubaina.BookPart;
import br.com.caelum.tubaina.Chapter;

public class BookPartsBuilderTest {

    @Test
    public void shouldBuildBookParts() throws Exception {
        String partOneText = "[part part one]";
        String partTwoText = "[part part two]";
        Chapter first = new ChapterBuilder("first", "introduction",
                "[section test]\nchpater 1 text").build();
        Chapter second = new ChapterBuilder("second", "introduction",
                "[section test]\nchpater 2 text").build();
        Chapter third = new ChapterBuilder("third", "introduction",
                "[section test]\nchpater 3 text").build();
        List<BookPart> bookParts = new BookPartsBuilder().addPartFrom(partOneText)
                .addChaptersToLastAddedPart(Arrays.asList(first)).addPartFrom(partTwoText)
                .addChaptersToLastAddedPart(Arrays.asList(second, third)).build();

        BookPart partOne = bookParts.get(0);
        BookPart partTwo = bookParts.get(1);

        assertEquals(2, bookParts.size());
        assertEquals("part one", partOne.getTitle());
        assertEquals("part two", partTwo.getTitle());
        assertEquals("first", partOne.getChapters().get(0).getTitle());
        assertEquals("second", partTwo.getChapters().get(0).getTitle());
        assertEquals("third", partTwo.getChapters().get(1).getTitle());
    }

    @Test
    public void shouldBookPartsWithIntro() throws Exception {
        String partOneText = "[part part one]\n introduction text";
        Chapter first = new ChapterBuilder("first", "introduction",
                "[section test]\nchpater 1 text").build();
        Chapter second = new ChapterBuilder("second", "introduction",
                "[section test]\nchpater 2 text").build();

        List<BookPart> bookParts = new BookPartsBuilder().addPartFrom(partOneText)
                .addChaptersToLastAddedPart(Arrays.asList(first, second)).build();
        assertEquals("introduction text", bookParts.get(0).getIntroduction());

    }
    
}
