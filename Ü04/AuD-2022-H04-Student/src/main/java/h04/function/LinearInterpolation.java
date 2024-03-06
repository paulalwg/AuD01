package h04.function;

import org.jetbrains.annotations.Nullable;

/**
 * Fits a function to a set of data points.
 *
 * <p>The function has the following form:
 * <ol>
 *   <li>Create a double-array and copy all indices values of y to the new array which are not {@code null}.</li>
 *   <li>The indices containing {@code null} values are linearly interpolated using the next left and right known function
 *   values.</li>
 *   <li>Create an int-array and round all values.</li>
 *   <li>Create a {@code ArrayDoubleToIntFunction} containing the rounded values.</li>
 * </ol>
 *
 * @author Nhan Huynh
 */
public class LinearInterpolation implements DoubleToIntFunctionFitter {

    @Override
    public DoubleToIntFunction fitFunction(@Nullable Integer[] y) {


        int x0 = 0;
        int x1 = 0;
        int f0 = y[x0];
        int f1 = y[x0];
        int x;
        // x ist dann die stelle die man sucht

        int[] resultArray = new int[y.length];

        // stelle 0 ist auf jeden Fall != null wird also schon gefüllt
        resultArray[0] = y[0];

        // ab stell 1 wird das Array gefüllt
        for (int i = 1; i < resultArray.length; i++) {

            // wenn an einer Stelle des Arrays null steht wird die Interpolation gebildet, das f0 bleibt ab der gleichen Stelle
            // da es falls am nächsten Index auch null steht noch mal verwendet werden muss
            if (y[i] == null) {

                // suchen des nächsten rechts davon gelegenen Index
                for (int j = i + 1; j < resultArray.length; j++) {

                    // wenn die passende Stelle gefunden wurd werden f1 und x1 zugewiesen
                    if (y[j] != null) {
                        f1 = y[j];
                        x1 = j;
                        break;
                    }
                }

                // bilden der interpolation
                x = i;


                double interpolation = (double) f0 + ((f1 - f0)/ (x1 -x0)) * (x - x0);

                resultArray[i] = (int) Math.round(interpolation);

                // die Schleife wird weitergeführt
                continue;
            }

            // x0 wird auf i gesetzt und f0 auf y[x0] da dort die nächste bekannte Zahl ist
            x0 = i;
            f0 = y[x0];
            resultArray[i] = y[i];
        }


        return new ArrayDoubleToIntFunction(resultArray);
    }
}
