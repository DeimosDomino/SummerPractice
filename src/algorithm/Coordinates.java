package algorithm;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private double x;
    private double y;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setCord(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void moveCord(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distance(Coordinates a) {
        return Math.sqrt(Math.pow(this.x - a.getX(), 2) + Math.pow(this.y - a.getY(), 2));
    }

    public boolean equals(Coordinates a) {
        if (this == a) {
            return true;
        }
        if (Math.abs(this.x - a.getX()) < 0.000001 && Math.abs(this.y - a.getY()) < 0.000001) {
            return true;
        }
        return false;
    }

    public String toString() {
        return ("(" + x + ";" + y + ")");
    }
}
