package seliv.graphlib;

import java.util.List;

/**
 * A top-level graph interface of the library with the API from requirements.
 */
public interface Graph<V> {
    /**
     * Adds a vertex to a graph. Forbids <code>null</code> values, silently ignores duplicates.
     */
    void addVertex(V vertex);

    /**
     * Adds an edge to a graph. The edge is either directed or undirected depending on the implementation.
     * Forbids <code>null</code> vertices and vertices not previously added to the graph, silently ignores duplicates.
     */
    void addEdge(V vertexFrom, V vertexTo);


    /**
     * Calculates a path from <code>vertexFrom</code> to <code>vertexTo</code>.
     * Forbids <code>null</code> vertices and vertices not previously added to the graph.
     * The default implementation utilizes the BFS algorithm thus providing an optimal path for unweighted graph.
     * A path from a vertex to itself is zero-length, not a non-trivial path with at least one edge.
     *
     * @return A list of {@link Edge} comprising a path or <code>null</code> if the destination is unreachable from source.
     */
    List<Edge<V>> getPath(V vertexFrom, V vertexTo);

    /**
     * An abstract representation of an edge in a graph.
     */
    interface Edge<V> {
        /**
         * @return source vertex of an edge from a directed graph or one of the vertices from an undirected graph.
         */
        V getFrom();

        /**
         * @return destination vertex of an edge from a directed graph or the other vertex from an undirected graph.
         */
        V getTo();
    }
}
