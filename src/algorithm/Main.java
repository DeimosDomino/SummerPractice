package algorithm;
import java.util.ArrayList;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args){
        try{
            Graph graph = new Graph();
            Vertex a = new Vertex(0, 0, 0);
            Vertex b = new Vertex(1, 0, 31);
            Vertex c = new Vertex(2, 0, 62);
            Vertex d = new Vertex(3, 31, 0);
            Vertex e = new Vertex(4, 31, 31);
            Vertex f = new Vertex(5, 31, 62);
            Vertex g = new Vertex(6, 62, 0);
            Vertex h = new Vertex(7, 62, 31);
            Vertex i = new Vertex(8, 62, 62);
            Vertex j = new Vertex(9, 93, 0);
            Vertex k = new Vertex(10, 93, 31);
            Vertex l = new Vertex(11, 93, 62);
            Vertex m = new Vertex(12, 124, 0);
            Vertex n = new Vertex(13, 124, 31);
            Vertex o = new Vertex(14, 124, 62);
            graph.addVertex(a);
            graph.addVertex(b);
            graph.addVertex(c);
            graph.addVertex(d);
            graph.addVertex(e);
            graph.addVertex(f);
            graph.addVertex(g);
            graph.addVertex(h);
            graph.addVertex(i);
            graph.addVertex(j);
            graph.addVertex(k);
            graph.addVertex(l);
            graph.addVertex(m);
            graph.addVertex(n);
            graph.addVertex(o);
            graph.addLine(new Line(1, a, b));
            graph.addLine(new Line(1, a, f));
            graph.addLine(new Line(1, a, g));
            graph.addLine(new Line(1, b, c));
            graph.addLine(new Line(1, b, g));
            graph.addLine(new Line(1, b, h));
            graph.addLine(new Line(1, c, d));
            graph.addLine(new Line(1, c, h));
            graph.addLine(new Line(1, c, i));
            graph.addLine(new Line(1, d, e));
            graph.addLine(new Line(1, d, i));
            graph.addLine(new Line(1, d, j));
            graph.addLine(new Line(1, e, j));
            graph.addLine(new Line(1, f, g));
            graph.addLine(new Line(1, f, k));
            graph.addLine(new Line(1, f, l));
            graph.addLine(new Line(1, g, h));
            graph.addLine(new Line(1, g, l));
            graph.addLine(new Line(1, g, m));
            graph.addLine(new Line(1, h, i));
            graph.addLine(new Line(1, h, m));
            graph.addLine(new Line(1, h, n));
            graph.addLine(new Line(1, i, j));
            graph.addLine(new Line(1, i, n));
            graph.addLine(new Line(1, i, o));
            graph.addLine(new Line(1, j, o));
            graph.addLine(new Line(1, k, l));
            graph.addLine(new Line(1, l, m));
            graph.addLine(new Line(1, m, n));
            graph.addLine(new Line(1, n, o));
            TreeMap<Integer, Integer> list = graph.makeStartArrayList(a);
            TreeMap<Integer, String> path = graph.makePathList(a);
            ArrayList<Line> iterationLine = new ArrayList<Line>();
            ArrayList<TreeMap<Vertex, Integer>> iterationDestination = new ArrayList<TreeMap<Vertex, Integer>>();
            try{
                Integer valuePath = graph.getMinPath(o, list, path, iterationLine, iterationDestination);
                System.out.println(valuePath);
                ArrayList<Vertex> minPath = graph.minPathArray(a, o, path);
                for(Vertex cur : minPath){
                    System.out.println(cur.getId());
                }
            }catch(UnsupportedOperationException error){
                System.out.println(error.getMessage());
            }
        }catch(NullPointerException ex){
            System.out.println(ex.getMessage());
        }

    }

}
