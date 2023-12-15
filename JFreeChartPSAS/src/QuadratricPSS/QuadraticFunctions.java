package QuadratricPSS;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class QuadraticFunctions {
    private double a, b, c, interval, saltRange;
    private int range, windowValue;

    public QuadraticFunctions(double a, double b, double c, double interval, int range, double saltRange, int windowValue) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.interval = interval;
        this.range = range;
        this.saltRange = saltRange;
        this.windowValue = windowValue;
    }

    public void generateAndSaveCharts() throws IOException {
        // Generate Quadratic Data
        XYSeries quadraticSeries = new XYSeries("Quadratic");
        for (double x = -range; x <= range; x += interval) {
            double y = a * Math.pow(x, 2) + b * x + c;
            quadraticSeries.add(x, y);
        }

        // Generate Salted Data
        XYSeries saltedSeries = new XYSeries("Salted");
        Random random = new Random();
        for (int i = 0; i < quadraticSeries.getItemCount(); i++) {
            double x = quadraticSeries.getX(i).doubleValue();
            double y = quadraticSeries.getY(i).doubleValue();
            double salt = (random.nextDouble() * 2 - 1) * saltRange;
            saltedSeries.add(x, y + salt);
        }

        // Generate Smoothed Data
        XYSeries smoothedSeries = new XYSeries("Smoothed");
        for (int i = 0; i < saltedSeries.getItemCount(); i++) {
            double sum = 0;
            int count = 0;
            for (int j = i - windowValue; j <= i + windowValue; j++) {
                if (j >= 0 && j < saltedSeries.getItemCount()) {
                    sum += saltedSeries.getY(j).doubleValue();
                    count++;
                }
            }
            double x = saltedSeries.getX(i).doubleValue();
            double average = sum / count;
            smoothedSeries.add(x, average);
        }

        // Create and Save Plots
        saveChartAsPNG("quadratic.png", "Quadratic Function", quadraticSeries);
        saveChartAsPNG("salted.png", "Salted Quadratic Function", saltedSeries);
        saveChartAsPNG("smoothed.png", "Smoothed Quadratic Function", smoothedSeries);
    }

    private static void saveChartAsPNG(String filename, String chartTitle, XYSeries series) throws IOException {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createScatterPlot(
                chartTitle,
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        ChartPanel panel = new ChartPanel(chart);
        panel.setBounds(0, 0, 800, 600);
        panel.setDoubleBuffered(false);
        panel.setChart(chart);
        panel.paintComponent(image.createGraphics());

        ImageIO.write(image, "png", new File(filename));
    }
}

