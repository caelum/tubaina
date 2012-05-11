package br.com.caelum.tubaina.parser.html.referencereplacer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.OutputDocument;

public class CodeReferenceReplacer extends AbstractReferenceReplacer {

    @Override
    protected void replaceOn(Element containerDiv, OutputDocument outputDocument, Element reference, Element label) {
    	  if(label.getName().equals("pre")) {
              
              Element title = containerDiv.getFirstElementByClass("referenceableTitle");
              
              while (title.getStartTag().getName() != "h1") {
                  containerDiv = containerDiv.getParentElement();
                  
                  title = containerDiv.getFirstElementByClass("referenceableTitle");
              }
              List<Element> codes = containerDiv.getAllElements("pre");
              int codePosition = codes.indexOf(label) + 1;
              String chapterAndSectionNumber = title.getTextExtractor().toString().split("-")[0].trim();
              String chapterNumber = chapterAndSectionNumber.split("\\.")[0];
              outputDocument.replace(reference, reference.toString().replace("*", chapterNumber+"."+codePosition));
          }
    }

}
