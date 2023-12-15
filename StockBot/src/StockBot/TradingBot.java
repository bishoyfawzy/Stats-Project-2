package StockBot;

import java.util.ArrayList;

public class TradingBot {
    private double balance;
    private int sharesOwned;
    private double averagePurchasePrice; // Tracks the weighted average purchase price of shares owned

    public TradingBot(double initialBalance) {
        this.balance = initialBalance;
        this.sharesOwned = 0;
        this.averagePurchasePrice = 0.0;
    }

    public void evaluateTrade(ArrayList<StockData> data, int period) {
        if (data.size() < period) {
            System.out.println("Not enough data to evaluate trades.");
            return;
        }

        for (int i = period; i < data.size(); i++) {
            ArrayList<StockData> subList = new ArrayList<>(data.subList(i - period, i));
            double currentPrice = data.get(i).getClose();
            double movingAverage = TechnicalAnalysis.calculateMovingAverage(subList, period);
            double mean = TechnicalAnalysis.calculateMean(subList);
            double standardDeviation = TechnicalAnalysis.calculateStandardDeviation(subList);
            
            double upperThreshold = mean + standardDeviation;
            double lowerThreshold = mean - standardDeviation;

            double tradePercentage = determineTradePercentage(currentPrice, movingAverage, mean, standardDeviation);
            double tradeAmount = balance * tradePercentage;
            int sharesToTrade = (int) (tradeAmount / currentPrice);

            // Buy or sell based on the moving average and standard deviation
            if (currentPrice < movingAverage && currentPrice > lowerThreshold && tradeAmount > 0) {
                buyShares(sharesToTrade, currentPrice);
            } else if (currentPrice > movingAverage && sharesOwned > 0) {
                sellShares(sharesToTrade, currentPrice);
            }

            // Additional logic to sell based on profit or cut losses
            if (sharesOwned > 0) {
                double profitPercentage = (currentPrice - averagePurchasePrice) / averagePurchasePrice;
                if (profitPercentage >= 0.10) {
                    // Sell 50% of shares if profit is 10% or more
                    sellShares(sharesOwned / 2, currentPrice);
                } else if (profitPercentage <= -0.07) {
                    // Sell 80% of shares if loss is 7% or more
                    sellShares((int)(sharesOwned * 0.8), currentPrice);
                }
            }

            // Print the date and action for transparency
            System.out.println("Date: " + data.get(i).getDate() + ", Balance: " + balance + ", Shares Owned: " + sharesOwned);
        }
    }

    private double determineTradePercentage(double currentPrice, double movingAverage, double mean, double standardDeviation) {
        if (currentPrice < mean - standardDeviation) {
            return 0.5; // 50% if price is below one standard deviation from the mean
        } else if (currentPrice < movingAverage) {
            return 0.2; // 20% if price is below moving average
        } else if (currentPrice > mean + standardDeviation) {
            return -1; // Sell all if price is above one standard deviation from the mean
        } else if (currentPrice > movingAverage) {
            return -0.5; // Sell 50% if price is above moving average
        }
        return 0; // No trade
    }

    private void buyShares(int shares, double price) {
        double cost = shares * price;
        if (cost <= balance) {
            averagePurchasePrice = ((averagePurchasePrice * sharesOwned) + (price * shares)) / (sharesOwned + shares);
            sharesOwned += shares;
            balance -= cost;
            System.out.println("Bought " + shares + " shares at " + price + " each.");
        }
    }

    private void sellShares(int shares, double price) {
        if (shares > sharesOwned) {
            shares = sharesOwned; // Can't sell more than we own
        }
        sharesOwned -= shares;
        balance += shares * price;
        System.out.println("Sold " + shares + " shares at " + price + " each.");
        if (sharesOwned == 0) {
            averagePurchasePrice = 0.0; // Reset if all shares are sold
        }
    }

    // Getter methods
    public double getBalance() {
        return balance;
    }

    public int getSharesOwned() {
        return sharesOwned;
    }
}

