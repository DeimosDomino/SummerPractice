package GraphWriterReader;

import algorithm.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

public class Main2{
    
    public static void main(String[] args){

        Graph graph = GraphWriterReader.read(Paths.get("myFile"));
        HashSet<Line> lines = graph.allLines();
        for(Line line : lines){
            System.out.println(line.getStartVertex().getX() + " " + line.getStartVertex().getY() + "    " + line.getEndVertex().getX() + " " + line.getEndVertex().getY());
        }
    }

}