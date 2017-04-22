import java.io.*;
import java.util.*;

class Cure {

    ArrayList<Point> points;
    ArrayList<Cluster> clusters = new ArrayList<Cluster>();

    int noOfClusters = 3;

    public Cure() {
        points = null;
    }

    public Cure(ArrayList<Point> inpPoints) {
        points = inpPoints;
    }

    void run() {
        checkInput();
        //System.out.println(points);
        setInitialClusters();
        setClosest(clusters);

        buildClusters();

    }

    void buildClusters() {
        //PriorityQueue<Cluster> pq = new 
        //
    }

    void setInitialClusters() {
        for (Point point : points) {
            Cluster newCluster = new Cluster(point);
            clusters.add(newCluster);
            point.cluster = newCluster;
            newCluster.rep = new ArrayList<Point>(Arrays.asList(point));
        }
    }

    void setClosest(ArrayList<Cluster> currClusters) {
        for (Cluster cluster : currClusters) {
            Cluster closest = findClosest(currClusters, cluster);
            cluster.closest = closest;
            System.out.println(cluster + " -> " + closest);
        }
    }

    Cluster findClosest(ArrayList<Cluster> allClusters, Cluster currCluster) {
        double minDist = Double.POSITIVE_INFINITY;
        Cluster closest = null;

        for (Cluster cluster : allClusters) {
            if (cluster.equals(currCluster)) {
                continue;
            }
            double dist = findDistance(currCluster, cluster);
            if (dist < minDist) {
                minDist = dist;
                closest = cluster;
            }
        }
        return closest;
    }

    double findDistance(Cluster one, Cluster two) {
        double minDist = Double.POSITIVE_INFINITY;
        for (Point rep1 : one.rep) {
            for (Point rep2 : two.rep) {
                double dist = rep1.findDistance(rep2);
                if (dist < minDist) {
                    minDist = dist;
                }
            }
        }
        return minDist;
    }

    void checkInput() {
        if (points == null) {
            try {
                points = Helper.getPoints(new FileReader("test.txt"));
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
        new Cure().run();
    }
}
