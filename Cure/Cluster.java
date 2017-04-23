import java.util.*;

class Cluster {
    ArrayList<Point> points;

    ArrayList<Double> mean;

    ArrayList<Point> rep;

    Cluster closest;

    public Cluster() {
        points = new ArrayList<Point>();
    }

    public Cluster(Point point) {
        points = new ArrayList<Point>();
        points.add(point);
    }

    ArrayList<Double> getMean() {
        mean = new ArrayList<Double>();
        for (int i = 0; i < points.get(0).values.size(); i++) {
            double sum = 0;
            for (Point point : points) {
                sum += point.values.get(i);
            }
            double avg = sum / points.size();
            mean.add(avg);
        }
        return mean;
    }

    @Override
    public String toString() {
        return points.toString();
    }
}
