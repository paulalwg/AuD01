package h03;

import java.util.LinkedList;
import java.util.List;

/**
 * A class that represents string matching BOFA using an intern array of lists of transitions -
 * basically an array containing the states and their possible transitions to other states.
 *
 * @param <T> The type of the function/letters of the used alphabet.
 */
public class PartialMatchLengthUpdateValuesAsAutomaton<T> extends PartialMatchLengthUpdateValues<T> {
    /**
     * The states of the automaton as a list of its transitions.
     */
    private List<Transition<T>>[] theStates;

    /**
     * Constructs a PartialMatchLengthUpdateValuesAsAutomaton object with the given function and search string.
     * This is done by creating the private array of this object by creating the various lists and their possible transitions to other states.
     *
     * @param fct          The function to be used.
     * @param searchString The search string to be used.
     */
    public PartialMatchLengthUpdateValuesAsAutomaton(FunctionToInt<T> fct, T[] searchString) {
        super(fct);

        //erzeugen des Arrays mit der Länge der Zustände

        List<Transition<T>>[] theStates = new List[searchString.length + 1];


// füllen des Arrays mit leeren TransitionListen
        for (int i = 0; i < theStates.length; i++) {

            // erstellen der Liste
            List<Transition<T>> transitionList = new LinkedList<Transition<T>>();

            theStates[i] = transitionList;

        }

        //initialisieren der Listen an allen indizes von theStates
        for (int i = 0; i < theStates.length; i++) {


            // füllen der Transitionslisten: bei zustand 1 beginnend wird für jeden Zustand geschaut
            // welche Zustände mit welchen Buchstaben erreicht werden können

            for (int j = 1; j < theStates.length; j++) {

                List<T> letters = new LinkedList<T>();


                // erzeugen des teilarrays vom searchString - wir überprüfen den index m : m+1 weil länge immer index+1
                Object[] objectArrayToCompute = new Object[i + 1];
                T[] arrayToCompute = (T[]) objectArrayToCompute;

                // füllen des Teilarrays vom searchstring außer dem letzten Platz
                int index = 0;
                for (int n = 0; n < arrayToCompute.length - 1; n++) {

                    arrayToCompute[n] = searchString[index];
                    index++;
                }

                // durchgehen des searchStrings um die letters zu füllen mit den computewerten: alle möglichen kombinationen werden ausprobiert
                for (int m = 0; m < searchString.length; m++) {

                    // falls wir beim letzten Buchstaben angekommen sind muss jeder Buchstabe geprüft werden
                    if ( i >= searchString.length){
                        arrayToCompute[arrayToCompute.length-1] = searchString[m];
                    }
                    // Hier kann i nur < searchString.length sein
                    // falls gerade Buchstabe der zum nächsten Zustand führt wird continue - der Fall wird später eingefügt
                    // hier muss noch geprüft werden ob ein Buchstabe zu Zustand 1 führt.
                  else if (fct.apply(searchString[i]) == fct.apply(searchString[m])) {

                    continue;
                   }
                    // ansonsten wird der buchstabe an m eingefügt und mit compute getestet
                    else {

                        // füllen des letzten Platzes : durchgehen durch alle möglichen Buchstaben
                        arrayToCompute[arrayToCompute.length - 1] = searchString[m];
                    }


                    // checken ob der Buchstabe schon in der Liste ist
                    // ist der gleiche Buchstabe (apply angewendet auf T ) schon vorhanden?
                    boolean containsLetter = false;

                    for (int k = 0; k < letters.size(); k++) {
                        if (fct.apply(letters.get(k)) == fct.apply(searchString[m]))
                            containsLetter = true;
                    }

                    // wenn der Buchstabe noch nicht vorhanden ist und zum gesuchten Zustand führt wird er in die liste mitaufgenommen,
                    // m weil wir grade den Buchstaben an stelle m betrachten
                    if (computePartialMatchLengthUpdateValues(arrayToCompute) == j && !containsLetter)
                        letters.add(searchString[m]);

                }

                // ist der gleiche Buchstabe (apply angewendet auf T ) schon vorhanden oder i der index gar nicht mehr vorhanden?
                boolean containsLetterOrIndexOutOfBounds = false;

                for (int k = 0; k < letters.size(); k++) {

                    if (i >= searchString.length) {
                        containsLetterOrIndexOutOfBounds = true;
                        break;
                    }

                    if ( fct.apply(letters.get(k)) == fct.apply(searchString[i]))
                        containsLetterOrIndexOutOfBounds = true;
                }

                // wenn wir für Zustand j schauen muss der Buchstabe an index i (dessen vorkommen zu Zustand j führt) eingefügt werden
                // solange er ncht schon in der Liste ist oder wir beim letzten Buchstaben angekommen sind
                if (i == j - 1 && !containsLetterOrIndexOutOfBounds)
                    letters.add(searchString[i]);

                theStates[i].add(new Transition<T>(j, letters));

            }

        }

        this.theStates = theStates;


    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getPartialMatchLengthUpdate(int state, T letter) {

        // der Automat wird  an index state nach dem Letter durchsucht und das J zurückgegeben in dem der Letter gefunden wurde oder 0
        int update = 0;

        for (Transition<T> trans : theStates[state]) {

            for (int i = 0; i < trans.LETTERS.size(); i++) {

                // wenn apply aufgerufen auf den letter gleich dem letter aus der Liste am gesuchten state ist wird update
                // upgedatet
                if (fct.apply(trans.LETTERS.get(i)) == fct.apply(letter))
                    update = trans.J;
            }
        }

        return update;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSearchStringLength() {

        return theStates.length - 1;
    }
}
