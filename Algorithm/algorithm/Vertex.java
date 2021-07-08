package algorithm;
public class Vertex implements Comparable<Vertex>{
    private final int idOfVertex;
    private Coordinates cordOfVertex;
    private final static double DIAMETER = 30;

    public Vertex(int id, double x, double y){
        this.idOfVertex = id;
        this.cordOfVertex = new Coordinates(x, y);
    }

    public double getDiameter(){
        return DIAMETER;
    }

    public int getId(){
        return idOfVertex;
    }

    public Coordinates get(){
        return cordOfVertex;
    }

    public double getX(){
        return cordOfVertex.getX();
    }

    public double getY(){
        return cordOfVertex.getY();
    }

    public void setCordOfVertex(double x, double y){
        this.cordOfVertex.setCord(x, y);
    }

    public double distance(Vertex a){
        return Math.abs(this.get().distance(a.get()));
    }

    public boolean equals(Vertex a){
        if(this == a){
            return true;
        }
        if(this.idOfVertex == a.getId() && this.cordOfVertex.equals(a.get())){
            return true;
        }
        return false;
    }

    public int compareTo(Vertex obj){
        if(obj.getId() > this.idOfVertex){
            return -1;
        }
        if(obj.getId() < this.idOfVertex){
            return 1;
        }
        return 0;
    }
}
