package br.com.caelum.tubaina.parser.html.kindle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.tubaina.BookPart;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.io.TubainaHtmlDir;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import br.com.caelum.tubaina.util.Utilities;
import freemarker.template.Configuration;

public class PartToKindle {
    private final Parser parser;

    private final Configuration cfg;

    public PartToKindle(final Parser parser, final Configuration cfg) {
        this.parser = parser;
        this.cfg = cfg;
    }

    public StringBuffer generateKindlePart(BookPart part, TubainaHtmlDir bookRootDir, int partNumber) {
        ChapterToKindle chapterToKindle = new ChapterToKindle(parser, cfg);
        StringBuffer chaptersContent = new StringBuffer();
        List<Chapter> chapters = part.getChapters();
        for (Chapter chapter : chapters) {
            StringBuffer chapterContent = chapterToKindle.generateKindleHtmlChapter(chapter);
            chapterContent = fixPaths(chapter, chapterContent);
            chaptersContent.append(chapterContent);
            if (!chapter.getResources().isEmpty()) {
                bookRootDir.cd(Utilities.toDirectoryName(null, chapter.getTitle())).writeResources(
                        chapter.getResources());
            }
        }
        bookRootDir.cd(Utilities.toDirectoryName(null, part.getTitle())).writeResources(
                part.getResources());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("part", part);
        map.put("parser", parser);
        map.put("partNumber", partNumber);
        map.put("chaptersContent", chaptersContent.toString());
        map.put("sanitizer", new HtmlSanitizer());
        StringBuffer processedContent = new FreemarkerProcessor(cfg).process(map, "bookPart.ftl");
        String sluggedPartTitle = Utilities.toDirectoryName(null, part.getTitle());
        processedContent = new StringBuffer(processedContent.toString().replace("$$RELATIVE$$", sluggedPartTitle));
        return processedContent;
    }
    
    private StringBuffer fixPaths(Chapter chapter, StringBuffer chapterContent) {
        String chapterName = Utilities.toDirectoryName(null, chapter.getTitle());
        return new StringBuffer(chapterContent.toString().replace("$$RELATIVE$$", chapterName));
    }

}
