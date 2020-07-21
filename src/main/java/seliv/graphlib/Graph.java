package seliv.graphlib;

import java.util.List;

public interface Graph<V> {
    void addVertex(V vertex);
    void addEdge(V vertexFrom, V vertexTo);
    List<Edge<V>> getPath(V vertexFrom, V vertexTo);

    interface Edge<V> {
        V getFrom();
        V getTo();
    }
}
