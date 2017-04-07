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

        for (; k < data.size(); k++) {
            System.out.println("K = " + k);
            clusters.clear();
            assignInitial();
            performClustering();
            printClusters();            
            double avgDist = findAverageDistance(clusters);
            System.out.println("Average distance: " + findAverageDistance(clusters));
            System.out.println("");
        }
    }

    void assignInitial() {
        for (int i = 0; i < data.size(); i++) {
            indices.set(i, -1);
        }

        /* Assign k random points */
        HashMap<Integer,Boolean> taken = new HashMap<Integer,Boolean>();
        for (int i = 0; i < k; i++) {
            int newpoint = findRandomPoint(taken);
            System.out.println("Random point " + i + ": " + newpoint);

            clusters.add(new Cluster(data.get(newpoint)));
            indices.set(newpoint, i);
        }
    }

    void assignInitialNonRandom() {
        for (int i = 0; i < data.size(); i++) {
            indices.set(i, -1);
        }

        ArrayList<Integer> centers = new ArrayList<Integer>();
        int randomPoint = findRandomPoint();
        centers.add(randomPoint);
        System.out.println("Random point 0" + ": " + randomPoint);
        clusters.add(new Cluster(data.get(randomPoint)));
        indices.set(randomPoint, 0);

        for (int i = 1; i < k; i++) {
            int newpoint = selectNewpoint(centers);
            System.out.println("Next point " + i + ": " + newpoint);
            
            clusters.add(new Cluster(data.get(newpoint)));
            indices.set(newpoint, i);
            centers.add(newpoint);
        }
    }

    int selectNewpoint(ArrayList<Integer> centers) {
        double maxDist = 0;
        int maxPoint = -1;
        for (int i = 0; i < data.size(); i++) {
            double currDist = 0;
            Pair<Double,Double> point = data.get(i);
            for (Integer index : centers) {
                Pair<Double,Double> centerPoint = data.get(index);
                currDist += findDistance(point, centerPoint);
            }
            if (currDist > maxDist && (centers.indexOf(i) == -1)) {
                maxDist = currDist;
                maxPoint = i;
            }
        }
        return maxPoint;
    }

    int findRandomPoint() {
        HashMap<Integer,Boolean> taken = new HashMap<Integer,Boolean>();
        return findRandomPoint(taken);
    }

    int findRandomPoint(HashMap<Integer,Boolean> taken) {
        int num = -1;
        while (true) {
            double current = Math.random();
            if (current == 1) {
                continue;
            }
            num = (int)(current * data.size());
            if (taken.get(num) != null) {
                continue;
            }
            taken.put(num, true);
            break;
        }
        return num;
    }
            
    void performClustering() {
        int counter = 0;
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
            counter++;
            if (!change) {
                break;
            }        
        }
        System.out.println("Iterations: " + counter);
    }

    double findAverageDistance(ArrayList<Cluster> clusters) {
        double avgDist = 0;
        for (Cluster cluster : clusters) {
            Pair<Double,Double> mean = cluster.calculateMean();
            for (Pair<Double,Double> point : cluster.points) {
                double dist = findDistance(mean, point);
                avgDist += dist * dist;
            }
        }
        return avgDist;
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
        for (int i = 0; i < data.size(); i++) {
            indices.add(-1);
        }
    }

    public static void main(String[] args) {
        new KMeans().run();
    }
}

