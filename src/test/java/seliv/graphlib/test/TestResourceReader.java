package seliv.graphlib.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seliv.graphlib.Graph;
import seliv.graphlib.directed.DirectedGraph;
import seliv.graphlib.util.GraphReader;

import java.io.InputStream;
import java.util.List;

public class TestResourceReader {
    private static final Logger logger = LoggerFactory.getLogger(TestResourceReader.class);

    private static InputStream readResourceAsStream(String path) {
        return TestResourceReader.class.getResourceAsStream(path);
    }

    @Test
    public void testResourceReading() {
        Graph<Long> g = new DirectedGraph<>();
        InputStream is = readResourceAsStream("/positive/01");
        GraphReader.read(g, is);
        logger.info("Graph: " + g);

        List<Graph.Edge<Long>> path = g.getPath(1L, 4L);
        logger.info("Path : " + path);

        Assertions.assertEquals(3, path.size(), "Path length is invalid");
    }
}
