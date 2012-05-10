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
            List<Element> codes = containerDiv.getAllElements("pre");
            int codePosition = codes.indexOf(label) + 1;

            Element title = containerDiv.getFirstElementByClass("referenceableTitle");
            
            String number = title.getTextExtractor().toString().split("-")[0].trim();
            outputDocument.replace(reference, reference.toString().replace("*", number+"."+codePosition));
        }
    }

}
