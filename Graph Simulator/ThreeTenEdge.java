import org.apache.commons.collections15.Factory;

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

import java.awt.Color;

/**
 *  This class represents an edge for the SimGUI graph. The
 *  graph class is generic, so it shouldn't assume
 *  that all edges are ThreeTenEdges.
 *  
 *  @author Katherine (Raven) Russell
 *  @author Jacob Strokus
 */
public final class ThreeTenEdge extends ThreeTenGraphComponent {
	/**
	 *  The ID for the next edge to be created. This auto-increments
	 *  with ever constructor call to this class.
	 */
	public static int edgeCount = 0;
	
	/**
	 *  The names edges should be using when generated automatically.
	 */
	private static String[] names = {""};
	
	/**
	 *  Creates a new ThreeTenNode with default settings.
	 */
	public ThreeTenEdge() {
		super(edgeCount++);
		setColor(Color.BLACK);
		if(names.length > 0) {
			setText(names[id%names.length]);
		}
	}
	
	/**
     * Creates a {@code Factory} that in turn creates an instance of this edge type.
     * @return the created factory
     */
	public static Factory<ThreeTenEdge> getFactory() { 
		return new Factory<ThreeTenEdge> () {
			public ThreeTenEdge create() {
				return new ThreeTenEdge();
			}
		};
	}
	
	/**
	 *  Loads the edge names from a file.
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

