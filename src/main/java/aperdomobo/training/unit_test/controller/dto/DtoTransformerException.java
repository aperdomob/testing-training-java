package aperdomobo.training.unit_test.controller.dto;

public class DtoTransformerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -82544067837173809L;

	public DtoTransformerException() {
		
	}
	
	public DtoTransformerException(String message) {
		super(message);
	}
	
	public DtoTransformerException(Throwable cause) {
		super(cause);
	}
	
	public DtoTransformerException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DtoTransformerException(String message, Throwable cause, boolean enableSuppresion, boolean writableStackTrace) {
		super(message, cause, enableSuppresion, writableStackTrace);
	}
}
