package br.com.caelum.tubaina.parser.markdown;

public class S3Path {

	private String remotePath;

	public S3Path(String remotePath) {
		this.remotePath = remotePath;
	}
	
	public String getRemotePath() {
		return remotePath;
	}
}
