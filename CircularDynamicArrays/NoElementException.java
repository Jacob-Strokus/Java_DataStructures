/**
 * Creates a custom no element exception that extends Java's RuntimeException
 * class.
 * 
 * @author Jacob Strokus
 *
 */
public class NoElementException extends RuntimeException {

	/**
	 * A unique serial version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method creates a NoElementException and passes the String message to parent
	 * class.
	 * 
	 * @param message - Message to be paired with exception.
	 */
	public NoElementException(String message) {
		super(message);
	}
}
