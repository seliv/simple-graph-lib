package seliv.graphlib.undirected;

import seliv.graphlib.Graph;
import seliv.graphlib.directed.DirectedGraph;

import java.util.*;

public class UndirectedGraph<V> extends DirectedGraph<V> {
    public void addEdge(V vertexFrom, V vertexTo) {
        super.addEdge(vertexFrom, vertexTo);
        super.addEdge(vertexTo, vertexFrom);
    }

    @Override
    protected Graph.Edge<V> createEdge(V vertexFrom, V vertexTo) {
        return new Edge<>(vertexFrom, vertexTo);
    }

    static class Edge<V> implements Graph.Edge<V> {
        private final V vertex1;
        private final V vertex2;

        // Unordered set of vertices for comparison
        private final Set<V> vertices = new HashSet<>();

        Edge(V vertex1, V vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            vertices.add(vertex1);
            vertices.add(vertex2);
        }

        @Override
        public V getFrom() {
            return vertex1;
        }

        @Override
        public V getTo() {
            return vertex2;
        }

        @Override
        public String toString() {
            return "(" + getFrom() + " - " + getTo()+ ')';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return vertices.equals(((Edge<?>)o).vertices);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertices);
        }
    }
}
