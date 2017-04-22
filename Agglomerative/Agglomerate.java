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

    public static void main(String[] args) {
        new Agglomerate().run();
    }
}
