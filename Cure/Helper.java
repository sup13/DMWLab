import java.io.*;
import java.util.*;

class Helper {

    public static ArrayList<Point> getPoints(InputStreamReader ir) {
        ArrayList<Point> points = new ArrayList<Point>();

        try {

            BufferedReader br = new BufferedReader(ir);
            String input = "";
            while ((input = br.readLine()) != null) {
                if (input.trim().equals("")) {
                    continue;
                }
                String[] split = input.trim().split(" ");
                Double x = Double.parseDouble(split[1]);
                Double y = Double.parseDouble(split[2]);
                ArrayList<Double> values = new ArrayList<Double>(Arrays.asList(x, y));
                Point point = new Point(values);
                points.add(point);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return points;
    }
}
