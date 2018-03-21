package kmeans;

import java.awt.Color;
import java.awt.BasicStroke;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class XYChart {

    private int clusters_count = 0;

    public XYChart(int cc) {
        this.clusters_count = cc;
    }

    public ChartPanel xyChartCreator(java.util.List<Cluster> clustered_data) {

        JFreeChart jfreechart = ChartFactory.createScatterPlot(
                "Ploting Thyroid Clusters", "T3", "TST", createDataset(clustered_data),
                PlotOrientation.VERTICAL, true, true, false);
        java.awt.Shape cross = org.jfree.util.ShapeUtilities.createRegularCross(3, 1);
        java.awt.Shape diamond = org.jfree.util.ShapeUtilities.createDiamond(3);
        java.awt.Shape triangle = org.jfree.util.ShapeUtilities.createUpTriangle(3);
        java.awt.Shape dcross = org.jfree.util.ShapeUtilities.createDiagonalCross(6,2);


        final XYPlot xyPlot = jfreechart.getXYPlot();
        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);
        org.jfree.chart.renderer.xy.XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesShape(0, cross);
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesShape(1, diamond);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesShape(2, triangle);
        renderer.setSeriesPaint(2, Color.BLUE);
        renderer.setSeriesShape(3, dcross);
        renderer.setSeriesPaint(3, Color.ORANGE);


        return new ChartPanel(jfreechart);

    }

    private XYDataset createDataset(java.util.List<Cluster> clustered_data) {

        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        XYSeries series = null;

        Cluster c = null;
        double x = 0;
        //double y = 0;
        for (int i = 0; i < clusters_count; i++) {
            c = clustered_data.get(i);

            series = new XYSeries("Cluster #" + i+1 + "   :   " + c.getPoints().size() + "   point(s)");
            
            for (int j = 0; j < c.getPoints().size(); j++) {
                series.add(c.getPoints().get(j).getAttributes().get(1), c.getPoints().get(j).getAttributes().get(2));

            }

            xySeriesCollection.addSeries(series);



        }

        XYSeries centroids_series = new XYSeries("Centroids ");

        for (int i = 0; i < clusters_count; i++) {
            c = clustered_data.get(i);
            centroids_series.add(c.getCentroid().getAttributes().get(1), c.getCentroid().getAttributes().get(2));

        }
        xySeriesCollection.addSeries(centroids_series);
        
        return xySeriesCollection;
    }

}
