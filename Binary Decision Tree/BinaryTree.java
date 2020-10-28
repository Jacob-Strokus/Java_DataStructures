/**
 * Blueprint class to create Binary Tree objects. This class the generic Binary
 * Tree Interface.
 * 
 * @param <T> generic type.
 * 
 * @author Jacob Strokus
 *
 */
public class BinaryTree<T> implements BinaryTreeInterface<T> {

	/**
	 * Private instance variable of the root node.
	 */
	protected BinaryNode<T> root;

	/**
	 * Default constructor to create BinaryTree Objects.
	 */
	public BinaryTree() {
		root = null;
	}

	/**
	 * Constructor to create BinaryTree Objects.
	 * 
	 * @param data T value representing the data in the Node.
	 */
	public BinaryTree(T data) {
		root = new BinaryNode<T>(data);
	}

	/**
	 * Full constructor to create BinaryTree Objects.
	 * 
	 * @param data      T value representing the data in the Node.
	 * @param leftTree  tree referencing the left subtree.
	 * @param rightTree tree referencing the right subtree.
	 */
	public BinaryTree(T data, BinaryNode<T> leftTree, BinaryNode<T> rightTree) {
		root = new BinaryNode<T>(data);
		root.setRightChild(rightTree);
		root.setLeftChild(leftTree);
	}

	/**
	 * Method to get the data in the root node.
	 * 
	 * @return T value for data in root node.
	 */
	@Override
	public T getRootData() {
		if (root == null) {
			throw new EmptyTreeException("There is no root node.");
		} else {
			return root.getData();
		}
		
	}

	/**
	 * Method to get the root node.
	 * 
	 * @return the root node.
	 */
	@Override
	public BinaryNode<T> getRootNode() {
		return root;
	}

	/**
	 * Method to set the root node.
	 * 
	 * @param nodeToSet the generic binary node to be set.
	 */
	@Override
	public void setRootNode(BinaryNode<T> nodeToSet) {
		root = nodeToSet;
	}

	/**
	 * Method to get the height of the tree.
	 * 
	 * @return Integer representing the height of the tree.
	 */
	@Override
	public int getHeight() {
		return root.getHeight();
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
	 * Method to check if the tree is empty.
	 * 
	 * @return boolean expression whether the tree is empty.
	 */
	@Override
	public boolean isEmpty() {

		return root == null;
	}

	/**
	 * Method to clear the tree.
	 */
	@Override
	public void clear() {

		root = null;
	}

	/**
	 * Method to set the tree.
	 * 
	 * @param data      T value representing the data in the Node.
	 * @param leftTree  tree referencing the left subtree.
	 * @param rightTree tree referencing the right subtree.
	 */
	public void setTree(T data, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {

		if (leftTree == null || rightTree == null) {
			// do nothing
		} else {
			// Check if they are same object.
			if (leftTree.getRootNode() == null || rightTree.getRootNode() == null
					|| leftTree.getRootNode().equals(rightTree.getRootNode())) {
				// do nothing
			}

			// Allocate new node.
			root = new BinaryNode<T>(data, leftTree.getRootNode(), rightTree.getRootNode());

			// Ensure every node is in one tree.
			if (root != leftTree.getRootNode()) {
				leftTree.setRootNode(null);
			}
			if (root != rightTree.getRootNode()) {
				rightTree.setRootNode(null);
			}
		}

	}

	/**
	 * Method to conduct an inOrder traversal of the tree.
	 */
	public void inOrderTraversal() {

		if (root != null) {
			root = root.getLeftChild();
			System.out.print(" " + root.getData());
			root = root.getRightChild();
			inOrderTraversal();
		}
	}

}
