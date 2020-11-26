/**
 * Creates an AVL tree. This class extends the Binary Search Tree class and the
 * Comparable class. This class is generic. Main method used for testing.
 * 
 * @param <T> generic type.
 * @author Jacob Strokus
 *
 */
class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {

	/**
	 * Reference to the current node in the tree.
	 */
	AVLNode<T> current = (AVLNode<T>) this.getRootNode();

	/**
	 * Default constructor to create AVLTree objects.
	 */
	public AVLTree() {
		root = null;
	}

	/**
	 * Constructor to create AVLTree with root data.
	 */
	public AVLTree(T newEntry) {

		// this.setRootNode(new AVLNode<T>(newEntry));
		root = new AVLNode<T>(newEntry);
	}

	/**
	 * Add a new entry to the AVL tree.
	 * 
	 * @param newEntry data to add.
	 * @return data of the node added.
	 */
	public T add(T newEntry) {

		T valueToReturn = null;
		AVLNode<T> added = new AVLNode<>(newEntry);

		if (contains(newEntry)) { // already in tree.
			T entry = this.getEntry(newEntry);
			valueToReturn = entry;

		} else {

			if (root == null) { // no root yet.
				root = new AVLNode<T>(newEntry);
				valueToReturn = root.getData();

			} else {
				if (current == null) {
					current = (AVLNode<T>) root;
				}

				int comparison = newEntry.compareTo(current.getData());

				if (comparison < 0) { // to the left
					if (current.hasLeftChild()) {
						current = (AVLNode<T>) current.getLeftChild();
						add(newEntry);
					} else {
						current.setLeftChild(added);

					}

				} else if (comparison > 0) { // to the right
					if (current.hasRightChild()) {
						current = (AVLNode<T>) current.getRightChild();
						add(newEntry);
					} else {
						current.setRightChild(added);

					}
				}

				// balancing
				updateHeight(current);
				if (!isBalancedBoolean(current)) {
					AVLNode<T> newParent = balance(current);

					if (isBalancedBoolean(newParent) && newParent.hasLeftChild() && newParent.hasRightChild()) {

						root = newParent;

					} else {

						if (newParent.getData().compareTo(current.getData()) > 0) {

							current.setRightChild(newParent);
							updateHeight(current);

							if (!isBalancedBoolean(current)) {

								newParent = balance(current);
								root = newParent;
							}
						} else {
							current.setLeftChild(newParent);
							updateHeight(current);

							if (!isBalancedBoolean(current)) {

								newParent = balance(current);
								root = newParent;
							}

						}
					}

				}

			}

		}

		current = (AVLNode<T>) root;

		return valueToReturn;
	}

	/**
	 * Remove an entry from the tree
	 * 
	 * @param entry The entry.
	 * 
	 * @return the data in the node.
	 */
	public T remove(T entry) {

		if (this.isEmpty()) {
			throw new EmptyTreeException("Tree is empty.");
		}

		T valueToReturn = super.remove(entry);

		// balancing
		updateHeight(current);
		if (!isBalancedBoolean(current)) {
			AVLNode<T> newParent = balance(current);
			root = newParent;
		}

		return valueToReturn;

	}

	/**
	 * Method to balance the tree.
	 * 
	 * @param current the current node.
	 * @return new balanced subtree.
	 */
	private AVLNode<T> balance(AVLNode<T> current) {

		AVLNode<T> toReturn = current;
		int balance = isBalanced(current);
		if (balance < -1) {
			if (isBalanced((AVLNode<T>) current.getRightChild()) == 1) {
				toReturn = rightRotation((AVLNode<T>) current.getRightChild());
			} else {
				toReturn = leftRotation(current);
			}

		} else if (balance > 1) {
			if (isBalanced((AVLNode<T>) current.getLeftChild()) == -1) {
				toReturn = leftRotation((AVLNode<T>) current.getLeftChild());
			} else {
				toReturn = rightRotation(current);
			}

		}

		return toReturn;
	}

	/**
	 * Method to get the balance factor.
	 * 
	 * @param node current node.
	 * @return balance factor.
	 */
	private int isBalanced(AVLNode<T> node) {

		int lHeight = -1;
		int rHeight = -1;

		if (node.hasLeftChild()) {
			lHeight = node.getLeftChild().getHeight();
		}
		if (node.hasRightChild()) {
			rHeight = node.getRightChild().getHeight();
		}
		if (node.isLeaf()) {
			return 0;
		}

		return lHeight - rHeight;
	}

	/**
	 * is balanced check
	 * 
	 * @param node current node.
	 * @return whether it is in balance or not.
	 */
	private boolean isBalancedBoolean(AVLNode<T> node) {

		int lHeight = -1;
		int rHeight = -1;

		if (node.hasLeftChild()) {
			lHeight = node.getLeftChild().getHeight();
		}
		if (node.hasRightChild()) {
			rHeight = node.getRightChild().getHeight();
		}
		if (node.isLeaf()) {
			node.height = 0;
		}

		return Math.abs(lHeight - rHeight) <= 1;
	}

