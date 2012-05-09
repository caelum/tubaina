package br.com.caelum.tubaina.parser.html.referencereplacer;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.OutputDocument;

public class ChapterAndSectionReferenceReplacer extends AbstractReferenceReplacer {

    @Override
    protected void replaceOn(Element div, OutputDocument outputDocument, Element reference,
            Element label) {
        if (label.getName().equals("a")) {
            Element title = div.getFirstElementByClass("referenceableTitle");
            String number = title.getTextExtractor().toString().split("-")[0].trim();
            outputDocument.replace(reference, reference.toString().replace("*", number));
        }
    }

}
