package br.com.caelum.tubaina.io;

import java.io.File;
import java.util.Map;

import br.com.caelum.tubaina.resources.HtmlResourceManipulator;
import br.com.caelum.tubaina.resources.KindleResourceManipulator;
import br.com.caelum.tubaina.resources.ResourceManipulator;

public class KindleResourceManipulatorFactory implements ResourceManipulatorFactory{

	public ResourceManipulator build(File imageDestinationPath, Map<String, Integer> indexes, File logo) {
		return new KindleResourceManipulator(imageDestinationPath , indexes);
	}

}
