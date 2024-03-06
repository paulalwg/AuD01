package h03;

import java.util.LinkedList;
import java.util.List;

/**
 * This class realizes the algorithm of string matching BOFA using a pre-processed PartialMatchLengthUpdateValues object to search through a given source string.
 *
 * @param <T> The type of the letters, etc.
 */
public class StringMatcher<T> {
    /**
     * The update values to be used in the algorithm.
     */
    private final PartialMatchLengthUpdateValues<T> VALUES;

    /**
     * Constructs a new StringMatcher object with the given PartialMatchLengthUpdateValues object.
     *
     * @param values The update values for this object.
     */
    public StringMatcher(PartialMatchLengthUpdateValues<T> values) {
       this.VALUES = values;
    }

    /**
     * Finds and returns all indices at which an occurrence of the search string (pre-processed with the update values object) starts in the given source.
     *
     * @param source The source string to search through.
     * @return The list of calculated indices.
     */
    public List<Integer> findAllMatches(T[] source) {

   // erzeugen der Rückgabeliste
       List<Integer> matches = new LinkedList<Integer>();

       // falls null übergeben wird wird eine leere Liste zurückgegeben
       if ( source == null)
           return matches;

       int searchStringLength = VALUES.getSearchStringLength();

       int state = 0;

       // source wird einmal durchgeganen
       for ( int i = 0; i < source.length; i++){

           // der status wird aktualisiert
           state = VALUES.getPartialMatchLengthUpdate(state, source[i]);

           // wenn der status gleich der Länge des searchstrings ist wird der index zurückgerechnet, -2, weil wir bei index 1 anfangen
           // danach muss dann der status wieder geupdated werden
           if ( state == searchStringLength) {
               matches.add(i - (searchStringLength - 2));
               state = VALUES.getPartialMatchLengthUpdate(state, source[i]);
           }
       }

return matches;

    }
}
