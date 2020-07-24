package seliv.graphlib.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import seliv.graphlib.AbstractGraph;
import seliv.graphlib.Graph;
import seliv.graphlib.directed.DirectedGraph;
import seliv.graphlib.undirected.UndirectedGraph;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class TestInterfaceOperations {
    private static final long VERTICES_COUNT = 5;

    @ParameterizedTest
    @MethodSource("createGraphImplementation")
    public void addVertex_ValidInput_OK(AbstractGraph<Long> graph) {
        addVertices(graph);
        Set<Long> vertices = graph.listVertices();
        Assertions.assertEquals(VERTICES_COUNT, vertices.size(), "Invalid vertices count");
        for (long v = 0; v < VERTICES_COUNT; v++) {
            Assertions.assertTrue(vertices.contains(v), "Vertex " + v + " is not in graph");
        }
    }

    @ParameterizedTest
    @MethodSource("createGraphImplementation")
    public void addVertex_DuplicateVertices_OK(AbstractGraph<Long> graph) {
        addVertices(graph);
        addVertices(graph);
        Set<Long> vertices = graph.listVertices();
        Assertions.assertEquals(VERTICES_COUNT, vertices.size(), "Duplicate vertices are added");
    }

    @ParameterizedTest
    @MethodSource("createGraphImplementation")
    public void addVertex_NullVertex_ThrowsNullPointer(Graph<Long> graph) {
        Assertions.assertThrows(NullPointerException.class, () -> graph.addVertex(null));
    }

    @ParameterizedTest
    @MethodSource("createGraphImplementation")
    public void addEdge_ValidInput_OK(AbstractGraph<Long> graph, long edgesCountFactor) {
        addVertices(graph);
        createCompleteGraph(graph);
        long expected = edgesCountFactor * (VERTICES_COUNT * (VERTICES_COUNT - 1)) / 2;
        Assertions.assertEquals(expected, graph.listEdges().size(), "Invalid number of edges");
    }

    @ParameterizedTest
    @MethodSource("createGraphImplementation")
    public void addEdge_DuplicateEdges_OK(AbstractGraph<Long> graph, long edgesCountFactor) {
        addVertices(graph);
        createCompleteGraph(graph);
        createCompleteGraph(graph);
        long expected = edgesCountFactor * (VERTICES_COUNT * (VERTICES_COUNT - 1)) / 2;
        Assertions.assertEquals(expected, graph.listEdges().size(), "Invalid number of edges");
    }

    @ParameterizedTest
    @MethodSource("createGraphImplementation")
    public void addEdge_LoopEdge_ThrowsIllegalArgument(AbstractGraph<Long> graph) {
        addVertices(graph);
        Assertions.assertThrows(IllegalArgumentException.class, () -> graph.addEdge(1L, 1L));
    }

    @ParameterizedTest
    @MethodSource("createGraphImplementation")
    public void addEdge_NullVertex1_ThrowsNullPointer(AbstractGraph<Long> graph) {
        addVertices(graph);
        Assertions.assertThrows(NullPointerException.class, () -> graph.addEdge(null, 1L));
    }

    @ParameterizedTest
    @MethodSource("createGraphImplementation")
    public void addEdge_NullVertex2_ThrowsNullPointer(AbstractGraph<Long> graph) {
        addVertices(graph);
        Assertions.assertThrows(NullPointerException.class, () -> graph.addEdge(2L, null));
    }

    @ParameterizedTest
    @MethodSource("createGraphImplementation")
    public void addEdge_MissingVertex1_ThrowsIllegalArgument(AbstractGraph<Long> graph) {
        addVertices(graph);
        Assertions.assertThrows(IllegalArgumentException.class, () -> graph.addEdge(1L, 10L));
    }

    @ParameterizedTest
    @MethodSource("createGraphImplementation")
    public void addEdge_MissingVertex2_ThrowsIllegalArgument(AbstractGraph<Long> graph) {
        addVertices(graph);
        Assertions.assertThrows(IllegalArgumentException.class, () -> graph.addEdge(20L, 2L));
    }

    @ParameterizedTest
    @MethodSource("createGraphImplementation")
    public void getPath_CompleteGraph_OK(AbstractGraph<Long> graph) {
        addVertices(graph);
        createCompleteGraph(graph);
        Set<Long> vertices = graph.listVertices();
        for (Long v1 : vertices) {
            for (Long v2 : vertices) {
                List<Graph.Edge<Long>> path = graph.getPath(v1, v2);
                int expected = v1.equals(v2) ? 0 : 1;
                Assertions.assertEquals(expected, path.size(),
                        "Path length from " + v1 + " to " + v2 + " is incorrect");
            }
        }
    }

    @Test
    public void getPath_SparseDirectedGraph_OK() {
        AbstractGraph<Long> graph = new DirectedGraph<>();
        addVertices(graph);
        createSparseGraph(graph);
        for (long v1 = 0; v1 < VERTICES_COUNT; v1++) {
            for (long v2 = 0; v2 < VERTICES_COUNT; v2++) {
                List<Graph.Edge<Long>> path = graph.getPath(v1, v2);
                if (v2 >= v1) {
                    Assertions.assertEquals(v2 - v1, path.size(),
                            "Path length from " + v1 + " to " + v2 + " is incorrect");
                } else {
                    Assertions.assertNull(path, "Path from " + v1 + " to " + v2 + " should not exist");
                }
            }
        }
    }

    @Test
    public void getPath_SparseUndirectedGraph_OK() {
        AbstractGraph<Long> graph = new UndirectedGraph<>();
        addVertices(graph);
        createSparseGraph(graph);
        for (long v1 = 0; v1 < VERTICES_COUNT; v1++) {
            for (long v2 = 0; v2 < VERTICES_COUNT; v2++) {
                List<Graph.Edge<Long>> path = graph.getPath(v1, v2);
                long expected = Math.abs(v2 - v1);
                Assertions.assertEquals(expected, path.size(),
                        "Path length from " + v1 + " to " + v2 + " is incorrect");
            }
        }
    }

    private void addVertices(AbstractGraph<Long> graph) {
        for (long v = 0; v < VERTICES_COUNT; v++) {
            graph.addVertex(v);
        }
    }

    private void createCompleteGraph(AbstractGraph<Long> graph) {
        Set<Long> vertices = graph.listVertices();
        for (Long v1 : vertices) {
            for (Long v2 : vertices) {
                if (!v1.equals(v2)) {
                    graph.addEdge(v1, v2);
                }
            }
        }
    }

    private void createSparseGraph(AbstractGraph<Long> graph) {
        for (long v = 1; v < graph.listVertices().size(); v++) {
            graph.addEdge(v - 1, v);
        }
    }

    private static Stream<Arguments> createGraphImplementation() {
        return Stream.of(
                Arguments.of(new DirectedGraph<>(), 2),
                Arguments.of(new UndirectedGraph<>(), 1)
        );
    }
}
