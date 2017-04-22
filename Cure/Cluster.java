import java.util.*;

class Cluster {
    ArrayList<Point> points;

    public Cluster() {
        points = new ArrayList<Point>();
    }

    public Cluster(Point point) {
        points = new ArrayList<Point>();
        points.add(point);
    }

    @Override
    public String toString() {
        return points.toString();
    }
}
