package StockBot;

import java.util.ArrayList;

public class StockBotApp {
    public static void main(String[] args) {
        final int period = 14; // RSI period

        DataCollector collector = new DataCollector();
        // Load the stock data from the CSV file
        ArrayList<StockData> stockData = collector.loadDataFromCSV("AAPL.csv");

        // Initialize the trading bot with a starting balance
        TradingBot bot = new TradingBot(10000); // Starting with $10,000

        // Evaluate trades based on the collected stock data
        bot.evaluateTrade(stockData, period);

        // After processing trades, output the final results
        System.out.println("Final balance: $" + bot.getBalance());
        System.out.println("Shares owned: " + bot.getSharesOwned());

        // Now, let's write the RSI and smoothed MA data to a new CSV file
        try {
            TechnicalAnalysis.writeToCSV(stockData, "AAPL-RSI-MA.csv", period);
            System.out.println("RSI and MA data written to AAPL-RSI-MA.csv successfully.");
        } catch (Exception e) {
            System.err.println("An error occurred while writing to the CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
