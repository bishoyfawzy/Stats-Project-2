package StockBot;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TechnicalAnalysis {
    //Calculate Mean
    public static double calculateMean(ArrayList<StockData> data) {
        double sum = 0;
        for (StockData stockData : data) {
            sum += stockData.getClose();
        }
        return sum / data.size();
    }
    //Calculate Variance 
    public static double calculateVariance(ArrayList<StockData> data) {
        double mean = calculateMean(data);
        double sumOfSquares = 0;
        for (StockData stockData : data) {
            sumOfSquares += Math.pow(stockData.getClose() - mean, 2);
        }
        return sumOfSquares / data.size();
    }
    //Calculate Standard Deviation
    public static double calculateStandardDeviation(ArrayList<StockData> data) {
        return Math.sqrt(calculateVariance(data));
    }
    //Calculate RSI
    public static double calculateRSI(ArrayList<StockData> data, int period) {
        if (data.size() < period) {
            return -1;
        }

        double avgU = 0;
        double avgD = 0;

        for (int i = 1; i < period; i++) {
            double change = data.get(i).getClose() - data.get(i - 1).getClose();
            if (change > 0) {
                avgU += change;
            } else {
                avgD += Math.abs(change);
            }
        }

        avgU /= period;
        avgD /= period;

        double rs = avgU / avgD;
        return 100 - (100 / (1 + rs));
    }
    //Calculate Moving Average
    public static double calculateMovingAverage(ArrayList<StockData> data, int period) {
        if (data.size() < period) {
            return -1; // Not enough data
        }

        double sum = 0;
        for (int i = 0; i < period; i++) {
            sum += data.get(i).getClose();
        }

        return sum / period;
    }
    //Automatically smooth MA line
    public static ArrayList<Double> calculateSmoothedMA(ArrayList<StockData> data, int period) {
        ArrayList<Double> smoothedMA = new ArrayList<>();
        double sum = 0;
        for (int i = 0; i < period; i++) {
            sum += calculateMovingAverage(new ArrayList<>(data.subList(i, i + period)), period);
        }

        for (int i = period; i < data.size(); i++) {
            sum = sum - calculateMovingAverage(new ArrayList<>(data.subList(i - period, i)), period)
                      + data.get(i).getClose();
            smoothedMA.add(sum / period);
        }

        return smoothedMA;
    }
    //writes new values of RSI and MA to a new csv file (AAPL-RSI-MA.csv)
    public static void writeToCSV(ArrayList<StockData> data, String filename, int period) throws IOException {
        ArrayList<Double> rsis = new ArrayList<>();
        ArrayList<Double> mas = calculateSmoothedMA(data, period);

        // Calculating RSI
        for (int i = 0; i < data.size(); i++) {
            rsis.add(i < 2 * period ? null : calculateRSI(new ArrayList<>(data.subList(i - period, i)), period));
        }

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Date,Open,High,Low,Close,Volume,RSI,SmoothedMA\n");
            for (int i = 0; i < data.size(); i++) {
                StockData stockData = data.get(i);
                Double rsi = rsis.get(i);
                Double ma = i >= period ? mas.get(i - period) : null; // Corrected index for MA

                writer.write(String.format("%s,%f,%f,%f,%f,%d,%s,%s\n",
                    stockData.getDate().toString(),
                    stockData.getOpen(),
                    stockData.getHigh(),
                    stockData.getLow(),
                    stockData.getClose(),
                    stockData.getVolume(),
                    rsi != null ? rsi.toString() : "",
                    ma != null ? ma.toString() : ""
                ));
            }
        }
    }

}

