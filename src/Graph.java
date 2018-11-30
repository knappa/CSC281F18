import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * A class to encapsulate an unoriented, unweighted Graphs of moderate size. (self-loops allowed)
 * The graph is backed by an auto-resizing adjacency matrix, so things may get a little memory-intensive
 * when the number of vertices is large.
 * <p>
 * Created by knappa on 11/30/18.
 *
 * @author <a href="mailto:knapp@american.edu">Adam Knapp</a>
 * @version 0.1
 */
public class Graph {

    private int numVertices;
    private String[] vertexIds;
    private HashMap<String, Integer> vertexIdToIndexMap;
    private boolean[][] adjacencyMatrix;

    /**
     * Constructor for an empty graph.
     */
    public Graph() {
        numVertices = 0;
        final int initialCapacity = 16;
        vertexIds = new String[initialCapacity];
        adjacencyMatrix = new boolean[initialCapacity][initialCapacity];
        vertexIdToIndexMap = new HashMap<>();
    }

    /**
     * Unit tests for Graph class
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        Graph graph = new Graph();

        System.out.println("empty graph:");
        System.out.println(graph);

        graph.addVertex("DC");
        graph.addVertex("MD");
        graph.addVertex("VA");
        graph.addVertex("DE");
        graph.addVertex("WV");

        System.out.println("graph with vertices, no edges");
        System.out.println(graph);

        try {
            graph.addVertex("DC");
        } catch (IllegalArgumentException e) {
            System.out.println("double adding fails as expected");
        }

        graph.addEdge("DC", "MD");
        graph.addEdge("DC", "VA");
        graph.addEdge("MD", "VA");
        graph.addEdge("DE", "MD");
        graph.addEdge("WV", "VA");
        graph.addEdge("WV", "MD");

        System.out.println("Graph with edges");
        System.out.println(graph);


        System.out.println("check presence of edges");

        System.out.println("neighbors of DC");
        System.out.println(graph.getNeighbors("DC"));

        System.out.println("neighbors of MD");
        System.out.println(graph.getNeighbors("MD"));

        System.out.println("neighbors of VA");
        System.out.println(graph.getNeighbors("VA"));

        System.out.println("neighbors of WV");
        System.out.println(graph.getNeighbors("WV"));

        System.out.println(graph.areNeighbors("DC", "MD"));
        System.out.println(graph.areNeighbors("MD", "DC"));

        System.out.println(graph.areNeighbors("DC", "VA"));
        System.out.println(graph.areNeighbors("VA", "DC"));

        System.out.println(graph.areNeighbors("DE", "MD"));
        System.out.println(graph.areNeighbors("MD", "DE"));

        System.out.println(graph.areNeighbors("WV", "VA"));
        System.out.println(graph.areNeighbors("VA", "WV"));

        System.out.println(graph.areNeighbors("WV", "MD"));
        System.out.println(graph.areNeighbors("MD", "WV"));

        System.out.println("check absence of neighbors");
        System.out.println(graph.areNeighbors("WV", "DE"));
        System.out.println(graph.areNeighbors("DE", "WV"));

        System.out.println(graph.areNeighbors("WV", "DC"));
        System.out.println(graph.areNeighbors("DC", "WV"));

    }

    /**
     * Double the capacity of the backing arrays.
     */
    private void doubleCapacity() {
        final int oldCapacity = vertexIds.length;
        final int newCapacity = oldCapacity * 2;

        // create the new arrays
        String[] doubledVertexIds = new String[newCapacity];
        boolean[][] doubledAdjacencyMatrix = new boolean[newCapacity][newCapacity];

        // copy the old data over
        System.arraycopy(vertexIds, 0, doubledVertexIds, 0, oldCapacity);
        for (int i = 0; i < oldCapacity; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, doubledAdjacencyMatrix[i], 0, oldCapacity);
        }

