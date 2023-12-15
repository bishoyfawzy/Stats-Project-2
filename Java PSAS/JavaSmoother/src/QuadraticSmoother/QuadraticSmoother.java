package QuadraticSmoother;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuadraticSmoother {

    private String inputCsv;
    private String outputCsv;
    private int windowValue;

    public QuadraticSmoother(String inputCsv, String outputCsv, int windowValue) {
        this.inputCsv = inputCsv;
        this.outputCsv = outputCsv;
        this.windowValue = windowValue;
    }

    public void smoothData() throws IOException {
        List<Double> xValues = loadValues(inputCsv, 0);
        List<Double> yValues = loadValues(inputCsv, 1);
        List<Double> smoothedYValues = applySmoothing(yValues, windowValue);
        writeValues(xValues, smoothedYValues, outputCsv);
    }

    private List<Double> loadValues(String csvFile, int columnIndex) throws IOException {
        List<Double> values = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            reader.readLine(); // Skip the header line
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitLine = line.split(",");
                values.add(Double.parseDouble(splitLine[columnIndex]));
            }
        }
        return values;
    }

    private List<Double> applySmoothing(List<Double> yValues, int windowValue) {
        List<Double> smoothedYValues = new ArrayList<>();
        for (int i = 0; i < yValues.size(); i++) {
            double sum = 0;
            int count = 0;
            for (int j = i - windowValue; j <= i + windowValue; j++) {
                if (j >= 0 && j < yValues.size()) {
                    sum += yValues.get(j);
                    count++;
                }
            }
            double average = sum / count;
            smoothedYValues.add(average);
        }
        return smoothedYValues;
    }

    private void writeValues(List<Double> xValues, List<Double> yValues, String csvFile) throws IOException {
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.append("X,Y_Smoothed\n");
            for (int i = 0; i < xValues.size(); i++) {
                writer.append(xValues.get(i) + "," + yValues.get(i) + "\n");
            }
            
            System.out.println("Data has been smoothed and written to " + csvFile);
        }
    }
    
}

