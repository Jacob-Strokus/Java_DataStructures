import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;

import edu.uci.ics.jung.algorithms.generators.random.ErdosRenyiGenerator;
import edu.uci.ics.jung.algorithms.generators.random.ErdosRenyiGeneratorDirected;

import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import java.util.*;

import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.geom.Ellipse2D;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *  This is the main Graphical User Interface (GUI) class.
 *  
 *  @author Katherine (Raven) Russell
 *  @author Jacob Strokus
 */
class SimGUI {
	/**
	 *  Frame for the GUI.
	 */
	private JFrame frame;
	
	/**
	 *  The graph currently being displayed.
	 */
	private Graph<ThreeTenNode, ThreeTenEdge> graph = null;
	
	/**
	 *  The panel containing the graph display.
	 */
	private VisualizationViewer<ThreeTenNode, ThreeTenEdge> visServer = null;
	
	/**
	 *  Editing model for mouse.
	 */
	private EditingModalGraphMouse<ThreeTenNode, ThreeTenEdge> gm;
	
	/**
	 *  The panel containing the step, reset, and play buttons.
	 */
	private JPanel buttonPanel = null;
	
	/**
	 *  Whether or not a simulation is currently playing with
	 *  the play button (i.e. automatically playing).
	 */
	private boolean playing = false;
	
	/**
	 *  The seed to use for the random number generator
	 *  associated with the algorithm simulation.
	 */
	private final Random rand;
	
	/**
	 *  The connection probability for nodes in the graph
	 *  when generating new graphs.
	 */
	private final double prob;
	
	/**
	 *  The number of nodes the graph starts with.
	 */
	private final int numNodes;
	
