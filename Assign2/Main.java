import java.util.*;
import java.io.*;

class Main {

    ArrayList<ArrayList<String>> database = new ArrayList<ArrayList<String>>();
    HashMap<String, Integer> fList = new HashMap<String, Integer>();
    Node fpTree;

    int minSupp = 2; 
    /* Todo: Get support from console */

    private ArrayList<ArrayList<String>> patternBase 
        = new ArrayList<ArrayList<String>>();
    private ArrayList<Integer> patternBaseCounter
        = new ArrayList<Integer>();

    ArrayList<ArrayList<String>> frequentItems
        = new ArrayList<ArrayList<String>>();

    void run() {
        getInput("Data.txt");
        //System.out.println(database);
        calculateFrequency();
        System.out.println(fList);
        sortDatabase();
        System.out.println(database);
        fpTree = createTree(database);

        findFrequentItemsets(fpTree);
    }

    Node createTree(ArrayList<ArrayList<String>> data) {
        ArrayList<Integer> counter = new ArrayList<Integer>();
        for (int i = 0; i < data.size(); i++) {
            counter.add(1);
        }
        return createTree(data, counter);
    }

    Node createTree(ArrayList<ArrayList<String>> data, 
            ArrayList<Integer> counter) {
        Node tree = new Node("null");
        for (int i = 0; i < data.size(); i++) {
            ArrayList<String> transaction = data.get(i);
            for (int j = 0; j < counter.get(i); j++) {
                Node branch = tree;
                for (String item : transaction) {
                    branch = branch.insertChild(item);
                }
            }
        }
        tree.print();
        return tree;
    }

    void findFrequentItemsets(Node fpTree) {
        for (String item : fList.keySet()) {
            System.out.print(item + " -> ");
            findFrequentItemsets(fpTree, item);

            Node patternTree = createTree(patternBase, patternBaseCounter);
            System.out.println("");
            mine(patternTree);
        }        
    }

    void mine(Node patternTree) {
        for (Node child : patternTree.children) {
            ArrayList<String> path = new ArrayList<String>();
            mine(child, path);
        }
        System.out.println(frequentItems);
        frequentItems.clear();
    }

    void mine(Node patternTree, ArrayList<String> path) {
        if (patternTree.count < minSupp) {
            return;
        }
        ArrayList<String> current = new ArrayList<String>();
        current.add(patternTree.itemname);
        frequentItems.add(current);
        path.add(patternTree.itemname);
        if (path.size() > 1) {
            ArrayList<String> copyPath = new ArrayList<String>(path);
            frequentItems.add(copyPath);
        }
        for (Node child : patternTree.children) {
            mine(child, path);
        }
    }

    void findFrequentItemsets(Node fpTree, String item) {
        patternBase.clear();
        patternBaseCounter.clear();
        ArrayList<String> path = new ArrayList<String>();
        findPatternBase(fpTree, item, path);

        System.out.println(patternBase);
    }

    void findPatternBase(Node currNode, String item, ArrayList<String> path) {
        if (currNode.itemname.equals(item)) {
            ArrayList<String> currPath = new ArrayList<String>(path);
            patternBase.add(removeNull(currPath));
            patternBaseCounter.add(currNode.count);
        } else {
            path.add(currNode.itemname);
            for (Node child : currNode.children) {
                findPatternBase(child, item, path);
            }
            path.remove(currNode.itemname);
        }
    }

    ArrayList<String> removeNull(ArrayList<String> arr) {
        if (arr.size() <= 1) {
            return new ArrayList<String>();
        } else {
            ArrayList<String> removed = new ArrayList<String>(arr.subList(1,
                        arr.size()));
            return removed;
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
