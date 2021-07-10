package instruments;

import java.lang.reflect.Array;
import java.util.ArrayList;

import algorithm.Graph;

public class GraphStates {
    private ArrayList<GraphState> states;
    private int curIndex = -1;
    public GraphStates(){
        this.states = new ArrayList<GraphState>();
        this.add(new Graph());
    }

    public Graph next(){
        if(this.curIndex+1 != states.size())
            curIndex++;
        return states.get(curIndex).get();
    }

    public Graph getCur(){
        try{
            return this.states.get(curIndex).get();
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    
    }

    public Graph prev(){
        if(this.curIndex > 0)
            this.curIndex--;
        return states.get(curIndex).get();
    }

    public void add(Graph graph){
        while(curIndex+1 < this.states.size()){
            this.states.remove(this.states.size()-1);
        }
        states.add(new GraphState(graph));
        this.curIndex++;
    }

}
