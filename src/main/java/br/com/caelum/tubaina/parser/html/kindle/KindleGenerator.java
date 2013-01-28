package br.com.caelum.tubaina.parser.html.kindle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.BookPart;
import br.com.caelum.tubaina.TubainaBuilderData;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.bibliography.Bibliography;
import br.com.caelum.tubaina.bibliography.BibliographyFactory;
import br.com.caelum.tubaina.bibliography.HtmlBibliographyGenerator;
import br.com.caelum.tubaina.io.KindleResourceManipulatorFactory;
import br.com.caelum.tubaina.io.ResourceManipulatorFactory;
import br.com.caelum.tubaina.io.TubainaHtmlDir;
import br.com.caelum.tubaina.io.TubainaKindleIO;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.html.desktop.Generator;
import br.com.caelum.tubaina.parser.html.referencereplacer.BibliographyReferenceReplacer;
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
    private Configuration freeMarkerConfig;
    private List<String> ifdefs;

    public KindleGenerator(Parser parser, TubainaBuilderData data) {
        this.parser = parser;
        this.templateDir = new File(data.getTemplateDir(), "kindle/");
        this.ifdefs = data.getIfdefs();
        configureFreemarker();
    }

    public void generate(Book book, File outputDir) throws IOException {
        ResourceManipulatorFactory kindleResourceManipulatorFactory = new KindleResourceManipulatorFactory();
        TubainaHtmlDir bookRoot = new TubainaKindleIO(templateDir, kindleResourceManipulatorFactory)
                .createTubainaDir(outputDir);
        
        StringBuffer bookContent = generateHeader(book);
        
        StringBuffer introductionChaptersContent = generateIntroductionParts(book, bookRoot);
        bookContent.append(introductionChaptersContent);

        int partCount = 1;
        for (BookPart part : book.getParts()) {
            StringBuffer partContent = generatePart(book, part, bookRoot, partCount);
            bookContent.append(partContent);
            if (part.isPrintable())
                partCount++;
        }

        String htmlBibliography = generateHtmlBibliography(outputDir);
        bookContent.append(htmlBibliography);
        bookContent = resolveReferencesOf(bookContent);
        bookRoot.writeIndex(bookContent);
    }

    private StringBuffer generateIntroductionParts(Book book, TubainaHtmlDir bookRoot) {
        return new IntroductionChaptersToKindle(parser, freeMarkerConfig, bookRoot)
                .generateIntroductionChapters(book.getIntroductionChapters());
    }

    private String generateHtmlBibliography(File outputDir) {
        File bibliographyFile = new File(outputDir, "bib.xml");
        Bibliography bibliography = new BibliographyFactory().build(bibliographyFile);
        String htmlBibliography = new HtmlBibliographyGenerator(freeMarkerConfig)
                .generateTextOf(bibliography);
        return htmlBibliography;
    }

    private StringBuffer resolveReferencesOf(StringBuffer bookContent) {
        List<ReferenceReplacer> replacers = new ArrayList<ReferenceReplacer>();

        replacers.add(new ChapterAndSectionReferenceReplacer());
        replacers.add(new ImageReferenceReplacer());
        replacers.add(new CodeReferenceReplacer());
        replacers.add(new BibliographyReferenceReplacer());

        ReferenceParser referenceParser = new ReferenceParser(replacers);

        bookContent = new StringBuffer(referenceParser.replaceReferences(bookContent.toString()));
        return bookContent;
    }

    private StringBuffer generatePart(Book book, BookPart part, TubainaHtmlDir bookRoot,
            int partCount) {
        return new PartToKindle(parser, freeMarkerConfig, ifdefs).generateKindlePart(part, bookRoot,
                partCount);
    }

    private StringBuffer generateHeader(Book book) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("booktitle", book.getName());
        return new FreemarkerProcessor(freeMarkerConfig).process(map, "book-header.ftl");
    }

    private void configureFreemarker() {
        freeMarkerConfig = new Configuration();
        try {
            freeMarkerConfig.setDirectoryForTemplateLoading(templateDir);
        } catch (IOException e) {
            throw new TubainaException("Couldn't load freemarker template for Kindle HTML mode", e);
        }
        freeMarkerConfig.setObjectWrapper(new BeansWrapper());
    }
}
