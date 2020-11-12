import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;

import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.graph.util.EdgeType;

import org.apache.commons.collections15.Factory;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collection;

//Uncomment the following lines if you want to use the java.util version
//import java.util.HashMap; //or use ThreeTenHashMap!
//import java.util.HashSet; //or use ThreeTenHashSet!

import java.util.NoSuchElementException;

/**
 * This class creates a graph objects to power the simulator. This class extends
 * the ThreeTenGraphComponent for vertices and extends the
 * ThreeTenGraphComponent class for edges. This class implements the Graph and
 * undirectedGraph interfaces. Main method used for testing purposes.
 * 
 * @author Jacob Strokus
 *
 * @param <V> generic type for Vertices.
 * @param <E> generic type for Edges.
 */
class ThreeTenGraph<V extends ThreeTenGraphComponent, E extends ThreeTenGraphComponent>
		implements Graph<V, E>, UndirectedGraph<V, E> {

	/**
	 * Integer representing the number of vertices in the graph.
	 */
	private int numVertices;

	/**
	 * Integer representing the number of edges in the graph.
	 */
	private int numEdges;

	/**
	 * Mapping for graph representing an adjacency list.
	 */
	private HashMap<V, E> adjacencyList;

	/**
	 * Creates a new graph. Initializing all appropriate instance variables.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenGraph() {

		adjacencyList = new HashMap<V, E>();
		numVertices = numEdges = 0;

	}

	/**
	 * Returns a view of all edges in this graph. In general, this obeys the
	 * Collection contract, and therefore makes no guarantees about the ordering of
	 * the vertices within the set.
	 * 
	 * @return a Collection view of all edges in this graph.
	 */
	public Collection<E> getEdges() {

		System.out.println("getEdges()");

		Collection<E> edges = new ArrayList<>();

		for (E edge : adjacencyList.values()) {
			if (edge == null) {
				continue;
			} else {
				edges.add(edge);
			}

		}

		return edges;
	}

	/**
	 * Returns a view of all vertices in this graph. In general, this obeys the
	 * Collection contract, and therefore makes no guarantees about the ordering of
	 * the vertices within the set.
	 * 
	 * @return a Collection view of all vertices in this graph.
	 */
	public Collection<V> getVertices() {

		Collection<V> vertices = new ArrayList<>();

		for (V vertex : adjacencyList.keySet()) {
			vertices.add(vertex);
		}

		return vertices;
	}

	/**
	 * Returns the number of edges in this graph.
	 * 
	 * @return the number of edges in this graph.
	 */
	public int getEdgeCount() {

		return numEdges;
	}

	/**
	 * Returns the number of vertices in this graph.
	 * 
	 * @return the number of vertices in this graph.
	 */
	public int getVertexCount() {

		return numVertices;
	}

	/**
	 * Returns the collection of vertices in this graph which are connected to edge.
	 * Note that for some graph types there are guarantees about the size of this
	 * collection (i.e., some graphs contain edges that have exactly two endpoints,
	 * which may or may not be distinct). Implementations for those graph types may
	 * provide alternate methods that provide more convenient access to the
	 * vertices.
	 * 
	 * @param edge the edge whose incident vertices are to be returned.
	 * @return the collection of vertices which are connected to edge, or null if
	 *         edge is not present.
	 */
	public Collection<V> getIncidentVertices(E edge) {

		Collection<V> incidentVertices = new ArrayList<>();

		for (V vertex : adjacencyList.keySet()) {

			if (!(adjacencyList.get(vertex) == null) && adjacencyList.get(vertex).equals(edge)) {

				incidentVertices.add(vertex);
			}
		}

		return incidentVertices;
	}

	/**
	 * Returns the collection of vertices which are connected to vertex via any
	 * edges in this graph. If vertex is connected to itself with a self-loop, then
	 * it will be included in the collection returned.
	 * 
	 * @param vertex the vertex whose neighbors are to be returned.
	 * @return the collection of vertices which are connected to vertex, or null if
	 *         vertex is not present.
	 */
	public Collection<V> getNeighbors(V vertex) {

		Collection<V> neighbors = new ArrayList<>();

		if (adjacencyList.get(vertex) != null) { // make sure the vertex has an edge ( and therefore at least 1 neighbor)
													

			for (E edge : adjacencyList.values()) {

				if (edge == null) {
					continue;
				}
				if (adjacencyList.get(vertex).equals(edge)) { // if the edge that is connected to the vertex passed in equals the one in the list
															

					// we know that it is a neighbor so we need to
					// get the vertex connected by the edge
					for (V v : adjacencyList.keySet()) {

						if (!(adjacencyList.get(v) == null) && adjacencyList.get(v).equals(edge) && !v.equals(vertex)) {

							if (!neighbors.contains(v)) { // if not already in neighbor list, add it

								neighbors.add(v);
							}

						}
					}

				}
			}

		} else {
			neighbors = null;
		}

		return neighbors;
	}

	/**
	 * Returns the collection of edges in this graph which are connected to vertex.
	 * 
	 * @param vertex the vertex whose incident edges are to be returned.
	 * @return the collection of edges which are connected to vertex, or null if
	 *         vertex is not present.
	 */
	public Collection<E> getIncidentEdges(V vertex) {

		Collection<E> incidentEdges = new ArrayList<>();

		for (V v : adjacencyList.keySet()) {

			if (!(adjacencyList.get(vertex) == null) && adjacencyList.get(vertex).equals(adjacencyList.get(v))) {

				E connectingEdge = adjacencyList.get(v);

				if (!(incidentEdges.contains(connectingEdge))) {

					incidentEdges.add(connectingEdge);
				}

			}
		}

		return incidentEdges;
	}

	/**
	 * Returns an edge that connects v1 to v2. If this edge is not uniquely defined
	 * (that is, if the graph contains more than one edge connecting v1 to v2), any
	 * of these edges may be returned. findEdgeSet(v1, v2) may be used to return all
	 * such edges. Returns null if either of the following is true:
	 * <ul>
	 * <li/>v1 is not connected to v2.
	 * <li/>either v1 or v2 are not present in this graph.
	 * </ul>
	 *
	 * <p><b>Note</b>: for purposes of this method, v1 is only considered to be
	 * connected to v2 via a given <i>directed</i> edge e if v1 == e.getSource() &&
	 * v2 == e.getDest() evaluates to true. (v1 and v2 are connected by an
	 * undirected edge u if u is incident to both v1 and v2.).
	 * 
	 * @param v1 The source vertex.
	 * @param v2 the destination vertex.
	 * 
	 * 
	 * @return an edge that connects v1 to v2, or null if no such edge exists (or
	 *         either vertex is not present).
	 * 
	 */
	public E findEdge(V v1, V v2) {

		E edgeToReturn = null;

		if (!adjacencyList.containsKey(v1) || !adjacencyList.containsKey(v2)) { // if one or both vertices are not in graph return null
																			

		} else {
			E e1 = adjacencyList.get(v1);
			E e2 = adjacencyList.get(v2);

			if (!(adjacencyList.get(v1) == null) && this.isIncident(v1, e2) && !(adjacencyList.get(v2) == null)
					&& this.isIncident(v2, e1)) { // if 'u' is incident to both v1 and v2

				edgeToReturn = e1; // they are connected
			}
		}

		return edgeToReturn;
	}

	/**
	 * Adds edge e to this graph such that it connects vertex v1 to v2. If this
	 * graph does not contain v1, v2, or both, implementations may choose to either
	 * silently add the vertices to the graph or throw an IllegalArgumentException.
	 * If this graph assigns edge types to its edges, the edge type of e will be the
	 * default for this graph. See Hypergraph.addEdge() for a listing of possible
	 * reasons for failure. In addition, this should fail if the vertices or edge
	 * violates any given restrictions (such as invalid IDs). Equivalent to
	 * addEdge(e, new Pair/<V/>(v1, v2)).
	 * 
	 * @param e  the edge to be added.
	 * @param v1 the first vertex to be connected.
	 * @param v2 the second vertex to be connected.
	 * @return true if the add is successful, false otherwise.
	 *
	 *
	 */
	public boolean addEdge(E e, V v1, V v2) {

		if (!adjacencyList.containsKey(v1) || !adjacencyList.containsKey(v2)) {
			throw new IllegalArgumentException("One or both of the vertices do not exist in the graph.");
		}

		adjacencyList.put(v1, e);
		adjacencyList.put(v2, e);
		numEdges++;

		return true;
	}

	/**
	 * Adds vertex to this graph. Fails if vertex is null or already in the graph.
	 * Also fails if the vertex violates and constraints given in the project (such
	 * as ID restrictions).
	 * 
	 * @param vertex the vertex to add.
	 * @return true if the add is successful, and false otherwise.
	 * @throws IllegalArgumentException if vertex is null.
	 */
	public boolean addVertex(V vertex) {

		System.out.println("AddVertex()");

		if (vertex == null) {

			throw new IllegalArgumentException("Vertex is null.");

		} else if (adjacencyList.containsKey(vertex)) {

			return false;
		}

		adjacencyList.put(vertex, adjacencyList.get(vertex));
		numVertices++;

		return true;
	}

	/**
	 * Removes edge from this graph. Fails if edge is null, or is otherwise not an
	 * element of this graph.
	 * 
	 * @param edge the edge to remove.
	 * @return true if the removal is successful, false otherwise.
	 */
	public boolean removeEdge(E edge) {

		if (edge == null || !adjacencyList.containsValue(edge)) {
			return false;
		}
		Collection<V> keysForRemove = new ArrayList<>();

		for (V vertex : adjacencyList.keySet()) {

			if (vertex == null) {
				continue;
			}

			if (!(adjacencyList.get(vertex) == null) && adjacencyList.get(vertex).equals(edge)) {

				keysForRemove.add(vertex);

			}

		}

		for (V vert : keysForRemove) {
			adjacencyList.remove(vert, edge);
		}
		numEdges--;

		return true;
	}

	/**
	 * Removes vertex from this graph. As a side effect, removes any edges e
	 * incident to vertex if the removal of vertex would cause e to be incident to
	 * an illegal number of vertices. (Thus, for example, incident hyperedges are
	 * not removed, but incident edges--which must be connected to a vertex at both
	 * endpoints--are removed.).
	 * 
	 * <p>Fails under the following circumstances:
	 * <ul>
	 * <li/>vertex is not an element of this graph.
	 * <li/>vertex is null.
	 * </ul>
	 * 
	 * @param vertex the vertex to remove.
	 * @return true if the removal is successful, false otherwise.
	 */
	public boolean removeVertex(V vertex) {
		if (vertex == null || !adjacencyList.containsKey(vertex)) {
			return false;
		}

		adjacencyList.remove(vertex);

		for (V vertices : adjacencyList.keySet()) {

			E edge = adjacencyList.get(vertices);

			if (this.isIncident(vertex, edge)) {

				adjacencyList.remove(vertex, edge);
			}
		}

		numVertices--;

		return true;
	}

	/**
	 * toString() Method.
	 * 
	 * @return String representation of data.
	 */
	public String toString() {
		// you may edit this to make string representations of your
		// graph for testing
		return super.toString();
	}

	/**
	 * Controls the flow of the program. Used for testing.
	 * 
	 * @param args Command-line arguments supplied as an Array of String Objects.
	 */
	public static void main(String[] args) {

		/**
		 * Nested anonymous inner-class that creates Person Objects that extend the
		 * ThreeTenGraphComponent class.
		 * 
		 * @author Jacob Strokus
		 *
		 */
		class Person extends ThreeTenGraphComponent {

			/**
			 * Constructor to create Person Objects.
			 * 
			 * @param id Integer representing a person's unique id.
			 */
			public Person(int id) {
				super(id);
			}
		}

		/**
		 * Nested anonymous inner-class that creates Cat Objects that extend the
		 * ThreeTenGraphComponent class.
		 * 
		 * @author Jacob Strokus
		 *
		 */
		class Cat extends ThreeTenGraphComponent {

			/**
			 * Constructor to create Cat Objects.
			 * 
			 * @param id Integer representing a person's unique id.
			 */
			public Cat(int id) {
				super(id);
			}
		}

		/**
		 * Nested anonymous inner-class that creates IntComponent Objects that extend
		 * the ThreeTenGraphComponent class.
		 * 
		 * @author Jacob Strokus
		 *
		 */
		class IntComponent extends ThreeTenGraphComponent {

			/**
			 * Constructor to create IntComponent Objects.
			 * 
			 * @param id Integer representing a person's unique id.
			 */
			public IntComponent(int id) {
				super(id);
			}
		}

		// constructs a graph

		ThreeTenGraph<Person, Cat> graph1 = new ThreeTenGraph<>();

		for (int i = 0; i < 3; i++) {
			graph1.addVertex(new Person(i));
		}

		for (Person p : graph1.getVertices()) {
			graph1.addEdge(new Cat((int) (Math.random() * 100)), p, p);
		}

		if (graph1.getVertexCount() == 3 && graph1.getEdgeCount() == 3) {
			System.out.println("Yay 1");
		}

		// create a set of nodes and edges to test with
		IntComponent[] nodes = { new IntComponent(1), new IntComponent(26), new IntComponent(2), new IntComponent(25),
			new IntComponent(3), new IntComponent(24), new IntComponent(4), new IntComponent(23),
			new IntComponent(5), new IntComponent(22) };

		IntComponent[] edges = { new IntComponent(10000000), new IntComponent(4), new IntComponent(Integer.MAX_VALUE),
			new IntComponent(Integer.MIN_VALUE), new IntComponent(500), new IntComponent(600000) };

		// constructs a graph
		ThreeTenGraph<IntComponent, IntComponent> graph2 = new ThreeTenGraph<>();
		for (IntComponent n : nodes) {
			graph2.addVertex(n);
		}
		graph2.addEdge(edges[0], nodes[0], nodes[1]);
		graph2.addEdge(edges[1], nodes[2], nodes[2]);
		graph2.addEdge(edges[2], nodes[3], nodes[4]);
		graph2.addEdge(edges[3], nodes[6], nodes[7]);
		graph2.addEdge(edges[4], nodes[8], nodes[9]);
		graph2.addEdge(edges[5], nodes[9], nodes[0]);

		if (graph2.containsVertex(new IntComponent(1)) && graph2.containsEdge(new IntComponent(10000000))) {
			System.out.println("Yay 2");
		}

		for (Person p : graph1.getVertices()) {
			if (graph1.removeVertex(p) == true) {
				System.out.println("Vertx successfully removed in graph1: " + p);
			}
		}
		System.out.println("\n-------------------\n");

		System.out.println("getNeighbor() check: " + graph2.getNeighbors(nodes[0]));

		if (graph2.isNeighbor(nodes[0], nodes[9])) {
			System.out.println("\nisNeighbor() check: Yay 3\n");
		}
		if (graph2.isIncident(nodes[9], edges[5])) {
			System.out.println("Yay 4");
		}
			
		if (graph2.isNeighbor(nodes[4], nodes[3])) {
			System.out.println("isNeighbor() check: Yay 5");
		}

		System.out.println("\n-------------------\n");
		System.out.println("getVertices() check: \n\n" + graph2.getVertices());
		System.out.println("# of vertices: " + graph2.getVertexCount());
		if (graph2.getVertexCount() == 10) {
			System.out.println("Yay 6");
		}

		System.out.println("\n-------------------\n");
		System.out.println("getEdges() check: \n\n" + graph2.getEdges());
		System.out.println("number of edges: " + graph2.getEdgeCount());
		System.out.println("\n-------------------\n");

		for (IntComponent edge : graph2.getEdges()) {
			if (graph2.removeEdge(edge)) {
				System.out.println("Removing edge: " + edge);
			}
		}
		System.out.println("\nCheck if edges were removed (should be null) : " + graph2.getEdges());
		System.out.println("number of edges: " + graph2.getEdgeCount());
		System.out.println("\n-------------------\n");

		// constructs a graph
		ThreeTenGraph<IntComponent, IntComponent> graph3 = new ThreeTenGraph<>();
		for (IntComponent n : nodes) {
			graph3.addVertex(n);
		}
		graph3.addEdge(edges[0], nodes[0], nodes[1]);
		graph3.addEdge(edges[1], nodes[2], nodes[2]);
		graph3.addEdge(edges[2], nodes[3], nodes[4]);
		graph3.addEdge(edges[3], nodes[6], nodes[7]);
		graph3.addEdge(edges[4], nodes[8], nodes[9]);
		graph3.addEdge(edges[5], nodes[9], nodes[0]);

		System.out.println("\nCheck getIncidentVertices for edge[2]: " + graph3.getIncidentVertices(edges[2]));

		System.out.println("\nCheck findEdge() for nodes[0] and nodes[9]: " + graph3.findEdge(nodes[0], nodes[9]));

		if (graph3.addEdge(edges[0], nodes[1], nodes[1])) {
			System.out.println("Yay 7! self looping!");
		}
	
		 

	}

	/**
	 * Returns true if this graph's vertex collection contains vertex. Equivalent to
	 * getVertices().contains(vertex).
	 * 
	 * @param vertex the vertex whose presence is being queried.
	 * @return true iff this graph contains a vertex vertex.
	 */
	public boolean containsVertex(V vertex) {
		return getVertices().contains(vertex);
	}

	/**
	 * Returns true if this graph's edge collection contains edge. Equivalent to
	 * getEdges().contains(edge).
	 * 
	 * @param edge the edge whose presence is being queried.
	 * @return true iff this graph contains an edge edge.
	 */
	public boolean containsEdge(E edge) {
		return getEdges().contains(edge);
	}

	/**
	 * Returns true if vertex and edge are incident to each other. Equivalent to
	 * getIncidentEdges(vertex).contains(edge) and to
	 * getIncidentVertices(edge).contains(vertex).
	 * 
	 * @param vertex Vertex in th graph.
	 * @param edge   Edge in the graph.
	 * @return true if vertex and edge are incident to each other.
	 */
	public boolean isIncident(V vertex, E edge) {
		return getIncidentEdges(vertex).contains(edge);
	}

	/**
	 * Returns true if v1 and v2 share an incident edge. Equivalent to
	 * getNeighbors(v1).contains(v2).
	 * 
	 * @param v1 the first vertex to test.
	 * @param v2 the second vertex to test.
	 * @return true if v1 and v2 share an incident edge.
	 */
	public boolean isNeighbor(V v1, V v2) {
		return getNeighbors(v1).contains(v2);
	}

	/**
	 * Returns true if v1 is a predecessor of v2 in this graph. Equivalent to
	 * v1.getPredecessors().contains(v2).
	 * 
	 * @param v1 the first vertex to be queried.
	 * @param v2 the second vertex to be queried.
	 * @return true if v1 is a predecessor of v2, and false otherwise.
	 */
	public boolean isPredecessor(V v1, V v2) {
		return getPredecessors(v1).contains(v2);
	}

	/**
	 * Returns true if v1 is a successor of v2 in this graph. Equivalent to
	 * v1.getSuccessors().contains(v2).
	 * 
	 * @param v1 the first vertex to be queried.
	 * @param v2 the second vertex to be queried.
	 * @return true if v1 is a successor of v2, and false otherwise.
	 */
	public boolean isSuccessor(V v1, V v2) {
		return getSuccessors(v1).contains(v2);
	}

	/**
	 * Returns the number of edges incident to vertex. Special cases of interest:
	 * <ul>
	 * <li/>Incident self-loops are counted once.
	 * <li>If there is only one edge that connects this vertex to each of its
	 * neighbors (and vice versa), then the value returned will also be equal to the
	 * number of neighbors that this vertex has (that is, the output of
	 * getNeighborCount).
	 * <li>If the graph is directed, then the value returned will be the sum of this
	 * vertex's indegree (the number of edges whose destination is this vertex) and
	 * its outdegree (the number of edges whose source is this vertex), minus the
	 * number of incident self-loops (to avoid double-counting).
	 * </ul>
	 *
	 * <p>Equivalent to getIncidentEdges(vertex).size().
	 * 
	 * @param vertex the vertex whose degree is to be returned.
	 * @return the degree of this node.
	 *
	 */
	public int degree(V vertex) {
		return getIncidentEdges(vertex).size();
	}

	/**
	 * Returns the number of vertices that are adjacent to vertex (that is, the
	 * number of vertices that are incident to edges in vertex's incident edge set).
	 * 
	 * <p>Equivalent to getNeighbors(vertex).size().
	 * 
	 * @param vertex the vertex whose neighbor count is to be returned.
	 * @return the number of neighboring vertices.
	 */
	public int getNeighborCount(V vertex) {
		return getNeighbors(vertex).size();
	}

	/**
	 * Returns the number of incoming edges incident to vertex. Equivalent to
	 * getInEdges(vertex).size().
	 * 
	 * @param vertex the vertex whose indegree is to be calculated.
	 * @return the number of incoming edges incident to vertex.
	 */
	public int inDegree(V vertex) {
		return getInEdges(vertex).size();
	}

	/**
	 * Returns the number of outgoing edges incident to vertex. Equivalent to
	 * getOutEdges(vertex).size().
	 * 
	 * @param vertex the vertex whose outdegree is to be calculated.
	 * @return the number of outgoing edges incident to vertex.
	 */
	public int outDegree(V vertex) {
		return getOutEdges(vertex).size();
	}

	/**
	 * Returns the number of predecessors that vertex has in this graph. Equivalent
	 * to vertex.getPredecessors().size().
	 * 
	 * @param vertex the vertex whose predecessor count is to be returned.
	 * @return the number of predecessors that vertex has in this graph.
	 */
	public int getPredecessorCount(V vertex) {
		return getPredecessors(vertex).size();
	}

	/**
	 * Returns the number of successors that vertex has in this graph. Equivalent to
	 * vertex.getSuccessors().size().
	 * 
	 * @param vertex the vertex whose successor count is to be returned.
	 * @return the number of successors that vertex has in this graph.
	 */
	public int getSuccessorCount(V vertex) {
		return getSuccessors(vertex).size();
	}

	/**
	 * Returns the vertex at the other end of edge from vertex. (That is, returns
	 * the vertex incident to edge which is not vertex.).
	 * 
	 * @param vertex the vertex to be queried.
	 * @param edge   the edge to be queried.
	 * @return the vertex at the other end of edge from vertex.
	 */
	public V getOpposite(V vertex, E edge) {
		Pair<V> p = getEndpoints(edge);
		if (p.getFirst().equals(vertex)) {
			return p.getSecond();
		} else {
			return p.getFirst();
		}
	}

	/**
	 * Returns all edges that connects v1 to v2. If this edge is not uniquely
	 * defined (that is, if the graph contains more than one edge connecting v1 to
	 * v2), any of these edges may be returned. findEdgeSet(v1, v2) may be used to
	 * return all such edges. Returns null if v1 is not connected to v2. <br/>
	 * Returns an empty collection if either v1 or v2 are not present in this graph.
	 * 
	 * <p><b>Note</b>: for purposes of this method, v1 is only considered to be
	 * connected to v2 via a given <i>directed</i> edge d if v1 == d.getSource() &&
	 * v2 == d.getDest() evaluates to true. (v1 and v2 are connected by an
	 * undirected edge u if u is incident to both v1 and v2.).
	 * 
	 *@param v1 The source Vertex.
	 *@param v2 The destination Vertex.
	 *
	 * @return a collection containing all edges that connect v1 to v2, or null if
	 *         either vertex is not present.
	 *
	 */
	public Collection<E> findEdgeSet(V v1, V v2) {
		E edge = findEdge(v1, v2);
		if (edge == null) {
			return null;
		}

		ArrayList<E> ret = new ArrayList<>();
		ret.add(edge);
		return ret;

	}

	/**
	 * Returns true if vertex is the source of edge. Equivalent to
	 * getSource(edge).equals(vertex).
	 * 
	 * @param vertex the vertex to be queried.
	 * @param edge   the edge to be queried.
	 * @return true iff vertex is the source of edge.
	 */
	public boolean isSource(V vertex, E edge) {
		return getSource(edge).equals(vertex);
	}

	/**
	 * Returns true if vertex is the destination of edge. Equivalent to
	 * getDest(edge).equals(vertex).
	 * 
	 * @param vertex the vertex to be queried.
	 * @param edge   the edge to be queried.
	 * @return true iff vertex is the destination of edge.
	 */
	public boolean isDest(V vertex, E edge) {
		return getDest(edge).equals(vertex);
	}

	/**
	 * Adds edge e to this graph such that it connects vertex v1 to v2. Equivalent
	 * to addEdge(e, new Pair/<V/>(v1, v2)). If this graph does not contain v1, v2, or
	 * both, implementations may choose to either silently add the vertices to the
	 * graph or throw an IllegalArgumentException. If edgeType is not legal for this
	 * graph, this method will throw IllegalArgumentException. See
	 * Hypergraph.addEdge() for a listing of possible reasons for failure.
	 * 
	 * @param e        the edge to be added.
	 * @param v1       the first vertex to be connected.
	 * @param v2       the second vertex to be connected.
	 * @param edgeType the type to be assigned to the edge.
	 * @return true if the add is successful, false otherwise.
	 *
	 * 
	 */
	public boolean addEdge(E e, V v1, V v2, EdgeType edgeType) {
		// NOTE: Only undirected edges allowed

		if (edgeType == EdgeType.DIRECTED) {
			throw new IllegalArgumentException();
		}

		return addEdge(e, v1, v2);
	}

	/**
	 * Adds edge to this graph. Fails under the following circumstances:
	 * <ul>
	 * <li/>edge is already an element of the graph
	 * <li/>either edge or vertices is null
	 * <li/>vertices has the wrong number of vertices for the graph type
	 * <li/>vertices are already connected by another edge in this graph, and this
	 * graph does not accept parallel edges
	 * </ul>
	 * 
	 * @param edge     The edge to be added.
	 * @param vertices Collection of vertices in the graph.
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null, or if a
	 *                                  different vertex set in this graph is
	 *                                  already connected by edge, or if vertices
	 *                                  are not a legal vertex set for edge.
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(E edge, Collection<? extends V> vertices) {
		if (edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}

		V[] vs = (V[]) vertices.toArray();
		return addEdge(edge, vs[0], vs[1]);
	}

	/**
	 * Adds edge to this graph with type edgeType. Fails under the following
	 * circumstances:
	 * <ul>
	 * <li/>edge is already an element of the graph
	 * <li/>either edge or vertices is null
	 * <li/>vertices has the wrong number of vertices for the graph type
	 * <li/>vertices are already connected by another edge in this graph, and this
	 * graph does not accept parallel edges
	 * <li/>edgeType is not legal for this graph
	 * </ul>
	 * 
	 * @param edge     Edge to be added.
	 * @param vertices Collection of vertices in graph.
	 * @param edgeType Emuneration for the type of edge.
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null, or if a
	 *                                  different vertex set in this graph is
	 *                                  already connected by edge, or if vertices
	 *                                  are not a legal vertex set for edge.
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(E edge, Collection<? extends V> vertices, EdgeType edgeType) {
		if (edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}

		V[] vs = (V[]) vertices.toArray();
		return addEdge(edge, vs[0], vs[1], edgeType);
	}

	/**
	 * Returns the number of edges of type edgeType in this graph.
	 * 
	 * @param edgeType the type of edge for which the count is to be returned.
	 * @return the number of edges of type edgeType in this graph.
	 */
	public int getEdgeCount(EdgeType edgeType) {
		if (edgeType == EdgeType.UNDIRECTED) {
			return getEdgeCount();
		}
		return 0;
	}

	/**
	 * Returns the collection of edges in this graph which are of type edgeType.
	 * 
	 * @param edgeType the type of edges to be returned.
	 * @return the collection of edges which are of type edgeType, or null if the
	 *         graph does not accept edges of this type.
	 * @see EdgeType
	 */
	public Collection<E> getEdges(EdgeType edgeType) {
		if (edgeType == EdgeType.UNDIRECTED) {
			return getEdges();
		}
		return null;
	}

	/**
	 * Returns the number of vertices that are incident to edge. For hyperedges,
	 * this can be any nonnegative integer; for edges this must be 2 (or 1 if
	 * self-loops are permitted).
	 * 
	 * <p>Equivalent to getIncidentVertices(edge).size().
	 * 
	 * @param edge the edge whose incident vertex count is to be returned.
	 * @return the number of vertices that are incident to edge.
	 */
	public int getIncidentCount(E edge) {
		return getIncidentVertices(edge).size();
	}

	/**
	 * If directedEdge is a directed edge in this graph, returns the source;
	 * otherwise returns null. The source of a directed edge d is defined to be the
	 * vertex for which d is an outgoing edge. directedEdge is guaranteed to be a
	 * directed edge if its EdgeType is DIRECTED.
	 * 
	 * @param directedEdge The directed edge in the graph.
	 * @return the source of directedEdge if it is a directed edge in this graph,
	 *         or null otherwise.
	 */
	public V getSource(E directedEdge) {
		return null;
	}

	/**
	 * If directedEdge is a directed edge in this graph, returns the destination;
	 * otherwise returns null. The destination of a directed edge d is defined to be
	 * the vertex incident to d for which d is an incoming edge. directedEdge is
	 * guaranteed to be a directed edge if its EdgeType is DIRECTED.
	 * 
	 * @param directedEdge The directed edge in the graph.
	 * @return the destination of directedEdge if it is a directed edge in this
	 *         graph, or null otherwise.
	 */
	public V getDest(E directedEdge) {
		return null;
	}

	/**
	 * Returns a Collection view of the predecessors of vertex in this graph. A
	 * predecessor of vertex is defined as a vertex v which is connected to vertex
	 * by an edge e, where e is an outgoing edge of v and an incoming edge of
	 * vertex.
	 * 
	 * @param vertex the vertex whose predecessors are to be returned.
	 * @return a Collection view of the predecessors of vertex in this graph.
	 */
	public Collection<V> getPredecessors(V vertex) {
		return getNeighbors(vertex);
	}

	/**
	 * Returns a Collection view of the successors of vertex in this graph. A
	 * successor of vertex is defined as a vertex v which is connected to vertex by
	 * an edge e, where e is an incoming edge of v and an outgoing edge of vertex.
	 * 
	 * @param vertex the vertex whose predecessors are to be returned.
	 * @return a Collection view of the successors of vertex in this graph.
	 */
	public Collection<V> getSuccessors(V vertex) {
		return getNeighbors(vertex);
	}

	/**
	 * Returns a Collection view of the incoming edges incident to vertex in this
	 * graph.
	 * 
	 * @param vertex the vertex whose incoming edges are to be returned.
	 * @return a Collection view of the incoming edges incident to vertex in this
	 *         graph.
	 */
	public Collection<E> getInEdges(V vertex) {
		return getIncidentEdges(vertex);
	}

	/**
	 * Returns a Collection view of the outgoing edges incident to vertex in this
	 * graph.
	 * 
	 * @param vertex the vertex whose outgoing edges are to be returned.
	 * @return a Collection view of the outgoing edges incident to vertex in this
	 *         graph.
	 */
	public Collection<E> getOutEdges(V vertex) {
		return getIncidentEdges(vertex);
	}

	/**
	 * Returns the endpoints of edge as a Pair/<V/>.
	 * 
	 * @param edge the edge whose endpoints are to be returned.
	 * @return the endpoints (incident vertices) of edge.
	 */
	@SuppressWarnings("unchecked")
	public Pair<V> getEndpoints(E edge) {
		Object[] ends = getIncidentVertices(edge).toArray();
		return new Pair<>((V) ends[0], (V) ends[1]);
	}

	/**
	 * Returns a {@code Factory} that creates an instance of this graph type.
	 * 
	 * @param <V> the vertex type for the graph factory.
	 * @param <E> the edge type for the graph factory.
	 * 
	 * @return {@code Factory} that creates an instance of this graph type.
	 */
	public static <V extends ThreeTenGraphComponent, E extends ThreeTenGraphComponent> Factory<Graph<V, E>> getFactory() {
		return new Factory<Graph<V, E>>() {

			/**
			 * Method to create graphs.
			 * 
			 * @return new graph.
			 */
			public Graph<V, E> create() {
				return new ThreeTenGraph<>();
			}
		};
	}

	/**
	 * Returns a {@code Factory} that creates an instance of this graph type.
	 * 
	 * @param <V> the vertex type for the graph factory.
	 * @param <E> the edge type for the graph factory.
	 * 
	 * @return new graph factory.
	 */
	public static <V extends ThreeTenGraphComponent, E extends ThreeTenGraphComponent> Factory<UndirectedGraph<V, E>> getUndirectedFactory() {
		return new Factory<UndirectedGraph<V, E>>() {

			/**
			 * Method to create undirectedGraphs.
			 * 
			 * @return new undirected graph.
			 */
			public UndirectedGraph<V, E> create() {
				return new ThreeTenGraph<>();
			}
		};
	}

	/**
	 * Returns the edge type of edge in this graph.
	 * 
	 * @param edge the edge to be evaluated.
	 * @return the EdgeType of edge, or null if edge has no defined type.
	 */
	public EdgeType getEdgeType(E edge) {
		return EdgeType.UNDIRECTED;
	}

	/**
	 * Returns the default edge type for this graph.
	 * 
	 * @return the default edge type for this graph.
	 */
	public EdgeType getDefaultEdgeType() {
		return EdgeType.UNDIRECTED;
	}
}
