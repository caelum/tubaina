package br.com.caelum.tubaina;

import java.io.Reader;

public class AfcFile {

    private final Reader reader;
    private final String fileName;

    public AfcFile(Reader reader, String fileName) {
        this.reader = reader;
        this.fileName = fileName;
    }

    public Reader getReader() {
        return reader;
    }

    public String getFileName() {
        return fileName;
    }
}
