/**
 * A class that implements the ADT binary search tree by extending BinaryTree.
 * 
 * @author Jacob Strokus
 * 
 */
public class BinarySearchTree<T extends Comparable<? super T>> extends BinaryTree<T> implements SearchTreeInterface<T> {

	/**
	 * Default constructor.
	 */
	public BinarySearchTree() {
		super();
	}

	/**
	 * Constructor to create BinarySearchTree
	 * 
	 * @param rootEntry the root node of the tree.
	 */
	public BinarySearchTree(T rootEntry) {
		super();
		setRootNode(new BinaryNode<T>(rootEntry));
	}

	/**
	 * Disable setTree as the BST has an ordering property.
	 */
	public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method to get an entry from the tree.
	 * 
	 * @param anEntry data to be looked up.
	 */
	public T getEntry(T anEntry) {
		return findEntry(getRootNode(), anEntry);
	}

	/**
	 * Find and entry in the tree.
	 * 
	 * @param rootNode the root node.
	 * @param anEntry  the data.
	 * @return the data if found.
	 */
	private T findEntry(BinaryNode<T> rootNode, T anEntry) {
		T result = null;

		if (rootNode != null) {
			T rootEntry = rootNode.getData();

			if (anEntry.equals(rootEntry))
				result = rootEntry;
			else if (anEntry.compareTo(rootEntry) < 0)
				result = findEntry(rootNode.getLeftChild(), anEntry);
			else
				result = findEntry(rootNode.getRightChild(), anEntry);
		}

		return result;
	}

	/**
	 * Check if an entry is in the tree.
	 * 
	 * @param entry the data to be checked.
	 * 
	 * @return boolean whether the data entry was found.
	 */
	public boolean contains(T entry) {
		return getEntry(entry) != null;
	}

	/**
	 * Add a new node.
	 * 
	 * @param data to be added.
	 */
	public T add(T newEntry) {
		T result = null;

		if (isEmpty())
			setRootNode(new BinaryNode<>(newEntry));
		else
			result = addEntry(getRootNode(), newEntry);

		return result;
	}

	/**
	 * Adds anEntry to the nonempty subtree rooted at rootNode.
	 * 
	 * @param rootNode the root node.
	 * @param anEntry  the data.
	 * @return the data added
	 */
	private T addEntry(BinaryNode<T> rootNode, T anEntry) {
		// Assertion: rootNode != null
		T result = null;
		int comparison = anEntry.compareTo(rootNode.getData());

		if (comparison == 0) {
			result = rootNode.getData();
			rootNode.setData(anEntry);
		} else if (comparison < 0) {
			if (rootNode.hasLeftChild())
				result = addEntry(rootNode.getLeftChild(), anEntry);
			else
				rootNode.setLeftChild(new BinaryNode<>(anEntry));
		} else {
			// Assertion: comparison > 0

			if (rootNode.hasRightChild())
				result = addEntry(rootNode.getRightChild(), anEntry);
			else
				rootNode.setRightChild(new BinaryNode<>(anEntry));
		}

		return result;
	}

	/**
	 * remove an entry from the tree.
	 * 
	 * @param anEntry data to be removed.
	 */
	public T remove(T anEntry) {
		ReturnObject oldEntry = new ReturnObject(null);
		BinaryNode<T> newRoot = removeEntry(getRootNode(), anEntry, oldEntry);
		setRootNode(newRoot);

		return oldEntry.get();
	}

	/**
	 * Removes an entry from the tree rooted at a given node.
	 * 
	 * @param rootNode A reference to the root of a tree.
	 * @param anEntry  The object to be removed.
	 * @param oldEntry An object whose data field is null.
	 * @return The root node of the resulting tree; if anEntry matches an entry in
	 *         the tree, oldEntry's data field is the entry that was removed from
	 *         the tree; otherwise it is null.
	 */
	private BinaryNode<T> removeEntry(BinaryNode<T> rootNode, T anEntry, ReturnObject oldEntry) {
		if (rootNode != null) {
			T rootData = rootNode.getData();
			int comparison = anEntry.compareTo(rootData);

			if (comparison == 0) // anEntry == root entry
			{
				oldEntry.set(rootData);
				rootNode = removeFromRoot(rootNode);
			} else if (comparison < 0) // anEntry < root entry
			{
				BinaryNode<T> leftChild = rootNode.getLeftChild();
				BinaryNode<T> subtreeRoot = removeEntry(leftChild, anEntry, oldEntry);
				rootNode.setLeftChild(subtreeRoot);
			} else // anEntry > root entry
			{
				BinaryNode<T> rightChild = rootNode.getRightChild();
				// A different way of coding than for left child:
				rootNode.setRightChild(removeEntry(rightChild, anEntry, oldEntry));
			}
		}

		return rootNode;
	}

	/**
	 * Removes the entry in a given root node of a subtree.
	 * 
	 * @param rootNode The root node of the subtree.
	 * @return the root node of the revised subtree.
	 */
	private BinaryNode<T> removeFromRoot(BinaryNode<T> rootNode) {
		// Case 1: rootNode has two children
		if (rootNode.hasLeftChild() && rootNode.hasRightChild()) {
			// Find node with largest entry in left subtree
			BinaryNode<T> leftSubtreeRoot = rootNode.getLeftChild();
			BinaryNode<T> largestNode = findLargest(leftSubtreeRoot);

			// Replace entry in root
			rootNode.setData(largestNode.getData());

			// Remove node with largest entry in left subtree
			rootNode.setLeftChild(removeLargest(leftSubtreeRoot));
		} // end if

		// Case 2: rootNode has at most one child
		else if (rootNode.hasRightChild())
			rootNode = rootNode.getRightChild();
		else
			rootNode = rootNode.getLeftChild();

		// Assertion: If rootNode was a leaf, it is now null

		return rootNode;
	} // end removeFromRoot

	/**
	 * Finds the node containing the largest entry in a given tree.
	 * 
	 * @param rootNode The root node of the tree.
	 * @return the node containing the largest entry in the tree.
	 */
	private BinaryNode<T> findLargest(BinaryNode<T> rootNode) {
		if (rootNode.hasRightChild())
			rootNode = findLargest(rootNode.getRightChild());

		return rootNode;
	}

	/**
	 * Removes the node containing the largest entry in a given tree.
	 * 
	 * @param rootNode the root node of the tree.
	 * @return the root node of the revised tree.
	 */
	private BinaryNode<T> removeLargest(BinaryNode<T> rootNode) {
		if (rootNode.hasRightChild()) {
			BinaryNode<T> rightChild = rootNode.getRightChild();
			rightChild = removeLargest(rightChild);
			rootNode.setRightChild(rightChild);
		} else
			rootNode = rootNode.getLeftChild();

		return rootNode;
	}

	/**
	 * Anonymous inner class to support the Binary Search Tree class.
	 * 
	 * @author Jacob Strokus
	 *
	 */
	private class ReturnObject {

		/**
		 * the data.
		 */
		private T item;

		/**
		 * Constructor to create ReturnObject objects
		 * 
		 * @param entry the data to be set.
		 */
		private ReturnObject(T entry) {
			item = entry;
		}

		/**
		 * get the data.
		 * 
		 * @return the data.
		 */
		public T get() {
			return item;
		}

		/**
		 * set the data.
		 * 
		 * @param entry the data.
		 */
		public void set(T entry) {
			item = entry;
		}
	}
}
