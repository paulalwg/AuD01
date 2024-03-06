package h04.function;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a function that accepts a list-valued argument and produces an int-valued result.
 *
 * <p>The function values are calculated by the ratio of inversions to the maximum number of inversions.
 *
 * @author Nhan Huynh
 */
public class FunctionOnRatioOfInversions<T> extends FunctionOnDegreeOfDisorder<T> {

    /**
     * The function to be applied to the ratio of runs.
     */
    private final DoubleToIntFunction function;

    /**
     * Constructs and initializes a {@code FunctionOnRatioOfInversions}.
     *
     * @param cmp      the comparator used to compare the elements of the list
     * @param function the function to be applied to the ratio of runs
     */
    public FunctionOnRatioOfInversions(Comparator<? super T> cmp, DoubleToIntFunction function) {
        super(cmp);
        this.function = function;
    }

    @Override
    public int apply(List<T> elements) {

        // falls die Liste leer ist
        if (elements.size() == 0)
            return function.apply(0.0);

        // schauen
        Iterator<T> itEl = elements.iterator();

        T ti0 = itEl.next();

        // falls die Liste nur ein Element hat, also kein einziges paar
        if (!itEl.hasNext())
            return function.apply(0.0);


        int actualInversions = 0;

        T ai = null;
        T aj;

        // durchschalten durch die gesamte Sequenz beginnend bei index eins bis zu index size
        for (int i = 1; i <= elements.size(); i++) {

            Iterator<T> itElements = elements.iterator();


            // schalten des iterators auf das aktuelle zu betrachtende Element
            // beginnend bei 0 um beim aktuellen element zu landen, dadurch dass wir in der äußeren schleife bei 1 beginnen und bis size gehen
            // der index wird danach verwendet um das jeweils nächste element anzuschauen
            for (int j = 0; j < i; j++) {
                ai = itElements.next();
            }

            // solange der iterator noch elemente ausgibt werden ai und aj verglichen
            // wenn ai > aj wird die Anzahl der Inversions hochgesetzt
            while (itElements.hasNext()) {

                aj = itElements.next();

                if (cmp.compare(ai, aj) > 0)
                    actualInversions++;
            }


        }


        // berechnung der maximalen Anzahl an inversions
        int maxInversions = 0;

        for (int i = elements.size() - 1; i >= 1; i--) {
            maxInversions += i;
        }


        return function.apply( (double) actualInversions/maxInversions);
    }
}
