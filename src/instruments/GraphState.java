package instruments;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import algorithm.*;

public class GraphState {
    private byte[] grState;
    public GraphState(Graph graph){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream ois = new ObjectOutputStream(baos);
            ois.writeObject(graph);
            grState = baos.toByteArray();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public Graph get(){
        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(grState);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Graph)ois.readObject();
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }


}
