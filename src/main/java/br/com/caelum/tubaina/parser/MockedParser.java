package br.com.caelum.tubaina.parser;

public class MockedParser implements Parser {

    public String parse(String text) {
        return text;
    }

    public String parseAnswer(String text, int id) {

        return text;
    }

    public String parseBox(String text, String options) {

        return text;
    }

    public String parseCode(String text, String options) {

        return text;
    }
    
    public String parseGist(String options) {
    	
    	return options;
    }

    public String parseExercise(String text, int id) {

        return text;
    }

    public String parseImage(String text, String options) {

        return text;
    }

    public String parseIndex(String name) {

        return name;
    }

    public String parseItem(String text) {

        return text;
    }

    public String parseJava(String text, String options) {

        return text;
    }

    public String parseList(String text, String options) {

        return text;
    }

    public String parseNote(String text, String title) {
        return text;
    }

    public String parseParagraph(String text) {

        return text;
    }

    public String parseQuestion(String text) {

        return text;
    }

    public String parseTodo(String text) {

        return text;
    }

    public String parseXml(String text, String options) {

        return text;
    }

    public String parseColumn(String text) {

        return text;
    }

    public String parseRow(String text) {

        return text;
    }

    public String parseTable(String text, String title, boolean noborder,
            int columns) {
        return text;
    }

    public String parseCenteredParagraph(String text) {
        return text;
    }

    public String parseRuby(String text, String options) {
        return text;
    }

    public String parseRef(String text) {
        return text;
    }

}
