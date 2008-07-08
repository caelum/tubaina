package br.com.caelum.tubaina.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class RegexConfigurator {

	public List<Tag> read(String regexFile, String replaceFile) throws IOException {
		Properties regexP = new Properties();
		regexP.load(RegexConfigurator.class.getResourceAsStream(regexFile));
		Properties replaceP = new Properties();
		replaceP.load(RegexConfigurator.class.getResourceAsStream(replaceFile));
		
		List<Tag> tags = new ArrayList<Tag>();
		for (Object o : regexP.keySet()) {
			String key = (String) o;
			
			String regex = regexP.getProperty(key);
			String replacement = replaceP.getProperty(key, "$0");
			tags.add(new RegexTag(regex, replacement));
		}
		return tags;
	}
}
