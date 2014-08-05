package br.com.caelum.tubaina.parser.html.desktop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.TubainaBuilderData;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.io.HtmlResourceManipulatorFactory;
import br.com.caelum.tubaina.io.ResourceManipulatorFactory;
import br.com.caelum.tubaina.io.TubainaHtmlDir;
import br.com.caelum.tubaina.io.TubainaHtmlIO;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.util.Utilities;
import br.com.caelum.tubaina.util.XHTMLValidator;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class FlatHtmlGenerator implements Generator {

    private static final Logger LOG = Logger.getLogger(FlatHtmlGenerator.class);

    private final Parser parser;

    private final boolean shouldValidateXHTML;

    private final File templateDir;

    private Configuration cfg;

    private List<String> ifdefs;

    public FlatHtmlGenerator(final Parser parser, TubainaBuilderData data) {
        this.parser = parser;
        this.shouldValidateXHTML = data.isStrictXhtml();
        this.templateDir = new File(data.getTemplateDir(), "html/");
        this.ifdefs = data.getIfdefs();
        configureFreemarker();
    }

    public void generate(Book book, File directory) throws IOException {

        ResourceManipulatorFactory htmlResourceManipulatorFactory = new HtmlResourceManipulatorFactory();
        TubainaHtmlDir bookRoot = new TubainaHtmlIO(templateDir, htmlResourceManipulatorFactory)
                .createTubainaDir(directory, book);

        List<String> dirTree = createDirTree(book, directory);
        StringBuffer toc = new BookToTOC().generateTOC(book, cfg, dirTree);
        bookRoot.writeIndex(fixPaths(toc));

        int chapterIndex = 1;
        int currentDir = 1;
        for (Chapter chapter : book.getChapters()) {
            int curdir = currentDir++;
            StringBuffer chapterText = new ChapterToString(parser, cfg, dirTree, ifdefs).generateChapter(
                    book, chapter, chapterIndex, curdir);

            bookRoot.cd(Utilities.toDirectoryName(null, chapter.getTitle())).writeIndex(
                    fixPaths(chapterText)).writeResources(chapter.getResources());

            chapterIndex++;
        }

        if (shouldValidateXHTML) {
            validateXHTML(directory, dirTree);
        }

        // TODO: this won't work
        Map<String, Integer> indexes = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
        StringBuffer index = new IndexToString(dirTree, cfg).createFlatIndex(indexes, book);
        List<Chapter> introductionChapters = book.getIntroductionChapters();
        for (Chapter chapter : introductionChapters) {
        	bookRoot.cd(".").writeResources(chapter.getResources());	
		}
    }

    private StringBuffer fixPaths(StringBuffer chFullText) {
        return new StringBuffer(chFullText.toString().replace("$$RELATIVE$$", "."));
    }

    private void configureFreemarker() {
        cfg = new Configuration();
        try {
            cfg.setDirectoryForTemplateLoading(templateDir);
        } catch (IOException e) {
            new TubainaException("Couldn't load freemarker templates for Flat HTML mode", e);
        }
        cfg.setObjectWrapper(new BeansWrapper());
    }

    private List<String> createDirTree(final Book b, final File parent) {
        List<String> dirTree = new ArrayList<String>();

        String rootDir = Utilities.toDirectoryName(null, b.getName());
        File root = new File(parent, rootDir);
        root.mkdir();
        dirTree.add(rootDir);
        for (Chapter c : b.getChapters()) {
            String chapDir = rootDir + "/" + Utilities.toDirectoryName(null, c.getTitle());
            if (dirTree.contains(chapDir)) {
                throw new TubainaException("Doubled archive name: " + c.getTitle());
            }
            File chapter = new File(parent, chapDir);
            chapter.mkdir();
            dirTree.add(chapDir);

            for (Section s : c.getSections()) {
                if (s.getTitle() != null) {
                    String secDir = chapDir + "/#" + Utilities.toDirectoryName(null, s.getTitle());
                    int equals = 1;
                    while (dirTree.contains(secDir)) {
                        secDir = secDir.replaceFirst("-" + equals + "$", "");
                        secDir += "-" + ++equals;
                        LOG.warn("Double section name in the same chapter: " + s.getTitle());
                    }
                    dirTree.add(secDir);
                }
            }
        }

        return dirTree;
    }

    private void validateXHTML(final File directory, final List<String> dirTree) {
        XHTMLValidator validator = new XHTMLValidator();
        boolean foundInvalidXHTML = false;
        for (String s : dirTree) {
            if (!validator.isValid(directory, s)) {
                foundInvalidXHTML = true;
                LOG.warn("This is not a xhtml valid file: " + s + "/index.html");
            }
        }
        if (foundInvalidXHTML) {
            throw new TubainaException("Some xhtml generated is not valid. See "
                    + XHTMLValidator.validatorLogFile + " for further information");
        }

    }
}