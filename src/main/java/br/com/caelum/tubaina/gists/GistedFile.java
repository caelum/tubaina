package br.com.caelum.tubaina.gists;

public class GistedFile {
	public GistedFile(String name, String language, String content) {
		super();
		this.name = name;
		this.language = language;
		this.content = content;
	}

	private final String name;
	private final String language;
	private final String content;

	public String getName() {
		return name;
	}

	public String getLanguage() {
		return language;
	}

	public String getContent() {
		return content;
	}

}
