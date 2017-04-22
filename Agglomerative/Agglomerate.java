import java.util.*;
import java.io.*;

class Agglomerate {

    ArrayList<Point> points;
    ArrayList<Cluster> clusters = new ArrayList<Cluster>();

    int noOfClusters = 3;

    public Agglomerate() {
        points = null;
    }

    public Agglomerate(ArrayList<Point> inpPoints) {
        points = inpPoints;
    }

    void run() {
        checkInput();
        //System.out.println(points);
        assignClusters();
        agglomerate();
        printClusters();
        //System.out.println(clusters);
    }

    void agglomerate() {
        while (clusters.size() > noOfClusters) {
            agglomerate(clusters);
        }
    }

    void agglomerate(ArrayList<Cluster> clusters) {
        double minDist = Double.POSITIVE_INFINITY;
        Cluster one = null;
        Cluster two = null;
        for (int i = 0; i < clusters.size(); i++) {
            for (int j = i+1; j < clusters.size(); j++) {
                double dist = completeLink(clusters.get(i), clusters.get(j));
                if (dist < minDist) {
                    minDist = dist;
                    one = clusters.get(i);
                    two = clusters.get(j);
                }
            }
        }
        combine(clusters, one, two);
    }

    void combine(ArrayList<Cluster> clusters, Cluster one, Cluster two) {
        clusters.remove(two);
        for (int i = 0; i < two.points.size(); i++) {
            Point point = two.points.get(i);
            point.cluster = one;
            one.points.add(point);
        }
    }

    double completeLink(Cluster one, Cluster two) {
        double maxDist = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < one.points.size(); i++) {
            Point first = one.points.get(i);
            for (int j = 0; j < two.points.size(); j++) {
                Point second = two.points.get(j);
                double currDist = first.findDistance(second);
                if (currDist > maxDist) {
                    maxDist = currDist;
                }
            }
        }
        return maxDist;
    }

    void assignClusters() {
        for (int i = 0; i < points.size(); i++) {
            Cluster cluster = new Cluster(points.get(i));
            points.get(i).cluster = cluster;
            clusters.add(cluster);
        }
    }

    void checkInput() {
        if (points == null) {
            try {
                points = Helper.getPoints(new FileReader("example.txt"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    void printClusters() {
        for (Cluster cluster : clusters) {
            System.out.println(cluster);
        }
    }

    public static void main(String[] args) {
        new Agglomerate().run();
    }
}
