package br.com.caelum.tubaina;

import java.io.File;

public class TubainaBuilderData {
	public boolean strictXhtml;
	public File templateDir;
	public boolean showNotes;
	public boolean noAnswer;
	public String outputFileName;

	public TubainaBuilderData(boolean strictXhtml, File templateDir, boolean showNotes, boolean noAnswer,
			String outputFileName) {
		this.strictXhtml = strictXhtml;
		this.templateDir = templateDir;
		this.showNotes = showNotes;
		this.noAnswer = noAnswer;
		this.outputFileName = outputFileName;
	}
}