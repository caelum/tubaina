package br.com.caelum.tubaina.builder;

import java.util.List;

import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.builder.replacer.ReplacerType;
import br.com.caelum.tubaina.resources.Resource;

public class SectionBuilder {

	private final String sectionTitle;

	private final String sectionContent;

	private final List<Resource> resources;

	public SectionBuilder(String sectionTitle, String sectionContent, List<Resource> resources) {
		this.sectionTitle = sectionTitle;
		this.sectionContent = sectionContent;
		this.resources = resources;
	}

	public Section build() {
		return new Section(sectionTitle, new ChunkSplitter(resources, ReplacerType.ALL).splitChunks(sectionContent));
	}
}
