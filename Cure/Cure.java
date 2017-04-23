import java.io.*;
import java.util.*;

class Cure {

    ArrayList<Point> points;
    ArrayList<Cluster> clusters = new ArrayList<Cluster>();

    int noOfClusters = 3;
    int noOfRepresentatives = 2;
    double alpha = 0.2;

    public Cure() {
        points = null;
    }

    public Cure(ArrayList<Point> inpPoints) {
        points = inpPoints;
    }

    void run() {
        checkInput();
        //System.out.println(points);
        setInitialClusters();
        setClosest(clusters);

        buildClusters();
        printClusters();

    }

    void buildClusters() {
        PriorityQueue<Cluster> pq = new PriorityQueue<Cluster>(1, new Comp());

        addClusters(pq, clusters);
        //System.out.println(pq.peek());
        while (pq.size() > noOfClusters) {
            //System.out.println("PQ Size: " + pq.size());
            Cluster u = pq.poll();
            Cluster v = u.closest;

            pq.remove(v);
            Cluster w = merge(u, v);
            //System.out.println("MERGED");
            //System.out.println("POINTS " + w);
            //System.out.println("REP " + w.rep);
            
            w.closest = pq.peek();
            ArrayList<Cluster> pqList = new ArrayList<Cluster>(pq);
            for (Cluster x : pqList) {
                if (findDistance(w, x) < findDistance(w, w.closest)) {
                    w.closest = x;
                }
                if (x.closest == u || x.closest == v) {
                    if (findDistance(x, x.closest) < findDistance(x, w)) {
                        x.closest = findClosest(pqList, x);
                    } else {
                        x.closest = w;
                    }
                    //pq.remove(x);
                    //it.remove();
                    //pq.add(x);
                } else if (findDistance(x, x.closest) > findDistance(x, w)) {
                    x.closest = w;
                    //pq.remove(x);
                    //it.remove();
                    //pq.add(x);
                }
            }
            pq = new PriorityQueue<Cluster>(1, new Comp());
            addClusters(pq, pqList);
            pq.add(w);
        }
        clusters = new ArrayList<Cluster>(pq);
    }

    Cluster merge(Cluster u, Cluster v) {
        Cluster w = new Cluster();
        w.points.addAll(u.points);
        w.points.addAll(v.points);

        w.mean = computeMean(u, v);

        ArrayList<Point> farthestPoints = new ArrayList<Point>();
        for (int i = 0; i < noOfRepresentatives; i++) {
            double maxDist = 0;
            Point maxPoint = null;
            for (Point point : w.points) {
                double minDist;
                if (i == 0) {
                    minDist = point.findDistance(w.mean);
                } else {
                    minDist = Double.POSITIVE_INFINITY;
                    for (Point q : farthestPoints) {
                        minDist = Math.min(minDist, point.findDistance(q));
                    }
                }
                if (minDist >= maxDist) {
                    maxDist = minDist;
                    maxPoint = point;
                }
            }
            farthestPoints.add(maxPoint);
        }
        w.rep = new ArrayList<Point>();
        for (Point p : farthestPoints) {
            w.rep.add(reduce(p, w.mean, alpha));
        }
        return w;
    }

    Point reduce(Point org, ArrayList<Double> mean, Double alpha) {
        ArrayList<Double> newValues = new ArrayList<Double>();
        for (int i = 0; i < org.values.size(); i++) {
            double orgValue = org.values.get(i);
            double newValue = orgValue + alpha * (mean.get(i) - orgValue);
            newValues.add(newValue);
        }
        return new Point(newValues);
    }

    ArrayList<Double> computeMean(Cluster u, Cluster v) {
        ArrayList<Double> mean = new ArrayList<Double>();
        ArrayList<Double> uMean = u.getMean();
        ArrayList<Double> vMean = v.getMean();

        for (int i = 0; i < u.points.get(0).values.size(); i++) {
            double val = uMean.get(i) * u.points.size() + vMean.get(i) * v.points.size();
            val = val / (u.points.size() + v.points.size());
            mean.add(val);
        }
        return mean;
    }

    void addClusters(PriorityQueue<Cluster> pq, ArrayList<Cluster> currClusters) {
        for (Cluster cluster : currClusters) {
            pq.add(cluster);
        }
    }

    void setInitialClusters() {
        for (Point point : points) {
            Cluster newCluster = new Cluster(point);
            clusters.add(newCluster);
            point.cluster = newCluster;
            newCluster.rep = new ArrayList<Point>(Arrays.asList(point));
        }
    }

    void setClosest(ArrayList<Cluster> currClusters) {
        for (Cluster cluster : currClusters) {
            Cluster closest = findClosest(currClusters, cluster);
            cluster.closest = closest;
            //System.out.println(cluster + " -> " + closest);
        }
    }

    Cluster findClosest(ArrayList<Cluster> allClusters, Cluster currCluster) {
        double minDist = Double.POSITIVE_INFINITY;
        Cluster closest = null;

        for (Cluster cluster : allClusters) {
            if (cluster.equals(currCluster)) {
                continue;
            }
            double dist = findDistance(currCluster, cluster);
            if (dist < minDist) {
                minDist = dist;
                closest = cluster;
            }
        }
        return closest;
    }

    double findDistance(Cluster one, Cluster two) {
        double minDist = Double.POSITIVE_INFINITY;
        for (Point rep1 : one.rep) {
            for (Point rep2 : two.rep) {
                double dist = rep1.findDistance(rep2);
                if (dist < minDist) {
                    minDist = dist;
                }
            }
        }
        return minDist;
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

    class Comp implements Comparator<Cluster> {
        public int compare(Cluster c1, Cluster c2) {
            double ans = (findDistance(c1, c1.closest) - findDistance(c2, c2.closest));
            if (ans > 0) {
                return 1;
            } else if (ans < 0) {
                return -1;
            } else {
                return 0;
            }            
        }
    }
}
