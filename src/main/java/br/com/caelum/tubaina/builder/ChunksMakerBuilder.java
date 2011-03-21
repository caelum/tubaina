package br.com.caelum.tubaina.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.tubaina.builder.replacer.AnswerReplacer;
import br.com.caelum.tubaina.builder.replacer.BoxReplacer;
import br.com.caelum.tubaina.builder.replacer.CenteredParagraphReplacer;
import br.com.caelum.tubaina.builder.replacer.CodeReplacer;
import br.com.caelum.tubaina.builder.replacer.ExerciseReplacer;
import br.com.caelum.tubaina.builder.replacer.ImageReplacer;
import br.com.caelum.tubaina.builder.replacer.IndexReplacer;
import br.com.caelum.tubaina.builder.replacer.ItemReplacer;
import br.com.caelum.tubaina.builder.replacer.JavaReplacer;
import br.com.caelum.tubaina.builder.replacer.ListReplacer;
import br.com.caelum.tubaina.builder.replacer.NoteReplacer;
import br.com.caelum.tubaina.builder.replacer.ParagraphReplacer;
import br.com.caelum.tubaina.builder.replacer.QuestionReplacer;
import br.com.caelum.tubaina.builder.replacer.Replacer;
import br.com.caelum.tubaina.builder.replacer.RubyReplacer;
import br.com.caelum.tubaina.builder.replacer.TableColumnReplacer;
import br.com.caelum.tubaina.builder.replacer.TableReplacer;
import br.com.caelum.tubaina.builder.replacer.TableRowReplacer;
import br.com.caelum.tubaina.builder.replacer.TodoReplacer;
import br.com.caelum.tubaina.builder.replacer.VerbatimReplacer;
import br.com.caelum.tubaina.builder.replacer.XmlReplacer;
import br.com.caelum.tubaina.resources.Resource;

public class ChunksMakerBuilder {

	private Map<String, List<Replacer>> replacerMap = new HashMap<String, List<Replacer>>();

	private String paragraphTerminator = "java|box|code|img|list|xml|exercise|note|answer|item|question|todo|index|ruby|table|row|center";

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
		List<Replacer> replacers;

		// Answer tag
		replacers = new ArrayList<Replacer>();
		replacers.add(new BoxReplacer(resources));
		replacers.add(new CodeReplacer());
		replacers.add(new ImageReplacer(resources));
		replacers.add(new JavaReplacer());
		replacers.add(new ListReplacer(resources));
		replacers.add(new NoteReplacer(resources));
		replacers.add(new XmlReplacer());
		replacers.add(new IndexReplacer(resources));
		replacers.add(new TodoReplacer());
		replacers.add(new RubyReplacer());
		replacers.add(new TableReplacer(resources));

		replacers.add(new CenteredParagraphReplacer());
		replacers.add(new ParagraphReplacer(paragraphTerminator));
		replacerMap.put("answer", replacers);

		// Box tag
		replacers = new ArrayList<Replacer>();
		replacers.add(new CodeReplacer());
		replacers.add(new ImageReplacer(resources));
		replacers.add(new JavaReplacer());
		replacers.add(new ListReplacer(resources));
		replacers.add(new XmlReplacer());
		replacers.add(new NoteReplacer(resources));
		replacers.add(new IndexReplacer(resources));
		replacers.add(new TodoReplacer());
		replacers.add(new RubyReplacer());
		replacers.add(new TableReplacer(resources));
		replacers.add(new CenteredParagraphReplacer());
		replacers.add(new ParagraphReplacer(paragraphTerminator));
		replacerMap.put("box", replacers);

		// Exercise tag
		replacers = new ArrayList<Replacer>();
		replacers.add(new QuestionReplacer(resources));
		replacers.add(new TodoReplacer());
		replacerMap.put("exercise", replacers);

		// Item tag
		replacers = new ArrayList<Replacer>();
		replacers.add(new BoxReplacer(resources));
		replacers.add(new CodeReplacer());
		replacers.add(new ExerciseReplacer(resources));
		replacers.add(new ListReplacer(resources));
		replacers.add(new ImageReplacer(resources));
		replacers.add(new JavaReplacer());
		replacers.add(new NoteReplacer(resources));
		replacers.add(new XmlReplacer());
		replacers.add(new IndexReplacer(resources));
		replacers.add(new TodoReplacer());

