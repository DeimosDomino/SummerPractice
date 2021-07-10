package test;

import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import algorithm.*;

public class testGraph {
    private static Vertex a;
    private static Vertex b;
    private static Vertex c;
    private static Vertex d;
    private static Vertex e;
    private static Vertex f;

    @BeforeAll
    public static void init(){
        a = new Vertex(0, 0, 0);
        b = new Vertex(1, 31, 0);
        c = new Vertex(2, 0, 31);
        d = new Vertex(3, 31, 31);
        e = new Vertex(4, 12, 0);
        f = null;

    }

    @Test
    public void testGetVertex(){
        Graph graph = new Graph();
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);

        Assert.assertEquals(a, graph.getVertex(1, 1));
        Assert.assertEquals(null, graph.getVertex(80, 80));
    }

    @Test
    public void testAddVertex(){
        Graph graph = new Graph();
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);

        TreeMap<Vertex, TreeSet<Line>> matrix = new TreeMap<Vertex, TreeSet<Line>>();

        matrix.put(a, new TreeSet<Line>());
        matrix.put(b, new TreeSet<Line>());
        matrix.put(c, new TreeSet<Line>());
        matrix.put(d, new TreeSet<Line>());

        Assert.assertEquals(matrix, graph.getMatrix());
    }

    @Test
    public void testAddLine(){
        Graph graph = new Graph();
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        Line aa = new Line(12, a, a);
        Line ab = new Line(12, a, b);
        Line ab1 = new Line(78, a, b);
        Line ac = new Line(12, a, c);
        graph.addLine(aa);
        graph.addLine(ab);
        graph.addLine(ab1);
        graph.addLine(ac);

        TreeMap<Vertex, TreeSet<Line>> matrix = new TreeMap<Vertex, TreeSet<Line>>();

        TreeSet<Line> lines = new TreeSet<Line>();

        lines.add(ab);
        lines.add(ac);

        matrix.put(a, lines);
        matrix.put(b, new TreeSet<Line>());
        matrix.put(c, new TreeSet<Line>());
        matrix.put(d, new TreeSet<Line>());

        Assert.assertEquals(matrix, graph.getMatrix());
    }

    @Test
    public void testDeleteAll(){
        Graph graph = new Graph();
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addLine(new Line(12, a, b));
        graph.addLine(new Line(12, a, c));
        graph.deleteAll();

        TreeMap<Vertex, TreeSet<Line>> matrix = new TreeMap<Vertex, TreeSet<Line>>();

        Assert.assertEquals(matrix, graph.getMatrix());
    }

    @Test
    public void testGetLine(){
        Graph graph = new Graph();
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        Line ab = new Line(12, a, b);
        Line ac = new Line(12, a, c);
        graph.addLine(ab);
        graph.addLine(ac);

        Assert.assertEquals(ab, graph.getLine(a, b));
        Assert.assertEquals(null, graph.getLine(b, c));
        Assert.assertEquals(null, graph.getLine(null, d));
    }

    @Test
    public void testGetAllVertex(){
        Graph graph = new Graph();
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);

        TreeSet<Vertex> allvertexes = new TreeSet<Vertex>();

        allvertexes.add(a);
        allvertexes.add(b);
        allvertexes.add(c);
        allvertexes.add(d);

        Assert.assertEquals(allvertexes, graph.allVertexes());
    }

    @Test
    public void testGetAllLines(){
        Graph graph = new Graph();
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        Line ab = new Line(12, a, b);
        Line ac = new Line(12, a, c);
        graph.addLine(ab);
        graph.addLine(ac);

        HashSet<Line> allLines = new HashSet<Line>();

        allLines.add(ab);
        allLines.add(ac);

        Assert.assertEquals(allLines, graph.allLines());
    }

    @Test
    public void testStartArray(){
        Graph graph = new Graph();
        Vertex f = new Vertex(7, 1, 90);
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);

        TreeMap<Integer, Integer> list1 = new TreeMap<Integer, Integer>();
        TreeMap<Integer, Integer>list2 = new TreeMap<Integer, Integer>();
        for(int i = 0; i < 4; i++){
            if(i == 1){
                list1.put(i, 0);
                list2.put(i, Integer.MAX_VALUE);
            }else{
                list1.put(i, Integer.MAX_VALUE);
                list2.put(i, Integer.MAX_VALUE);
            }
        }

        Assert.assertEquals(list1, graph.makeStartArrayList(b));
        Assert.assertEquals(list2, graph.makeStartArrayList(f));
    }

    @Test
    public void testPathArray(){
        Graph graph = new Graph();
        Vertex f = new Vertex(7, 1, 90);
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);

        TreeMap<Integer, String> list1 = new TreeMap<Integer, String>();
        TreeMap<Integer, String>list2 = new TreeMap<Integer, String>();
        for(int i = 0; i < 4; i++){
            list1.put(i, "");
            list2.put(i, "");
        }

        Assert.assertEquals(list1, graph.makePathList(b));
        Assert.assertEquals(list2, graph.makePathList(f));
    }

    @Test
    public void testMinPath(){
        //есть путь
        Vertex a = new Vertex(0, 0, 0);;
        Vertex b = new Vertex(1, 31, 0);
        Vertex c = new Vertex(2, 0, 31);
        Vertex d = new Vertex(3, 31, 31);
        Line lineab = new Line(2, a, b);
        Line lineac = new Line(2, a, c);
        Line linead = new Line(3, a, d);
        Line linebd = new Line(1, b, d);
        Line linecb = new Line(-2, c, b);
        Line linecd = new Line(0, c, d);
        Graph graph = new Graph();
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addLine(lineab);
        graph.addLine(lineac);
        graph.addLine(linead);
        graph.addLine(linebd);
        graph.addLine(linecb);
        graph.addLine(linecd);
        TreeMap<Integer, Integer> list = graph.makeStartArrayList(c);
        TreeMap<Integer, String> path = graph.makePathList(c);
        ArrayList<Line> iterationLine = new ArrayList<Line>();
        ArrayList<TreeMap<Vertex, Integer>> iterationDestination = new ArrayList<TreeMap<Vertex, Integer>>();
        Integer result = -2;
        Assert.assertEquals(result, graph.getMinPath(b, list, path, iterationLine, iterationDestination));
        graph.deleteAll();
        list.clear();
        path.clear();
        iterationDestination.clear();
        iterationLine.clear();

        //отрицательный цикл
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addLine(new Line(3, a, b));
        graph.addLine(new Line(-2, c, a));
        graph.addLine(new Line(-2, b, c));
        list = graph.makeStartArrayList(b);
        path = graph.makePathList(b);
        try{
            result = graph.getMinPath(b, list, path, iterationLine, iterationDestination);
        }catch(UnsupportedOperationException error){
            assertEquals("This graph has negative cycle", error.getMessage());
        }
        list.clear();
        path.clear();
        iterationDestination.clear();
        iterationLine.clear();

        //нет пути
        graph.addVertex(d);
        graph.deleteLine(c, a);
        list = graph.makeStartArrayList(a);
        path = graph.makePathList(a);
        try{
            result = graph.getMinPath(d, list, path, iterationLine, iterationDestination);
        }catch(UnsupportedOperationException error){
            assertEquals("There is no path to this vertex", error.getMessage());
        }


    }

    @Test
    public void testMinPathString(){
        Vertex a = new Vertex(0, 0, 0);;
        Vertex b = new Vertex(1, 31, 0);
        Vertex c = new Vertex(2, 0, 31);
        Vertex d = new Vertex(3, 31, 31);
        Line lineab = new Line(2, a, b);
        Line lineac = new Line(2, a, c);
        Line linead = new Line(3, a, d);
        Line linebd = new Line(1, b, d);
        Line linecb = new Line(-2, c, b);
        Line linecd = new Line(0, c, d);
        Graph graph = new Graph();
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addLine(lineab);
        graph.addLine(lineac);
        graph.addLine(linead);
        graph.addLine(linebd);
        graph.addLine(linecb);
        graph.addLine(linecd);
        TreeMap<Integer, Integer> list = graph.makeStartArrayList(c);
        TreeMap<Integer, String> path = graph.makePathList(c);
        ArrayList<Line> iterationLine = new ArrayList<Line>();
        ArrayList<TreeMap<Vertex, Integer>> iterationDestination = new ArrayList<TreeMap<Vertex, Integer>>();
        graph.getMinPath(b, list, path, iterationLine, iterationDestination);
        

        ArrayList<Vertex> pathMin = new ArrayList<Vertex>();

        pathMin.add(c);
        pathMin.add(b);


        Assert.assertEquals(pathMin, graph.minPathArray(c, b, path));
    }

    @Test
    public void testDeleteLine(){
        Graph graph = new Graph();
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        Line ab = new Line(12, a, b);
        Line ac = new Line(12, a, c);
        graph.addLine(ab);
        graph.addLine(ac);
        graph.deleteLine(a, b);
        graph.deleteLine(a, d);

        HashSet<Line> allLines = new HashSet<Line>();

        allLines.add(ac);

        Assert.assertEquals(allLines, graph.allLines());
    }

    @Test
    public void testDeleteVertex(){
        Graph graph = new Graph();
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.deleteVertex(30, 30);
        graph.deleteVertex(80,80);

        TreeSet<Vertex> allvertexes = new TreeSet<Vertex>();

        allvertexes.add(a);
        allvertexes.add(b);
        allvertexes.add(c);
 

        Assert.assertEquals(allvertexes, graph.allVertexes());
    }
}
