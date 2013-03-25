package br.com.caelum.tubaina.parser;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class RegexConfiguratorTest {

	@Test
	public void testReadDefaultRegexFile() throws IOException {
		RegexConfigurator configurator = new RegexConfigurator();
		@SuppressWarnings("unused")
		List<RegexTag> tags = configurator.read("/regex.properties", "/html.properties");
		//TODO: Fazer esse teste! 
	}
}