		replacers.add(new RubyReplacer());
		replacers.add(new CenteredParagraphReplacer());
		replacers.add(new ParagraphReplacer(paragraphTerminator));
		replacerMap.put("item", replacers);

		// List tag
		replacers = new ArrayList<Replacer>();
		replacers.add(new ItemReplacer(resources));
		replacers.add(new NoteReplacer(resources));
		replacers.add(new TodoReplacer());
		replacerMap.put("list", replacers);

		// Note tag
		replacers = new ArrayList<Replacer>();
		replacers.add(new CodeReplacer());
		replacers.add(new ImageReplacer(resources));
		replacers.add(new JavaReplacer());
		replacers.add(new ListReplacer(resources));
		replacers.add(new XmlReplacer());
		replacers.add(new IndexReplacer(resources));
		replacers.add(new TodoReplacer());

		replacers.add(new RubyReplacer());
		replacers.add(new TableReplacer(resources));
		replacers.add(new CenteredParagraphReplacer());
		replacers.add(new ParagraphReplacer(paragraphTerminator));
		replacerMap.put("note", replacers);

		// Question tag
		replacers = new ArrayList<Replacer>();
		replacers.add(new AnswerReplacer(resources));
		replacers.add(new BoxReplacer(resources));
		replacers.add(new CodeReplacer());
		replacers.add(new ImageReplacer(resources));
		replacers.add(new JavaReplacer());
		replacers.add(new ListReplacer(resources));
		replacers.add(new NoteReplacer(resources));
		replacers.add(new XmlReplacer());
		replacers.add(new IndexReplacer(resources));
		replacers.add(new TodoReplacer());
		replacers.add(new RubyReplacer());

		replacers.add(new TableReplacer(resources));
		replacers.add(new CenteredParagraphReplacer());
		replacers.add(new ParagraphReplacer(paragraphTerminator));
		replacerMap.put("question", replacers);

		// Table tag
		replacers = new ArrayList<Replacer>();
		replacers.add(new TableRowReplacer(resources));
		replacers.add(new TodoReplacer());
		replacerMap.put("table", replacers);

		// Row tag
		replacers = new ArrayList<Replacer>();
		replacers.add(new TableColumnReplacer(resources));
		replacers.add(new TodoReplacer());
		replacerMap.put("row", replacers);

		// Column tag
		replacers = new ArrayList<Replacer>();
		replacers.add(new BoxReplacer(resources));
		replacers.add(new CodeReplacer());
		replacers.add(new ExerciseReplacer(resources));
		replacers.add(new ImageReplacer(resources));
		replacers.add(new JavaReplacer());
		replacers.add(new ListReplacer(resources));
		replacers.add(new NoteReplacer(resources));
		replacers.add(new XmlReplacer());
		replacers.add(new TodoReplacer());

		replacers.add(new RubyReplacer());
		replacers.add(new ParagraphReplacer(paragraphTerminator));
		replacerMap.put("col", replacers);

		// All tags not restricted to others, like ItemTag
		replacers = new ArrayList<Replacer>();
		replacers.add(new BoxReplacer(resources));
		replacers.add(new CodeReplacer());
		replacers.add(new ExerciseReplacer(resources));
		replacers.add(new ImageReplacer(resources));
		replacers.add(new JavaReplacer());
		replacers.add(new ListReplacer(resources));
		replacers.add(new NoteReplacer(resources));
		replacers.add(new XmlReplacer());

		replacers.add(new IndexReplacer(resources));
		replacers.add(new TodoReplacer());
		replacers.add(new RubyReplacer());
		replacers.add(new TableReplacer(resources));
		replacers.add(new VerbatimReplacer());
		replacers.add(new CenteredParagraphReplacer());
		replacers.add(new ParagraphReplacer(paragraphTerminator));
		replacerMap.put("all", replacers);

	}

	public ChunksMaker build(String tag) {
		ChunksMaker maker = new ChunksMaker();
		if (replacerMap.containsKey(tag)) {
			for (Replacer replacer : replacerMap.get(tag)) {
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
