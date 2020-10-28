/**
 * Interface for a Binary Tree project representing the component pieces of the
 * Covid Health Check for GMU. This interface utilizes generic type.
 * 
 * @param <T> generic type.
 * 
 * @author Jacob Strokus
 *
 */
public interface BinaryTreeInterface<T> {

	/**
	 * Method to get the data stored in the root node of a tree. Note that you
	 * cannot return the data of an empty tree.
	 * 
	 * @return the data stored in the root node of a tree.
	 */
	public T getRootData();

	/**
	 * Method to get the the root Node.
	 * 
	 * @return the reference to the root node of a tree.
	 */
	public BinaryNode<T> getRootNode();

	/**
	 * This method sets the root node of a tree.
	 * 
	 * @param nodeToSet the Node to be set as root.
	 */
	public void setRootNode(BinaryNode<T> nodeToSet);

	/**
	 * Method to get the height of the tree.
	 * 
	 * @return Integer representing the height of the binary tree.
	 */
	public int getHeight();

	/**
	 * Method to get the number of nodes in the tree.
	 * 
	 * @return Integer representing the number of nodes in the binary tree.
	 */
	public int getNumberOfNodes();

	/**
	 * Method to check is the tree is empty.
	 * 
	 * @return returns true if a tree is empty, false otherwise.
	 */
	public boolean isEmpty();

	/**
	 * Method to clear the tree.
	 */
	public void clear();
}
