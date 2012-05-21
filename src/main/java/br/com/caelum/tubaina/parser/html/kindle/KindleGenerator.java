package br.com.caelum.tubaina.parser.html.kindle;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.BookPart;
import br.com.caelum.tubaina.TubainaBuilderData;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.io.KindleResourceManipulatorFactory;
import br.com.caelum.tubaina.io.ResourceManipulatorFactory;
import br.com.caelum.tubaina.io.TubainaHtmlDir;
import br.com.caelum.tubaina.io.TubainaKindleIO;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.html.desktop.Generator;
import br.com.caelum.tubaina.parser.html.referencereplacer.ChapterAndSectionReferenceReplacer;
import br.com.caelum.tubaina.parser.html.referencereplacer.CodeReferenceReplacer;
import br.com.caelum.tubaina.parser.html.referencereplacer.ImageReferenceReplacer;
import br.com.caelum.tubaina.parser.html.referencereplacer.ReferenceParser;
import br.com.caelum.tubaina.parser.html.referencereplacer.ReferenceReplacer;
import br.com.caelum.tubaina.template.FreemarkerProcessor;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class KindleGenerator implements Generator {

    private final Parser parser;
    private final File templateDir;
    private Configuration cfg;

    public KindleGenerator(Parser parser, TubainaBuilderData data) {
        this.parser = parser;
        this.templateDir = new File(data.templateDir, "kindle/");
        configureFreemarker();
    }

    public void generate(Book book, File outputDir) throws IOException {
        StringBuffer bookContent = generateHeader(book);

        ResourceManipulatorFactory kindleResourceManipulatorFactory = new KindleResourceManipulatorFactory();

        TubainaHtmlDir bookRoot = new TubainaKindleIO(templateDir, kindleResourceManipulatorFactory)
                .createTubainaDir(outputDir, book);

        int partCount = 0;
        for (BookPart part : book.getParts()) {
            StringBuffer partContent = generatePart(book, part, bookRoot, partCount);
            bookContent.append(partContent);
            partCount++;
        }
        
        ReferenceReplacer chapterAndSectionReferenceReplacer = new ChapterAndSectionReferenceReplacer();
        ImageReferenceReplacer imageReferenceReplacer = new ImageReferenceReplacer();
        CodeReferenceReplacer codeReferenceReplacer = new CodeReferenceReplacer();

        ReferenceParser referenceParser = new ReferenceParser(Arrays.asList(
                chapterAndSectionReferenceReplacer, imageReferenceReplacer, codeReferenceReplacer));

        bookContent = new StringBuffer(referenceParser.replaceReferences(bookContent.toString()));

        bookRoot.writeIndex(bookContent);
    }

    private StringBuffer generatePart(Book book, BookPart part, TubainaHtmlDir bookRoot, int partCount) {
        return new PartToKindle(parser, cfg).generateKindlePart(part, bookRoot, partCount);
    }

    private StringBuffer generateHeader(Book book) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("booktitle", book.getName());
        return new FreemarkerProcessor(cfg).process(map, "book-header.ftl");
    }

    private void configureFreemarker() {
        cfg = new Configuration();
        try {
            cfg.setDirectoryForTemplateLoading(templateDir);
        } catch (IOException e) {
            throw new TubainaException("Couldn't load freemarker template for Kindle HTML mode", e);
        }
        cfg.setObjectWrapper(new BeansWrapper());
    }
}
