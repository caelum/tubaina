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
            Element containerReferenceable = source.getElementById(labelId).getParentElement();
            if (!isValid(containerReferenceable)) {
                throw new IllegalStateException("you should wrap the chapter or section with a div of 'referenceable' class");
            }
            Element title = containerReferenceable.getFirstElementByClass("referenceableTitle");
            String number = title.getTextExtractor().toString().split("-")[0].trim();
            outputDocument.replace(reference, reference.toString().replace("*", number));
        }
        return outputDocument.toString();
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
