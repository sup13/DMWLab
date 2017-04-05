import java.io.*;
import java.util.*;

class Helper {

    static ArrayList<Pair<Double,Double>> getData(InputStreamReader sr) {
        ArrayList<Pair<Double,Double>> inp = new ArrayList<Pair<Double,Double>>();
        try {
            BufferedReader br = new BufferedReader(sr);
            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                currentLine = currentLine.trim();
                if (currentLine.equals("")) {
                    continue;
                }
                String[] split = currentLine.split(" ");
                Double first = Double.parseDouble(split[1]);
                Double second = Double.parseDouble(split[2]);
                Pair<Double,Double> point = new Pair<Double,Double>(first,second);
                inp.add(point);
            }
        } catch (IOException iex) {
            iex.printStackTrace();
        }
        return inp;
    }
}
