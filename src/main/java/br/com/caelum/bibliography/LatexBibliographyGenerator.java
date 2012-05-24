package br.com.caelum.bibliography;

public class LatexBibliographyGenerator implements BibliographyGenerator {

    public String generateTextOf(Bibliography bibliography) {
        StringBuffer result = new StringBuffer();

        for (BibliographyEntry entry : bibliography.getEntries()) {
            result.append("@" + entry.type + "{");
            result.append(entry.label + ",\n");
            addField(result, "author", entry.author);
            addField(result, "title", entry.title);
            addField(result, "year", entry.year);
            addField(result, "howPublished", "\\url{" + entry.howPublished + "}");
            if (entry.publisher != null)
                addField(result, "publisher", entry.publisher);
            result.append("}\n");
        }

        return result.toString();
    }

    private void addField(StringBuffer result, String key, String value) {
        result.append(key + " = {" + value + "},\n");

    }
}
