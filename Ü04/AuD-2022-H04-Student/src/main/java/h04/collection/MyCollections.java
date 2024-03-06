package h04.collection;

import h04.function.ListToIntFunction;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * A collection that allows to order (sort) the unordered sequence. The sorting algorithm is based on merge-sort, switching to
 * selection-sort when the sequence is small to increase performance.
 *
 * @param <T> the type of the elements in the list that can be sorted
 * @author Nhan Huynh
 */
public class MyCollections<T> {

    /**
     * Determines the toggle length when the sorting algorithm should be toggled (usage of another sorting algorithm).
     */
    private final ListToIntFunction<T> function;

    /**
     * The comparator used to compare the elements of the list.
     */
    private final Comparator<? super T> cmp;

    /**
     * Constructs and initializes a {@code MyCollections}.
     *
     * @param function the function determining the toggle length
     * @param cmp      the comparator used to compare the elements of the list
     */
    public MyCollections(ListToIntFunction<T> function, Comparator<? super T> cmp) {
        this.function = function;
        this.cmp = cmp;
    }

    /**
     * Sorts the list in place.
     *
     * @param list the list to sort
     */
    public void sort(List<T> list) {

        // kopieren der Liste in ListItems
        ListItem<T> head = this.listToListItem(list);



        // sortieren der ListItems
        ListItem<T> sortedHead = this.adaptiveMergeSortInPlace(head, function.apply(list));



        // übertragen der sortierten Reihenfolge in die Liste
        this.listItemToList(sortedHead, list);


        int count = 0;

    }

    /**
     * Transfers all elements from a list to a list item sequence.
     *
     * @param list the list to transfer from
     * @return the list item sequence containing the element of the list
     */
    private ListItem<T> listToListItem(List<T> list) {

        ListItem<T> head = null;
        ListItem<T> tail = null;

        Iterator<T> itList = list.iterator();

// solange noch Elemente in der Liste sind werden neue Items angehängt und der key übertragen
        while (itList.hasNext()) {

            if (head == null) {
                head = tail = new ListItem<>();
                head.key = itList.next();
            } else {
                tail.next = new ListItem<>();
                tail = tail.next;
                tail.key = itList.next();

            }
        }

        return head;
    }

    /**
     * Transfers all elements from a ListItem sequence to a list.
     *
     * @param head the list item sequence
     * @param list the list to transfer to
     */
    private void listItemToList(ListItem<T> head, List<T> list) {



        ListItem<T> pointer = head;
        int index = 0;

        // solange der pointer nächste Elemente hat wird das element der Liste am Index durch den schlüsselwert des pointers überschrieben
        while (pointer != null && index < list.size()) {
            list.set(index, pointer.key);

            index++;
            pointer = pointer.next;
        }
    }

    /**
     * Sorts the list in place using the merge sort algorithm. If the (sub-)sequence is smaller than the specified threshold, the
     * selection sort algorithm  (in place) is used.
     *
     * @param head      the list to sort
     * @param threshold the threshold determining the toggle length
     * @return the sorted list
     */
    private ListItem<T> adaptiveMergeSortInPlace(ListItem<T> head, int threshold) {

        if (head == null)
            return null;

        ListItem<T> pCounter = head;

        // Einrichten der variablen zum durchzählen der Liste sowie ob die Sequenz sortiert ist
        // der count beginnt bei 1 weil wir schon bei pCounter.next == null aufhören
        int count = 1;
        boolean isSorted = true;

        T t1;
        T t2;


        while (pCounter.next != null) {

            t1 = pCounter.key;
            t2 = pCounter.next.key;

            // sobald einmal der zweite Wert dem ersten vorausgeht ist die Sequenz nicht sortiert
            if (cmp.compare(t1, t2) > 0)
                isSorted = false;

            // zählen der Elemente und weiterschalten des pointers
            count++;
            pCounter = pCounter.next;


        }

        // 1 und 2 sind eingetreten - head wird zurückgegeben
        if (isSorted) {

            return head;
        }

        // nur 1 ist eingetreten : selectionsort wird mit der sequenz aufgerufen
        else if (count <= threshold) {
            return selectionSortInPlace(head);
        }
        // ansonsten werden die beiden Teilsequenzen in sich durch den rekursiven aufruf sortiert
        else {

            if (count % 2 == 1)
                count++;

            // einrichten der optimalSize
            int optimalSize = count / 2;

            // erstellen der zweiten Liste
            ListItem<T> rightHead = split(head, optimalSize);


            // zusammenfügen der Teilsequenzen
            return merge(adaptiveMergeSortInPlace(head, threshold), adaptiveMergeSortInPlace(rightHead, threshold));


        }


    }

