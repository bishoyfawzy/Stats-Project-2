package QuadraticPlotter;

import java.io.FileWriter;
import java.io.IOException;

public class QuadraticPlotter {
    private double a, b, c, interval;
    private int range;

    public QuadraticPlotter(double a, double b, double c, double interval, int range) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.interval = interval;
        this.range = range;
    }

    public void generateData() throws IOException {
        FileWriter csvWriter = new FileWriter("quadratic_data.csv");
        csvWriter.append("X,Y\n");

        for (double x = -range; x <= range; x += interval) {
            double y = a * Math.pow(x, 2) + b * x + c;
            csvWriter.append(x + "," + y + "\n");
        }

        csvWriter.flush();
        csvWriter.close();
        System.out.println("Data has been written to quadratic_data.csv");
    }
}
