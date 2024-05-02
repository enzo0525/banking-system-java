package bank_system;

public class CustomExceptionMessage extends Exception{
	private static final long serialVersionUID = 1L;

	public CustomExceptionMessage(String errorMessage) {
		super(errorMessage);
	}
}