        // move to new arrays
        vertexIds = doubledVertexIds;
        adjacencyMatrix = doubledAdjacencyMatrix;
    }

    /**
     * Add a vertex to the graph. All vertices must have unique string identifiers.
     *
     * @param vertexName string identifier for the vertex
     */
    public void addVertex(String vertexName) {

        if (vertexIdToIndexMap.containsKey(vertexName)) {
            throw new IllegalArgumentException("graph already contains a vertex with this identifier");
        }

        if (numVertices >= vertexIds.length) {
            doubleCapacity();
        }

        // numVertices will be the id/index of the newly added vertex
        vertexIds[numVertices] = vertexName;
        vertexIdToIndexMap.put(vertexName, numVertices);
        numVertices++;
    }

    /**
     * Adds an edge to the graph. Edges are undirected, and loops are allowed.
     *
     * @param vertexOne the identifier of a vertex in this graph
     * @param vertexTwo the identifier of a vertex in this graph
     */
    public void addEdge(String vertexOne, String vertexTwo) {

        final int vertexOneIndex = vertexIdToIndexMap.getOrDefault(vertexOne, -1);
        final int vertexTwoIndex = vertexIdToIndexMap.getOrDefault(vertexTwo, -1);
        if (vertexOneIndex == -1 || vertexTwoIndex == -1) {
            throw new IllegalArgumentException("Vertex not part of this graph");
        }

        adjacencyMatrix[vertexOneIndex][vertexTwoIndex] = true;
        adjacencyMatrix[vertexTwoIndex][vertexOneIndex] = true;

    }

    /**
     * Check to see if two vertices are neighbors
     *
     * @param vertexOne the identifier of a vertex in this graph
     * @param vertexTwo the identifier of a vertex in this graph
     */
    public boolean areNeighbors(String vertexOne, String vertexTwo) {

        final int vertexOneIndex = vertexIdToIndexMap.getOrDefault(vertexOne, -1);
        final int vertexTwoIndex = vertexIdToIndexMap.getOrDefault(vertexTwo, -1);
        if (vertexOneIndex == -1 || vertexTwoIndex == -1) {
            throw new IllegalArgumentException("Vertex not part of this graph");
        }

        return adjacencyMatrix[vertexOneIndex][vertexTwoIndex];
    }

    /**
     * Computes the neighbors of a vertex in the graph. Note that, if a vertex has a
     * self-link, it will be counted as being its own neighbor.
     *
     * @param vertexId the identifier of a vertex in this graph
     * @return a set of the identifiers of neighbors of the given vertex
     */
    public Set<String> getNeighbors(String vertexId) {

        // get the index of the vertex

        final int vertexIndex = vertexIdToIndexMap.getOrDefault(vertexId, -1);
        if (vertexIndex == -1) {
            throw new IllegalArgumentException("Vertex not part of this graph");
        }

        // find the neighbors

        HashSet<String> neighbors = new HashSet<>();
        final int capacity = vertexIds.length;
        final boolean[] neighborArray = adjacencyMatrix[vertexIndex];

        for (int i = 0; i < capacity; i++)
            if (neighborArray[i]) neighbors.add(vertexIds[i]);

        return neighbors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (numVertices == 0) {
            return "Graph()";
        }

        sb.append("Graph( ");

        sb.append("Vertices( ");

        StringJoiner stringJoinerVertices = new StringJoiner(" , ");
        for (int i = 0; i < numVertices; i++) {
            stringJoinerVertices.add(vertexIds[i]);
        }
        sb.append(stringJoinerVertices);

        sb.append(" ), Edges( ");

        StringJoiner stringJoinerEdges = new StringJoiner(" , ");
        for (int i = 0; i < numVertices; i++) {
            for (int j = i; j < numVertices; j++) {
                if (adjacencyMatrix[i][j]) {
                    stringJoinerEdges.add("(" + vertexIds[i] + "<->" + vertexIds[j] + ")");
                }
            }
        }
        sb.append(stringJoinerEdges);

        sb.append(" ) )");
        return sb.toString();
    }

}
