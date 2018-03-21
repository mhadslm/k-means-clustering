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

public class Cluster {

    public List<Point> points;
    public Point centroid;
    public int id;

    //Creates a new Cluster
    public Cluster(int id) {
        this.id = id;
        this.points = new ArrayList<Point>();
        this.centroid = null;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public Point getCentroid() {
        return centroid;
    }

    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    public int getId() {
        return id;
    }

    public void clear() {
        points.clear();
    }

    public void plotCluster() {
        System.out.println("*************[Cluster Num: " + id + "||  Cluster Size: " + points.size() + " ]*************");
        System.out.print("centroid:");
        centroid.plotPoint();
        System.out.println("[Points: " + points.size() + "]");
        if (!points.isEmpty()) {
            for (int i = 0; i < points.size(); i++) {
                (points.get(i)).plotPoint();
                
            }System.out.println();
        }else {
                          System.out.print("empty cluster");
  
                    }
        System.out.print("]");
        System.out.println();
    }
    
}
