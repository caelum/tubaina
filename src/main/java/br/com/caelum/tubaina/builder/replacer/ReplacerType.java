package br.com.caelum.tubaina.builder.replacer;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.tubaina.resources.Resource;

public enum ReplacerType {
	ANSWER{
		@Override
		public List<Replacer> getReplacers(List<Resource> resources, String paragraphTerminator) {
			List<Replacer> replacers = new ArrayList<Replacer>();
			replacers.add(new BoxReplacer(resources));
			replacers.add(new CodeReplacer());
			replacers.add(new GistReplacer());
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
			return replacers;
		}
	},
	BOX{
		@Override
		public List<Replacer> getReplacers(List<Resource> resources, String paragraphTerminator) {
			List<Replacer> replacers = new ArrayList<Replacer>();
			replacers.add(new CodeReplacer());
			replacers.add(new GistReplacer());
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
			return replacers;
		}
	},
	EXERCISE{
		@Override
		public List<Replacer> getReplacers(List<Resource> resources, String paragraphTerminator) {
			List<Replacer> replacers = new ArrayList<Replacer>();
			replacers.add(new QuestionReplacer(resources));
			replacers.add(new TodoReplacer());
			return replacers;
		}
	},
	ITEM{
		@Override
		public List<Replacer> getReplacers(List<Resource> resources, String paragraphTerminator) {
			List<Replacer> replacers = new ArrayList<Replacer>();
			replacers.add(new BoxReplacer(resources));
			replacers.add(new CodeReplacer());
			replacers.add(new GistReplacer());
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
			replacers.add(new ParagraphInsideItemReplacer(paragraphTerminator));
			return replacers;
		}
	},
	LIST{
		@Override
		public List<Replacer> getReplacers(List<Resource> resources, String paragraphTerminator) {
			List<Replacer> replacers = new ArrayList<Replacer>();
			replacers.add(new ItemReplacer(resources));
			replacers.add(new NoteReplacer(resources));
			replacers.add(new TodoReplacer());
			return replacers;
		}
	},
	NOTE{
		@Override
		public List<Replacer> getReplacers(List<Resource> resources, String paragraphTerminator) {
			List<Replacer> replacers = new ArrayList<Replacer>();
			replacers.add(new CodeReplacer());
			replacers.add(new GistReplacer());
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
			return replacers;
		}
	},
	QUESTION{
		@Override
		public List<Replacer> getReplacers(List<Resource> resources, String paragraphTerminator) {
			List<Replacer> replacers = new ArrayList<Replacer>();
			replacers.add(new AnswerReplacer(resources));
			replacers.add(new BoxReplacer(resources));
			replacers.add(new CodeReplacer());
			replacers.add(new GistReplacer());
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
			return replacers;
		}
	},
	TABLE{
		@Override
		public List<Replacer> getReplacers(List<Resource> resources, String paragraphTerminator) {
			List<Replacer> replacers = new ArrayList<Replacer>();
			replacers.add(new TableRowReplacer(resources));
			replacers.add(new TodoReplacer());
			return replacers;
		}
	},
	ROW{
		@Override
		public List<Replacer> getReplacers(List<Resource> resources, String paragraphTerminator) {
			List<Replacer> replacers = new ArrayList<Replacer>();
			replacers.add(new TableColumnReplacer(resources));
			replacers.add(new TodoReplacer());
			return replacers;
		}
	},
	COLUMN{
		@Override
		public List<Replacer> getReplacers(List<Resource> resources, String paragraphTerminator) {
			List<Replacer> replacers = new ArrayList<Replacer>();
			replacers.add(new BoxReplacer(resources));
			replacers.add(new CodeReplacer());
			replacers.add(new GistReplacer());
			replacers.add(new ExerciseReplacer(resources));
			replacers.add(new ImageReplacer(resources));
			replacers.add(new JavaReplacer());
			replacers.add(new ListReplacer(resources));
			replacers.add(new NoteReplacer(resources));
			replacers.add(new XmlReplacer());
			replacers.add(new TodoReplacer());

			replacers.add(new RubyReplacer());
			replacers.add(new ParagraphReplacer(paragraphTerminator));
			return replacers;
		}
	},
	ALL{
		@Override
		public List<Replacer> getReplacers(List<Resource> resources, String paragraphTerminator) {
			// All tags not restricted to others, like ItemTag
			List<Replacer> replacers = new ArrayList<Replacer>();	replacers.add(new BoxReplacer(resources));
			replacers.add(new CodeReplacer());
			replacers.add(new GistReplacer());
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
			replacers.add(new CenteredParagraphReplacer());
			replacers.add(new ParagraphReplacer(paragraphTerminator));
			return replacers;
		}
	};
	
	public abstract List<Replacer> getReplacers(List<Resource> resources, String paragraphTerminator);

}
