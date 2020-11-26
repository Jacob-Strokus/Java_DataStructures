/**
 * A class that implements the BinaryTreeInterface.
 * 
 * @author Jacob Strokus
 * 
 */
public class BinaryTree<T> implements BinaryTreeInterface<T> {

	/**
	 * The root node.
	 */
	protected BinaryNode<T> root;

	/**
	 * Default constructor.
	 */
	public BinaryTree() {
		root = null;
	}

	/**
	 * Constructor to create BinaryTree's with the root data.
	 * 
	 * @param rootData the root data.
	 */
	public BinaryTree(T rootData) {
		root = new BinaryNode<>(rootData);
	}

	/**
	 * Constructor to create BinaryTree's with the root data and the child nodes.
	 * 
	 * @param rootData  the root data.
	 * @param leftTree  the left subtree.
	 * @param rightTree the right subtree.
	 */
	public BinaryTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
		privateSetTree(rootData, leftTree, rightTree);
	}

	/**
	 * Sets the tree with the root data.
	 * 
	 * @param rootData the root data.
	 */
	public void setTree(T rootData) {
		root = new BinaryNode<>(rootData);
	}

	/**
	 * Sets the tree with the root data and the left and right sub trees.
	 * 
	 * @param rootData  the root data.
	 * @param leftTree  the left subtree.
	 * @param rightTree the right subtree.
	 */
	public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree) {
		privateSetTree(rootData, (BinaryTree<T>) leftTree, (BinaryTree<T>) rightTree);
	}

	/**
	 * private method to set the tree.
	 * 
	 * @param rootData  the root data.
	 * @param leftTree  the left subtree.
	 * @param rightTree the right subtree.
	 */
	private void privateSetTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
		
		root = new BinaryNode<>(rootData);

		if ((leftTree != null) && !leftTree.isEmpty())
			root.setLeftChild(leftTree.root);

		if ((rightTree != null) && !rightTree.isEmpty()) {
			if (rightTree != leftTree)
				root.setRightChild(rightTree.root);
			else
				root.setRightChild(rightTree.root.copy());
		}

		if ((leftTree != null) && (leftTree != this))
			leftTree.clear();

		if ((rightTree != null) && (rightTree != this))
			rightTree.clear();
	}

	/**
	 * Get the root's data.
	 * 
	 * @return the root data.
	 */
	public T getRootData() {
		if (isEmpty())
			throw new EmptyTreeException();
		else
			return root.getData();
	}

	/**
	 * Check if the tree is empty.
	 * 
	 * @return boolean whether the tree is empty.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Clear the tree.
	 */
	public void clear() {
		root = null;
	}

	/**
	 * Set the data.
	 * 
	 * @param rootData the root data.
	 */
	protected void setRootData(T rootData) {
		root.setData(rootData);
	}

	/**
	 * Set the root node.
	 * 
	 * @param rootNode root node.
	 */
	public void setRootNode(BinaryNode<T> rootNode) {
		root = rootNode;
	}

	/**
	 * Get the root node.
	 * 
	 * @return the root node.
	 */
	public BinaryNode<T> getRootNode() {
		return root;
	}

	/**
	 * Get the height of the tree.
	 * 
	 * @return Integer representing the height of the tree.
	 */
	public int getHeight() {
		if (root == null)
			throw new NullPointerException();
		else
			return root.getHeight();
	}

	/**
	 * Get the number of nodes in the tree.
	 * 
	 * @return the number of nodes.
	 */
	public int getNumberOfNodes() {
		if (root == null)
			throw new NullPointerException();
		else
			return root.getNumberOfNodes();
	}

}