    /**
     * Splits the list into two subsequences.
     *
     * <p>The decomposition of the list into two subsequences is related to the searched optimal size and the number of
     * elements of runs, which is close to the optimal size.
     *
     * @param head        the list to split
     * @param optimalSize the optimal size after the split
     * @return the second part of the list
     */
    private ListItem<T> split(ListItem<T> head, int optimalSize) {

        // falls head null ist wird null zurückgegeben
        if (head == null)
            return null;

        assert head.next != null;


        // zählen der Items, wenn eine ungerade Anzahl an Items vorhanden ist wird die optimalSize -1 genommen
        int count = 0;
        ListItem<T> countPointer = head;

        while (countPointer != null) {
            count++;
            countPointer = countPointer.next;
        }

        // zuweisen der Anzahl der Schritte die vorangegangen werden muss

        int stepsToOptimalSize = count - optimalSize;


        // der Verweis auf das ListItem was zurückgegeben wird
        ListItem<T> secondHead;


        ListItem<T> previousRunPointer = head;
        ListItem<T> pointer = head;

        ListItem<T> pSrcPrevious = null;
        ListItem<T> pSrcActual = null;


        int previousRunDistancecount = 0;

        int actualrunDistanceCount = 0;

        boolean newRun = false;


        T t1 = head.key;
        T t2;

        // weiterschalten bis zur optimalSize
        for (int i = 0; i < stepsToOptimalSize; i++) {

            newRun = false;


            if (pointer.next == null)
                break;

            t2 = pointer.next.key;

            //wenn ein neuer Run gefunden wird wird der previousRunPointer auf das Element gesetzt, der pSrcPrevious auf den Pointer
            // und der previousRuncount auf 0 gesetzt
            // der pointer wird eins weitergeschaltet und t2 in t2 gespeichert.
            if (cmp.compare(t1, t2) > 0) {
                pSrcPrevious = pointer;
                previousRunPointer = pointer.next;
                previousRunDistancecount = 0;

                // wenn der pointer auf dem head ist werden die zwei counts auf den head gestellt und der pointer eins weitergeschaltet
                if (pointer == head) {

                    pSrcActual = pointer;

                    previousRunDistancecount++;
                    pointer = pointer.next;
                    t1 = t2;
                    newRun = true;
                    continue;
                }
                pointer = pointer.next;

                if (pSrcActual != null)
                    pSrcActual = pSrcActual.next;

                t1 = t2;

                // falls der pointer in der letzten Runde auf dem richtigen element steht und da genau ein anfang von einem neuen Run ist

                newRun = true;

                continue;

            }

            // wenn der pointer auf dem head ist werden die zwei counts auf den head gestellt und der pointer eins weitergeschaltet
            if (pointer == head) {

                pSrcActual = pointer;

                previousRunDistancecount++;
                pointer = pointer.next;
            }

            // ansonsten werden der previous und der
            else {
                // dann wird so oder so der count eins hochgesetzt
                // und der pointer auf eins weiter
                previousRunDistancecount++;
                pointer = pointer.next;

                if (pSrcActual != null) {

                    pSrcActual = pSrcActual.next;
                }
            }

            // als nächstes wird dann der aktuelle Wert verglichen
            t1 = t2;

        }


        // der pointer steht genau auf dem ELement an dem die Optimalsize gewährleistet würde
        // dh Suche des nächsten run Anfangs

        while (pointer != null && !newRun) {


            t2 = pointer.key;

            // wenn der nächste Run gefunden wurde dann steht der pointer auf dem anfang des nächsten runs
            // und die schleife wird verlassen
            if (cmp.compare(t1, t2) > 0)
                break;

            //  ansonsten wird der actualcount eins hochgsetztr und der pointer wird eins weitergeschaltet
            actualrunDistanceCount++;
            pointer = pointer.next;

            if (pSrcActual != null)
                pSrcActual = pSrcActual.next;

        }


        // wenn der vorherige run näher an der optimalLength ist wird oder die Distanz gleich ist der previous als head zurückgegeben
        if (actualrunDistanceCount < previousRunDistancecount || previousRunDistancecount == actualrunDistanceCount && pointer != null) {

            //verweisen aud den secondHead
            secondHead = pointer;

            // abschneiden des Rests
            if (pSrcActual != null)
                pSrcActual.next = null;


            return secondHead;


        }

        // wenn der aktuelle run näher ist

        // verweis auf den Pointer
        secondHead = previousRunPointer;

        // abschneiden des Rests
        if (pSrcPrevious != null)
            pSrcPrevious.next = null;


        return secondHead;
    }

