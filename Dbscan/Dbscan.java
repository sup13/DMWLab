import java.util.*;
import java.io.*;

class Dbscan {

    ArrayList<Point> points;
    ArrayList<Cluster> clusters = new ArrayList<Cluster>();

    double maxDist = 4;
    int minPoints = 6;

    public Dbscan() {
        points = null;
    }

    public Dbscan(ArrayList<Point> inpPoints) {
        points = inpPoints;
    }

    void run() {
        checkInput();
        //System.out.println(points);

        buildClusters(points);
        System.out.println("No of clusters: " + clusters.size());
        printNoise();

    }

    void buildClusters(ArrayList<Point> points) {
        for (Point point : points) {
            if (point.isClassified == true) {
                continue;
            }
            ArrayList<Point> neighbours = findNeighbours(points, point, maxDist);
            //System.out.println(neighbours);
            if (neighbours.size() >= minPoints) {
                point.isClassified = true;
                point.type = Type.CORE;
                Cluster newcluster = new Cluster(point);
                clusters.add(newcluster);
                point.cluster = newcluster;
                expandCluster(neighbours, points, newcluster);
            } else {
                point.type = Type.NOISE;
            }
        }
    }

    void expandCluster(ArrayList<Point> neighbours, ArrayList<Point> points, Cluster cluster) {
        for (Point point : neighbours) {
            if (point.isClassified == true) {
                continue;
            }
            point.isClassified = true;
            point.cluster = cluster;
            cluster.points.add(point);
            ArrayList<Point> newNeighbours = findNeighbours(points, point, maxDist);
            if (newNeighbours.size() >= minPoints) {
                point.type = Type.CORE;
                expandCluster(newNeighbours, points, cluster);
            } else {
                point.type = Type.BOUNDARY;
            }
        }
    }

    ArrayList<Point> findNeighbours(ArrayList<Point> points, Point currPoint, double maximumDist) {
        ArrayList<Point> neighbours = new ArrayList<Point>();
        for (Point point : points) {
            if (point.equals(currPoint)) {
                continue;
            }
            double currDist = currPoint.findDistance(point);
            if (currDist <= maximumDist) {
                neighbours.add(point);
            }
        }
        return neighbours;
    }

    void checkInput() {
        if (points == null) {
            try {
                points = Helper.getPoints(new FileReader("input.txt"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    void printNoise() {
        for (Point point : points) {
            if (point.type == Type.NOISE) {
                System.out.println(point);
            }
        }
    }

    public static void main(String[] args) {
        new Dbscan().run();
    }
}

