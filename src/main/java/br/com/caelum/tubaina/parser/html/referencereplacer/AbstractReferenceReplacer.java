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
            
            Element div = findReferenceableContainer(label);
            if (!isValid(div)) {
                outputDocument.replace(reference, reference.toString().replace("*", "?"));
                continue;
            }
            
            replaceOn(div, outputDocument, reference, label);
        }
        return outputDocument.toString();
    }

    protected abstract void replaceOn(Element div, OutputDocument outputDocument, Element reference, Element label);
    
    private Element findReferenceableContainer(Element label) {
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
