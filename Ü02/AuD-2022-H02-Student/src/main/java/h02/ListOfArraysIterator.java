package h02;

import java.util.NoSuchElementException;

/**
 * Represents an iterator over a ListOfArrays object.
 *
 * @param <T> The generic type of the list that this iterator iterates on.
 */
public class ListOfArraysIterator<T> {


    private ListOfArraysItem<T> listOfArraysItem = null;

    int index = 0;


    /**
     * A constructor to construct a ListOfArraysIterator object.
     *
     * @param head The head of the list to iterate over.
     */
    public ListOfArraysIterator(ListOfArraysItem<T> head) {

        this.listOfArraysItem = head;
    }

    /**
     * Returns whether there is another element to be iterated on.
     *
     * @return True iff there is another element.
     */
    public boolean hasNext() {

        return listOfArraysItem != null;
    }

    /**
     * Returns the next element of this iterator and moves the iterator one element forward.
     *
     * @return The next element.
     * @throws NoSuchElementException If there is no next element.
     */
    public T next() throws NoSuchElementException {

        // falls listOfArraysItem bei null steht wird eine Exception geworfen
        if (listOfArraysItem == null)
            throw new NoSuchElementException();

        // solange der index >= der currentNumber ist ein Knoten weitergeschaltet und der index wieder auf 0 gesetzt
        while (  index >= listOfArraysItem.currentNumber) {

            listOfArraysItem = listOfArraysItem.next;
            index = 0;

            // falls dann das Ende der listOfArrays erreicht ist wird eine Exception geworfen
            if (listOfArraysItem == null)
                throw new NoSuchElementException();
        }

        while (listOfArraysItem.array[index] == null){
            index++;
        }

        // der Komponent wird gespeichert und der index eins hochgeschaltet
        T component = listOfArraysItem.array[index];
        index++;

        // falls nach hochzählen der index >= der Länge des Arrays ist oder
        // für hasNext() muss das Attribut immer am aktuellen Element stehen
        //// solange der index >= der currentNumber ist ein Knoten weitergeschaltet und der index wieder auf 0 gesetzt
        while (listOfArraysItem != null && index >= listOfArraysItem.currentNumber) {
            listOfArraysItem = listOfArraysItem.next;
            index = 0;
        }

        return component;


    }
}
