package seliv.graphlib.util;

import seliv.graphlib.AbstractGraph;
import seliv.graphlib.undirected.UndirectedGraph;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GraphView<V> extends JPanel {
    private final AbstractGraph<V> graph;

    public void showView() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(this, BorderLayout.CENTER);

        JFrame mainFrame = new JFrame();
        mainFrame.add(mainPanel);
        mainFrame.setSize(768, 768);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public GraphView(AbstractGraph<V> graph) {
        this.graph = graph;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
        g.setFont(f);

        int x0 = getWidth() / 2;
        int y0 = getHeight() / 2;
        int fh = g.getFontMetrics(f).getHeight();
        int br = (int)(fh * 1.33);
        double dw = br / 5.0;
        double r = 0.90 * Math.min(x0, y0);

        // Calculating vertices
        Set<V> vertices = graph.listVertices();
        double da = 2.0 * Math.PI / vertices.size();
        double a = -0.5 * Math.PI;
        Map<V, Point> vertexCoordinates = new HashMap<>();
        for (V vertex : vertices) {
            double x = x0 + r * Math.cos(a);
            double y = y0 + r * Math.sin(a);
            Point p = new Point((int)x, (int)y);
            vertexCoordinates.put(vertex, p);
            a+= da;
        }

        // Drawing edges
        g.setColor(Color.BLACK);
        for (Map.Entry<V, Point> entry : vertexCoordinates.entrySet()) {
            V fromVertex = entry.getKey();
            Point fromPoint = entry.getValue();

            Set<V> adjacent = graph.listAdjacent(fromVertex);
            for (V toVertex : adjacent) {
                Point toPoint = vertexCoordinates.get(toVertex);
                if (graph instanceof UndirectedGraph) {
                    g.drawLine(fromPoint.x, fromPoint.y, toPoint.x, toPoint.y);
                } else {
                    double dx = toPoint.x - fromPoint.x;
                    double dy = toPoint.y - fromPoint.y;
                    double l = Math.sqrt((dx * dx) + (dy * dy));
                    dx /= l;
                    dy /= l;
                    g.fillPolygon(new int[]{
                                    fromPoint.x + (int) (dy * dw),
                                    fromPoint.x - (int) (dy * dw),
                                    toPoint.x
                            },
                            new int[]{
                                    fromPoint.y - (int) (dx * dw),
                                    fromPoint.y + (int) (dx * dw),
                                    toPoint.y
                            }, 3
                    );
                }
            }
        }

        // Drawing vertices
        for (Map.Entry<V, Point> entry : vertexCoordinates.entrySet()) {
            String vertexString = entry.getKey().toString();
            Point p = entry.getValue();
            int w = g.getFontMetrics().stringWidth(vertexString);
            g.setColor(Color.LIGHT_GRAY);
            g.fillOval(p.x  - br / 2, p.y - br / 2, br, br);
            g.setColor(Color.BLACK);
            g.drawString(vertexString, p.x - w / 2, p.y + fh / 4);
        }
    }
}
