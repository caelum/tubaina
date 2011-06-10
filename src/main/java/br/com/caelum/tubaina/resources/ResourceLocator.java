package br.com.caelum.tubaina.resources;

import java.io.File;

import org.apache.log4j.Logger;

import br.com.caelum.tubaina.TubainaException;

public class ResourceLocator {

	private static final Logger LOG = Logger.getLogger(ResourceLocator.class);
	private static ResourceLocator locator;
	private final File rootDir;
	
	private ResourceLocator(File rootDir) {
		this.rootDir = rootDir;
	}
	
	public static void initialize(String rootPath) {
		initialize(new File(rootPath));
	}
	
	public static void initialize(File rootDir) {
		if (locator != null) {
			LOG.warn("Locator has already been initialized"+locator.getInstance().rootDir.getAbsolutePath());
		}
		if(!rootDir.exists()) {
			throw new RuntimeException("Resource directory does not exist: " + rootDir.getAbsolutePath());
		}
		LOG.info("Initializing ResourceLocator at dir: " + rootDir.getAbsolutePath());
		locator = new ResourceLocator(rootDir);
	}

	public static ResourceLocator getInstance() {
		if (locator == null) {
			throw new TubainaException("ResourceLocator has not been initialized");
		}
		return locator;
	}
	
	public File getFile(String path) {
		File file = new File(rootDir, path);
		if (!file.exists()) {
			LOG.warn("File requested: '" + path + "' doesn't exist");
		}
		return file;
	}
}
