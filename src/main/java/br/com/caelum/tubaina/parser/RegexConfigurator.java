package br.com.caelum.tubaina.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;

public class RegexConfigurator {

	public List<RegexTag> read(String regexFile, String replaceFile) throws IOException {
		Properties regexP = new Properties();
		regexP.load(RegexConfigurator.class.getResourceAsStream(regexFile));
		Properties replaceP = new Properties();
		replaceP.load(RegexConfigurator.class.getResourceAsStream(replaceFile));
		
		List<RegexTag> tags = new ArrayList<RegexTag>();
		TreeSet keys = new TreeSet(regexP.keySet());
		for (Object o : keys) {
			String key = (String) o;
			String regex = regexP.getProperty(key);
			String replacement = replaceP.getProperty(key, "$0");
			tags.add(new RegexTag(regex, replacement));
		}
		return tags;
	}
}
