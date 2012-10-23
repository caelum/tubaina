package br.com.caelum.tubaina.parser.pygments;

public enum CodeOutputType {
    
    
    LATEX {
        @Override
        public String pygmentsFormatterName() {
            return "latex";
        }
    }, 
    HTML {
        @Override
        public String pygmentsFormatterName() {
            return "html";
        }
    }, 
    
    KINDLE_HTML {
        @Override
        public String pygmentsFormatterName() {
            return "html";
        }
    };

    public abstract String pygmentsFormatterName();
}
