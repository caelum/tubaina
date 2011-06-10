package br.com.caelum.tubaina;

public enum ParseTypes {
	
	HTML("html"), LATEX("latex"), HTMLFLAT("htmlflat"), SINGLE_HTML("singlehtml");

	private final String type;

	private ParseTypes(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
		
	}
	
}
