import org.apache.commons.collections15.Factory;

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *  This class represents a node for the SimGUI graph. Remember
 *  that your graph class is generic, so it shouldn't assume
 *  that all nodes are ThreeTenNodes.
 *  
 *  @author Katherine (Raven) Russell
  *@author Jacob Strokus
 */
class ThreeTenNode extends ThreeTenGraphComponent {
	/**
	 *  The ID for the next node to be created. This auto-increments
	 *  with ever constructor call to this class.
	 */
	public static int nodeCount = 0;
	
	/**
	 *  The names nodes should be using when generated automatically.
	 */
	private static String[] names = {""};
	
	/**
	 *  Creates a new ThreeTenNode with default settings.
	 */
	public ThreeTenNode() {
		super(nodeCount++);
		if(names.length > 0) {
			setText(names[id%names.length]);
		}
	}
	
	/**
     * Creates a {@code Factory} that in turn creates an instance of this node type.
     * @return the created factory
	 */
	public static Factory<ThreeTenNode> getFactory() { 
		return new Factory<ThreeTenNode> () {
			public ThreeTenNode create() {
				return new ThreeTenNode();
			}
		};
	}
	
	/**
	 *  Loads the node names from a file.
	 *  @param filename the file containing the names to use
	 */
	public static void loadNames(String filename) {
		ArrayList<String> fileContents = new ArrayList<>();
		try(Scanner s = new Scanner(new File(filename))) {
			while(s.hasNextLine()) {
				fileContents.add(s.nextLine());
			}
			if(fileContents.size() > 0) {
				names = fileContents.toArray(new String[0]);
			}
		}
		catch(Exception e) { }
	}
}
