package br.com.caelum.tubaina.parser.html.referencereplacer;

import java.util.List;

import net.htmlparser.jericho.Element;

public class ImageReferenceReplacer extends AbstractReferenceReplacer {

    @Override
    protected String extractTextToReplaceReference(Element containerDiv, Element label) {
        String text = "*";
        if (label.getName().equals("img")) {

            Element title = containerDiv.getFirstElementByClass("referenceableTitle");
            while (title.getStartTag().getName() != "h2") {
                containerDiv = containerDiv.getParentElement();
                title = containerDiv.getFirstElementByClass("referenceableTitle");
            }
            List<Element> imgs = containerDiv.getAllElements("img");
            int imagePosition = imgs.indexOf(label) + 1;
            String chapterAndSectionNumber = title.getTextExtractor().toString().split("-")[0]
                    .trim();
            String chapterNumber = chapterAndSectionNumber.split("\\.")[0];
            text = chapterNumber + "." + imagePosition;
        }
        return text;
    }

}
