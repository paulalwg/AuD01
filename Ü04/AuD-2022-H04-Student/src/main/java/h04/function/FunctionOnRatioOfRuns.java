package h04.function;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a function that accepts a list-valued argument and produces an int-valued result.
 *
 * <p>The function values are calculated by the ratio of runs to the number of elements.
 *
 * @author Nhan Huynh
 */
public class FunctionOnRatioOfRuns<T> extends FunctionOnDegreeOfDisorder<T> {

    /**
     * The function to be applied to the ratio of runs.
     */
    private final DoubleToIntFunction function;

    /**
     * Constructs and initializes a {@code FunctionOnRatioOfRuns}.
     *
     * @param cmp      the comparator used to compare the elements of the list
     * @param function the function to be applied to the ratio of runs
     */
    public FunctionOnRatioOfRuns(Comparator<? super T> cmp, DoubleToIntFunction function) {
        super(cmp);
        this.function = function;
    }

    @Override
    public int apply(List<T> elements) {

        int runs = 0;
        int numberOfElements = elements.size();

        Iterator<T> itElements = elements.iterator();

        // falls die Liste leer ist wird die function angewendet auf 0 zurückgegeben
        if (!itElements.hasNext())
            return function.apply(0);

        // der iterator muss ein next haben, der Fall dass elements leer ist wurde schon abgeprüft

        T t1 = itElements.next();
        T t2;


        // elements hat mindestens zwei Elemente:
        while (itElements.hasNext()) {

            t2 = itElements.next();

            // wenn das zweite t kleiner dem ersten ist wird runs eins hochgesetzt
            if (cmp.compare(t1, t2) > 0)
                runs++;

            // der Wert von t2 wird in t1 gespeichert, er ist der nächste der verglichen werden muss.
            t1 = t2;


        }

        // runs wird noch einmal eins hochgesetzt, weil vorher nur an den "umschaltstellen" der einzelnen runs hochgesetzt wurde
        // da dann aber noch der letzte run ergänzt werden muss bzw. falls die liste nur ein element hat dieser einer run noch mit rein muss
        runs++;


        return function.apply((double) runs/numberOfElements);
    }

}
