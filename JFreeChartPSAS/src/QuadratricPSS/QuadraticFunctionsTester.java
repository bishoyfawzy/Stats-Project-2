package QuadratricPSS;

import java.io.IOException;

public class QuadraticFunctionsTester {
    public static void main(String[] args) {
        QuadraticFunctions functions = new QuadraticFunctions(1.0, 2.0, 1.0, 0.5, 15, 12.0, 3);
        try {
            functions.generateAndSaveCharts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
