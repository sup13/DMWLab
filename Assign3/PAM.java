import java.util.*;
import java.io.*;

class PAM {

    ArrayList<Pair<Double,Double>> data;
    ArrayList<Boolean> isMediod;    

    ArrayList<Cluster> clusters = new ArrayList<Cluster>();

    int k = 2;

    public PAM(ArrayList<Pair<Double,Double>> inputData) {
        data = inputData;
    }

    public PAM() {
        data = null;
    }

    void run() {
        checkInput();
        printData();

        assignInitial();

        findMediods();
        printMediods();

        assignClusters();
        printClusters();
    }

    void assignClusters() {
        for (int i = 0; i < k; i++) {
            clusters.add(new Cluster());
        }
        ArrayList<Pair<Double,Double>> mediods = createMediods();
        for (int i = 0; i < data.size(); i++) {
            Pair<Double,Double> point = data.get(i);
            int nearest = findNearest(point, mediods);
            clusters.get(nearest).points.add(point);
        }
    }

    void findMediods() {
        while (true) {
            boolean flag = false;
            for (int i = 0; i < data.size(); i++) {
                if (isMediod.get(i) == false) {
                    continue;
                }
                double prevCost = findCost();
                for (int j = 0; j < data.size(); j++) {
                    if (isMediod.get(j) == true) {
                        continue;
                    }
                    isMediod.set(i, false);
                    isMediod.set(j, true);
                    double newCost = findCost();
                    if (newCost < prevCost) {
                        flag = true;
                        break;
                    } else {
                        isMediod.set(i, true);
                        isMediod.set(j, false);
                    }
                }
                if (flag == true) {
                    break;
                }
            }
            if (flag == false) {
                break;
            }
        }
    }

    double findCost() {
        double totalCost = 0;
        ArrayList<Pair<Double,Double>> mediods = createMediods();
        for (int i = 0; i < data.size(); i++) {
            Pair<Double,Double> point = data.get(i);
            int nearest = findNearest(point, mediods);
            totalCost += findDistance(point, mediods.get(nearest));
        }
        return totalCost;
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

    void assignInitial() {
        /* Assign k random points */
        HashMap<Integer,Boolean> taken = new HashMap<Integer,Boolean>();
        for (int i = 0; i < k; i++) {
            int newpoint = findRandomPoint(taken);
            System.out.println("Random point " + i + ": " + newpoint);

            isMediod.set(newpoint, true);
        }
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

    ArrayList<Pair<Double,Double>> createMediods() {
        ArrayList<Pair<Double,Double>> mediods = new ArrayList<Pair<Double,Double>>();
        for (int i = 0; i < data.size(); i++) {
            if (isMediod.get(i) == true) {
                mediods.add(data.get(i));
            }
        }
        return mediods;
    }
            
    void printData() {
        System.out.println("DATA");
        for (Pair<Double,Double> point : data) {
            System.out.println(point.first + " " + point.second);
        }
        System.out.println("");
    }

    void printMediods() {
        for (int i = 0; i < data.size(); i++) {
            if (isMediod.get(i) == true) {
                System.out.println("Mediod: " + i + " Value: " + 
                        data.get(i).first + "," + data.get(i).second);
            }
        }
    }

    void printClusters() {
        for (Cluster cluster : clusters) {
            System.out.println(cluster.points);
        }
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
        isMediod = new ArrayList<Boolean>();
        for (int i = 0; i < data.size(); i++) {
            isMediod.add(false);
        }
    }

    public static void main(String[] args) {
        new PAM().run();
    }
}

