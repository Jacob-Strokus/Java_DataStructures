import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Blueprint class to create CovidHealthBuilder Objects.
 * 
 * @author Jacob Strokus
 *
 */
public class CovidHealthBuilder {

	/**
	 * A decision tree data structure that contains the data. It has a data type of
	 * DecisionTreeInterface. This tree will be initialized by reading the content
	 * of a file recursively.
	 */
	DecisionTreeInterface<String> healthTree;

	/**
	 * Node to be instantiated in Constructor. Will end up being the root node.
	 */
	BinaryNode<String> node;

	/**
	 * Constructor accepts the file name that contains the content of a tree (as
	 * specified in readData()), builds, and initializes the healthTree using a
	 * recursive method buildTree().
	 * 
	 * @param filename String literal representing the name of a file.
	 */
	public CovidHealthBuilder(String filename) {
		int index = 0;
		ArrayList<String> data = readData(filename);
		BinaryNode<String> tree = buildTree(data, node, index);
		if (tree == null) {
			throw new EmptyTreeException("tree is empty.");
		}
		healthTree = new DecisionTree<>(tree.getData());
	}

	/**
	 * This method accepts the file name that contains contents of a tree. The
	 * method returns an ArrayList that contains all the contents of the file.
	 * 
	 * @param file String literal representing the name of a file.
	 * @return ArrayList of String literals.
	 */
	public ArrayList<String> readData(String file) {

		ArrayList<String> dataSet = new ArrayList<>();
		File fileToRead = new File(file);
		Scanner scan = null;
		try {
			scan = new Scanner(fileToRead);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				if (line.length() == 0) {
					return dataSet;
				} else {
					String[] parts = line.split(",");
					for (String s : parts)
						dataSet.add(s);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		} finally {
			scan.close();
		}

		return dataSet;

	}

	/**
	 * This method builds a binary decision tree recursively. It accepts the
	 * ArrayList returned by the readData method, a binary node that represents a
	 * node in a tree and the index of the elements in the ArrayList.
	 * 
	 * @param dataSet ArrayList of String literals representing the data set.
	 * @param node    Node to be set.
	 * @param index   Integer representing the correct index.
	 * @return BinaryNode of type String for the root node of the tree.
	 */
	public BinaryNode<String> buildTree(ArrayList<String> dataSet, BinaryNode<String> node, int index) {

		// base case

		if (index < dataSet.size()) {
			node = new BinaryNode<>(dataSet.get(index));
			if (node.getData().equalsIgnoreCase("null")) {
				return null;
			}

			// set left node
			node.setLeftChild(buildTree(dataSet, node.getLeftChild(), 2 * index + 1));

			// set right now
			node.setRightChild(buildTree(dataSet, node.getRightChild(), 2 * index + 2));

		}

		
		return node;

	}

	/**
	 * Method to get the healthTree.
	 * 
	 * @return the healthTree.
	 */
	protected DecisionTreeInterface<String> getHealthTree() {
		return healthTree;
	}

	/**
	 * Method to set the healthTree.
	 * 
	 * @param healthTree the healthTree to set.
	 */
	protected void setHealthTree(DecisionTreeInterface<String> healthTree) {
		this.healthTree = healthTree;
	}

	/**
	 * This method asks a series of questions based on the tree initialized during
	 * the instantiation of the class.
	 */
	public void decide() {

		Scanner scan = new Scanner(System.in);
		if (healthTree == null || healthTree.isEmpty()) {

			throw new EmptyTreeException("Tree is empty.");

		} else {

			if (!healthTree.isAnswer()) {

				System.out.println(healthTree.getCurrentData());

				String input = scan.nextLine();

				if (input.equalsIgnoreCase("yes")) {

					healthTree.moveToYes();
					decide();

				} else {

					healthTree.moveToNo();
					decide();
				}
			} else {

				System.out.println(healthTree.getCurrentData());
				System.out.println("Satisfied with my intelligence?\n");
				String input = scan.nextLine();

				if (input.equalsIgnoreCase("yes")) {

				} else
					learn();
			}
		}

	}

	/**
	 * This method is going to handle if the user is not satisfied with the output
	 * of the decide() method.
	 */
	public void learn() {

		Scanner scan = new Scanner(System.in);

		System.out.println("What should be the answer? ");
		String answer = scan.nextLine();

		System.out.println("Give me a question whose answer is yes for " + healthTree.getCurrentNode() + " but no for "
				+ healthTree.getCurrentNode() + 1);
		String question = scan.nextLine();

		updateTree(question, answer, "Someething goes here but I don't know what it is.");

	}

	/**
	 * Method to update the tree with new question and their respective answers.
	 * 
	 * @param question String representing the new question to be added.
	 * @param noAns    String representing the no answer.
	 * @param yesAns   String representing the yes answer.
	 */
	public void updateTree(String question, String noAns, String yesAns) {

		healthTree.getCurrentNode().setData(question);
		healthTree.setResponses(noAns, yesAns);

	}

}
