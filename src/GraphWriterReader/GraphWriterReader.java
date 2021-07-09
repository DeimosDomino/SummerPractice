package GraphWriterReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import algorithm.Graph;


public class GraphWriterReader{
    private GraphWriterReader(){};

    // writing a graph to a file
    public static void write(Graph graph, Path path){
        
        try{
            if(Files.exists(path))
                Files.delete(path);
            Files.createFile(path);
            FileOutputStream fos = new FileOutputStream(path.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(graph);
                
        
        }catch(Exception e){
            System.err.println(e);
            try{
                Files.delete(path);
            //Handling situation in which Files.delete(path) throws exception :)
            }catch(Exception deleteEx){
                System.err.println(deleteEx);
            }
        }
    }

    public static void write(Graph graph, String pathStr){
        Path path = Paths.get(pathStr);
        write(graph, path);
    }
    //reading a graph from the file
    public static Graph read(Path path){
        FileInputStream fis;
        ObjectInputStream ois;
        try{
            if(!Files.exists(path)){
                throw new IOException("File \"" + path.toString() + "\" don't exist");
            }
            fis = new FileInputStream(path.toFile());
            ois = new ObjectInputStream(fis);
            return (Graph)ois.readObject();
        }catch(Exception e){
            System.err.println(e);
        }
        return null;
    }

    public static Graph read(String pathStr){
        Path path = Paths.get(pathStr);
        return read(path);
    }
}