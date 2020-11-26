/**
 * This class extends the BinaryNode class of trees. An AVLNode contains a
 * height as an attribute. The height attribute indicates the height of a
 * subtree rooted at that specific node. Main Method used for testing.
 * 
 * @author Jacob Strokus
 *
 */
class AVLNode<T> extends BinaryNode<T> {

	/**
	 * The height of a subtree rooted at that specific node.
	 */
	protected Integer height;

	/**
	 * Constructor to create AVLNodes.
	 */
	public AVLNode() {

		super(null);
		height = -1;

	}

	/**
	 * Constructor to create AVLNodes with root entry.
	 * 
	 * @param data data for root node.
	 */
	public AVLNode(T data) {

		super(data);
		height = this.getHeight();

	}

	/**
	 * Constructor to create AVLNodes with root entry and the children nodes.
	 * 
	 * @param data      data for root node.
	 * @param leftNode  the left node.
	 * @param rightNode the right node.
	 */
	public AVLNode(T data, AVLNode<T> leftNode, AVLNode<T> rightNode) {

		super(data, leftNode, rightNode);
		height = this.getHeight();
	}

	/**
	 * Set the left child.
	 */
	public void setLeftChild(AVLNode<T> newLeftChild) {
		super.setLeftChild(newLeftChild);

	}

	/**
	 * Set the right child.
	 */
	public void setRightChild(AVLNode<T> newRightChild) {
		super.setRightChild(newRightChild);

	}

	/**
	 * @return the height
	 */
	public int getHeight() {

		if (this.hasLeftChild() && this.hasRightChild()) { // has both children.

			height = 1 + Math.max(this.getRightChild().getHeight(), this.getLeftChild().getHeight());

		} else if (this.hasLeftChild() && !this.hasRightChild()) { // has only left child.

			height = 1 + this.getLeftChild().getHeight();

		} else if (!this.hasLeftChild() && this.hasRightChild()) { // has only right child.

			height = 1 + this.getRightChild().getHeight();

		} else if (this.getData() != null) { // no children and tree is not null.

			height = 0;
		}

		return height;

	}

	/**
	 * Used for debugging.
	 * 
	 * @param args Command-line arguments supplied as an array of string objects.
	 */
	public static void main(String[] args) {

		AVLNode<Integer> node1 = new AVLNode<>(1, null, null);
		AVLNode<Integer> node2 = new AVLNode<>(2);
		AVLNode<Integer> node3 = new AVLNode<>(2, node1, node2);
		AVLNode<Integer> node4 = new AVLNode<>(4, null, null);
		AVLNode<Integer> node5 = new AVLNode<>(5, node3, null);
		AVLNode<Integer> node6 = new AVLNode<>(6, node5, node3);
		AVLNode<Integer> node7 = new AVLNode<>();
		AVLNode<Integer> node8 = new AVLNode<>(8, null, node6);

		AVLNode<Integer> node10 = new AVLNode<>(10, node1, null);
		AVLNode<Integer> node11 = new AVLNode<>(11, null, null);
		AVLNode<Integer> node12 = new AVLNode<>(12, node10, node11);
		AVLNode<Integer> node13 = new AVLNode<>(13, null, null);

		System.out.println(node1.getHeight()); // 0
		System.out.println(node2.getHeight()); // 0
		System.out.println(node3.getHeight() + "\n"); // 1
		System.out.println(node5.getHeight()); // 2

		System.out.println(node6.getHeight()); // 3
		System.out.println(node7.getHeight() + "\n"); // -1
		System.out.println(node8.getHeight()); // 4

		node2.setLeftChild(node1);
		System.out.println(node2.getHeight()); // 1

		node7.setLeftChild(node2);
		System.out.println(node7.getHeight() + "\n"); // 2

		node13.setRightChild(node12);
		System.out.println(node13.getHeight()); // 3
		System.out.println(node10.getHeight()); // 1
		System.out.println(node11.getHeight()); // 0

		System.out.println(node4.getHeight());
	}

}
