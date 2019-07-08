package shiftman.server;


/**
 * Represents unwanted/unexpected event that occurred during use of Shift Manager 
 * @author Denise Jarry
 *
 */
public class ShiftManException extends Exception {

	//Serializing the class (as per Eclipse recommendation)
	private static final long serialVersionUID = 16081999L;
	
	private String _message;

	public ShiftManException(String message) {
		_message = message;
	}
	
	public String getMessage() {
		return _message;
	}

}
