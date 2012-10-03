package br.com.caelum.tubaina;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TubainaBuilderData {
	private boolean strictXhtml;
	private File templateDir;
	private boolean showNotes;
	private boolean noAnswer;
	private String outputFileName;
    private List<String> ifdefs;
    private String linkParameter;

	public TubainaBuilderData(boolean strictXhtml, File templateDir, boolean showNotes, boolean noAnswer,
			String outputFileName) {
	    this.ifdefs = new ArrayList<String>();
		this.strictXhtml = strictXhtml;
		this.templateDir = templateDir;
		this.showNotes = showNotes;
		this.noAnswer = noAnswer;
		this.outputFileName = outputFileName;
		this.linkParameter = "";
    }

    public boolean isStrictXhtml() {
        return strictXhtml;
    }

    public void setStrictXhtml(boolean strictXhtml) {
        this.strictXhtml = strictXhtml;
    }

    public File getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(File templateDir) {
        this.templateDir = templateDir;
    }

    public boolean isShowNotes() {
        return showNotes;
    }

    public void setShowNotes(boolean showNotes) {
        this.showNotes = showNotes;
    }

    public boolean isNoAnswer() {
        return noAnswer;
    }

    public void setNoAnswer(boolean noAnswer) {
        this.noAnswer = noAnswer;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public List<String> getIfdefs() {
        return ifdefs;
    }

    public void setIfdefs(List<String> ifdefs) {
        this.ifdefs = ifdefs;
    }

    public String getLinkParameter() {
        return linkParameter;
    }
    
    public void setLinkParameter(String linkParameter) {
        this.linkParameter = linkParameter;
        
    }
	
	
}