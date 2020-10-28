/**
 * Some of the operations in the classes throw exception. For example,
 * getRootData() cannot return a value if the tree is empty. This class handles
 * the error by returning a String. This class extends Java’s RuntimeException
 * class.
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
	 * Method creates a EmptyTreeException and passes the String message to parent
	 * class.
	 * 
	 * @param message - Message to be paired with exception.
	 */
	public EmptyTreeException(String message) {
		super(message);
	}

}
