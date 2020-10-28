/**
 * Blueprint class to create DecisionTree Objects. This class extends the
 * BinaryTree class and implements the DecisionTreeInterface.
 * 
 * @param <T> generic type.
 * 
 * @author Jacob Strokus
 *
 */
public class DecisionTree<T> extends BinaryTree<T> implements DecisionTreeInterface<T> {

	/**
	 * Private instance variable to keep track of which node we are currently at in
	 * the tree.
	 */
	protected BinaryNode<T> currentNode;

	/**
	 * Default constructor to create DecisionTree Objects. root by default is set to
	 * null.
	 */
	public DecisionTree() {
		root = null;
	}

	/**
	 * Constructor to create DecisionTree Objects. Data passed in parameter is set
	 * to the root node.
	 * 
	 * @param data Data to bee set to the root node.
	 */
	public DecisionTree(T data) {
		root = new BinaryNode<>(data);
	}

	/**
	 * This method returns true if the current node contains the final
	 * decision(answer), false otherwise. Note which node contains the final answer
	 * in decision trees.
	 * 
	 * @return boolean expression whether the current node contains the final
	 *         answer.
	 */
	@Override
	public boolean isAnswer() {

		if (currentNode != null && currentNode.isLeaf()) {

			return true;

		}

		return false;
	}

	/**
	 * Sets the current node to it's left child. Note that the current node should
	 * not be null.
	 * 
	 */
	@Override
	public void moveToNo() {

		if (currentNode == null) {

		} else {
			currentNode = currentNode.getLeftChild();
		}

	}

	/**
	 * Sets the current node to it's right child. Note that the current node should
	 * not be null.
	 * 
	 */
	@Override
	public void moveToYes() {

		if (currentNode == null) {

		} else {
			currentNode = currentNode.getRightChild();
		}
	}

	/**
	 * Sets the current node to the root.
	 */
	@Override
	public void resetCurrentNode() {
		currentNode = root;
	}

	/**
	 * Method to get the current node.
	 * 
	 * @return the reference to the current node.
	 * 
	 */
	@Override
	public BinaryNode<T> getCurrentNode() {
		return currentNode;
	}

	/**
	 * Method to get the data in the current node.
	 * 
	 * @return the data portion of the current node.
	 */
	@Override
	public T getCurrentData() {

		if (currentNode == null) {
			return null;
		} else
			return currentNode.getData();

	}

	/**
	 * Method to get the number of nodes in the tree.
	 * 
	 * @return Integer representing the number of nodes in the tree.
	 */
	@Override
	public int getNumberOfNodes() {

		return root.getNumberOfNodes();
	}

	/**
	 * Sets the data in the children (left and right) of the current node
	 * respectively.
	 */
	@Override
	public void setResponses(T responseForNo, T responseForYes) {

		BinaryNode<T> left = new BinaryNode<>(responseForNo);
		BinaryNode<T> right = new BinaryNode<>(responseForYes);

		currentNode.setLeftChild(left);
		currentNode.setRightChild(right);

	}

	/**
	 * Controls the flow of the program. Used for testing.
	 * 
	 * @param args Command-line arguments supplied as an Array of String Objects.
	 */
	public static void main(String[] args) {
		DecisionTree<String> dTree = new DecisionTree<>();
		dTree.setTree("D", null, null);
		DecisionTree<String> fTree = new DecisionTree<>();
		fTree.setTree("F", null, null);
		DecisionTree<String> gTree = new DecisionTree<>();
		gTree.setTree("G", null, null);
		DecisionTree<String> hTree = new DecisionTree<>();
		hTree.setTree("H", null, null);
		DecisionTree<String> eTree = new DecisionTree<>();
		eTree.setTree("E", fTree, gTree);
		DecisionTree<String> bTree = new DecisionTree<>();
		bTree.setTree("B", dTree, eTree);
		DecisionTree<String> empty = new DecisionTree<>();
		DecisionTree<String> cTree = new DecisionTree<>();
		cTree.setTree("C", empty, hTree);
		DecisionTree<String> aTree = new DecisionTree<>();
		aTree.setTree("A", bTree, cTree);
		BinaryNode<String> node = new BinaryNode<>("A");
		DecisionTree<String> node1 = aTree;
		node1.resetCurrentNode();
		System.out.println(node1.getNumberOfNodes());

	}
}
