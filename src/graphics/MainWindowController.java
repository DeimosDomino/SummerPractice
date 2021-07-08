package graphics;

import algorithm.Graph;
import algorithm.Line;
import algorithm.Vertex;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeMap;

public class MainWindowController {
    private Graph graph = new Graph();
    @FXML
    privpackage graphics;

import algorithm.Graph;
import algorithm.Line;
import algorithm.Vertex;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeMap;

public class MainWindowController {
    private Graph graph = new Graph();
    @FXML
    private Button cursorButton,
            addNodeButton,
            addLineButton,
            deleteNodeButton,
            deleteLineButton,
            deleteAllButton,
            playButton,
            oneStepForwardButton,
            playForwardButton,
            getResultButton;
    @FXML
    private Canvas canvas;
    Color graphColor = Color.DEEPSKYBLUE, selectColor = Color.WHEAT, textColor = Color.BLACK, resultColor = Color.FORESTGREEN;
    private short state = 0;
    private int idVertex = 0;
    private Vertex startLine = null;
    private Vertex startVertex = null, endVertex = null;

    @FXML
    private void cursorButtonClick (ActionEvent event) {
        removeSelected();
        oneStepForwardButton.setVisible(false);
        playForwardButton.setVisible(false);
        getResultButton.setVisible(false);
        this.state = 0;
    }
    @FXML
    private void addNodeButtonClick (ActionEvent event) {
        removeSelected();
        oneStepForwardButton.setVisible(false);
        playForwardButton.setVisible(false);
        getResultButton.setVisible(false);
        this.state = 1;
    }
    @FXML
    private void addLineButtonClick (ActionEvent event) {
        removeSelected();
        oneStepForwardButton.setVisible(false);
        playForwardButton.setVisible(false);
        getResultButton.setVisible(false);
        this.state = 2;
    }
    @FXML
    private void deleteNodeButtonClick (ActionEvent event) {
        removeSelected();
        oneStepForwardButton.setVisible(false);
        playForwardButton.setVisible(false);
        getResultButton.setVisible(false);
        this.state = 3;
    }
    @FXML
    private void deleteLineButtonClick (ActionEvent event) {
        removeSelected();
        oneStepForwardButton.setVisible(false);
        playForwardButton.setVisible(false);
        getResultButton.setVisible(false);
        this.state = 4;
    }
    @FXML
    private void deleteAllButtonClick (ActionEvent event) {
        removeSelected();
        oneStepForwardButton.setVisible(false);
        playForwardButton.setVisible(false);
        getResultButton.setVisible(false);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.rgb(244, 244, 244));
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graph.deleteAll();
        this.state = 5;
        idVertex = 0;
    }
    @FXML
    private void playButtonClick (ActionEvent event) {
        removeSelected();
        oneStepForwardButton.setVisible(true);
        playForwardButton.setVisible(true);
        getResultButton.setVisible(true);
        this.state = 6;
    }
    @FXML
    private void oneStepForwardButtonClick (ActionEvent event) {
        if (startVertex != null && endVertex != null) {
            // code
        }
    }
    @FXML
    private void playForwardButtonClick (ActionEvent event) {
        if (startVertex != null && endVertex != null) {

        }
    }
    @FXML
    private void getResultButtonClick (ActionEvent event) {
        if (startVertex != null && endVertex != null) {
            TreeMap<Integer, String> pathString = graph.makePathList(startVertex);
            int minPath = 0;
            try {
                minPath = graph.getMinPath(startVertex, endVertex,
                        graph.makeStartArrayList(startVertex),
                        graph.makeVisitedList(startVertex), 0, pathString);
                ArrayList<Vertex> path = graph.minPathArray(startVertex, endVertex, pathString);
                Vertex temp = null;
                for (Vertex vertex : path) {
                    if (temp != null)
                        drawLine(canvas, temp, vertex, resultColor);
                    drawVertex(canvas, vertex.getX(), vertex.getY(), resultColor);
                    temp = vertex;
                }
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("The minimum path weight is " + minPath);
                alert.showAndWait();
                drawGraph(canvas, graph, graphColor);
            } catch (UnsupportedOperationException exception) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        }
    }
    @FXML
    private void canvasClick (MouseEvent event) {
        switch (this.state) {
            case 1:
                if (graph.addVertex(new Vertex(idVertex, event.getX(), event.getY()))) {
                    drawVertex(canvas, event.getX(), event.getY(), graphColor);
                    idVertex++;
                }
                break;

            case 2:
                if (startLine == null) {
                    if ((startLine = graph.getVertex(event.getX(), event.getY())) != null)
                        drawVertex(canvas, startLine.getX(), startLine.getY(), selectColor);
                } else {
                    Vertex endLine = graph.getVertex(event.getX(), event.getY());
                    if (endLine != null) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Line weight selection");
                        dialog.setHeaderText(null);
                        dialog.setContentText("Please enter line weight:");
                        Optional<String> lineWeightString = dialog.showAndWait();

                        int lineWeight = 0;
                        boolean isNumber = true;
                        if (lineWeightString.isPresent())
                            try {
                                lineWeight = Integer.parseInt(lineWeightString.get());
                            } catch (NumberFormatException exception) {
                                isNumber = false;
                            }
                        else
                            isNumber = false;

                        if (isNumber && graph.addLine(new Line(lineWeight, startLine, endLine))) {
                            drawLine(canvas, startLine, endLine, graphColor);
                            drawText(canvas, lineWeightString.get(),
                                    Math.abs(startLine.getX() + endLine.getX()) / 2,
                                    Math.abs(startLine.getY() + endLine.getY()) / 2,
                                    textColor);
                        }
                    }
                    drawVertex(canvas, startLine.getX(), startLine.getY(), graphColor);
                    startLine = null;
                }
                break;

            case 3:
                if (graph.getVertex(event.getX(), event.getY()) != null) {
                    graph.deleteVertex(event.getX(), event.getY());
                    drawGraph(canvas, graph, graphColor);
                }
                break;

            case 4:
                if (startLine == null) {
                    if ((startLine = graph.getVertex(event.getX(), event.getY())) != null)
                        drawVertex(canvas, startLine.getX(), startLine.getY(), selectColor);
                } else {
                    Vertex endLine = graph.getVertex(event.getX(), event.getY());
                    if (graph.getLine(startLine, endLine) != null) {
                        graph.deleteLine(startLine, endLine);
                        drawGraph(canvas, graph, graphColor);
                    }
                    drawVertex(canvas, startLine.getX(), startLine.getY(), graphColor);
                    startLine = null;
                }
                break;

            case 6:
                if (startVertex == null || endVertex != null) {
                    removeSelected();
                    if ((startVertex = graph.getVertex(event.getX(), event.getY())) != null)
                        drawVertex(canvas, startVertex.getX(), startVertex.getY(), selectColor);
                } else {
                    if ((endVertex = graph.getVertex(event.getX(), event.getY())) != null) {
                        drawVertex(canvas, endVertex.getX(), endVertex.getY(), selectColor);
                    }
                }
                break;

            default:
                break;
        }
    }

    private void drawVertex (Canvas canvas, double x, double y, Color color) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(color);
        graphicsContext.fillOval(x - 10, y - 10, 20, 20);
    }

    private void drawLine (Canvas canvas, Vertex start, Vertex end, Color color) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setStroke(color);
        graphicsContext.setLineWidth(3);
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
        graphicsContext.strokeLine(start.getX(), start.getY(), hX, hY);
        graphicsContext.strokeLine(hX + w * nX, hY + w * nY, end.getX(), end.getY());
        graphicsContext.strokeLine(hX - w * nX, hY - w * nY, end.getX(), end.getY());
        graphicsContext.strokeLine(hX + w * nX, hY + w * nY, hX - w * nX, hY - w * nY);
    }

    private void drawText (Canvas canvas, String text, double x, double y, Color color) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(color);
        graphicsContext.setFont(new Font(12));
        graphicsContext.fillText(text, x, y);
    }

    private void drawGraph (Canvas canvas, Graph graph, Color color) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.rgb(244, 244, 244));
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Vertex vertex : graph.allVertexes()) {
            drawVertex(canvas, vertex.getX(), vertex.getY(), graphColor);
        }
        for (Line line : graph.allLines()) {
            drawLine(canvas, line.getStartVertex(), line.getEndVertex(), graphColor);
            drawText(canvas, "" + line.getWeight(),
                    Math.abs(line.getStartVertex().getX() + line.getEndVertex().getX()) / 2,
                    Math.abs(line.getStartVertex().getY() + line.getEndVertex().getY()) / 2,
                    textColor);
        }
    }

    private void removeSelected () {
        if (this.startLine != null) {
            drawVertex(canvas, this.startLine.getX(), this.startLine.getY(), graphColor);
            this.startLine = null;
        }
        if (this.startVertex != null) {
            drawVertex(canvas, this.startVertex.getX(), this.startVertex.getY(), graphColor);
            this.startVertex = null;
        }
        if (this.endVertex != null) {
            drawVertex(canvas, this.endVertex.getX(), this.endVertex.getY(), graphColor);
            this.endVertex = null;
        }
    }
}
