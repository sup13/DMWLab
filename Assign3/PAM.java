import java.util.*;
import java.io.*;

class PAM {

    ArrayList<Pair<Double,Double>> data;

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
        new PAM().run();
    }
}

