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
        double ans = 0;
        for (int i = 0; i < values.size(); i++) {
            ans += (this.values.get(i) - two.values.get(i)) * (this.values.get(i) - two.values.get(i));
        }
        return Math.sqrt(ans);
    }

    double findDistance(ArrayList<Double> two) {
        Point temp = new Point(two);
        return findDistance(temp);
    }

    @Override
    public String toString() {
        return values.toString();
    }
}







