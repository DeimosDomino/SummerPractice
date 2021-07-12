package graphics;

import algorithm.Graph;
import algorithm.Line;
import algorithm.Vertex;

import instruments.GraphWriterReader;
import instruments.GraphStates;

import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.imageio.ImageIO;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class MainWindowController {
    private Graph graph = new Graph();
    private Stage primaryStage;

    @FXML
    private Button cursorButton,
            addNodeButton,
            addLineButton,
            deleteNodeButton,
            deleteLineButton,
            deleteAllButton,
            playButton,
            toStartButton,
            oneStepBackButton,
            playForwardButton,
            oneStepForwardButton,
            getResultButton;
    @FXML
    private Label result, crutch;

    @FXML
    private Canvas canvas;
    private Color graphColor = Color.PLUM, selectColor = Color.WHEAT, textColor = Color.BLACK, pathColor = Color.MAROON;
    private short state = 0;

    private int idVertex = 0;
    private Vertex startLine = null;
    private Vertex startVertex = null, endVertex = null;
    private Vertex dragVertex = null;

    private int minPath;
    private ArrayList<Vertex> path;
    private ArrayList<Line> visualLines;
    private ArrayList<TreeMap<Vertex, Integer>> visualVertices;
    private String message;
    private int step;
    private boolean isRunning;

    private GraphStates graphStates = new GraphStates();

    public void setPrimaryStage (Stage stage) {
        this.primaryStage = stage;
    }

    public void setup() {
        Scene scene = primaryStage.getScene();
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN), new Runnable() {
            @FXML public void run() {
                menuUndo(new ActionEvent());
            }
        });
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN), new Runnable() {
            @FXML public void run() {
                menuRedo(new ActionEvent());
            }
        });
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN), new Runnable() {
            @FXML public void run() {
                menuSave(new ActionEvent());
            }
        });
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN), new Runnable() {
            @FXML public void run() {
                menuOpen(new ActionEvent());
            }
        });
    }

    @FXML
    private void cursorButtonClick (ActionEvent event) {
        if (isRunning)
            return;
        clearResult();
        removeSelected();
        toStartButton.setVisible(false);
        oneStepBackButton.setVisible(false);
        playForwardButton.setVisible(false);
        oneStepForwardButton.setVisible(false);
        getResultButton.setVisible(false);
        this.state = 0;
    }

    @FXML
    private void addNodeButtonClick (ActionEvent event) {
        if (isRunning)
            return;
        clearResult();
        removeSelected();
        toStartButton.setVisible(false);
        oneStepBackButton.setVisible(false);
        playForwardButton.setVisible(false);
        oneStepForwardButton.setVisible(false);
        getResultButton.setVisible(false);
        this.state = 1;
    }

    @FXML
    private void addLineButtonClick (ActionEvent event) {
        if (isRunning)
            return;
        clearResult();
        removeSelected();
        toStartButton.setVisible(false);
        oneStepBackButton.setVisible(false);
        playForwardButton.setVisible(false);
        oneStepForwardButton.setVisible(false);
        getResultButton.setVisible(false);
        this.state = 2;
    }

    @FXML
    private void deleteNodeButtonClick (ActionEvent event) {
        if (isRunning)
            return;
        clearResult();
        removeSelected();
        toStartButton.setVisible(false);
        oneStepBackButton.setVisible(false);
        playForwardButton.setVisible(false);
        oneStepForwardButton.setVisible(false);
        getResultButton.setVisible(false);
        this.state = 3;
    }

    @FXML
    private void deleteLineButtonClick (ActionEvent event) {
        if (isRunning)
            return;
        clearResult();
        removeSelected();
        toStartButton.setVisible(false);
        oneStepBackButton.setVisible(false);
        playForwardButton.setVisible(false);
        oneStepForwardButton.setVisible(false);
        getResultButton.setVisible(false);
        this.state = 4;
    }

    @FXML
    private void deleteAllButtonClick (ActionEvent event) {
        if (isRunning)
            return;
        clearResult();
        removeSelected();
        toStartButton.setVisible(false);
        oneStepBackButton.setVisible(false);
        playForwardButton.setVisible(false);
        oneStepForwardButton.setVisible(false);
        getResultButton.setVisible(false);
        GraphDrawer.clear(canvas);
        graph.deleteAll();
        graphStates.add(graph);
        this.state = 5;
        idVertex = 0;
    }

    @FXML
    private void playButtonClick (ActionEvent event) {
        if (isRunning)
            return;
        clearResult();
        removeSelected();
        toStartButton.setVisible(true);
        oneStepBackButton.setVisible(true);
        playForwardButton.setVisible(true);
        oneStepForwardButton.setVisible(true);
        getResultButton.setVisible(true);
        this.state = 6;
    }

    private void printResult () {
        if (message == null) {
            GraphDrawer.drawPath(canvas, graph, path, pathColor);
            result.setText("The minimum path weight is " + minPath);
            crutch.setText("The minimum path weight is " + minPath);
        } else {
            result.setText(message);
            crutch.setText(message);
        }
    }

    protected AnimationTimer animationTimer = new AnimationTimer() {
        private long time;
        @Override
        public void handle (long now) {
            if (!isRunning)
                this.stop();
            if (now - time > 1e9) {
                visualize();
                time = now;
            }
        }
    };

    private void visualize () {
        try {
            step++;
            GraphDrawer.drawGraph(canvas, graph, graphColor, textColor);
            GraphDrawer.drawVisualization(canvas, graph, visualLines.get(step), visualVertices.get(step), selectColor, textColor);
        } catch (IndexOutOfBoundsException exception) {
            step = -1;
            isRunning = false;
            printResult();
            animationTimer.stop();
        }
    }

    private void clearResult () {
        if (!result.getText().equals("")) {
            result.setText("");
            crutch.setText("");
            GraphDrawer.drawGraph(canvas, graph, graphColor, textColor);
        }
    }

    @FXML
    private void toStartButtonClick (ActionEvent event) {
        clearResult();
        isRunning = false;
        startVertex = null;
        endVertex = null;
        GraphDrawer.drawGraph(canvas, graph, graphColor, textColor);
    }

    @FXML
    private void oneStepBackButtonClick (ActionEvent event) {
        clearResult();
        if (!isRunning && startVertex != null && endVertex != null) {
            if (step > 0) {
                step--;
                GraphDrawer.drawGraph(canvas, graph, graphColor, textColor);
                GraphDrawer.drawVisualization(canvas, graph, visualLines.get(step), visualVertices.get(step), selectColor, textColor);
            } else {
                GraphDrawer.drawGraph(canvas, graph, graphColor, textColor);
            }
        }
    }

    @FXML
    private void playForwardButtonClick (ActionEvent event) {
        clearResult();
        if (startVertex != null && endVertex != null) {
            isRunning = true;
            animationTimer.start();
        }
    }

    @FXML
    private void oneStepForwardButtonClick (ActionEvent event) {
        clearResult();
        if (!isRunning && startVertex != null && endVertex != null) {
            try {
                step++;
                GraphDrawer.drawGraph(canvas, graph, graphColor, textColor);
                GraphDrawer.drawVisualization(canvas, graph, visualLines.get(step), visualVertices.get(step), selectColor, textColor);
            } catch (IndexOutOfBoundsException exception) {
                step = -1;
                printResult();
            }
        }
    }

    @FXML
    private void getResultButtonClick (ActionEvent event) {
        clearResult();
        if (!isRunning && startVertex != null && endVertex != null) {
            printResult();
        }
    }

    @FXML
    private void canvasClick (MouseEvent event) {
        if (isRunning)
            return;
        clearResult();
        switch (this.state) {
            case 1:
                if (graph.addVertex(new Vertex(idVertex, event.getX(), event.getY()))) {
                    GraphDrawer.drawVertex(canvas, event.getX(), event.getY(), graphColor);
                    idVertex++;
                    graphStates.add(graph);
                }
                break;

            case 2:
                if (startLine == null) {
                    if ((startLine = graph.getVertex(event.getX(), event.getY())) != null)
                        GraphDrawer.drawVertex(canvas, startLine, selectColor);
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
                            if (graph.getLine(endLine, startLine) == null) {
                                GraphDrawer.drawLine(canvas, startLine, endLine, graphColor);
                                GraphDrawer.drawText(canvas, lineWeightString.get(),
                                        Math.abs(startLine.getX() + endLine.getX()) / 2,
                                        Math.abs(startLine.getY() + endLine.getY()) / 2,
                                        textColor);
                            } else {
                                GraphDrawer.drawGraph(canvas, graph, graphColor, textColor);
                            }
                            graphStates.add(graph);
                        }
                    }
                    GraphDrawer.drawVertex(canvas, startLine, graphColor);
                    startLine = null;
                }
                break;

            case 3:
                if (graph.getVertex(event.getX(), event.getY()) != null) {
                    graph.deleteVertex(event.getX(), event.getY());
                    GraphDrawer.drawGraph(canvas, graph, graphColor, textColor);
                    graphStates.add(graph);
                }
                break;

            case 4:
                if (startLine == null) {
                    if ((startLine = graph.getVertex(event.getX(), event.getY())) != null)
                        GraphDrawer.drawVertex(canvas, startLine, selectColor);
                } else {
                    Vertex endLine = graph.getVertex(event.getX(), event.getY());
                    if (endLine != null && graph.getLine(startLine, endLine) != null) {
                        graph.deleteLine(startLine, endLine);
                        GraphDrawer.drawGraph(canvas, graph, graphColor, textColor);
                        graphStates.add(graph);
                    }
                    GraphDrawer.drawVertex(canvas, startLine, graphColor);
                    startLine = null;
                }
                break;

            case 6:
                if (startVertex == null || endVertex != null) {
                    removeSelected();
                    if ((startVertex = graph.getVertex(event.getX(), event.getY())) != null)
                        GraphDrawer.drawVertex(canvas, startVertex, selectColor);
                } else {
                    if ((endVertex = graph.getVertex(event.getX(), event.getY())) != null) {
                        GraphDrawer.drawVertex(canvas, endVertex, selectColor);

                        TreeMap<Integer, String> pathString = graph.makePathList(startVertex);
                        try {
                            this.visualLines = new ArrayList<Line>();
                            this.visualVertices = new ArrayList<TreeMap<Vertex, Integer>>();
                            this.minPath = graph.getMinPath(endVertex, graph.makeStartArrayList(startVertex), pathString, this.visualLines, this.visualVertices);
                            this.path = graph.minPathArray(startVertex, endVertex, pathString);
                            this.message = null;
                        } catch (UnsupportedOperationException exception) {
                            this.path = null;
                            this.message = exception.getMessage();
                        }
                        step = -1;
                    }
                }
                break;

            default:
                break;
        }
    }

    @FXML
    public void canvasPressed (MouseEvent event) {
        if (this.state == 0) {
            if ((dragVertex = graph.getVertex(event.getX(), event.getY())) != null)
                GraphDrawer.drawVertex(canvas, dragVertex, selectColor);
        }
    }

    @FXML
    private void canvasReleased (MouseEvent event) {
        if (this.state == 0 && dragVertex != null) {
            Set<Vertex> array = graph.allVertexes();
            boolean isAbleToDrag = true;
            for (Vertex vertex : array) {
                if (vertex != dragVertex && vertex.distance(new Vertex(-1, event.getX(), event.getY())) <= vertex.getDiameter()) {
                    isAbleToDrag = false;
                    break;
                }
            }

            if (isAbleToDrag) {
                dragVertex.setCordOfVertex(event.getX(), event.getY());
                GraphDrawer.drawGraph(canvas, graph, graphColor, textColor);
                graphStates.add(graph);
            } else {
                GraphDrawer.drawVertex(canvas, dragVertex, graphColor);
            }
        }
        dragVertex = null;
    }

    private void removeSelected () {
        if (this.startLine != null) {
            GraphDrawer.drawVertex(canvas, this.startLine, graphColor);
            this.startLine = null;
        }
        if (this.startVertex != null) {
            GraphDrawer.drawVertex(canvas, this.startVertex, graphColor);
            this.startVertex = null;
        }
        if (this.endVertex != null) {
            GraphDrawer.drawVertex(canvas, this.endVertex, graphColor);
            this.endVertex = null;
        }
    }

    public void menuNew (ActionEvent event) {
        if (isRunning)
            return;
        startLine = null;
        startVertex = null;
        endVertex = null;
        deleteAllButtonClick(event);
    }

    public void menuOpen (ActionEvent event) {
        if (isRunning)
            return;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("GRAPH files (*.graph)", "*.graph");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            startLine = null;
            startVertex = null;
            endVertex = null;
            graph = GraphWriterReader.read(file.getPath());
            if (graph != null) {
                GraphDrawer.drawGraph(canvas, graph, graphColor, textColor);
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("images/warning.png").toString()));
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("No such file or the file is broken");
                alert.showAndWait();
                graph = new Graph();
                GraphDrawer.clear(canvas);
            }
        }
    }

    public void menuSave (ActionEvent event) {
        if (isRunning)
            return;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("GRAPH files (*.graph)", "*.graph");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            GraphWriterReader.write(graph, file.getPath());
        }
    }

    public void menuSaveAs (ActionEvent event) {
        if (isRunning)
            return;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null){
            try {
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Can't save canvas");
                alert.showAndWait();
            }
        }
    }

    public void menuQuit (ActionEvent event) {
        primaryStage.close();
    }

    public void menuUndo (ActionEvent event) {
        graph = graphStates.prev();
        GraphDrawer.drawGraph(canvas, graph, graphColor, textColor);
    }

    public void menuRedo (ActionEvent event) {
        graph = graphStates.next();
        GraphDrawer.drawGraph(canvas, graph, graphColor, textColor);
    }

    public void menuSelect (ActionEvent event) {
        cursorButton.requestFocus();
        cursorButtonClick(event);
    }

    public void menuAddNode (ActionEvent event) {
        addNodeButton.requestFocus();
        addNodeButtonClick(event);
    }

    public void menuAddLine (ActionEvent event) {
        addLineButton.requestFocus();
        addLineButtonClick(event);
    }

    public void menuDeleteNode (ActionEvent event) {
        deleteNodeButton.requestFocus();
        deleteNodeButtonClick(event);
    }

    public void menuDeleteLine (ActionEvent event) {
        deleteLineButton.requestFocus();
        deleteLineButtonClick(event);
    }

    public void menuDeleteAll (ActionEvent event) {
        deleteAllButton.requestFocus();
        deleteAllButtonClick(event);
    }

    public void menuPlay (ActionEvent event) {
        playButton.requestFocus();
        playButtonClick(event);
    }

    public void menuHelp (ActionEvent event) {
        if (isRunning)
            return;
        Alert alert = new Alert(AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("images/info.png").toString()));
        alert.setTitle("Help");
        alert.setHeaderText("A quick guide to using the program");
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(Double.MAX_VALUE);

        Canvas canvas = new Canvas(300, 164);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.drawImage(new Image(this.getClass().getResource("images/helpInfo.png").toString()),
                0, 0, canvas.getWidth(), canvas.getHeight());
        gridPane.add(canvas, 0, 0);

        Label label = new Label(" 1. Ability to drag nodes." + System.lineSeparator() +
                "\tSelect the node and drag it to the desired location with the key pressed." + System.lineSeparator() +
                "\tIf it is impossible to locate the node at a new location, the node will return to its original location." + System.lineSeparator() +
                " 2. Ability to add nodes." + System.lineSeparator() +
                "\tClick on an empty space on the canvas to add a node." + System.lineSeparator() +
                "\tThe node will appear if it is allowed to place in this place." + System.lineSeparator() +
                " 3. Ability to add lines." + System.lineSeparator() +
                "\tClick on the first node, which is the beginning of the added line, then on the second." + System.lineSeparator() +
                "\tThen enter an integer, which is the weight of the line." + System.lineSeparator() +
                "\tThe line will appear if it did not exist or the nodes are selected correctly." + System.lineSeparator() +
                " 4. Ability to delete nodes." + System.lineSeparator() +
                "\tClick on the node to delete it and all incident lines." + System.lineSeparator() +
                " 5. Ability to delete lines." + System.lineSeparator() +
                "\tClick on two nodes to remove a line from the first to the second nodes." + System.lineSeparator() +
                " 6. Ability to delete graph." + System.lineSeparator() +
                "\tRemove all nodes and lines from canvas." + System.lineSeparator() +
                " 7. Ability to run program." + System.lineSeparator() +
                "\tClick on two nodes to select the start node and the end node." + System.lineSeparator() +
                "\tThree buttons open at the bottom of the screen." + System.lineSeparator() +
                " 8. Ability to go to the next step." + System.lineSeparator() +
                "\tRuns the algorithm." + System.lineSeparator() +
                "\tDemonstrates the next step of the algorithm." + System.lineSeparator() +
                " 9. Ability to visualize the algorithm." + System.lineSeparator() +
                "\tRuns the algorithm." + System.lineSeparator() +
                "\tDemonstrates the algorithm visualization." + System.lineSeparator() +
                " 10. Ability to show the result of the algorithm." + System.lineSeparator() +
                "\tRuns the algorithm." + System.lineSeparator() +
                "\tDemonstrates the algorithm result.");
        gridPane.add(label, 1, 0);

        alert.getDialogPane().setExpandableContent(gridPane);
        alert.showAndWait();
    }

    public void menuAbout (ActionEvent event) {
        if (isRunning)
            return;
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("images/iconLogo.png").toString()));
        dialog.setTitle("About");
        dialog.setHeaderText("Information about \"Ford-Bellman algorithm\" program");
        dialog.setGraphic(new ImageView(this.getClass().getResource("images/iconLogo.png").toString()));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.setContentText("\"Ford-Bellman algorithm\"" + System.lineSeparator() +
                "\tbuilt on 12.07.2021" + System.lineSeparator() +
                "Powered by NoName:" + System.lineSeparator() +
                "\tAxyonova Ekaterina" + System.lineSeparator() +
                "\tKraev Denis" + System.lineSeparator() +
                "\tLambin Alexey");
        dialog.showAndWait();
    }
}