	/**
	 *  Load up the GUI.
	 *  
	 *  @param numNodes the number of nodes to generate
	 *  @param prob the connection probability for nodes
	 *  @param seed seed for the random number generator
	 */
	public SimGUI(int numNodes, double prob, int seed) {
		this.rand = new Random(seed);
		this.numNodes = numNodes;
		this.prob = prob;
		
		frame = new JFrame("Graph Simulation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 700);
		frame.getContentPane().setLayout(new FlowLayout());
		
		reset();
		makeMenu(); //needs to go after so gm is set
		
		frame.setVisible(true);
	}
	
	/**
	 *  Makes the menu for the simulation.
	 */
	public void makeMenu() {
		frame.setJMenuBar(null);
		JMenuBar menuBar = new JMenuBar();
		
		//exit option
		JMenu simMenu = new JMenu("Simulation");
		simMenu.setPreferredSize(new Dimension(80,20)); // Change the size 
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		simMenu.add(exit);
		menuBar.add(simMenu);
		
		//graph editing options
		JMenu modeMenu = gm.getModeMenu();
		modeMenu.setText("Mode");
		modeMenu.setIcon(null); // I'm using this in a main menu
		modeMenu.setPreferredSize(new Dimension(50,20)); // Change the size 
		menuBar.add(modeMenu);
		
		frame.setJMenuBar(menuBar);
	}
	
	/**
	 *  Makes the graph components.
	 */
	public void makeGraphPanel() {
		if(visServer != null) frame.remove(visServer);
		
		//Layout (ISOMLayout also looks good)
		Layout<ThreeTenNode, ThreeTenEdge> layout = new KKLayout<>(graph);
		layout.setSize(new Dimension(600,600));
		visServer = new VisualizationViewer<ThreeTenNode, ThreeTenEdge>(layout);
		visServer.setPreferredSize(new Dimension(600,600));
		
		visServer.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		RenderContext<ThreeTenNode, ThreeTenEdge> context = visServer.getRenderContext();
		
		//label edges with toString()
		context.setEdgeLabelTransformer(
			new Transformer<ThreeTenEdge,String>(){
				public String transform(ThreeTenEdge e) {
					return e.toString();
				}
			}
		);
		
		//color arrows with edge color
		context.setArrowFillPaintTransformer(
			new Transformer<ThreeTenEdge,Paint>(){
				public Paint transform(ThreeTenEdge e) {
					return e.getColor();
				}
			}
		);
		
		//color lines with edge color
		context.setEdgeDrawPaintTransformer(
			new Transformer<ThreeTenEdge,Paint>(){
				public Paint transform(ThreeTenEdge e) {
					return e.getColor();
				}
			}
		);
		
		//set edge line stroke to bolder
		context.setEdgeStrokeTransformer(
			new Transformer<ThreeTenEdge,Stroke>(){
				public Stroke transform(ThreeTenEdge e) {
					return new BasicStroke(2);
				}
			}
		);
		
		//move edge labels off the lines
		context.setLabelOffset(-5);
		
		//make nodes bigger
		context.setVertexShapeTransformer(
			new Transformer<ThreeTenNode,Shape>(){
				public Shape transform(ThreeTenNode v) {
					int s = 30;
					return new Ellipse2D.Double(-s/2.0, -s/2.0, s, s);
				}
			}
		);
		
		//label vertices with toString()
		context.setVertexLabelTransformer(
			new Transformer<ThreeTenNode,String>(){
				public String transform(ThreeTenNode v) {
					return v.toString();
				}
			}
		);
		
		//color vertices with node color
		context.setVertexFillPaintTransformer(
			new Transformer<ThreeTenNode,Paint>(){
				public Paint transform(ThreeTenNode v) {
					return v.getColor();
				}
			}
		);
		
		//Add user interactions
		gm = new EditingModalGraphMouse<>(context, ThreeTenNode.getFactory(), ThreeTenEdge.getFactory());
		gm.setMode(ModalGraphMouse.Mode.EDITING);
		visServer.setGraphMouse(gm);
		
		frame.add(visServer, 0);
		frame.revalidate();
	}
	
	/**
	 *  Makes the panel containing the step, reset, and play buttons.
	 */
	public void makeBottomButtons() {
		if(buttonPanel != null) frame.remove(buttonPanel);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		
		//reset button
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				reset();
			}
		});
		buttonPanel.add(reset);
		
		frame.add(buttonPanel, 1);
		frame.revalidate();
	}
	
	/**
	 *  Generates a new graph for the algorithm.
	 */
	public void genGraph() {
		ThreeTenNode.nodeCount = 0;
		ThreeTenEdge.edgeCount = 0;
		
		Factory<ThreeTenNode> nodeFactory = ThreeTenNode.getFactory();
		Factory<ThreeTenEdge> edgeFactory = ThreeTenEdge.getFactory();
		
		ErdosRenyiGenerator<ThreeTenNode, ThreeTenEdge> gen = new ErdosRenyiGenerator<>(
				ThreeTenGraph.<ThreeTenNode,ThreeTenEdge>getUndirectedFactory(),
				nodeFactory, edgeFactory,
				this.numNodes,this.prob
			);
		gen.setSeed(this.rand.nextInt());
		graph = gen.create();
	}
	
	/**
	 *  Load a new simulation.
	 */
	public void reset() {
		genGraph();
		makeGraphPanel();
		makeMenu();
		makeBottomButtons();
	}
	
	/**
	 *  A main method to run the simulation with GUI.
	 *  
	 *  @param args [0] = the number of nodes, [1] = the connectivity of the nodes, [2] = the seed for the alg's random number generator
	 */
	public static void main(String[] args) {
		if(args.length == 5) {
			ThreeTenNode.loadNames(args[3]);
			ThreeTenEdge.loadNames(args[4]);
		}
		
		if(args.length == 0) {
			new SimGUI(6,0.4,0);
		}
		else if(args.length == 1) {
			new SimGUI(Integer.parseInt(args[0]),0.4,0);
		}
		else if(args.length == 2) {
			new SimGUI(Integer.parseInt(args[0]),Double.parseDouble(args[1]),0);
		}
		else if(args.length == 3 || args.length == 5) {
			new SimGUI(Integer.parseInt(args[0]),Double.parseDouble(args[1]),Integer.parseInt(args[2]));
		}
		else {
			System.out.println("Call with one of the following:\njava SIMGui\njava SIMGui [numNodes]\njava SIMGui [numNodes] [connectProb]\njava SIMGui [numNodes] [connectProb] [seed]\njava SIMGui [numNodes] [connectProb] [seed] [nodeNamesFile] [edgeNamesFile]");
		}
	}
}
