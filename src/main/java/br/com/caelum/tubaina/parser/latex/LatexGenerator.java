package br.com.caelum.tubaina.parser.latex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.BookPart;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.TubainaBuilderData;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.bibliography.Bibliography;
import br.com.caelum.tubaina.bibliography.BibliographyFactory;
import br.com.caelum.tubaina.bibliography.LatexBibliographyGenerator;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.html.desktop.Generator;
import br.com.caelum.tubaina.resources.AnswerResource;
import br.com.caelum.tubaina.resources.LatexResourceManipulator;
import br.com.caelum.tubaina.resources.Resource;
import br.com.caelum.tubaina.resources.ResourceManipulator;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class LatexGenerator implements Generator {

	private final Parser parser;

	private static final Logger LOG = Logger.getLogger(LatexGenerator.class);

	private final File templateDir;

	private final boolean noAnswer;

	private final String latexOutputFileName;

    private List<String> ifdefs;

	public LatexGenerator(Parser parser, TubainaBuilderData data) {
		this.parser = parser;
		this.templateDir = data.getTemplateDir();
		this.noAnswer = data.isNoAnswer();
		this.latexOutputFileName = data.getOutputFileName() + ".tex";
		this.ifdefs = data.getIfdefs();
	}

	public void generate(Book book, File directory) throws IOException {
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(templateDir);
		cfg.setObjectWrapper(new BeansWrapper());
		cfg.setDefaultEncoding("UTF-8");

		PrintStream stream;
		writeBibTex(directory);

		StringBuffer latex = new BookToLatex(parser).generateLatex(book, cfg, ifdefs);

		// print the latex document to an archive
		File fileBook = new File(directory, latexOutputFileName);
		stream = new PrintStream(fileBook, "UTF-8");
		stream.append(latex);
		stream.close();

		copyResources(directory, book);
	}

	private void writeBibTex(File directory) throws FileNotFoundException, UnsupportedEncodingException {
		File bibliographyFile = new File(directory, "bib.xml");
		Bibliography bibliography = new BibliographyFactory().build(bibliographyFile);
		String latexBibliography = new LatexBibliographyGenerator().generateTextOf(bibliography);
		PrintStream stream = new PrintStream(new File(directory, "book.bib"), "UTF-8");
		stream.append(latexBibliography);
		stream.close();
	}

	private void copyResources(File directory, Book b) throws IOException {
		// Dependencies (styles, logo)
		copyFileWithExtenstion(directory, Arrays.asList(".png", ".jpeg", ".bib", ".sty", ".bst"));

		// Creating Answer Booklet
		File answerFile = new File(directory, "answer.tex");
		if (answerFile.exists()) {
			LOG.warn("Answer File already exists. Deleting it");
			answerFile.delete();
		}
		if (!noAnswer && hasAnswer(b.getChapters())) {
			PrintStream stream = new PrintStream(new FileOutputStream(answerFile), true, "UTF-8");
			stream.println("\\chapter{\\answerBooklet}");
			stream.close();
		}
		
		List<Resource> resources = retrieveResources(b);
		ResourceManipulator manipulator = new LatexResourceManipulator(directory, answerFile, parser, noAnswer);
		copyResources(resources, manipulator);
	}

    private void copyFileWithExtenstion(File directory, List<String> extenstions) throws IOException {
        for (final String extension : extenstions) {
            File[] files = new File(templateDir, "latex").listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(extension);
                }
            });
            for (File file : files) {
                FileUtils.copyFileToDirectory(file, directory);
            }
        }
    }

	private List<Resource> retrieveResources(Book b) {
		List<Resource> resources = new ArrayList<Resource>();
		for (Chapter c : b.getChapters()) {
			resources.addAll(c.getResources());
		}
		for (BookPart bp : b.getParts()) {
			resources.addAll(bp.getResources());
		}
		return resources;
	}

	private void copyResources(List<Resource> resources, ResourceManipulator manipulator) {
		boolean resourceCopyFailed = false;
		for (Resource r : resources) {

			try {
				r.copyTo(manipulator);
			} catch (TubainaException e) {
				resourceCopyFailed = true;
			}

		}
		if (resourceCopyFailed)
			throw new TubainaException("Couldn't copy some resources. See the Logger for further information");
	}

	private boolean hasAnswer(List<Chapter> chapters) {
		for (Chapter chapter : chapters) {
			for (Resource resource : chapter.getResources()) {
				if (resource instanceof AnswerResource) {
					return true;
				}
			}
		}
		return false;
	}
}
