package seliv.graphlib;

import java.util.*;

public abstract class AbstractGraph<V> implements Graph<V> {
    /**
     * @return A list of edges comprising a path (possibly empty if source equals destination) or <code>null</code> if destination is unreachable from source.
     */
    public List<Edge<V>> getPath(V source, V destination) {
        validateVertex(source);
        validateVertex(destination);
        
        // BFS implementation

        Queue<V> queue = new LinkedList<>();
        // For each vertex holds the previously visited one; doubles as a set of visited vertices.
        Map<V, V> visitedFrom = new HashMap<>();

        queue.add(source);
        visitedFrom.put(source, null);

        while (!queue.isEmpty()) {
            V current = queue.remove();
            if (destination.equals(current)) {
                return backTrace(visitedFrom, current);
            }
            Set<V> adjacent = listAdjacent(current);
            for (V v : adjacent) {
                if (!visitedFrom.containsKey(v)) {
                    visitedFrom.put(v, current);
                    queue.add(v);
                }
            }
        }
        // There is no path to reach destination - we may have a disconnected graph
        return null;
    }

    /**
     * Reconstructs the path basing on the accumulated knowledge of direct moves between vertices.
     */
    private List<Edge<V>> backTrace(Map<V, V> visitedFrom, V current) {
        // We are adding edges backwards, it's done easier with LinkedList.
        // Additionally the most straightforward further use of a path is traversing from the beginning to the end.
        LinkedList<Edge<V>> result = new LinkedList<>();
        V previous = visitedFrom.get(current);
        while (previous != null) {
            Edge<V> edge = getEdge(previous, current);
            result.addFirst(edge);

            final V oneMoreStepBack = visitedFrom.get(previous);
            current = previous;
            previous = oneMoreStepBack;
        }
        return result;
    }

    /**
     * Checks if the vertex belongs to the graph.
     * @throws IllegalArgumentException is this vertex does not belong to the graph.
     */
    protected abstract void validateVertex(V vertex) throws IllegalArgumentException;

    protected abstract Set<V> listAdjacent(V vertex);

    protected abstract Graph.Edge<V> getEdge(V vertexFrom, V vertexTo);
}
