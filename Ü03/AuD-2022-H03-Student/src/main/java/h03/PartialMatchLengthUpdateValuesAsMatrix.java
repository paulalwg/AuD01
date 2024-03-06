package h03;

/**
 * A class that represents string matching BOFA using an intern matrix.
 *
 * @param <T> The type of the function/letters of the used alphabet.
 */
public class PartialMatchLengthUpdateValuesAsMatrix<T> extends PartialMatchLengthUpdateValues<T> {
    /**
     * The matrix of this object in which the lookup-table is implemented.
     */
    private int[][] matrix;

    /**
     * Constructs a PartialMatchLengthUpdateValuesAsMatrix object with the given function and search string.
     * This is done by creating the private matrix of this object so that it may be used to look up next possible states.
     *
     * @param fct          The function to be used.
     * @param searchString The search string to be used.
     */
    public PartialMatchLengthUpdateValuesAsMatrix(FunctionToInt<T> fct, T[] searchString) {

        // initialisierung der fct mit dem übergebenen Parameter
        super(fct);

        // erzeugen der Matrix : y-Ache: sizeOfAlphabet und x-Achse seachString.length+1
        int[][] matrix = new int[fct.sizeOfAlphabet()][searchString.length + 1];

        // füllen der Matrix mit dem Searchstring - wenn alles klappt : i+1 weil wir bei index 1 anfangen
        for (int i = 0; i < searchString.length; i++) {

            matrix[fct.apply(searchString[i])][i] = i + 1;
        }

        // berechnen des Zustands nach finden eines Vorkommens, also die Zahl bei der wieder gestartet wird, wenn ein Vorkommen
        //gefunden wurde (für gag zB wird wieder bei 1 gestartet), in der Zeile des letzten im suchstring vorkommenden "Buchstabens" und der
        // Spalte der Länge des searchstrings
        matrix[fct.apply(searchString[searchString.length - 1])][searchString.length] =
            computePartialMatchLengthUpdateValues(searchString);


        // füllen der restlichen Fälle der Buchstaben die im suchString vorkommen

        // durchgehen aller Buchstaben des Suchstrings und Berechnen der Fälle die im Suchstring vorkommen
        for (int i = 0; i < searchString.length; i++) {

            // durchgehen der einzelnenIndizes der Arrays in den Zeilen der Buchstaben des Suchstrings
            for (int j = 0; j < searchString.length + 1; j++) {

                // wenn noch keine Zahl in die Lookuptable gespeichert wurde werden die Zustände berechnet
                if (matrix[fct.apply(searchString[i])][j] == 0) {

                    // erstellen des Arrays indem die ersten x "Buchstaben" des Suchstrings + das Zeichen der reihe gespeichert werden
                    // ga wird gesucht, gg ist das vorkommen {null,null}
                    Object[] objectArray = new Object[j+1];
                    T[] arrayToCompute = (T[])objectArray;


                    // speichern der Zeichen des Suchstrings; {g,null}
                    for ( int m = 0; m < j; m++){
                        arrayToCompute[m] = searchString[m];
                    }

                    // hinzufügen des aktuell betrachteten "Buchstaben"
                    arrayToCompute[j] = searchString[i];



                    matrix[fct.apply(searchString[i])][j] = computePartialMatchLengthUpdateValues(arrayToCompute) ;
                }
            }
        }

        this.matrix = matrix;

        // sollte einer der restlichen Buchstaben die im Alphabet sind und nicht im Suchstring
        //vorkommen vorkommen steht 0 in der Lookup table


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPartialMatchLengthUpdate(int state, T letter) {

        return matrix[fct.apply(letter)][state];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSearchStringLength() {

        return matrix[1].length - 1;
    }
}
