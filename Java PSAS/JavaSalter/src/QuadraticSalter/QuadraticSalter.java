package QuadraticSalter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class QuadraticSalter {
    private String inputCsv;
    private String outputCsv;
    private double saltRange;
    private int dataPoints;

    public QuadraticSalter(String inputCsv, String outputCsv, double saltRange, int dataPoints) {
        this.inputCsv = inputCsv;
        this.outputCsv = outputCsv;
        this.saltRange = saltRange;
        this.dataPoints = dataPoints;
    }

    public void saltData() throws IOException {
        Random random = new Random();
        try (
            BufferedReader reader = new BufferedReader(new FileReader(inputCsv));
            FileWriter writer = new FileWriter(outputCsv)
        ) {
            String line;
            writer.append("X,Y_Salted\n");
            reader.readLine(); // Skip the header line

            while ((line = reader.readLine()) != null && --dataPoints > 0) {
                String[] values = line.split(",");
                double x = Double.parseDouble(values[0]);
                double y = Double.parseDouble(values[1]);

                double salt = (random.nextDouble() * 2 - 1) * saltRange;
                double ySalted = y + salt;

                writer.append(x + "," + ySalted + "\n");
            }

            writer.flush();
            System.out.println("Data has been salted and written to " + outputCsv);
        }
    }
}

