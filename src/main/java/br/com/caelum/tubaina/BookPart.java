package br.com.caelum.tubaina;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import br.com.caelum.tubaina.chunk.IntroductionChunk;
import br.com.caelum.tubaina.resources.ImageResource;
import br.com.caelum.tubaina.resources.Resource;
import br.com.caelum.tubaina.resources.ResourceLocator;

public class BookPart {

    private List<Chapter> chapters;
    private String title;
    private boolean printable;
    private String introductionText;
    private Chunk introductionChunk;
    private final List<Resource> resources;
    private final String illustrationPath;

    public BookPart(String title, boolean printable, String introduction,
            IntroductionChunk introductionChunk, String illustrationPath, List<Resource> resources) {
        this.printable = printable;
        this.introductionText = introduction;
        this.introductionChunk = introductionChunk;
        this.illustrationPath = FilenameUtils.getName(illustrationPath);
        this.resources = resources;
        if (!this.illustrationPath.isEmpty()) {
            File imageFile = ResourceLocator.getInstance().getFile(illustrationPath);
            resources.add(new ImageResource(imageFile, "100"));
        }
        this.chapters = new ArrayList<Chapter>();
        this.title = title;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public String getTitle() {
        return this.title;
    }

    public void addAllChapters(List<Chapter> chapters) {
        this.chapters.addAll(chapters);
    }

    public boolean isPrintable() {
        return printable;
    }

    public String getIntroduction() {
        return introductionChunk == null ? "" : introductionChunk.asString();
    }

    public String getIntroductionText() {
        return this.introductionText;
    }
    
    public List<Resource> getResources() {
        return resources;
    }
    
    public String getIllustrationPath() {
        return illustrationPath;
    }
}
