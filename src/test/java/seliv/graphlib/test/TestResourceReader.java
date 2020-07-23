package seliv.graphlib.test;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
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

        Assert.assertEquals("Path length", 3, path.size());
    }
}
