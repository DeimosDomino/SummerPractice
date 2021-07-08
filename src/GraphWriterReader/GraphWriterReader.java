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
            Files.createFile(path);
            FileOutputStream fos = new FileOutputStream(path.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(graph);
        
        //Handling situation in which file already exists
        }catch(FileAlreadyExistsException faee){                                       
            System.err.println("File \"" + path.toString() + "\" already exists");
        
        
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
        }catch(IOException ioe){
            System.out.println(ioe);
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