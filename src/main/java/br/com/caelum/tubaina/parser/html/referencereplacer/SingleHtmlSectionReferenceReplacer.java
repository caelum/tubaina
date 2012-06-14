package br.com.caelum.tubaina.parser.html.referencereplacer;

import java.util.List;

import net.htmlparser.jericho.Element;

public class SingleHtmlSectionReferenceReplacer extends AbstractReferenceReplacer {
    @Override
    protected String extractTextToReplaceReference(Element labelParentDiv, Element label) {
        String text = "*";
        if (label.getName().equals("a")
                && labelParentDiv.getFirstElementByClass("chapterHeader") == null) {
            Element title = labelParentDiv.getFirstElementByClass("referenceableTitle");
            List<Element> spans = title.getAllElements("span");
            if (!spans.isEmpty()) {
                String sectionNumber = spans.get(0).getContent().toString();
                text = sectionNumber;
            }
        }
        return text;
    }
}
