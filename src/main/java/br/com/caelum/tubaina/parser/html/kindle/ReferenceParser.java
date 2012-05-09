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
        replaceTextLabels();
        replaceImages();
        return outputDocument.toString();
    }

    private void replaceTextLabels() {
        List<Element> references = source.getAllElementsByClass("reference");
        for (Element reference : references) {
            String labelId = reference.getAttributeValue("href").replace("#", "");
            Element label = source.getElementById(labelId);
            
            Element div = findReferenceableContainer(label);
            if (!isValid(div)) {
                outputDocument.replace(reference, reference.toString().replace("*", "?"));
                continue;
            }
            
            if(label.getName().equals("img")) continue;
            Element title = div.getFirstElementByClass("referenceableTitle");
            String number = title.getTextExtractor().toString().split("-")[0].trim();
            outputDocument.replace(reference, reference.toString().replace("*", number));
        }
    }
    
    private void replaceImages() {
        List<Element> references = source.getAllElementsByClass("reference");
        for (Element reference : references) {
            String labelId = reference.getAttributeValue("href").replace("#", "");
            Element label = source.getElementById(labelId);
            
            Element containerReferenceable = findReferenceableContainer(label);
            if (!isValid(containerReferenceable)) {
                outputDocument.replace(reference, reference.toString().replace("*", "?"));
                continue;
            }
            
            if(!label.getName().equals("img")) continue;
            List<Element> imgs = containerReferenceable.getAllElements("img");
            int imagePosition = imgs.indexOf(label) + 1;
            Element title = containerReferenceable.getFirstElementByClass("referenceableTitle");
            String number = title.getTextExtractor().toString().split("-")[0].trim();
            outputDocument.replace(reference, reference.toString().replace("*", number+"."+imagePosition));
        }
    }

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
