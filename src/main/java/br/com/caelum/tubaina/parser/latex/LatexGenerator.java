package br.com.caelum.tubaina.parser.latex;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.resources.AnswerResource;
import br.com.caelum.tubaina.resources.LatexResourceManipulator;
import br.com.caelum.tubaina.resources.Resource;
import br.com.caelum.tubaina.resources.ResourceManipulator;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class LatexGenerator {

	private final Parser parser;

	private static final Logger LOG = Logger.getLogger(LatexGenerator.class);

	private final File templateDir;

	private final boolean noAnswer;

	public LatexGenerator(Parser parser, File templateDir, boolean noAnswer) {
		this.parser = parser;
		this.templateDir = templateDir;
		this.noAnswer = noAnswer;
	}

	public void generate(Book book, File directory, String outputFileName) throws IOException {
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(templateDir);
		cfg.setObjectWrapper(new BeansWrapper());
		cfg.setDefaultEncoding("UTF-8");

		StringBuffer latex = new BookToLatex(parser).generateLatex(book, cfg);

		// print the latex document to an archive
		File fileBook = new File(directory, outputFileName);
		PrintStream stream = new PrintStream(fileBook, "UTF-8");
		stream.append(latex);
		stream.close();

		copyResources(directory, book);
	}

	private void copyResources(File directory, Book b) throws IOException {
		// Dependencies (styles, logo)
		FileUtils.copyFileToDirectory(new File(templateDir, "latex/tubaina.sty"), directory);
		FileUtils.copyFileToDirectory(new File(templateDir, "latex/xcolor.sty"), directory);
		File[] images = new File(templateDir, "latex").listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.contains(".png") || name.contains(".bib");
			}
		});
		for (File image : images) {
			FileUtils.copyFileToDirectory(image, directory);			
		}

		boolean resourceCopyFailed = false;
		
		//Creating Answer Booklet
		File answerFile = new File(directory, "answer.tex");
		if (answerFile.exists()){
			LOG.warn("Answer File already exists. Deleting it");
			answerFile.delete();
		}
		if (!noAnswer && hasAnswer(b.getChapters())) {
			PrintStream stream = new PrintStream(new FileOutputStream(answerFile), true, "UTF-8");
			stream.println("\\chapter{\\answerBooklet}");
			stream.close();
		}
		for (Chapter c : b.getChapters()) {
			
			ResourceManipulator manipulator = new LatexResourceManipulator(directory, answerFile, parser, noAnswer);
			
			for (Resource r : c.getResources()) {

				try {
					r.copyTo(manipulator);
				} catch (TubainaException e) {
					resourceCopyFailed = true;
				}

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
