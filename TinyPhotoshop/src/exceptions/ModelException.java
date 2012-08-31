package exceptions;

public class ModelException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ModelException(String message){
		super("|Model-Error: "+message+"|");
	}

}
