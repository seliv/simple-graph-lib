package seliv.graphlib.test;

import org.junit.Test;
import seliv.graphlib.Graph;
import seliv.graphlib.directed.DirectedGraph;

import java.util.List;

public class Test1 {
    @Test
    public void test1() {
        Graph<String> g = new DirectedGraph<>();

        g.addVertex("One");
        g.addVertex("Two");
        g.addVertex("Three");

        g.addEdge("One", "Two");
        g.addEdge("Two", "Three");
        g.addEdge("Three", "One");

        List<Graph.Edge<String>> path = g.getPath("Three", "Two");
        System.out.println("path = " + path);
    }
}
