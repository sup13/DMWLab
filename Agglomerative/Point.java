import java.util.*;

class Point {

    ArrayList<Double> values;
    Cluster cluster = null;

    public Point() {
        values = new ArrayList<Double>();
    }

    public Point(ArrayList<Double> values) {
        this.values = values;
    }

    double findDistance(Point two) {
        double ans = (this.values.get(0) - two.values.get(0)) * (this.values.get(0) - two.values.get(0));
        ans += (this.values.get(1) - two.values.get(1)) * (this.values.get(1) - two.values.get(1));
        return Math.sqrt(ans);
    }

    @Override
    public String toString() {
        return values.toString();
    }
}







