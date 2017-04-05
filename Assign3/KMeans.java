import java.util.*;
import java.io.*;

class KMeans {

    ArrayList<Pair<Double,Double>> data;
    ArrayList<Integer> indices = new ArrayList<Integer>();

    ArrayList<Cluster> clusters = new ArrayList<Cluster>();

    /* Todo: Get K from console */
    int k = 2;

    public KMeans(ArrayList<Pair<Double,Double>> inputData) {
        data = inputData;
    }

    public KMeans() {
        data = null;
    }

    void run() {
        checkInput();
        printData();

        assignInitial();
        performClustering();
        printClusters();
    }

    void assignInitial() {
        for (int i = 0; i < data.size(); i++) {
            indices.add(-1);
        }

        /* Todo: assign k random points */
        clusters.add(new Cluster(data.get(0)));
        clusters.add(new Cluster(data.get(1)));
        indices.set(0, 0);
        indices.set(1, 1);

    }

    void performClustering() {

        while (true) {
            ArrayList<Pair<Double,Double>> means = calculateMeans(clusters);

            boolean change = false;
            for (int i = 0; i < data.size(); i++) {
                Pair<Double,Double> point = data.get(i);
                int newIndex = findNearest(point, means);
                int prevIndex = indices.get(i);
                if (prevIndex == newIndex) {
                    continue;
                } else {
                    change = true;
                    if (prevIndex != -1) {
                        clusters.get(prevIndex).points.remove(point);
                    }                    
                    clusters.get(newIndex).points.add(point);
                    indices.set(i, newIndex);
                }
            }
            if (!change) {
                break;
            }
        }
    }

    int findNearest(Pair<Double,Double> point, ArrayList<Pair<Double,Double>> centers) {
        double minDist = Double.POSITIVE_INFINITY;
        int index = -1;

        for (int i = 0; i < centers.size(); i++) {
            double dist = findDistance(point, centers.get(i));
            if (dist < minDist) {
                minDist = dist;
                index = i;
            }
        }
        return index;
    }

    double findDistance(Pair<Double,Double> one, Pair<Double,Double> two) {
        double ans = (one.first - two.first) * (one.first - two.first);
        ans += (one.second - two.second) * (one.second - two.second);
        return Math.sqrt(ans);
    }

    ArrayList<Pair<Double,Double>> calculateMeans(ArrayList<Cluster> clusters) {
        ArrayList<Pair<Double,Double>> means = new ArrayList<Pair<Double,Double>>();
        for (Cluster cluster : clusters) {
            means.add(cluster.calculateMean());
        }
        return means;
    }

    void printClusters() {
        for (Cluster cluster : clusters) {
            System.out.println(cluster.points);
        }
    }

    void printData() {
        System.out.println("DATA");
        for (Pair<Double,Double> point : data) {
            System.out.println(point.first + " " + point.second);
        }
        System.out.println("");
    }

    void checkInput() {        
        if (data == null) {
            try {
                data = Helper.getData(new FileReader("data.txt"));
            } catch (FileNotFoundException fex) {
                fex.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        new KMeans().run();
    }
}

