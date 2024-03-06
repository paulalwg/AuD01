package h03;

/**
 * An abstract class that contains a function (FunctionToInt) and can compute the length of partial string matches of a given string of type T.
 *
 * @param <T> The type of objects to be searched through by an object of this class.
 */
public abstract class PartialMatchLengthUpdateValues<T> {
    /**
     * The function to be used in this object.
     */
    protected FunctionToInt<T> fct;

    /**
     * Constructs a PartialMatchLengthUpdateValues object with the given function.
     *
     * @param fct The function to be used by this object.
     */
    public PartialMatchLengthUpdateValues(FunctionToInt<T> fct) {
        this.fct = fct;
    }

    /**
     * Returns the next state that will be entered when using the given letter from the given state.
     *
     * @param state  The current state.
     * @param letter The letter to be added.
     * @return The next state.
     */
    public abstract int getPartialMatchLengthUpdate(int state, T letter);

    /**
     * Returns the length of the search string used in this object.
     *
     * @return The length of the search string.
     */
    public abstract int getSearchStringLength();

    /**
     * Returns the amount of elements k in searchString so that the first k elements of searchString match the last k elements of searchString.
     *
     * @param searchString The searchString to search through.
     * @return The amount k.
     */
    protected int computePartialMatchLengthUpdateValues(T[] searchString) {

      // index 1 ist der anfangsindex, index2 der momentan betrachtete anfangsindex für ein potentielles match,
        // index2ToCheck ist der index der weiterläuft um die nächsten Indizes zu vergleichen
        int index1 = 0;
        int indexToCheck = 1;
        int index2ToCheck = indexToCheck;
        int k = 0;
        boolean matchfound = false;


        // solange der index2ToCheck nicht am ende des Arrays angekommen ist werden matches gesucht
        while (indexToCheck < searchString.length && index2ToCheck < searchString.length) {

            // wenn ein match gefunden wird wird k++, der index1 und index2 weitergeschaltet und der nächste index verglichen
            //matchfound ist dann true, damit falls der nächste index nicht übereinstimmt der erste index wieder auf 0 gesetzt wird
            if (fct.apply(searchString[index1]) == fct.apply(searchString[index2ToCheck])) {
                k++;
                index1++;
                index2ToCheck++;
                matchfound = true;
            }
            // falls davor ein match gefunden wurde, jetzt keine übereinstimmung mehr ist wird index1 wieder auf 0 gesetzt,
            // und der zu checkende Index eins hochgesetzt, der fortlaufende Index ist dann gleich dem indexToCheck
            else if (matchfound) {
                index1 = 0;
                k = 0;
                matchfound = false;
                indexToCheck++;
                index2ToCheck = indexToCheck;
            }
            // ansonsten wird index2 weitergeschaltet und mit index0 verglichen, der fortlaufende Index steht immer auf dem
            //indexToCheck
            else {
                indexToCheck++;
                index2ToCheck = indexToCheck;
            }


        }

        // k wird zurückgegeben
        return k;

    }
}

