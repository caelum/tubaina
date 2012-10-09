package br.com.caelum.tubaina.parser.html.desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

public class CodeCache {

    private String output;
    private String tempDir;
    public static final Logger LOG = Logger.getLogger(CodeCache.class);

    public CodeCache(String output) {
        this.output = output;
        this.tempDir = System.getProperty("java.io.tmpdir");
    }

    public boolean exists(String code) {
        File cacheFile = cacheFile(code);

        return cacheFile.exists();
    }

    public String find(String code) {
        File cacheFile = cacheFile(code);
        
        Scanner s;
        try {
            s = new Scanner(new FileInputStream(cacheFile));
            s.useDelimiter("\\A");
            LOG.debug("found code on cache: " + cacheFile);
            return s.next();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(String plainCode, String codeHighlighted) {
        if (!exists(plainCode)) {
            File cacheFile = cacheFile(plainCode);
            try {
                LOG.debug("writing code to cache: " + cacheFile);
                cacheFile.createNewFile();
                PrintStream saida = new PrintStream(cacheFile);
                saida.print(codeHighlighted);
                saida.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private File cacheFile(String plainCode) {
        String md5 = DigestUtils.md5Hex(plainCode);
        return  new File(tempDir, md5 + output + ".code");
    }

}
