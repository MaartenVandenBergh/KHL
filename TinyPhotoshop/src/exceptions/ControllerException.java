package exceptions;

public class ControllerException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ControllerException(String message) {
        super("|Controller-Error: "+message+"|");
    }

}
