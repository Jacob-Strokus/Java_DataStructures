/**
 * A class of runtime exceptions thrown by methods to indicate that a tree is
 * empty.
 * 
 * @author Jacob Strokus
 * 
 */
public class EmptyTreeException extends RuntimeException {
	/**
	 * A unique serial version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates an EmptyTreeException.
	 */
	public EmptyTreeException() {
		this(null);
	}

	/**
	 * Creates an EmptyTreeException with message.
	 * 
	 * @param message the message to be displayed after exception is thrown.
	 */
	public EmptyTreeException(String message) {
		super(message);
	}
}
