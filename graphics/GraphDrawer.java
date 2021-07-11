package graphics;

import algorithm.Coordinates;
import algorithm.Graph;
import algorithm.Line;
import algorithm.Vertex;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.TreeMap;

public class GraphDrawer {
    private static final double ds = 7;

    public static void drawVertex (Canvas canvas, double x, double y, Color color) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(color);
        graphicsContext.fillOval(x - 10, y - 10, 20, 20);
    }

    public static void drawVertex (Canvas canvas, Vertex vertex, Color color) {
        drawVertex(canvas, vertex.getX(), vertex.getY(), color);
    }

    private static Coordinates drawLine (Canvas canvas, Vertex start, Vertex end, Color color, boolean isBack) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setStroke(color);
        graphicsContext.setLineWidth(3);
        double dt = isBack ? - ds : 0;
        final double h = 30, w = 7;
        double vX = end.getX() - start.getX();
        double vY = end.getY() - start.getY();
        double v = Math.sqrt(vX * vX + vY * vY);
        vX = vX / v;
        vY = vY / v;
        double nX = vY;
        double nY = -vX;
        double hX = end.getX() - h * vX;
        double hY = end.getY() - h * vY;
        graphicsContext.strokeLine(start.getX() + dt * nX, start.getY() + dt * nY, hX + dt * nX, hY + dt * nY);
        graphicsContext.strokeLine(hX + w * nX + dt * nX, hY + w * nY + dt * nY, end.getX() + dt * nX, end.getY() + dt * nY);
        graphicsContext.strokeLine(hX - w * nX + dt * nX, hY - w * nY + dt * nY, end.getX() + dt * nX, end.getY() + dt * nY);
        graphicsContext.strokeLine(hX + w * nX + dt * nX, hY + w * nY + dt * nY, hX - w * nX + dt * nX, hY - w * nY + dt * nY);
        return new Coordinates(nX, nY);
    }

    public static void drawLine (Canvas canvas, Vertex start, Vertex end, Color color) {
        drawLine(canvas, start, end, color, false);
    }

    public static void drawLine (Canvas canvas, Line line, Color color) {
        drawLine(canvas, line.getStartVertex(), line.getEndVertex(), color, false);
    }

    public static void drawText (Canvas canvas, String text, double x, double y, Color color) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(color);
        graphicsContext.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        graphicsContext.fillText(text, x, y);
    }

    public static void drawPath (Canvas canvas, Graph graph, ArrayList<Vertex> path, Color color) {
        Vertex temp = null;
        for (Vertex vertex : path) {
            if (temp != null) {
                boolean isBack = graph.getLine(vertex, temp) != null;
                GraphDrawer.drawLine(canvas, temp, vertex, color, isBack);
            }
            GraphDrawer.drawVertex(canvas, vertex, color);
            temp = vertex;
        }
    }

    public static void drawVisualization (Canvas canvas, Graph graph, Line line, TreeMap<Vertex, Integer> destinations, Color selectColor, Color textColor) {
        drawVertex(canvas, line.getStartVertex(), selectColor);
        drawVertex(canvas, line.getEndVertex(), selectColor);
        boolean isBack = graph.getLine(line.getEndVertex(), line.getStartVertex()) != null;
        drawLine(canvas, line.getStartVertex(), line.getEndVertex(), selectColor, isBack);
        for (Vertex vertex : destinations.keySet())
            if (destinations.get(vertex) == Integer.MAX_VALUE)
                drawText(canvas, "\u221E", vertex.getX(), vertex.getY(), textColor);
            else
                drawText(canvas, destinations.get(vertex).toString(), vertex.getX(), vertex.getY(), textColor);
    }

    public static void drawGraph (Canvas canvas, Graph graph, Color graphColor, Color textColor) {
        clear(canvas);
        for (Vertex vertex : graph.allVertexes()) {
            drawVertex(canvas, vertex, graphColor);
        }
        for (Line line : graph.allLines()) {
            if (graph.getLine(line.getEndVertex(), line.getStartVertex()) == null) {
                drawLine(canvas, line, graphColor);
                drawText(canvas, "" + line.getWeight(),
                        Math.abs(line.getStartVertex().getX() + line.getEndVertex().getX()) / 2,
                        Math.abs(line.getStartVertex().getY() + line.getEndVertex().getY()) / 2,
                        textColor);
            } else {
                Coordinates n = drawLine(canvas, line.getStartVertex(), line.getEndVertex(), graphColor, true);
                drawText(canvas, "" + line.getWeight(),
                        Math.abs(line.getStartVertex().getX() + line.getEndVertex().getX()) / 2 - ds * n.getX(),
                        Math.abs(line.getStartVertex().getY() + line.getEndVertex().getY()) / 2 - ds * n.getY(),
                        textColor);
            }
        }
    }

    public static void clear (Canvas canvas) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.rgb(244, 244, 244));
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
