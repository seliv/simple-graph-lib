package seliv.graphlib.test;

import seliv.graphlib.directed.DirectedGraph;
import seliv.graphlib.undirected.UndirectedGraph;
import seliv.graphlib.util.GraphReader;
import seliv.graphlib.util.GraphView;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class DisplayTestGraphs {
    public static void main(String[] args) {
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        JFrame mainFrame = new JFrame();
        mainFrame.setSize(768, 768);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.add(mainPanel);

        for (int test = 1; test <= 5; test++) {
            String name = "0" + test;
            InputStream graphStream = readResourceAsStream("/positive/" + name);
            DirectedGraph<Long> directedGraph = new DirectedGraph<>();
            GraphReader.read(directedGraph, graphStream);

            graphStream = readResourceAsStream("/positive/" + name);
            UndirectedGraph<Long> undirectedGraph = new UndirectedGraph<>();
            GraphReader.read(undirectedGraph, graphStream);

            tabbedPane.add("D-" + name , new GraphView<>(directedGraph));
            tabbedPane.add("U-" + name , new GraphView<>(undirectedGraph));
        }
    }

    private static InputStream readResourceAsStream(String path) {
        return DisplayTestGraphs.class.getResourceAsStream(path);
    }
}
