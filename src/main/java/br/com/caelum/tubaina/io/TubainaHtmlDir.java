package br.com.caelum.tubaina.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.IOUtils;

import br.com.caelum.tubaina.TubainaException;
import br.com.caelum.tubaina.resources.HtmlResourceManipulator;
import br.com.caelum.tubaina.resources.Resource;
import br.com.caelum.tubaina.resources.ResourceManipulator;

public class TubainaHtmlDir implements TubainaDir{

	private final File currentFolder;
	private final File templateDir;
	private final ResourceManipulatorFactory resourceManipulatorFactory;

	public TubainaHtmlDir(File outputFolder, File templateDir, ResourceManipulatorFactory resourceManipulatorFactory) {
		this.currentFolder = outputFolder;
		this.templateDir = templateDir;
		this.resourceManipulatorFactory = resourceManipulatorFactory;
	}

	public TubainaHtmlDir cd(String directoryName) {
		File destination = new File(currentFolder, directoryName);
		destination.mkdir();
		return new TubainaHtmlDir(destination, templateDir, resourceManipulatorFactory);
	}

	public TubainaHtmlDir writeIndex(StringBuffer bookContent) {
		File file = new File(currentFolder, "index.html");
		try {
			IOUtils.write(bookContent, new PrintStream(file, "UTF-8"));
			return this;
		} catch (IOException e) {
			throw new TubainaException("Couldn't create index.html in " + currentFolder);
		}
	}

	public TubainaHtmlDir writeResources(List<Resource> resources) {
		Map<String, Integer> indexes = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
		
		//TODO: remove this asap too
		File logo = new File(templateDir, "logo.png");
		
		ResourceManipulator manipulator = resourceManipulatorFactory.build(this.currentFolder, indexes, logo);		

		for (Resource r : resources) {
			r.copyTo(manipulator);
		}
		return this;
	}

}
