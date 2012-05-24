package br.com.caelum.tubaina.parser.html.referencereplacer;

import net.htmlparser.jericho.Element;

public class BibliographyReferenceReplacer extends AbstractReferenceReplacer {

    @Override
    protected String extractTextToReplaceReference(Element labelParentDiv, Element label) {
        String text = "*";
        if (label.getName().equals("li")) {
            String position = label.getAttributeValue("position");
             text = "[" + position + "]";
        }
        return text;
    }

}
