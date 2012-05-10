package br.com.caelum.tubaina.io;

import java.io.File;
import java.util.Map;

import br.com.caelum.tubaina.resources.ResourceManipulator;

public interface ResourceManipulatorFactory {
	public ResourceManipulator build(File imageDestinationPath, Map<String,Integer> indexes, File logo);
}
