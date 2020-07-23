package seliv.graphlib.util;

import seliv.graphlib.Graph;

import java.io.InputStream;
import java.util.Scanner;

/**
 * A utility class to read a Graph&lt;Long&gt; from a stream.
 *
 * Test case file format:
 *
 * The first line defines a graph:
 *    V E S - where V is the number of vertices, E is the number of edges, S is the start vertex for path search.
 * The following E lines define edges:
 *    V1 V2 W - where edge goes from V1 to V2 (either directed or undirected) with the weight W (if applicable).
 *
 * Vertices are marked 1 to V (not from 0) for historical reasons.
 */
public class GraphReader {
    public static void read(Graph<Long> graph, InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        // Reading graph summary: the number of vertices and edges
        long vertexCount = scanner.nextLong();
        long edgeCount = scanner.nextLong();
        // Skipping the rest of the line (it can contain additional information for tests that we don't need here)
        scanner.nextLine();

        // Creating vertices
        for (long i = 1; i <= vertexCount; i++) {
            graph.addVertex(i);
        }

        // Adding edges
        for (int i = 0; i < edgeCount; i++) {
            long vertexFrom = scanner.nextLong();
            long vertexTo = scanner.nextLong();
            // Skipping the rest of the line (it can contain additional information e.g. edge weight)
            scanner.nextLine();
            graph.addEdge(vertexFrom, vertexTo);
        }

        scanner.close();
    }
}
