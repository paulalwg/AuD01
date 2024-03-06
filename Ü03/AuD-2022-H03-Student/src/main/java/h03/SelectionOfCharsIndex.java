package h03;

import java.util.Iterator;
import java.util.List;

/**
 * A class that represents a function with a given alphabet.
 */
public class SelectionOfCharsIndex implements FunctionToInt<Character> {
    /**
     * The chars of the objects' alphabet.
     */
    private final char[] theChars;

    /**
     * Constructs a new SelectionOfCharsIndex object with the given alphabet scope.
     * The given alphabet must not be null, has to contain at least one element and each element has to be unique.
     *
     * @param theAlphabet The given alphabet.
     */
    public SelectionOfCharsIndex(List<Character> theAlphabet) {

        char[]theChars = new char[theAlphabet.size()];

        Iterator<Character> it1 = theAlphabet.iterator();

        for ( int i = 0; i < theChars.length; i++){
            theChars[i] = it1.next();
        }

        this.theChars = theChars;


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int sizeOfAlphabet() {

       return theChars.length;
    }

    /**
     * Returns the index at which the given parameter is contained in the alphabet.
     *
     * @param character The given parameter to be searched for.
     * @return The index of the given parameter.
     * @throws IllegalArgumentException Iff the given parameter is not contained in the alphabet.
     */
    @Override
    public int apply(Character character) throws IllegalArgumentException {

        // wenn das Zeichen in theChars enthalten ist wird der Index zurückgegeben
        for (int i = 0; i < theChars.length ; i ++){

            if (theChars[i] == (character))
                return i;
        }

       // andernfalls wird eine Exception geworfen
        throw new IllegalArgumentException();
    }
}
