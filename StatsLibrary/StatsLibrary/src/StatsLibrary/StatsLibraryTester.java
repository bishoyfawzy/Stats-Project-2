package StatsLibrary;

public class StatsLibraryTester {
    public static void main(String[] args) {
        double pdfValue = StatsLibrary.probabilityDensityFunction(1.0);
        double uniformProb = StatsLibrary.uniformProbabilityDistribution(0, 10, 5);

        System.out.println("PDF value at 1.0: " + pdfValue);
        System.out.println("Uniform Probability at 5 between 0 and 10: " + uniformProb);
    }
}

