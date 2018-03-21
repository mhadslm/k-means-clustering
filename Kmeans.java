/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmeans;

/**
 *
 * @author Muhammad Salim
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Random;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.util.Scanner;

public class Kmeans {

    //Number of Clusters. This metric should be related to the number of points
    private int clusters_count = 0;
    //Number of Points
    private int points_count = 0;
    //Number of attributes. This metric should be related to the number of fields in the dataset
    private int attributes_count = 0;
    //centroid initialization method
    private int centroid_init = 0;
    private int index = 0;

    private int iterations = 0;

    private List<Point> points;
    private List<Cluster> clusters;

    public Kmeans(File ds, int clusters_count, int init, int index) {

        this.clusters_count = clusters_count;
        this.centroid_init = init;
        this.index = index;
        this.points = new ArrayList<Point>();
        this.clusters = new ArrayList<Cluster>();

        /* loading Points from the dataset and creating clusters list
         with the initial centroids*/
        setLists(ds);

    }

    //The process to calculate the K Means, with iterating method.
    public void calculate() {
        boolean finish = false;
        int iteration = 0;
        // Add in new data, one at a time, recalculating centroids with each new one. 
        while (!finish) {
            //Clear cluster state
            clearClusters();
            List<Point> lastCentroids = getCentroids();

//Step 1: Assign points to the closer cluster
            assignCluster();

//Step 2: Calculate new centroids.
            calculateCentroids();
            iteration++;
            List<Point> currentCentroids = getCentroids();

//Step 3: Calculates total distance between new and old Centroids
            double distance = 0.0;
            /* in order to terminat the iteratin
             all the centroids must have the same attributes after the calculation step*/
            for (int i = 0; i < clusters_count; i++) {
                distance = distance + distance(lastCentroids.get(i), currentCentroids.get(i));
            }
//Step 4: testing for termination
            if (distance == 0) {
                finish = true;
                this.iterations = iteration;
                writeAllClusterstoFile();
            }
        }//while loop

    }

    private void assignCluster() {

        //Cluster c = null;
        //Point p = null;
        double max = Double.MAX_VALUE;
        double min = max;
        int cluster = 0;
        double d = 0.0;

        for (int i = 0; i < points.size(); i++) {
            //p = points.get(i);
            min = max;
            for (int j = 0; j < clusters.size(); j++) {
                //c = clusters.get(j);
                d = distance(points.get(i), clusters.get(j).getCentroid());
                if (d < min) {
                    min = d;
                    cluster = j;

                }
            }
            points.get(i).setCluster(cluster);
            clusters.get(cluster).addPoint(points.get(i));
        }
    }

    private void calculateCentroids() {
        /*calculate the new centroids attributes  for all clusters according 
         to the new points added in the last iteration*/

        Iterator ir;

        for (int i = 0; i < clusters_count; i++) {

            List<Point> cp = clusters.get(i).getPoints();
            // the cluster size
            int cl = cp.size();

            //Filling the 2D array
            //load the ith cluster points attributes in 2D array to calculate the new centroid
            double temp2darray[][] = new double[cl][attributes_count - 1];//the first atttribute will be ignored
            List<Double> attributes = null;
            for (int j = 0; j < cl; j++) {
                //the jth point attributes
                attributes = cp.get(j).getAttributes();
                ir = attributes.iterator();
                int k = 0;
                if (ir.hasNext()) {
                    ir.next();//ignore first atttribute
                }
                while (ir.hasNext()) {

                    //for (int k = 0; k < attributes_count; k++) {
                    temp2darray[j][k] = (Double) ir.next();
                    k++;

                    /*
                     a1      a2      a3        ... an  (l)
                     p1  p1a1    p1a2    p3a3                   
                     p2  p2a1    p2a2    p2a3
                     p3  p3a1    p3a2    p3a3
                     .
                     .
                        
                     pn
                     */
                }
            }
            /*calculate the new centroid according to the 2D array
            
             */

            if (cl > 0) {// the cluster is not empty
                List<Double> new_centroid_attributes = new ArrayList<Double>();

                /* the first attribute wich is the actual classification of the instance will not be changed
                 it will be transfered from the previous centroid atrributes to the new one
                 */
                //copying the first attribute
                new_centroid_attributes.add(clusters.get(i).getCentroid().getAttributes().get(i));
                //calculate the other attributs from the points included in the cluster
                double newvalue = 0;
                for (int l = 0; l < attributes_count - 1; l++) {//size decreased by 1 as the first attributed was ignored
                    newvalue = 0;
                    for (int r = 0; r < cl; r++) {
                        /*
                         a1      a2      a3        ... an  (l)
                         p1  p1a1    p1a2    p3a3                   
                         p2  p2a1    p2a2    p2a3
                         p3  p3a1    p3a2    p3a3
                         .
                         .
                        
                         pn
                        
                         (r)
                        
                         temp = [0][1]+
                         [1][1]+
                         [2][1]+
                         [3][1]
                        
                         */
                        newvalue = newvalue + temp2darray[r][l];
                    }
                    new_centroid_attributes.add((Double.valueOf(newvalue / cl)));

                    //System.out.println("newvalue : " + newvalue);
                    //System.out.println("newvalue : " + cl);
                    //System.out.println("newvalue / cl : " + newvalue / cl);
                }
                //System.out.println("new_centroid_attributes size : " + new_centroid_attributes.size());

                //clusters.get(i).getCentroid().setAttributes(new_centroid_attributes);
                clusters.get(i).setCentroid(new Point(new_centroid_attributes));

            }
            //System.out.println("calculated Centroid : " + i);
            //clusters.get(i).getCentroid().plotPoint();

            //recalculate the i+1 cluster's centroid
            //the number of iteration is equal to the number of clusters
        }
    }
