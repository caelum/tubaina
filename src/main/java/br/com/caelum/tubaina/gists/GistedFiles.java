package br.com.caelum.tubaina.gists;

import java.util.List;

public class GistedFiles {
	public GistedFiles(List<GistedFile> files) {
		this.entries = files;
	}

	private List<GistedFile> entries;
	
	public List<GistedFile> getEntries() {
		return entries;
	}
}
