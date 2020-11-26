/**
 * An interface of basic methods for tree.
 * 
 * @author Jacob Strokus
 * 
 */
public interface TreeInterface<T> {

	/**
	 * Get the root's data.
	 * 
	 * @return the data.
	 */
	public T getRootData();

	/**
	 * Get the height of the tree.
	 * 
	 * @return the height.
	 */
	public int getHeight();

	/**
	 * Get the number of nodes in the tree.
	 * 
	 * @return the number of nodes.
	 */
	public int getNumberOfNodes();

	/**
	 * Check if the tree is empty.
	 * 
	 * @return boolean if the tree is empty.
	 */
	public boolean isEmpty();

	/**
	 * Clear the tree.
	 */
	public void clear();
}
