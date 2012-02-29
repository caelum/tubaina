package br.com.caelum.tubaina.gists;

import com.google.gson.annotations.SerializedName;

public class GistResult {

	@SerializedName("html_url")
	private String htmlUrl;

	private GistedFiles files;

	public String getContent() {
		return files.getEntries().get(0).getContent();
	}

	public String getLanguage() {
		return files.getEntries().get(0).getLanguage();
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public String getFileName() {
		return files.getEntries().get(0).getName();
	}
}
