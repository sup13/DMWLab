import java.util.*;

class Cluster {

    ArrayList<Pair<Double,Double>> points;

    public Cluster() {
        points = new ArrayList<Pair<Double,Double>>();
    }

    public Cluster(Pair<Double,Double> point) {
        points = new ArrayList<Pair<Double,Double>>();
        points.add(point);
    }

    public Cluster(ArrayList<Pair<Double,Double>> pnts) {
        points = pnts;
    }

    Pair<Double,Double> calculateMean() {
        double sumx = 0;
        double sumy = 0;
        for (Pair<Double,Double> point : points) {
            sumx += point.first;
            sumy += point.second;
        }
        double meanx = sumx / points.size();
        double meany = sumy / points.size();
        return new Pair<Double,Double>(meanx, meany);
    }
}

