package br.com.caelum.tubaina.parser;

public interface Parser {
	public String parse(String text);
	public String parseParagraph(String text);
	public String parseParagraphInsideItem(String text);
	public String parseJava(String text, String options);
	public String parseBox(String text, String options);
	public String parseImage(String text, String options);
	public String parseCode(String text, String options);
	public String parseGist(String options);
	public String parseList(String text, String options);
	public String parseXml(String text, String options);
	public String parseExercise(String text, int id);
	public String parseAnswer(String text, int id);
	public String parseQuestion(String text);
	public String parseNote(String text, String title);
	public String parseItem(String text);
	public String parseTodo(String text);
	public String parseIndex(String name);
	public String parseTable(String text, String title, boolean noborder, int columns);
	public String parseRow(String text);
	public String parseColumn(String text);
	public String parseCenteredParagraph(String content);
	public String parseRuby(String content, String options);
}
