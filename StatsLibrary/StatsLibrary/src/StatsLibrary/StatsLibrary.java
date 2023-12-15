package StatsLibrary;

public class StatsLibrary {

    //Calculates the probability density for a given value in a normal distribution.
    public static double probabilityDensityFunction(double x) {
        return Math.exp(-x * x / 2) / Math.sqrt(2 * Math.PI); // Function for PDF
    }

    //Calculates the probability for a given range in a continuous uniform distribution.
    public static double uniformProbabilityDistribution(double a, double b, double x) {
        if (x < a || x > b) {
            return 0; // x is outside the distribution range
        }
        return 1 / (b - a); // Uniform distribution probability
    }
}
