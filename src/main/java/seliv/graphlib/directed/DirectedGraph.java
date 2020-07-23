package seliv.graphlib.directed;

import seliv.graphlib.AbstractGraph;
import seliv.graphlib.Graph;

import java.util.*;
import java.util.stream.Collectors;

public class DirectedGraph<V> extends AbstractGraph<V> {
    private final Map<V, Set<Graph.Edge<V>>> adjacency = new HashMap<>();

    public void addVertex(V vertex) {
        if (vertex == null) {
            throw new NullPointerException("null vertices are not supported");
        }
        adjacency.put(vertex, new HashSet<>());
    }

    public void addEdge(V vertexFrom, V vertexTo) {
        validateVertex(vertexFrom);
        validateVertex(vertexTo);
        if (vertexFrom.equals(vertexTo)) {
            throw new IllegalArgumentException("Loops are not supported");
        }
        Set<Graph.Edge<V>> edgesFrom = adjacency.get(vertexFrom);
        edgesFrom.add(createEdge(vertexFrom, vertexTo));
    }

    protected Graph.Edge<V> createEdge(V vertexFrom, V vertexTo) {
        return new Edge<>(vertexFrom, vertexTo);
    }

    protected void validateVertex(V vertex) throws IllegalArgumentException {
        if (vertex == null) {
            throw new NullPointerException("null vertices are not supported");
        }
        if (!adjacency.containsKey(vertex)) {
            throw new IllegalArgumentException("Vertex " + vertex + " is not found in a graph");
        }
    }

    public Set<V> listAdjacent(V vertex) {
        return adjacency.get(vertex)
                .stream()
                .map(Graph.Edge::getTo)
                .collect(Collectors.toSet());
    }

    protected Graph.Edge<V> getEdge(V vertexFrom, V vertexTo) {
        validateVertex(vertexFrom);
        validateVertex(vertexTo);
        return adjacency.get(vertexFrom)
                .stream()
                .filter(e -> e.getTo().equals(vertexTo))
                .findFirst()
                .orElseThrow(); // A non-existing edge at this point is a part of incorrect implementation
    }

    @Override
    public Set<V> listVertices() {
        return adjacency.keySet();
    }

    @Override
    public Set<Graph.Edge<V>> listEdges() {
        return adjacency.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    static class Edge<V> implements Graph.Edge<V> {
        private final V vertexFrom;
        private final V vertexTo;

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
