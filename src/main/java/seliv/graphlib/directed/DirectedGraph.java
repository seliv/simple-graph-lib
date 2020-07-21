package seliv.graphlib.directed;

import seliv.graphlib.Graph;

import java.util.*;

public class DirectedGraph<V> implements Graph<V> {
    private final Map<V, Set<V>> edges = new HashMap<>();

    public void addVertex(V vertex) {
        edges.put(vertex, new HashSet<>());
    }

    public void addEdge(V vertexFrom, V vertexTo) {
        if (!containsVertex(vertexFrom)) {
            throw new IllegalArgumentException("From vertex " + vertexFrom + " is not found in a graph");
        }
        if (!containsVertex(vertexTo)) {
            throw new IllegalArgumentException("To vertex " + vertexTo + " is not found in a graph");
        }
        Set<V> edgesFrom = edges.get(vertexFrom);
        edgesFrom.add(vertexTo);
    }

    public List<Graph.Edge<V>> getPath(V vertexFrom, V vertexTo) {
        // Fake implementation
        Edge<V> edge = new Edge<>(vertexFrom, vertexTo);
        return Collections.singletonList(edge);
    }

    private boolean containsVertex(V vertex) {
        return edges.containsKey(vertex);
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
