package seliv.graphlib.directed;

import seliv.graphlib.AbstractGraph;
import seliv.graphlib.Graph;

import java.util.*;

public class DirectedGraph<V> extends AbstractGraph<V> {
    private final Map<V, Set<V>> adjacency = new HashMap<>();

    public void addVertex(V vertex) {
        adjacency.put(vertex, new HashSet<>());
    }

    public void addEdge(V vertexFrom, V vertexTo) {
        validateVertex(vertexFrom);
        validateVertex(vertexTo);
        Set<V> edgesFrom = adjacency.get(vertexFrom);
        edgesFrom.add(vertexTo);
    }

    protected void validateVertex(V vertex) throws IllegalArgumentException {
        if (!adjacency.containsKey(vertex)) {
            throw new IllegalArgumentException("Vertex " + vertex + " is not found in a graph");
        }
    }

    protected Set<V> listAdjacent(V vertex) {
        return adjacency.get(vertex);
    }

    protected Graph.Edge<V> getEdge(V vertexFrom, V vertexTo) {
        // This is not a public method so invalid vertexFrom or vertexTo are a part of incorrect implementation
        if (!adjacency.get(vertexFrom).contains(vertexTo)) { // Can throw NPE, also a part of implementation fault
            throw new IllegalStateException("There is no edge from " + vertexFrom + " to " + vertexTo + " in a graph");
        }
        return new Edge<>(vertexFrom, vertexTo);
    }

    @Override
    protected Set<V> listVertices() {
        return adjacency.keySet();
    }

    @Override
    protected Set<Graph.Edge<V>> listEdges() {
        HashSet<Graph.Edge<V>> result = new HashSet<>();
        for (Map.Entry<V, Set<V>> entry : adjacency.entrySet()) {
            for (V v : entry.getValue()) {
                result.add(new Edge<>(entry.getKey(), v));
            }
        }
        return result;
    }

    static class Edge<V> implements Graph.Edge<V> {
        final V vertexFrom;
        final V vertexTo;

        Edge(V vertexFrom, V vertexTo) {
            this.vertexFrom = vertexFrom;
            this.vertexTo = vertexTo;
        }

        @Override
        public V getFrom() {
            return vertexFrom;
        }

        @Override
        public V getTo() {
            return vertexTo;
        }

        @Override
        public String toString() {
            return "(" + vertexFrom + " -> " + vertexTo + ')';
        }
    }
}
