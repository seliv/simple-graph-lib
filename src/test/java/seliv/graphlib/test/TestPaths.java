package seliv.graphlib.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seliv.graphlib.Graph;
import seliv.graphlib.directed.DirectedGraph;
import seliv.graphlib.undirected.UndirectedGraph;
import seliv.graphlib.util.GraphReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class TestPaths {
    private static final Logger logger = LoggerFactory.getLogger(TestPaths.class);

    @ParameterizedTest
    @MethodSource("readTestSamples")
    public void testPaths(Graph<Long> graph, Long startVertex, List<Integer> pathLengths) {
        logger.info("Testing graph " + graph + " start: " + startVertex + " lengths: " + pathLengths);
        for (long v = 1; v <= pathLengths.size(); v++) {
            List<Graph.Edge<Long>> path = graph.getPath(startVertex, v);
            Integer pathLength = path == null ? null : path.size();
            Integer expected = pathLengths.get((int) v - 1);

            Assertions.assertEquals(expected, pathLength,
                    "Path length from " + startVertex + " to " + v + " is incorrect: " +
                            pathLength + " instead of " + expected);
        }
    }

    private static Stream<Arguments> readTestSamples() {
        List<Arguments> testCases = new ArrayList<>();

        for (int test = 1; test <= 5; test++) {
            String name = "0" + test;

            InputStream graphStream = readResourceAsStream("/positive/" + name);
            InputStream answersDirectedStream = readResourceAsStream("/positive/" + name + ".d");
            
            DirectedGraph<Long> directedGraph = new DirectedGraph<>();
            Long startVertex = GraphReader.read(directedGraph, graphStream);
            List<Integer> answersDirected = readAnswers(answersDirectedStream);
            testCases.add(Arguments.of(directedGraph, startVertex, answersDirected));

            graphStream = readResourceAsStream("/positive/" + name);
            InputStream answersUndirectedStream = readResourceAsStream("/positive/" + name + ".u");
            
            UndirectedGraph<Long> undirectedGraph = new UndirectedGraph<>();
            startVertex = GraphReader.read(undirectedGraph, graphStream);
            List<Integer> answersUndirected = readAnswers(answersUndirectedStream);
            testCases.add(Arguments.of(undirectedGraph, startVertex, answersUndirected));
        }

        return testCases.stream();
    }

    private static InputStream readResourceAsStream(String path) {
        return TestPaths.class.getResourceAsStream(path);
    }

    private static List<Integer> readAnswers(InputStream inputStream) {
        List<Integer> result = new ArrayList<>();
        Scanner scanner = new Scanner(inputStream);
        do {
            String line = scanner.nextLine();
            if (line.startsWith("*")) {
                result.add(null);
            } else {
                result.add(Integer.parseInt(line));
            }
        } while (scanner.hasNextLine());
        scanner.close();
        return result;
    }

}
