package seliv.graphlib.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seliv.graphlib.Graph;
import seliv.graphlib.directed.DirectedGraph;

import java.util.List;

public class TestTypes {
    private static final Logger logger = LoggerFactory.getLogger(TestTypes.class);

    @Test
    public void testStrings() {
        Graph<String> g = new DirectedGraph<>();

        g.addVertex("One");
        g.addVertex("Two");
        g.addVertex("Three");

        g.addEdge("One", "Two");
        g.addEdge("Two", "Three");
        g.addEdge("Three", "One");

        List<Graph.Edge<String>> path = g.getPath("Three", "Two");
        logger.info("Path = " + path);
        Assertions.assertEquals(2, path.size());
    }

    @Test
    public void testIntegers() {
        Graph<Integer> g = new DirectedGraph<>();

        g.addVertex(-10);
        g.addVertex(0);
        g.addVertex(10);

        g.addEdge(-10, 0);
        g.addEdge(0, 10);
        g.addEdge(10, -10);

        List<Graph.Edge<Integer>> path = g.getPath(10, 0);
        logger.info("Path = " + path);
        Assertions.assertEquals(2, path.size());
    }

    @Test
    public void testDoubles() {
        Graph<Double> g = new DirectedGraph<>();

        g.addVertex(Math.PI);
        g.addVertex(Double.NaN);
        g.addVertex(0.25);

        g.addEdge(Math.PI, 0.0 / 0.0);
        g.addEdge(Double.NEGATIVE_INFINITY * 0.0, 1.0 / 4.0);
        g.addEdge(25.0 / 100.0, Math.PI);

        List<Graph.Edge<Double>> path = g.getPath(0.05 + 0.2, Double.NEGATIVE_INFINITY / Double.POSITIVE_INFINITY);
        logger.info("Path = " + path);
        Assertions.assertEquals(2, path.size());
    }

    @Test
    public void testObjects() {
        Graph<Object> g = new DirectedGraph<>();

        Object o1 = new Object();
        Object o2 = Exception.class;
        Object o3 = this;

        g.addVertex(o1);
        g.addVertex(o2);
        g.addVertex(o3);

        g.addEdge(o1, o2);
        g.addEdge(o2, o3);
        g.addEdge(o3, o1);

        List<Graph.Edge<Object>> path = g.getPath(o3, o2);
        logger.info("Path = " + path);
        Assertions.assertEquals(2, path.size());
    }
}
