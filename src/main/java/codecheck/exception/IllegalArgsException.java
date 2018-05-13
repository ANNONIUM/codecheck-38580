package codecheck.exception;

public class IllegalArgsException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private static String MESSAGE = "error! FAILED BY IllegalArgsException";
	private static String MESSAGE_WITH_ARGS  = "error! FAILED BY IllegalArgsException args size is %s";
	
	public IllegalArgsException() {
		super(MESSAGE);
	}
	
	public IllegalArgsException(String args) {
		super(String.format(MESSAGE_WITH_ARGS, args));
	}
}
