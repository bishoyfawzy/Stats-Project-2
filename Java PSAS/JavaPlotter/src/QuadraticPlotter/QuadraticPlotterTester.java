package QuadraticPlotter;

public class QuadraticPlotterTester {
    public static void main(String[] args) {
        QuadraticPlotter plotter = new QuadraticPlotter(4.0, 2.0, 1.0, 0.5, 15);
        try {
            plotter.generateData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
