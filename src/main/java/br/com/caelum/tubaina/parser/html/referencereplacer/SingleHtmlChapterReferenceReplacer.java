package br.com.caelum.tubaina.parser.html.referencereplacer;

import java.util.List;

import net.htmlparser.jericho.Element;

public class SingleHtmlChapterReferenceReplacer extends AbstractReferenceReplacer {

    @Override
    protected String extractTextToReplaceReference(Element labelParentDiv, Element label) {
        String text = "*";
        if (label.getName().equals("a") && labelParentDiv.getFirstElementByClass("chapterHeader") != null) {
            Element chapterHeader = labelParentDiv.getFirstElementByClass("chapterHeader");
            List<Element> spans = chapterHeader.getAllElements("span");
            if (!spans.isEmpty()) {
                String chapterNumber = spans.get(0).getContent().toString();
                return chapterNumber;
            }
        }
        return text;
    }

}
