import java.util.*;
import java.io.*;

class Main {

    ArrayList<ArrayList<String>> database = new ArrayList<ArrayList<String>>();
    HashMap<String, Integer> fList = new HashMap<String, Integer>();
    Node fpTree;

    int minSupp = 2; 
    /* Todo: Get support from console */

    private ArrayList<Pair<ArrayList<String>,Integer>> patternBase 
        = new ArrayList<Pair<ArrayList<String>, Integer>>();

    void run() {
        getInput("Data.txt");
        //System.out.println(database);
        calculateFrequency();
        System.out.println(fList);
        sortDatabase();
        System.out.println(database);
        fpTree = createTree();

        findFrequentItemsets(fpTree);
    }

    Node createTree() {
        Node tree = new Node("null");
        for (ArrayList<String> transaction : database) {
            Node branch = tree;
            for (String item : transaction) {
                branch = branch.insertChild(item);
            }
        }
        tree.print();
        return tree;
    }

    void findFrequentItemsets(Node fpTree) {
        for (String item : fList.keySet()) {
            System.out.print(item + " -> ");
            findFrequentItemsets(fpTree, item);
            Node patternTree = createTree(item);
        }        
    }

    Node createTree(String item) {
        return treeFromPatternBase(item, patternBase);
    }

    Node treeFromPatternBase(String item, 
            ArrayList<Pair<ArrayList<String>,Integer>> base) {
        Node root = new Node("null");
        return root;
    }

    void findFrequentItemsets(Node fpTree, String item) {
        patternBase.clear();
        ArrayList<String> path = new ArrayList<String>();
        findPatternBase(fpTree, item, path);

        System.out.println(patternBase);
    }

    void findPatternBase(Node currNode, String item, ArrayList<String> path) {
        if (currNode.itemname.equals(item)) {
            patternBase.add(new Pair<ArrayList<String>,Integer>
                    ((ArrayList<String>)path.clone(), currNode.count));
        } else {
            path.add(currNode.itemname);
            for (Node child : currNode.children) {
                findPatternBase(child, item, path);
            }
            path.remove(currNode.itemname);
        }
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
