package codecheck.exception;

public class ArgsSizeException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private static String MESSAGE = "error! FAILED BY ArgsSizeException";
	private static String MESSAGE_WITH_SIZE  = "error! FAILED BY ArgsSizeException args size is %d";
	
	public ArgsSizeException() {
		super(MESSAGE);
	}
	
	public ArgsSizeException(Integer size) {
		super(String.format(MESSAGE_WITH_SIZE, size));
	}
	
}
