package br.com.caelum.tubaina.parser.html.referencereplacer;

import net.htmlparser.jericho.Element;

public class SingleHtmlSectionReferenceReplacer extends AbstractReferenceReplacer {
    @Override
    protected String extractTextToReplaceReference(Element labelParentDiv, Element label) {
        String text = "*";
        if (label.getName().equals("a")
                && labelParentDiv.getFirstElementByClass("chapterHeader") == null) {
            Element title = labelParentDiv.getFirstElementByClass("referenceableTitle");
            String number = title.getTextExtractor().toString().split("-")[0].trim();
            text = number;
        }
        return text;
    }
}
