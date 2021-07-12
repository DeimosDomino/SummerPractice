package algorithm;

import java.io.Serializable;

public class Line implements Comparable<Line>, Serializable {
    private final int weight;
    private final Vertex startVertex;
    private final Vertex endVertex;
    private final String nameOfLine;

    public Line(int weight, Vertex start, Vertex end) throws NullPointerException{
        if(start == null || end == null){
            throw new NullPointerException("No vertex for this line");
        }
        this.weight = weight;
        this.startVertex = start;
        this.endVertex = end;
        this.nameOfLine = Integer.toString(start.getId()) + "&" + Integer.toString(end.getId());
    }

    public Vertex getStartVertex() {
        return startVertex;
    }

    public Vertex getEndVertex() {
        return endVertex;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return nameOfLine;
    }

    public boolean equals(Line obj) {
        if (this == obj) {
            return true;
        }
        if (obj.getEndVertex().getId() == this.endVertex.getId()
                && obj.getStartVertex().getId() == this.startVertex.getId()) {
            return true;
        }
        return false;
    }

    public int compareTo(Line obj) {
        if (obj.getEndVertex().getId() > this.endVertex.getId()) {
            return -1;
        }
        if (obj.getEndVertex().getId() < this.endVertex.getId()) {
            return 1;
        }
        return 0;
    }

}
