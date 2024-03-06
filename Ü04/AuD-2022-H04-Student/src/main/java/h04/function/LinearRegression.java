package h04.function;

import org.jetbrains.annotations.Nullable;

/**
 * Fits a function to a set of data points.
 *
 * <p>The function has the following form:
 * <pre>{@code
 *    n = number of data points
 *    samples = number of non null data points
 *    x = i / (n - 1)
 *    y = f(x)
 *    x^bar = sum(x_i) / samples
 *    y^bar = sum(y_i) / samples
 *    beta_1 = sum_i^n [(x_i - x hat) * (y_i - y hat)] / sum_i^n [(x_i - x hat)^2]
 *    beta_2 = y^bar - beta_1 * x^bar
 *
 *    Fitter(x, y) = beta_1 * x + beta_2
 * }</pre>
 *
 * @author Nhan Huynh
 */
public class LinearRegression implements DoubleToIntFunctionFitter {

    @Override
    public DoubleToIntFunction fitFunction(@Nullable Integer[] y) {


        double summeXi = 0;

        double summeYi = 0;

        double x = 0;

       int samples = 0;

        // bestimmen der Summe durch errechnen des x und addition des x und y
        // es wird bei 0 angefangen, weil von 1 bis zum letzten element hier an index 0 und index length-1 berechnet wird
        for (int i = 0; i < y.length; i++) {

            if (y[i] != null) {

                x = (double) i / (y.length - 1);
                summeXi += x;
                summeYi += y[i];
                samples++;
            }

        }

        // errechnen der Durchschnitte
        double xQuer = (double) 1 / samples * summeXi;
        double yQuer = (double) 1 / samples * summeYi;


        // Berechnung der Steigung

        double dividend = 0;

        // berechnen des oberen Teil des Bruches :
        // hier wird auch bei 0 gestartet weil wir vom ersten bis zum letzten element berechnen (index o bis length-1)
        for (int i = 0; i < y.length; i++) {

            if (y[i] != null) {

                x = (double) i / (y.length - 1);

                dividend += (x - summeXi) * (y[i] - summeYi);
            }
        }


        double divisor = 0;

        // berechnen des unteren Teil des Bruches :
        // hier wird auch bei 0 gestartet weil wir vom ersten bis zum letzten element berechnen (index o bis length-1)

        for (int i = 0; i < y.length; i++) {

            if (y[i] != null) {

                x = (double) i / (y.length - 1);
                divisor += (x - summeXi) * (x - summeXi);
            }
        }

        double beta1 = dividend / divisor;


        double beta2 = summeYi - beta1 * summeXi;

        return new LinearDoubleToIntFunction(beta1, beta2);


    }

}
