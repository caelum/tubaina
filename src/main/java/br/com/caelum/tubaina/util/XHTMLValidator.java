package br.com.caelum.tubaina.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.tidy.Tidy;

import br.com.caelum.tubaina.TubainaException;

public class XHTMLValidator {

	public static final String validatorLogFile = "XHTMLValidator.log";

	public boolean isValid(File directory, String xhtmlPath) {
		Tidy tidy = new Tidy();
		xhtmlPath += "/index.html";
		File xhtmlFile = new File(directory, xhtmlPath);
		try {

			OutputStream generatedOutput = new ByteArrayOutputStream();
			tidy.setInputStreamName(xhtmlPath);

			PrintWriter logWriter = new PrintWriter(generatedOutput);
			logWriter.append("=======================================================================\n");
			tidy.setErrout(logWriter);

			tidy.parse(new FileInputStream(xhtmlFile), null);

			logWriter.close();

			String outputToFilter = generatedOutput.toString();
			Pattern encodingPattern = Pattern.compile("(?s)(?i)(?m)^line \\d* column \\d* - Warning: replacing illegal character code \\d*$");
			Matcher encodingMatcher = encodingPattern.matcher(outputToFilter);
			
			//Removing encoding warnings
			while (encodingMatcher.find()){
				outputToFilter = outputToFilter.replaceFirst(Pattern.quote(encodingMatcher.group()), "");
			}
			
			Pattern xhtmlErrorPattern = Pattern.compile("(?s)(?i)(?m)^line");
			
			if (!xhtmlErrorPattern.matcher(outputToFilter).find()){
				return true;
			}
			
			OutputStream logStream = new FileOutputStream(new File(directory, validatorLogFile), true);
			PrintStream logFile = new PrintStream(logStream);

			logFile.println(generatedOutput);

			return false;
		} catch (FileNotFoundException e) {
			throw new TubainaException("File could not be open", e);
		}
	}
}
