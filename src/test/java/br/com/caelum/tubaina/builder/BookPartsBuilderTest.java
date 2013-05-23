package br.com.caelum.tubaina.builder;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.BookPart;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.resources.ResourceLocator;

public class BookPartsBuilderTest {
	
	SectionsManager sectionsManager = new SectionsManager();
    
    @Before
    public void setUp() {
        ResourceLocator.initialize(".");
    }

    @Test
    public void shouldBuildBookParts() throws Exception {
        String partOneText = "[part \"part one\"]";
        String partTwoText = "[part \"part two\"]";
        Chapter first = new ChapterBuilder("first", "introduction",
                "[section test]\nchpater 1 text", 1, sectionsManager).build();
        Chapter second = new ChapterBuilder("second", "introduction",
                "[section test]\nchpater 2 text", 2, sectionsManager).build();
        Chapter third = new ChapterBuilder("third", "introduction",
                "[section test]\nchpater 3 text", 3, sectionsManager).build();
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
    public void shouldBuildBookPartsWithIllustration() throws Exception {
        String partOneText = "[part \"part one\" illustration=resources/image.png]";
        String partTwoText = "[part \"part two\"]";
        Chapter first = new ChapterBuilder("first", "introduction",
                "[section test]\nchpater 1 text", 1, sectionsManager).build();
        Chapter second = new ChapterBuilder("second", "introduction",
                "[section test]\nchpater 2 text", 2, sectionsManager).build();
        Chapter third = new ChapterBuilder("third", "introduction",
                "[section test]\nchpater 3 text", 3, sectionsManager).build();
        List<BookPart> bookParts = new BookPartsBuilder().addPartFrom(partOneText)
                .addChaptersToLastAddedPart(Arrays.asList(first)).addPartFrom(partTwoText)
                .addChaptersToLastAddedPart(Arrays.asList(second, third)).build();
        
        BookPart partOne = bookParts.get(0);
        BookPart partTwo = bookParts.get(1);
        
        assertEquals(2, bookParts.size());
        assertEquals("part one", partOne.getTitle());
        assertEquals("image.png", partOne.getIllustrationPath());
        assertEquals("part two", partTwo.getTitle());
        assertEquals("first", partOne.getChapters().get(0).getTitle());
        assertEquals("second", partTwo.getChapters().get(0).getTitle());
        assertEquals("third", partTwo.getChapters().get(1).getTitle());
    }

    @Test
    public void shouldBuildBookPartsWithIntro() throws Exception {
        String partOneText = "[part \"part one\"]\n introduction text";
        Chapter first = new ChapterBuilder("first", "introduction",
                "[section \"test\"]\nchpater 1 text", 1, sectionsManager).build();
        Chapter second = new ChapterBuilder("second", "introduction",
                "[section \"test\"]\nchpater 2 text", 2, sectionsManager).build();

        List<BookPart> bookParts = new BookPartsBuilder().addPartFrom(partOneText)
                .addChaptersToLastAddedPart(Arrays.asList(first, second)).build();
        assertEquals("introduction text", bookParts.get(0).getIntroductionText());
        assertEquals("first", bookParts.get(0).getChapters().get(0).getTitle());
        assertEquals(1, bookParts.get(0).getChapters().get(0).getChapterNumber());
    }
    
}
