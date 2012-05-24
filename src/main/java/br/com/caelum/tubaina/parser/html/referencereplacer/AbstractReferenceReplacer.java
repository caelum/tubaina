package br.com.caelum.tubaina.parser.html.referencereplacer;

import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Source;

public abstract class AbstractReferenceReplacer implements ReferenceReplacer {

    public String replace(String htmlContent) {
        Source source = new Source(htmlContent);
        source.fullSequentialParse();
        OutputDocument outputDocument = new OutputDocument(source);
        List<Element> references = source.getAllElementsByClass("reference");
        for (Element reference : references) {
            String labelId = reference.getAttributeValue("href").replace("#", "");
            Element label = source.getElementById(labelId);

            Element div = findLabelContainer(label);
            if (!isValid(div)) {
                outputDocument.replace(reference, reference.toString().replace("*", "?"));
                continue;
            }

            String text = extractTextToReplaceReference(div, label);
            outputDocument.replace(reference, reference.toString().replace("*", text));
        }
        return outputDocument.toString();
    }

    protected abstract String extractTextToReplaceReference(Element labelParentDiv, Element label);

    private Element findLabelContainer(Element label) {
        if (label == null)
            return null;
        Element container = label.getParentElement();
        while (container != null && !container.getName().equals("div")) {
            container = container.getParentElement();
        }
        return container;
    }

    private boolean isValid(Element referenceableContainer) {
        if (referenceableContainer == null)
            return false;
        String classes = referenceableContainer.getAttributeValue("class");
        if (classes == null)
            return false;
        return classes.contains("referenceable");
    }

}
