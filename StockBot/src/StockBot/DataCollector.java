package StockBot;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataCollector {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yy");

    public ArrayList<StockData> loadDataFromCSV(String fileName) {
        ArrayList<StockData> stockDataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                LocalDate date = LocalDate.parse(fields[0], DATE_FORMATTER);
                double open = Double.parseDouble(fields[1]);
                double high = Double.parseDouble(fields[2]);
                double low = Double.parseDouble(fields[3]);
                double close = Double.parseDouble(fields[4]);
                long volume = (long) Double.parseDouble(fields[5]);
                StockData data = new StockData(date, open, high, low, close, volume);
                stockDataList.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stockDataList;
    }
}
