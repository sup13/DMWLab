import java.io.*;
import java.util.*;

class Cure {

    ArrayList<Point> points;
    ArrayList<Cluster> clusters = new ArrayList<Cluster>();

    public Cure() {
        points = null;
    }

    public Cure(ArrayList<Point> inpPoints) {
        points = inpPoints;
    }

    void run() {
        checkInput();
        System.out.println(points);
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
        new Cure().run();
    }
}

        
