package br.com.caelum.tubaina.parser.html.referencereplacer;

import net.htmlparser.jericho.Element;

public class ChapterAndSectionReferenceReplacer extends AbstractReferenceReplacer {

    @Override
    protected String extractTextToReplaceReference(Element div, Element label) {
        String text = "*";
        if (label.getName().equals("a")) {
            Element title = div.getFirstElementByClass("referenceableTitle");
            String number = title.getTextExtractor().toString().split("-")[0].trim();
            text = number;
        }
        return text;
    }

}
