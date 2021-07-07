package algorithm;
import java.util.ArrayList;

public class Main {
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
        ArrayList<Integer> list = graph.makeStartArrayList(a);
        ArrayList<Boolean> visited = graph.makeVisitedList(a);
        ArrayList<String> path = graph.makePathList(a);
        Integer valuePath = 0;
        try{
            valuePath = graph.getMinPath(a, d, list, visited, valuePath, path);
            System.out.println(valuePath);
        }catch(UnsupportedOperationException error){
            System.out.println(error.getMessage());
        }
        ArrayList<Vertex> minPath = graph.minPathArray(a, d, path);
        System.out.println(minPath);

    }

}
