package GraphWriterReader;

import algorithm.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

public class Main{
    
    public static void main(String[] args){
        Vertex a = new Vertex(0, 0, 0);
        Vertex b = new Vertex(1, 31, 0);
        Vertex c = new Vertex(2, 0, 31);
        Vertex d = new Vertex(3, 31, 31);
        Line lineab = new Line(2, a, b);
        Line lineac = new Line(2, a, c);
        Line linebc = new Line(4, b, c);
        Line linebd = new Line(3, b, d);
        Line linecd = new Line(3, c, d);
        Graph graph = new Graph();
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addLine(lineab);
        graph.addLine(lineac);
        graph.addLine(linebc);
        graph.addLine(linebd);
        graph.addLine(linecd);
        HashSet<Line> lines = graph.allLines();
        for(Line line : lines){
            System.out.println(line.getStartVertex().getX() + " " + line.getStartVertex().getY() + "    " + line.getEndVertex().getX() + " " + line.getEndVertex().getY());
        }
        Path path = Paths.get("myFile");
        GraphWriterReader.write(graph, path);
        
    }

}