	/**
	 * Method to update the height as needed.
	 * 
	 * @param node the current node.
	 * 
	 */
	private void updateHeight(AVLNode<T> node) {

		Integer heightLeftSubTree = -1;
		Integer heightRightSubTree = -1;

		if (node.hasLeftChild()) {
			heightLeftSubTree = node.getLeftChild().getHeight();
		}
		if (node.hasRightChild()) {
			heightRightSubTree = node.getRightChild().getHeight();
		}

		if (node.isLeaf()) {
			node.height = 0;
		}
		node.height = Math.max(heightLeftSubTree, heightRightSubTree) + 1;

	}

	/**
	 * Single right rotation.
	 * 
	 * @param curr the unbalanced node.
	 * @return newly balanced node.
	 */
	private AVLNode<T> rightRotation(AVLNode<T> curr) {

		AVLNode<T> tmp = null;

		if (curr.hasLeftChild()) {
			tmp = (AVLNode<T>) curr.getLeftChild();
			curr.setLeftChild(tmp.getRightChild());
			tmp.setRightChild(curr);
		}

		return tmp;
	}

	/**
	 * Single left rotation.
	 * 
	 * @param curr the unbalanced node.
	 * @return newly balanced node.
	 */
	private AVLNode<T> leftRotation(AVLNode<T> curr) {

		AVLNode<T> tmp = null;

		if (curr.getHeight() >= 1) {
			tmp = (AVLNode<T>) curr.getRightChild();
			tmp.setLeftChild(curr);
			curr.setRightChild(null);
		}
		if (curr.hasRightChild()) {
			tmp = (AVLNode<T>) curr.getRightChild();
			curr.setRightChild(tmp.getLeftChild());
			tmp.setLeftChild(curr);
		}

		return tmp;
	}

	/**
	 * Double rotation right then left.
	 * 
	 * @param curr the unbalanced node.
	 * @return newly balanced node.
	 */
	private AVLNode<T> rightLeftRotation(AVLNode<T> curr) {

		curr.setRightChild(rightRotation((AVLNode<T>) curr.getRightChild()));
		return leftRotation((AVLNode<T>) curr.getRightChild());

	}

	/**
	 * Double rotation left then right.
	 * 
	 * @param curr The unbalanced node.
	 * @return newly balanced node.
	 */
	private AVLNode<T> leftRightRotation(AVLNode<T> curr) {

		curr.setLeftChild(leftRotation((AVLNode<T>) curr.getLeftChild()));
		return rightRotation((AVLNode<T>) curr.getLeftChild());
	}

	/**
	 * Used for testing.
	 * 
	 * @param args Command-line arguments supplied as an array of string objects.
	 */
	public static void main(String[] args) {

		AVLTree<Integer> tree = new AVLTree<Integer>();

		tree.add(1);
		tree.add(3);
		tree.add(2);

		System.out.println(tree.getRootData());
		System.out.println(tree.getRootNode().getLeftChild().getData());
		System.out.println(tree.getRootNode().getRightChild().getData());

		/*
		 * tree.add(20); tree.add(15); tree.add(10); tree.add(5); tree.add(0);
		 * 
		 * System.out.println(tree.getRootData());
		 * System.out.println(tree.getRootNode().getLeftChild().getData());
		 * System.out.println(tree.getRootNode().getRightChild().getData());
		 * System.out.println(tree.getRootNode().getLeftChild().getLeftChild().getData()
		 * );
		 * System.out.println(tree.getRootNode().getRightChild().getRightChild().getData
		 * ()); System.out.println("height: " + tree.getRootNode().getHeight());
		 */

		/*
		 * tree.add(12); tree.add(16); tree.add(8); tree.add(14); tree.add(10);
		 * tree.add(4); tree.add(2); tree.add(6); tree.add(5);
		 * 
		 * System.out.println(tree.getRootData());
		 * System.out.println(tree.getRootNode().getLeftChild().getData());
		 * System.out.println(tree.getRootNode().getRightChild().getData());
		 * System.out.println(tree.getRootNode().getLeftChild().getLeftChild().getData()
		 * );
		 * System.out.println(tree.getRootNode().getLeftChild().getRightChild().getData(
		 * ));
		 * System.out.println(tree.getRootNode().getRightChild().getLeftChild().getData(
		 * ));
		 * System.out.println(tree.getRootNode().getRightChild().getRightChild().getData
		 * ()); System.out.println(tree.getRootNode().getLeftChild().getRightChild().
		 * getLeftChild().getData()); System.out.println("height: " +
		 * tree.getRootNode().getHeight());
		 */

		/*
		 * tree.add(5); tree.add(4); tree.add(3); tree.add(2); tree.add(1); tree.add(0);
		 * tree.remove(0); tree.remove(1); tree.remove(2); tree.remove(3);
		 * tree.remove(4); tree.remove(5);
		 */

	}

}
