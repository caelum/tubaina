package br.com.caelum.tubaina.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.tubaina.builder.replacer.Replacer;
import br.com.caelum.tubaina.builder.replacer.ReplacerType;
import br.com.caelum.tubaina.resources.Resource;

public class ChunksMakerBuilder {
	private Map<ReplacerType, List<Replacer>> replacerMap = new HashMap<ReplacerType, List<Replacer>>();

	private String paragraphTerminator = "java|box|code|gist|img|list|xml|exercise|note|answer|item|question|todo|index|ruby|table|row|center";

	private static final List<String> CLOSABLE_TAGS;
	static {
		CLOSABLE_TAGS = new ArrayList<String>();
		Collections.addAll(CLOSABLE_TAGS, "java", "box", "code", "list", "xml",
				"exercise", "note", "answer", "question", "label", "mail",
				"ruby", "table", "row", "col", "center");
	}

	public static boolean isClosableTag(String tag) {
		return CLOSABLE_TAGS.contains(tag.toLowerCase());
	}

	public ChunksMakerBuilder(List<Resource> resources) {
		for (ReplacerType replacerType : ReplacerType.values()) {
			replacerMap.put(replacerType, replacerType.getReplacers(resources, paragraphTerminator));
		}
	}

	public ChunksMaker build(ReplacerType type) {
		ChunksMaker maker = new ChunksMaker();
		if (replacerMap.containsKey(type)) {
			for (Replacer replacer : replacerMap.get(type)) {
				maker.register(replacer);
			}
		}
		// Every tag shall have an inner ToDo tag
		// maker.register(new TodoReplacer());
		// paragraphReplacer MUST be the last one to be parsed or it won't work
		// maker.register(new ParagraphReplacer(paragraphTerminator));
		return maker;
	}
}
