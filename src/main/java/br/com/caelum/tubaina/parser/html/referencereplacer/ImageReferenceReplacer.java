package br.com.caelum.tubaina.parser.html.referencereplacer;

import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.OutputDocument;

public class ImageReferenceReplacer extends AbstractReferenceReplacer {

    @Override
    protected void replaceOn(Element containerDiv, OutputDocument outputDocument, Element reference, Element label) {
        if(label.getName().equals("img")) { 
            List<Element> imgs = containerDiv.getAllElements("img");
            int imagePosition = imgs.indexOf(label) + 1;
            Element title = containerDiv.getFirstElementByClass("referenceableTitle");
            String number = title.getTextExtractor().toString().split("-")[0].trim();
            outputDocument.replace(reference, reference.toString().replace("*", number+"."+imagePosition));
        }
    }

}
