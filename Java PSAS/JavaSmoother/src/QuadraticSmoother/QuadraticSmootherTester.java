package QuadraticSmoother;

import java.io.IOException;

public class QuadraticSmootherTester {
    public static void main(String[] args) {
        QuadraticSmoother smoother = new QuadraticSmoother(
            "quadratic_data_smoothed2.csv", "quadratic_data_smoothed3.csv", 3);
        try {
            smoother.smoothData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