    /**
     * Merges the two given sub-sequences into one sorted sequence.
     *
     * @param left  the left sub-sequence
     * @param right the right sub-sequence
     * @return the merged sorted sequence
     */
    private ListItem<T> merge(ListItem<T> left, ListItem<T> right) {


        ListItem<T> head = null;
        ListItem<T> tail = null;

        ListItem<T> pointerLeft = left;
        ListItem<T> pointerRight = right;


        // solange beide Listen noch elemente haben werden diese verglichen und aneinandergereiht
        while (pointerLeft != null && pointerRight != null) {

            int comp = cmp.compare(pointerLeft.key, pointerRight.key);

            // das linke ist dem rechten vorangestellt
            if (comp < 0) {

                if (head == null) {
                    head = tail = pointerLeft;
                    pointerLeft = pointerLeft.next;
                } else {
                    tail.next = pointerLeft;
                    tail = tail.next;
                    pointerLeft = pointerLeft.next;
                }
            }

            // das rechte ist dem linken vorangestellt
            else if (comp > 0) {

                if (head == null) {
                    head = tail = pointerRight;
                    pointerRight = pointerRight.next;
                } else {
                    tail.next = pointerRight;
                    tail = tail.next;
                    pointerRight = pointerRight.next;
                }
            }

            // beide sind gleich - nur das linke muss eingereiht werden (es könnte sein, dass links weitere Elemente sind die gleichauf sind)

            else {

                if (head == null) {
                    head = tail = pointerLeft;
                    pointerLeft = pointerLeft.next;
                } else {
                    tail.next = pointerLeft;
                    tail = tail.next;
                    pointerLeft = pointerLeft.next;
                }

            }
        }

        // falls in der linken Sequenz noch Elemente sind werden diese angehängt
        if (pointerLeft != null) {

            if (head == null) {
                head = tail = pointerLeft;

            } else {
                tail.next = pointerLeft;
                tail = tail.next;

            }


        }

        // falls in der rechten Sequenz noch Elemente sind werden diese angehängt
        if (pointerRight != null) {
            if (head == null) {
                head = tail = pointerRight;

            } else {
                tail.next = pointerRight;
                tail = tail.next;

            }


        }


        return head;
    }

    /**
     * Sorts the list in place using the selection sort algorithm.
     *
     * @param head the list to sort
     * @return the sorted list
     */
    private ListItem<T> selectionSortInPlace(ListItem<T> head) {

        // falls eine einelementige Liste übergeben wird ist diese schon sortiert und muss zurückgegeben werden
        if (head == null || head.next == null)
            return head;


        // der head wird im itemToInsert gespeichert
        ListItem<T> itemToInsert = head;


        ListItem<T> previousItemToInsert = null;
        ListItem<T> pointerOnNextItemToInsert = head;
        ListItem<T> runPointer = head;


        // ermitteln der länge der Sequenz
        ListItem<T> pCount = head;
        int length = 0;
        while (pCount != null) {
            length++;
            pCount = pCount.next;
        }


        // äußere Schleife, für jeden index wird von hinten nach vorne das nächste Item ermittelt

        for (int i = length - 1; i > 0; i--) {

            pointerOnNextItemToInsert = head;
            runPointer = head;

            // suche des nächsten einzufügenden items
            for (int j = 0; j < i; j++) {

                // falls der runpointer oder next == null wird die schleife unterbrochen
                if (runPointer.next == null)
                    break;

                // wenn ein Element an der nächsten Stelle
                // gefunden wird das größer oder gleichgroß (und weiter rechts) ist wird der pointerOnNextItem auf dieses gesetzt und der previous auf den Pointer.
                if (cmp.compare(pointerOnNextItemToInsert.key, runPointer.next.key) < 0 ||
                    cmp.compare(pointerOnNextItemToInsert.key, runPointer.next.key) == 0) {

                    previousItemToInsert = runPointer;
                    pointerOnNextItemToInsert = runPointer.next;
                    runPointer = runPointer.next;

                    continue;

                }

                runPointer = runPointer.next;
            }

            //  der runpointer steht auf dem element hinter das das nächste Element soll

            // wenn der Wert des laufpointers dort bleiben soll
            if (runPointer == pointerOnNextItemToInsert) {
                continue;
            }


            // falls es der head ist wird dieser abgekoppelt
            else if (pointerOnNextItemToInsert == head) {

                itemToInsert = head;
                head = head.next;
                itemToInsert.next = null;

            }

            // wenn es ein item in der Mitte ist wird dieses augekoppelt
            else {

                itemToInsert = pointerOnNextItemToInsert;
                itemToInsert.next = null;
                previousItemToInsert.next = previousItemToInsert.next.next;


            }

            // einkoppeln
            // falls der runpointer am letzten Element ist
            if (runPointer.next == null) {

                runPointer.next = itemToInsert;

            }

            // falls es im head eingefügt wird
            else {

                itemToInsert.next = runPointer.next;
                runPointer.next = itemToInsert;
            }


        }

        return head;
    }

}









