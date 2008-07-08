package br.com.caelum.tubaina.template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.caelum.tubaina.TubainaException;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerProcessor {
	private final Configuration configuration;

	private final static Logger LOG = Logger.getLogger(FreemarkerProcessor.class);

	public FreemarkerProcessor(final Configuration configuration) {
		this.configuration = configuration;
	}

	@Deprecated
	public void processToFile(final Map<String, Object> map, final File destination, final String template)
			throws IOException {
		LOG.info(String.format("Processing template %s into file %s", template, destination.getCanonicalPath()));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(destination), "UTF-8"));
		try {
			Template temp = configuration.getTemplate(template);
			temp.process(map, out);
		} catch (Exception e) {
			throw new TubainaException(e);
		}
		out.flush();
	}

	public StringBuffer process(final Map<String, Object> map, final String template) {
		// TODO: make UnitTests for this method
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		try {
			Template temp = configuration.getTemplate(template);
			temp.process(map, out);
		} catch (Exception e) {
			throw new TubainaException(e);
		}
		out.flush();
		return writer.getBuffer();
	}

}
