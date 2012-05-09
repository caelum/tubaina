package br.com.caelum.tubaina.parser.html.kindle;

import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Source;

public class ReferenceParser {

    private Source source;
    private OutputDocument outputDocument;

    public ReferenceParser(String htmlContent) {
        source = new Source(htmlContent);
        outputDocument = new OutputDocument(source);
        source.fullSequentialParse();
    }

    public String replaceReferences() {
        List<Element> references = source.getAllElementsByClass("reference");
        for (Element reference : references) {
            String labelId = reference.getAttributeValue("href").replace("#", "");
            Element containerReferenceable = findReferenceableContainer(labelId);
            if (!isValid(containerReferenceable)) {
                outputDocument.replace(reference, reference.toString().replace("*", "?"));
                continue;
            }
            Element title = containerReferenceable.getFirstElementByClass("referenceableTitle");
            String number = title.getTextExtractor().toString().split("-")[0].trim();
            outputDocument.replace(reference, reference.toString().replace("*", number));
        }
        return outputDocument.toString();
    }

    private Element findReferenceableContainer(String labelId) {
        Element label = source.getElementById(labelId);
        if (label == null)
            return null;
        Element container = label.getParentElement();
        while (container != null && !container.getName().equals("div")) {
            container = container.getParentElement();
        }
        return container;
    }

    private boolean isValid(Element containerReferenceable) {
        if (containerReferenceable == null)
            return false;
        String classes = containerReferenceable.getAttributeValue("class");
        if (classes == null)
            return false;
        return classes.contains("referenceable");
    }

}
