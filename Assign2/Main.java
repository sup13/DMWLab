import java.util.*;
import java.io.*;

class Main {

    ArrayList<ArrayList<String>> database = new ArrayList<ArrayList<String>>();
    HashMap<String, Integer> fList = new HashMap<String, Integer>();
    Node tree;

    void run() {
        getInput("Data.txt");
        System.out.println(database);
        calculateFrequency();
        System.out.println(fList);
        sortDatabase();
        System.out.println(database);
        createTree();
    }

    void createTree() {
        tree = new Node("null");
        for (ArrayList<String> transaction : database) {
            Node branch = tree;
            for (String item : transaction) {
                branch = branch.insertChild(item);
            }
        }
        //System.out.println("AAAAA");
        tree.print();
    }

    class Comp implements Comparator<String> {
        public int compare(String item1, String item2) {
            /*For reverse sorting*/
            return fList.get(item2) - fList.get(item1);
        }
    }

    void sortDatabase() {
        for (ArrayList<String> transaction : database) {
            Collections.sort(transaction, new Comp());
        }
    }

    void calculateFrequency() {
        for (ArrayList<String> transaction : database) {
            for (String item : transaction) {
                if (fList.get(item) == null) {
                    fList.put(item, 1);
                } else {
                    fList.put(item, fList.get(item) + 1);
                }
            }
        }
    }

    void getInput(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String input;
            while ((input = br.readLine()) != null) {
                parseInput(input);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void parseInput(String input) {
        String[] split = input.split(" ");
        ArrayList<String> transaction = new ArrayList<String>();
        for (String item : split) {
            transaction.add(item);
        }
        database.add(transaction);
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
