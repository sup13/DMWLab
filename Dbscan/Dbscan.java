import java.util.*;
import java.io.*;

class Dbscan {

    ArrayList<Point> points;
    ArrayList<Cluster> clusters = new ArrayList<Cluster>();

    public Dbscan() {
        points = null;
    }

    public Dbscan(ArrayList<Point> inpPoints) {
        points = inpPoints;
    }

    void run() {
        checkInput();
        System.out.println(points);
    }

    void checkInput() {
        if (points == null) {
            try {
                points = Helper.getPoints(new FileReader("data.txt"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Dbscan().run();
    }
}

