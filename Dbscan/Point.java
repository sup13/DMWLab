import java.util.*;

class Point {

    ArrayList<Double> values;
    boolean isClassified = false;
    Cluster cluster = null;
    Type type = null;

    public Point() {
        values = new ArrayList<Double>();
    }

    public Point(ArrayList<Double> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return values.toString();
    }
}







