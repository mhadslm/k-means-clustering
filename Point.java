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

public class Point {

    private int index = 0;
    private List<Double> attributes = new ArrayList<Double>();
    private int attributes_count = 0;
    

    public Point(List<Double> attributes) {
        this.attributes = attributes;
        this.attributes_count = attributes.size();

    }

    public void setAttributes(List<Double> attributes) {
        this.attributes = attributes;
    }

    public List<Double> getAttributes() {
        return this.attributes;
    }

    public void setCluster(int n) {
        this.index = n;
    }

    public int getCluster() {
        return this.index;
    }

    public void plotPoint() {
        String s = " ";
       // if (!attributes.isEmpty()) {
            for (int i = 0; i < attributes.size(); i++) {

                s = s + String.valueOf(attributes.get(i)) + ",";

            }
        
        System.out.println(s.substring(0, s.length() - 1));
        //}else         System.out.println("empty point");

    }

}
