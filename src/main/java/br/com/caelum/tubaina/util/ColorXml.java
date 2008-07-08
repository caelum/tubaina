package br.com.caelum.tubaina.util;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import br.com.caelum.tubaina.TubainaException;


public class ColorXml {

	public static String color(String string){

		TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer;
		try {
			transformer = tFactory.newTransformer(new StreamSource(new File("src/config/xml2html.xslt")));
		 
			Writer writer = new StringWriter();
		
			transformer.transform(
			           new StreamSource(new StringReader(string)),
			           new StreamResult(writer));
			return writer.toString();

		} catch (TransformerException e) {
			throw new TubainaException(e);
		} 

		
	}
	
}
