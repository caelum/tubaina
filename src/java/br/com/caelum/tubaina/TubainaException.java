package br.com.caelum.tubaina;

public class TubainaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TubainaException() {
		super();
	}

	public TubainaException(String message, Throwable cause) {
		super(message, cause);
	}

	public TubainaException(String message) {
		super(message);
	}

	public TubainaException(Throwable cause) {
		super(cause);
	}

}
