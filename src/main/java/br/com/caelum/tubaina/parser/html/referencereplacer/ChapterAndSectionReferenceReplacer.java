package br.com.caelum.tubaina.parser.html.referencereplacer;

import net.htmlparser.jericho.Element;

public class ChapterAndSectionReferenceReplacer extends AbstractReferenceReplacer {

    @Override
    protected String extractTextToReplaceReference(Element div, Element label) {
        String text = "*";
        if (label.getName().equals("a")) {
            Element titleHeader = div.getFirstElementByClass("referenceableTitle");
            Element number = titleHeader.getFirstElementByClass("number");
            String[] split = number.getTextExtractor().toString().split(" ");
			text = split[split.length-1].trim();
        }
        return text;
    }

}
