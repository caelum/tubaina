package br.com.caelum.tubaina.parser.html.referencereplacer;

import java.util.List;

public class ReferenceParser {

    private List<ReferenceReplacer> referenceReplacers;
    
    public ReferenceParser(List<ReferenceReplacer> referenceReplacer) {
        referenceReplacers = referenceReplacer;
    }

    public String replaceReferences(String htmlContent) {
        String output = htmlContent;
        
        for (ReferenceReplacer referenceReplacer : referenceReplacers) {
            output = referenceReplacer.replace(output);
        }
        
        return output;
        
    }

}
