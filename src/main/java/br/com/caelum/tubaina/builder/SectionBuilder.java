package br.com.caelum.tubaina.builder;

import java.util.List;

import br.com.caelum.tubaina.Section;
import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.resources.Resource;

public class SectionBuilder {

	private final String sectionTitle;

	private final String sectionContent;

	private final List<Resource> resources;

	private final SectionsManager sectionsManager;

	public SectionBuilder(String sectionTitle, String sectionContent, List<Resource> resources, SectionsManager sectionsManager) {
		this.sectionTitle = sectionTitle;
		this.sectionContent = sectionContent;
		this.resources = resources;
		this.sectionsManager = sectionsManager;
	}

	public Section build() {
		return new Section(sectionTitle, new ChunkSplitter(resources, "all", sectionsManager).splitChunks(sectionContent));
	}
}
