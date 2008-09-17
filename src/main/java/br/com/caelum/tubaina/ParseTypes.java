package br.com.caelum.tubaina;

public enum ParseTypes {
	
	HTML("html"), LATEX("latex");

	private final String type;

	private ParseTypes(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
		
	}
	
}
