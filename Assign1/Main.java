import java.util.*;
import java.io.*;

class Main {

    ArrayList< HashMap<String, Integer> > database = new ArrayList< HashMap<String, Integer> >();

    ArrayList< HashMap< ArrayList<String>, Integer > > store = new ArrayList< HashMap< ArrayList<String>, Integer> >();

    int support = 2000; /*Min support count*/

    void run() {
        getInput("data/chess.dat");
        generateL1();
        int lNumber = 2;
        while (true) {
            boolean isEmpty = generateL(lNumber);
            if (isEmpty == true) {
                break;
            }
            lNumber++;
        }
        //createAssociations();
    }
    
    void generateL1() {
        HashMap< ArrayList<String>, Integer> l1 = new HashMap< ArrayList<String>, Integer>();
        /*Not for general cases*/
        for (int i = 1; i <= 75; i++) {
            int count = 0;
            String item = "" + i;
            
            ArrayList<String> itemset = new ArrayList<String>();
            itemset.add(item);

            count += getFrequency(itemset);

            if (count >= support) {
                l1.put(itemset, count);
                System.out.println(itemset + " " + count);
            }
        }
        store.add(l1);
    }

    boolean generateL(int lNumber) {
        boolean isEmpty = true;
        HashMap<ArrayList<String>, Integer> current = new HashMap<ArrayList<String>, Integer>();
        HashMap< ArrayList<String>, Integer> previous = store.get(lNumber - 2);

        ArrayList< ArrayList<String>> entryArr = new ArrayList<ArrayList<String>>(previous.keySet());

        for (int i = 0; i < entryArr.size(); i++) {
            for (int j = i + 1; j < entryArr.size(); j++) {
                ArrayList<String> first = entryArr.get(i);
                ArrayList<String> second = entryArr.get(j);

                if (isCompitable(first, second)) {
                    ArrayList<String> joined = join(first, second);

                    if (isApriori(joined, previous)) {

                        int count = getFrequency(joined);
                        if (count >= support) {
                            isEmpty = false;
                            current.put(joined, count);
                            System.out.println(joined + " " + count);
                        }
                    }
                }
            }
        }
        if (!isEmpty) {
            store.add(current);
        }
        return isEmpty;
    }

    boolean isApriori(ArrayList<String> itemset, HashMap<ArrayList<String>, Integer> previous) {
        ArrayList<ArrayList<String>> subsets = createSubsets(itemset);

        for (int i = 0; i < subsets.size(); i++) {
            ArrayList<String> currentSubset = subsets.get(i);
            if (previous.get(currentSubset) == null) {
                return false;
            }
        }
        return true;
    }

    int getFrequency(ArrayList<String> itemset) {
        int frequency = 0;
        for (HashMap<String, Integer> transaction : database) {
            int minimum = 10000;
            for (String item : itemset) {
                if (transaction.get(item) != null) {
                   if (transaction.get(item) < minimum) {
                       minimum = transaction.get(item);
                   }
                } else {
                    minimum = 0;
                }
            }
            frequency += minimum;
        }
        return frequency;
    }

    boolean isCompitable(ArrayList<String> first, ArrayList<String> second) {
        for (int i = 0; i < first.size() - 1; i++) {
            if (!first.get(i).equals(second.get(i))) {
                return false;
            }
        }
        return true;
    }

    ArrayList<String> join(ArrayList<String> first, ArrayList<String> second) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < first.size() - 1; i++) {
            result.add(first.get(i));
        }
        result.add(first.get(first.size() - 1));
        result.add(second.get(second.size() - 1));
        return result;
    }

    ArrayList<ArrayList<String>> createSubsets(ArrayList<String> set) {
        ArrayList<ArrayList<String>> subsets = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < set.size(); i++) {
            ArrayList<String> currentSubset = new ArrayList<String>();
            for (int j = 0; j < set.size(); j++) {
                if (j == i) {
                    continue;
                }
                currentSubset.add(set.get(j));
            }
            subsets.add(currentSubset);
        }
        return subsets;
    }


    void fillDatabase(String input) {
        String[] split = input.split(" ");
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        for (String item : split) {
            if (hm.get(item) == null) {
                hm.put(item, 1);
            } else {
                hm.put(item, hm.get(item) + 1);
            }
        }
        database.add(hm);
    }

    void getInput(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String input;
            while ((input = br.readLine()) != null) {
                fillDatabase(input);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
