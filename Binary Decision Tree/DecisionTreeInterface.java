/**
 * This interface represents all the functionalities the Decision tree that is
 * going to be used by the COVID19 health check. This interface utilizes generic
 * type. It extends the BinaryTreeInterface.
 * 
 * @param <T> generic type.
 * 
 * @author Jacob Strokus
 *
 */
public interface DecisionTreeInterface<T> extends BinaryTreeInterface<T> {

	/**
	 * This method returns true if the current node contains the final
	 * decision(answer), false otherwise. Note which node contains the final answer
	 * in decision trees.
	 * 
	 * @return boolean expression
	 */
	public boolean isAnswer();

	/**
	 * Set the current node to its left child. Note that the current node should not
	 * be null.
	 * 
	 */
	public void moveToNo();

	/**
	 * Set the current node to its right child. Note that the current node should
	 * not be null.
	 * 
	 */
	public void moveToYes();

	/**
	 * Set the current node to the root.
	 */
	public void resetCurrentNode();

	/**
	 * Method to get the reference to the current node.
	 * 
	 * @return reference to the current node.
	 */
	public BinaryNode<T> getCurrentNode();

	/**
	 * Method to get the current data of the current node.
	 * 
	 * @return the data portion of the current node.
	 */
	public T getCurrentData();

	/**
	 * Sets the data in the children (left and right) of the current node.
	 * 
	 * @param responseForNo  T value representing no.
	 * @param responseForYes T value representing yes.
	 */
	public void setResponses(T responseForNo, T responseForYes);

}