//Calculates the distance between two points.

    protected double distance(Point point, Point centroid) {
        double temp = 0;
        List<Double> attributes1 = point.getAttributes();
        List<Double> attributes2 = centroid.getAttributes();

        Iterator ir1 = attributes1.iterator();
        Iterator ir2 = attributes2.iterator();

        if (ir1.hasNext() && ir2.hasNext()) {
            //ignore first atttribute
            ir1.next();
            ir2.next();
        }

        while (ir1.hasNext() && ir2.hasNext()) {

            //for (int i = 0; i < attributes_count; i++) {//ignore first atttribute 
            try {
                temp = temp + Math.pow(Math.abs((Double) (ir1.next()) - (Double) (ir2.next())), 2);

                //temp = temp + Math.pow(Math.abs(attributes1.get(i+1).doubleValue() - attributes2.get(i+1).doubleValue()), 2);
            } catch (NumberFormatException nfe) {
                System.out.println("Error: attempting to convert categorical data  to numerical data");
                nfe.printStackTrace();
            }
        }

        return Math.sqrt(temp);
    }

    private void setLists(File dataset) {

        //creat scanner to read from the dataset file in the default directory
        Scanner linescanner = null;
        Scanner attributescanner = null;
        try {

            linescanner = new Scanner(new FileReader(dataset));

            List<Double> pointattributes = null;

            while (linescanner.hasNextLine()) {
                attributescanner = new Scanner(linescanner.nextLine());
                attributescanner.useDelimiter(",");
                pointattributes = new ArrayList<Double>();
                // use comma as separator

                while (attributescanner.hasNextDouble()) {
                    //reading the current line attributes
                    pointattributes.add(attributescanner.nextDouble());
                }
                //add the current line as a new point to the points
                points.add(new kmeans.Point(pointattributes));
                points_count++;

            }

            this.attributes_count = pointattributes.size();

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } finally {
            if (linescanner != null) {
                linescanner.close();
            }
            if (attributescanner != null) {
                attributescanner.close();
            }

        }
        //Create the Clusters 

        for (int i = 0; i < clusters_count; i++) {

            clusters.add(new Cluster(i));

        }
        //Initialize the Clusters 
        centroidsInit();

    }

    private void writeAllClusterstoFile() {
        File out;
        FileWriter fileWriter = null;
        out = new File("files\\allclusters.csv");
        if (out.isFile() && out.exists() && out.canWrite()) {
            try {
                fileWriter = new FileWriter(out);
                for (int i = 0; i < clusters.size(); i++) {
                    fileWriter.append("*** cluster " + i + " ***\n");
                    fileWriter.append("centroid\n");
                    for (int k = 1; k < attributes_count; k++) {
                        fileWriter.append(clusters.get(i).getCentroid().getAttributes().get(k).toString());
                        if (k == attributes_count - 1) {
                            fileWriter.append("\n");
                        } else {
                            fileWriter.append(",");
                        }
                    }
                    fileWriter.append("points\n");
                    for (int j = 0; j < clusters.get(i).getPoints().size(); j++) {
                        for (int k = 0; k < attributes_count; k++) {
                            fileWriter.append(clusters.get(i).getPoints().get(j).getAttributes().get(k).toString());
                            if (k == attributes_count - 1) {
                                fileWriter.append("\n");
                            } else {
                                fileWriter.append(",");
                            }
                        }
                    }// writing point terminated
                    fileWriter.append("\n");
                }// writing cluster terminated

            } catch (Exception e) {

                System.out.println("Error in CsvFileWriter !!!");

                e.printStackTrace();

            } finally {

                try {

                    fileWriter.flush();

                    fileWriter.close();

                } catch (IOException e) {

                    System.out.println("Error while flushing/closing fileWriter !!!");

                    e.printStackTrace();

                }

            }
            System.out.println("clusters were successfully writen to a file clusters.csv");

        } else {
            //javax.swing.JOptionPane.showInternalMessageDialog(JKmeans, this, "clusters were not writen to a file/n close clusters.csv file", javax.swing.JOptionPane.WARNING_MESSAGE);
            System.out.println("clusters were not writen to a file\n clusters.csv was changed or ddeleted file");
        }

    }

    private void centroidsInit() {
        if (!points.isEmpty()) {
            Random r;
            for (int i = 0; i < clusters_count; i++) {
                r = new Random();
                if (centroid_init == 0)//random sets
                {
                    clusters.get(i).setCentroid(points.get((int) ((points.size() - 1) * r.nextDouble())));
                } else if (centroid_init == 1)//indexed sets
                {
                    clusters.get(i).setCentroid(createRandomPoint());
                } else if (centroid_init == 2) {
                    clusters.get(i).setCentroid(points.get(index + i));
                }

            }
        }
    }

    private Point createRandomPoint() {
        Random r = new Random();
        List<Double> random_attributes = new ArrayList<Double>();
        for (int i = 0; i <= attributes_count; i++) {

            random_attributes.add(r.nextDouble() * 10);
            //random_attributes.add(10.0);

        }

        return new Point(random_attributes);
    }

    private void plotClusters() {
        for (int i = 0; i < clusters_count; i++) {
            clusters.get(i).plotCluster();
        }
    }

    private void clearClusters() {

        for (int i = 0; i < clusters_count; i++) {
            clusters.get(i).clear();

        }
    }

    private List<Point> getCentroids() {

        List centroids = new ArrayList<Point>();

        for (int i = 0; i < clusters_count; i++) {
            centroids.add(clusters.get(i).getCentroid());

        }

        return centroids;
    }

    protected List<Cluster> getClusters() {
        return clusters;
    }

    protected int getIterations() {
        return iterations;
    }

    /*
     public static void main(String[] arg) {

     Kmeans kmeans = new Kmeans("test.csv", 3);

     }
     */
}
