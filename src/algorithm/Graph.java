package algorithm;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Graph {
    private TreeMap<Vertex, TreeSet<Line>> matrix;

    public Graph() {
        this.matrix = new TreeMap<Vertex, TreeSet<Line>>();
    }

    public boolean addVertex(Vertex newNode) {
        Iterator<Vertex> vertexes = this.matrix.keySet().iterator();
        if (this.matrix.isEmpty()) {
            this.matrix.put(newNode, new TreeSet<Line>());
            return true;
        } else {
            boolean flag = true;
            while (vertexes.hasNext()) {
                if ((vertexes.next().distance(newNode) - newNode.getDiameter() > 0.000001)) {
                    flag = false;
                } else {
                    return false;
                }
            }
            if (!flag) {
                this.matrix.put(newNode, new TreeSet<Line>());
                return true;
            }
        }
        return false;
    }

    public Vertex getVertex(double x, double y) {
        Set<Vertex> vertexes = this.matrix.keySet();
        if (vertexes != null) {
            Iterator<Vertex> iterator = vertexes.iterator();
            while (iterator.hasNext()) {
                Vertex temp = iterator.next();
                if ((temp.getX() <= x + temp.getDiameter() / 2) && (temp.getX() >= x - temp.getDiameter() / 2)
                        && (temp.getY() <= y + temp.getDiameter() / 2) && (temp.getY() >= y - temp.getDiameter() / 2)) {
                    return temp;
                }
            }
            return null;
        }
        return null;
    }

    public boolean addLine(Line newLine) {
        // проверка на петли
        if (newLine.getStartVertex().equals(newLine.getEndVertex())) {
            return false;
        }
        // конец проверки
        if (this.matrix.get(newLine.getStartVertex()) != null) {
            Iterator<Line> iterator = this.matrix.get(newLine.getStartVertex()).iterator();
            if (iterator.hasNext()) {
                boolean flag = false;
                while (iterator.hasNext()) {
                    flag = iterator.next().getEndVertex().equals(newLine.getEndVertex());
                }
                if (!flag) {
                    this.matrix.get(newLine.getStartVertex()).add(newLine);
                    return true;
                }
            } else {
                this.matrix.get(newLine.getStartVertex()).add(newLine);
                return true;
            }
        }
        return false;
    }

    public Set<Vertex> allVertexes(){
        return this.matrix.keySet();
    }
    
    public HashSet<Line> allLines(){
        HashSet<Line> lines = new HashSet<Line>();
        Set<Vertex> vertexes = this.matrix.keySet();
        Iterator<Vertex> vertIter = vertexes.iterator(); 
        while(vertIter.hasNext()){
            Vertex cur = vertIter.next();
            TreeSet<Line> linesVert = this.matrix.get(cur);
            Iterator<Line> iterLine = linesVert.iterator();
            while(iterLine.hasNext()){
                lines.add(iterLine.next());
            }
        }
        return lines;
    }

    public Line getLine(Vertex start, Vertex end) {
        TreeSet<Line> lines = this.matrix.get(start);
        Iterator<Line> iter = lines.iterator();
        while(iter.hasNext()){
            Line tryLine = iter.next();
            if(tryLine.equals(new Line(0, start, end))){
                return tryLine;
            }
        }
        return null;
    }

    public TreeMap<Vertex, TreeSet<Line>> getMatrix() {
        return matrix;
    }

    public int getNumOFVertexes() {
        return this.matrix.size();
    }

    public TreeMap<Integer, Integer> makeStartArrayList(Vertex start) {
        TreeMap<Integer, Integer> list = new TreeMap<Integer, Integer>();
        Set<Vertex> nodes = this.matrix.keySet();
        if(nodes != null){
            Iterator<Vertex> iterator = nodes.iterator();
            while (iterator.hasNext()) {
                Vertex vertex = iterator.next();
                list.put(vertex.getId(), vertex.equals(start) ? 0 : Integer.MAX_VALUE);
            }
        }
        return list;
    }

    public void deleteAll() {
        this.matrix.clear();
    }

    public void deleteLine(Vertex start, Vertex end) {
        Set<Line> lines = this.matrix.get(start);
        if(lines != null && !lines.isEmpty()){
            Iterator<Line> allLines = lines.iterator();
            while(allLines.hasNext()){
                Line tryToDelete = allLines.next();
                if(tryToDelete.getEndVertex().equals(end)){
                    lines.remove(tryToDelete);
                    break;
                }
            }
        }
    }

    public void deleteVertex(double x, double y){
        Vertex toDelete = getVertex(x, y);
        this.matrix.remove(toDelete);
        Set<Vertex> vertexes = this.matrix.keySet();
        Line deleteLine = null;
        if(vertexes != null && !vertexes.isEmpty()){
            Iterator<Vertex> iterator = vertexes.iterator();
            while(iterator.hasNext()){
                Set<Line> allLines = this.matrix.get(iterator.next());
                if(allLines != null && !allLines.isEmpty()){
                    Iterator<Line> lines = allLines.iterator();
                    while(lines.hasNext()){
                        Line tryToDelete = lines.next();
                        if(tryToDelete.getEndVertex().equals(toDelete)){
                            deleteLine = tryToDelete;
                        }
                    }
                    if(deleteLine != null){
                        allLines.remove(deleteLine);
                    }
                }
            }
        }
    }
    // переделать под map
    public TreeMap<Integer, Boolean> makeVisitedList(Vertex start){
        TreeMap<Integer, Boolean>  visited = new TreeMap<Integer, Boolean> ();
        Set<Vertex> vertexes = this.matrix.keySet();
        for(Vertex i : vertexes){
            visited.put(i.getId(), i.getId() == start.getId() ? true : false);
        }
        return visited;
    }
    // переделать под map
    public TreeMap<Integer, String> makePathList(Vertex start){
        TreeMap<Integer, String>  path = new TreeMap<Integer, String>();
        Set<Vertex> vertexes = this.matrix.keySet();
        for(Vertex i : vertexes){
            path.put(i.getId(), i.getId() == start.getId() ? "" : Integer.toString(start.getId()));
        }
        return path;
    }
    // переделать под map
    public Integer getMinPath(Vertex start, Vertex end, TreeMap<Integer, Integer> destinations, TreeMap<Integer, Boolean> visited,
            Integer valuePath, TreeMap<Integer, String> path) throws UnsupportedOperationException {
        TreeSet<Line> tryToMakeLess = this.matrix.get(start);
        Iterator<Line> iterator = tryToMakeLess.iterator();
        while (iterator.hasNext()) {
            Line curLine = iterator.next();
            if(valuePath + curLine.getWeight() < destinations.get(curLine.getEndVertex().getId())){
                if(visited.get(curLine.getEndVertex().getId()) == false){
                    destinations.put(curLine.getEndVertex().getId(), valuePath + curLine.getWeight());
                    path.put(curLine.getEndVertex().getId(), Integer.toString(curLine.getStartVertex().getId()));
                }else{
                    throw new UnsupportedOperationException("This graph has negative cycle");
                }
            }
        }
        Integer min = Integer.MAX_VALUE;
        int indexOfMin = start.getId();

        // исправить под map
        for(Integer i : destinations.keySet()){
            if(destinations.get(i) < min && visited.get(i) == false){
                min = destinations.get(i);
                indexOfMin = i;
            }
        }
        if(min == Integer.MAX_VALUE && indexOfMin != end.getId()){
            throw new UnsupportedOperationException("No path to this vertex");
        }
        valuePath = min;
        visited.put(indexOfMin, true);
        Set<Vertex> vertexes = this.matrix.keySet();
        Iterator<Vertex> vertIterator = vertexes.iterator();
        while(vertIterator.hasNext()){
            Vertex current = vertIterator.next();
            if(current.getId() == indexOfMin){
                start = current;
            }
        }
        //добавляет все вершины, а не только нужные
        if (start.equals(end)) {
            return valuePath;
        }
        valuePath = getMinPath(start, end, destinations, visited, valuePath, path);
        return valuePath;
    }
    // переделать под map
    public ArrayList<Vertex> minPathArray(Vertex start, Vertex end, TreeMap<Integer, String> path){
        String minPath = minPathString(start, end, path);
        String[] sequence = minPath.split(" ");
        ArrayList<Vertex> min = new ArrayList<Vertex>();
        Set<Vertex> vertexes = this.matrix.keySet();
        for(String cur : sequence){
            for(Vertex vertex : vertexes){
                if(vertex.getId() == Integer.parseInt(cur)){
                    min.add(vertex);
                }
            }
        }
        return min;
    }
    // переделать под map
    private String minPathString(Vertex start, Vertex end, TreeMap<Integer, String> path){
        int index = end.getId();
        StringBuilder minPath = new StringBuilder(Integer.toString(end.getId()));
        minPath.append(" ");
        while(index != start.getId()){
            minPath.append(path.get(index));
            minPath.append(" ");
            try{
                index = Integer.parseInt(path.get(index));
            }catch(NumberFormatException r){}
        }
        minPath.deleteCharAt(minPath.length() - 1);
        minPath.reverse();
        String min = new String(minPath);
        return min;
    }
}
