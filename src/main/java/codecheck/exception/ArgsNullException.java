package codecheck.exception;

public class ArgsNullException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private static String MESSAGE = "error! FAILED BY ArgsSizeException Args is null";
	public ArgsNullException() {
		super(MESSAGE);
	}
}
