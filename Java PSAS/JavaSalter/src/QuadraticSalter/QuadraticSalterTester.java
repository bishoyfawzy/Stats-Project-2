package QuadraticSalter;

import java.io.IOException;

public class QuadraticSalterTester {
    public static void main(String[] args) {
        QuadraticSalter salter = new QuadraticSalter("quadratic_data.csv", "quadratic_data_salted.csv", 100.0, 150);
        try {
            salter.saltData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
