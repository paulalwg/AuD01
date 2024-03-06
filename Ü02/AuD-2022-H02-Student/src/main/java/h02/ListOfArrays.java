package h02;


import java.util.Collection;
import java.util.Iterator;

/**
 * A list of ListOfArraysItem objects.
 * Contains the length of the arrays of this list and a reference to the first and last item (or null as an empty list).
 *
 * @param <T> The generic type of this list
 */
public class ListOfArrays<T> {
    /**
     * The length of the arrays of the items of this list.
     */
    private static final int ARRAY_LENGTH;

    /**
     * The head of this list.
     */
    private ListOfArraysItem<T> head;

    /**
     * The tail of this list.
     */
    private ListOfArraysItem<T> tail;

    /**
     * der static - Block initialisiert die Klassenkosntante ARRAY_LENGTH mit 256, dies beschreibt die
     * maximale Länge der Arrays die von ListOfArraysItem referenziert werden
     */
    static {
        ARRAY_LENGTH =  256;
    }

    /**
     * Constructs a list of ListOfArrayItem objects that represents the elements given in sequence.
     *
     * @param sequence The elements to be added to the list.
     */
    public ListOfArrays(T[] sequence) {


        // null ||länge 0 : es wird eine leere Sequenz eingefügt - also head & tail blieben null
        if (sequence == null || sequence.length == 0) {

            head = tail = null;

        }


        // sequence.length > ARRAY_LENGTH ist wird so lange ein neues Array erzeugt und  die Elemente eingefügt, bis alle Elemente eingefügt sind

        else if (sequence.length > ARRAY_LENGTH) {


            // der index am übergebenen array
            int sequenceIndex = 0;


            // schleife zählt wie viele Arrays gebildet werden sollen
            while (sequenceIndex < sequence.length) {

                // wenn der Kopf noch leer ist wird das erste Array als Kopf eingefügt
                if (head == null) {

                    int newIndex = 0;

                    // erzeugen des neuen Arrays
                    Object[] ObjectArray = new Object[ARRAY_LENGTH];
                    T[] array = (T[]) ObjectArray;

                    // füllen des neuen Arrays
                    while (newIndex < ARRAY_LENGTH) {

                        // falls das Ende der sequence erreicht ist ist die Methode vorbei
                        if (sequenceIndex >= sequence.length)
                            return;

                        // falls null steht an dem Index in der übergebenen sequence wird der Index eins hochgeschaltet
                        //und die schleife weitergegangen
                        if (sequence[sequenceIndex] == null) {
                            sequenceIndex++;
                            continue;
                        }

                        // ansonsten wird das Element am sequenceIndex im nächsten Index des neuen Arrays gespeichert
                        array[newIndex] = sequence[sequenceIndex];
                        newIndex++;
                        sequenceIndex++;

                    }

                    // einfügen des Arrays in die ListOfArrays - currentNumber ist newIndex  weil newIndex mit 0 angefangen hat und nach jedem schleifenaufruf beim nächsten index ist
                    // die currentNumber abweichen kann von ARRAY_LENGTH

                    ListOfArraysItem<T> item = new ListOfArraysItem<>();
                    item.array = array;
                    item.currentNumber = newIndex;
                    head = tail = item;

                }

                // anhängen der nächsten Elemente an den tail und einfügen der Arrays in den tail

                else {

                    int newIndex = 0;

                    // erzeugen des neuen Arrays
                    Object[] ObjectArray = new Object[ARRAY_LENGTH];
                    T[] array = (T[]) ObjectArray;

                    // füllen des neuen Arrays
                    while (newIndex < ARRAY_LENGTH) {

                        // falls das Ende der sequence erreicht ist break - anhängen des Arrays an den tail, die restlichen Elemente bleiben null
                        if (sequenceIndex >= sequence.length)
                            break;

                        // falls null steht an dem Index in der übergebenen sequence wird der Index eins hochgeschaltet
                        //und die schleife weitergegangen
                        if (sequence[sequenceIndex] == null) {
                            sequenceIndex++;
                            continue;
                        }

                        // ansonsten wird das Element am sequenceIndex im nächsten Index des neuen Arrays gespeichert
                        array[newIndex] = sequence[sequenceIndex];
                        newIndex++;
                        sequenceIndex++;


                    }

                    // einfügen des Knotens hinter tail und verschieben von tail um eins nach hinten

                    ListOfArraysItem<T> item = new ListOfArraysItem<>();
                    item.array = array;
                    item.currentNumber = newIndex;
                    tail.next = item;
                    tail = tail.next;

                }


            }


        }

        //  das übergebene array hat <= ARRAY_LENGTH Elemente - es werden also nur alle elemente der sequence im head gespeichert
        else {

            // die eingefügten Elemente werden gezählt
            int currentNumber = 0;

            // erzeugen des neuen Arrays
            Object[] ObjectArray = new Object[ARRAY_LENGTH];
            T[] array = (T[]) ObjectArray;

            int sequenceIndex = 0;
            int newIndex = 0;
            // füllen des neuen Arrays solange in der Sequence elemente sind
            while (sequenceIndex < sequence.length) {

                // falls null steht an dem Index in der übergebenen sequence wird der Index eins hochgeschaltet
                //und die schleife weitergegangen
                if (sequence[sequenceIndex] == null) {
                    sequenceIndex++;
                    continue;
                }

                // ansonsten wird das Element am aktuellen index im nächsten index des neuen Arrays gespeichert & der Index wird gezählt
                array[newIndex] = sequence[sequenceIndex];
                newIndex++;
                sequenceIndex++;
                currentNumber++;


            }
            // einfügen des Arrays in die ListOfArrays - currentNumber ist newIndex +1 weil arrays bei 0 anfangen und
            // die currentNumber abweichen kann von ARRAY_LENGTH

            ListOfArraysItem<T> item = new ListOfArraysItem<>();
            item.array = array;
            item.currentNumber = currentNumber;
            head = tail = item;

        }
    }


    /**
     * Returns an iterator over this list.
     *
     * @return The iterator of type ListOfArraysIterator.
     */
    public ListOfArraysIterator<T> iterator() {

        return new ListOfArraysIterator<>(head);

    }

    /**
     * Inserts a given collection into this list at index i
     * (elements in front of index i will stay and elements at index i and after will be pushed behind the added collection).
     *
     * @param collection The collection to add.
     * @param i          The index at which the collection should be inserted.
     * @throws IndexOutOfBoundsException If the given index is not within the bounds of this list.
     */
    public void insert(Collection<T> collection, int i) throws IndexOutOfBoundsException {

        // falls die ListOfArrays leer ist oder der aktuale Parameter leer ist - passiert nichts
        if (collection == null)
            return;

        // zählen der Gesamtanzahl der Sequenz - alle currentNumbers gemeinsam
        ListOfArraysItem<T> countPointer = head;
        int numberOfElements = 0;
        while (countPointer != null) {
            numberOfElements += countPointer.currentNumber;
            countPointer = countPointer.next;
        }

        // falls i <0 || i > der Gesamtanzahl der Sequenz wird eine IndexOutOfBoundsException geworfen - falls gleich n wird
        // die Collection einfach hinten angehängt
        if (i < 0 || i > numberOfElements) {

            throw new IndexOutOfBoundsException(i);
        }


        // Iterator der Collection erstellen

        Iterator<T> collectionIterator = collection.iterator();


        //array zum zwischenspeichern erstellen

        // erzeugen des neuen Arrays sodass alle Elemente eines Arrays reinpassenwürden
        Object[] ObjectArrayToStorage = new Object[ARRAY_LENGTH * 2];
        T[] arrayToStorage = (T[]) ObjectArrayToStorage;


        // falls die collection leer ist passiert nichts
        if (!collectionIterator.hasNext())
            return;


        // falls der head null ist und i == 0 (durchs durchzählen muss das dann so sein - > collection wird in den head eingefügt
        if (head == null) {

            ListOfArraysItem<T> pointerOnCurrentNode = head;
            // 2/3 solange noch Elemente in der Collection sind werden neue Arrays hinter head eingefügt
            while (collectionIterator.hasNext()) {

                int newIndex = 0;

                // erzeugen des neuen Arrays
                Object[] ObjectArray = new Object[ARRAY_LENGTH];
                T[] array = (T[]) ObjectArray;

                // füllen des neuen Arrays
                while (newIndex < ARRAY_LENGTH && collectionIterator.hasNext()) {

                    array[newIndex] = collectionIterator.next();
                    newIndex++;

                }

                // falls nur ein Knoten vorhanden ist wird es an tail gehängt, der tail eins verschoben und der pointeronCurentNode auch
                if (pointerOnCurrentNode == tail) {
                    ListOfArraysItem<T> item = new ListOfArraysItem<>();
                    item.array = array;
                    item.currentNumber = newIndex;
                    item.next = tail.next;
                    tail.next = item;
                    tail = tail.next;
                    pointerOnCurrentNode = tail;

                } else if (pointerOnCurrentNode == head) {

                    ListOfArraysItem<T> item = new ListOfArraysItem<>();
                    item.array = array;
                    item.currentNumber = newIndex;
                    head = tail = item;

                }
                // wenn weitere Elemete folgen:
                else {
                    // einfügen des Knotens hinter dem pointer(head) und verschieben des pointers auf das aktuelle element

                    ListOfArraysItem<T> item = new ListOfArraysItem<>();
                    item.array = array;
                    item.currentNumber = newIndex;
                    item.next = pointerOnCurrentNode.next;
                    pointerOnCurrentNode.next = item;
                    pointerOnCurrentNode = pointerOnCurrentNode.next;

                }
            }


        }

        // falls die Collection ganz hinten angefügt werden soll
        // 1 collection <= rest des arrays || 2 collection > als rest des arrays - neue Knoten || 3 der letzte Knoten ist voll, die collection muss komplett in neue knoten gespeichert werden
        // - ausschließen des Falles danach (zugriff auf next
        if (i == numberOfElements) {

            // 1. im tail ist noch Platz - tail wird gefüllt
            if (tail.currentNumber < ARRAY_LENGTH) {

                // solange noch platz im array ist und in der collection noch elemente sind einfügen der Elemente
                // immer am letzten freien index, also currentnumber, weil die immer eins höher ist als der index
                while (tail.currentNumber < ARRAY_LENGTH && collectionIterator.hasNext()) {

                    tail.array[tail.currentNumber] = collectionIterator.next();
                    tail.currentNumber += 1;
                }
            }


            // 2/3 solange noch Elemente in der Collection sind werden neue Arrays
            while (collectionIterator.hasNext()) {

                int newIndex = 0;

                // erzeugen des neuen Arrays
                Object[] ObjectArray = new Object[ARRAY_LENGTH];
                T[] array = (T[]) ObjectArray;

                // füllen des neuen Arrays
                while (newIndex < ARRAY_LENGTH && collectionIterator.hasNext()) {

                    array[newIndex] = collectionIterator.next();
                    newIndex++;

                }


                // einfügen des Knotens hinter tail und verschieben von tail um eins nach hinten, die currentNumber wird angepasst

                ListOfArraysItem<T> item = new ListOfArraysItem<>();
                item.array = array;
                item.currentNumber = newIndex;
                tail.next = item;
                tail = tail.next;

            }

        }


        // collection wird  vorne oder in der Mitte eingefügt
        // i && i-1 sind im selben Knoten : nur der darf verändert werden
        // i && i-1 sind in verschiedenen Knoten : alles erst mal in die beiden und dann erst neue Knoten dazu

        else {


            // wir beginnen bei -1, weil wir bei 0 anfangen zu zählen
            int previousN = -1;

            ListOfArraysItem<T> pointerOnCurrentNode = head;


            // 1. Laufen zu index i-1

            // solange previousN + pointerOnCurrentNode < i-1 weil wir i-1 suchen
            while (previousN + pointerOnCurrentNode.currentNumber < i - 1) {

                previousN += pointerOnCurrentNode.currentNumber;

                if (pointerOnCurrentNode.next == null)
                    break;
                pointerOnCurrentNode = pointerOnCurrentNode.next;
            }


            // wir sind in dem Knoten in dem sich i-1 befindet : Laufen zu i -1

            int indexMinusOne = -1;

            int counter = 1;

            // hochzählen zum index -1
            while (previousN + counter < i) {

                indexMinusOne++;
                counter++;
            }

            //  falls der index-1 bei -1 stehen bleibt heißt es, dass wir an index 0 im array sind : kann auch im head sein
            // werden alle elemente im storage untergebracht und eingefügt
            if (indexMinusOne == -1) {


                for (int j = 0; j < pointerOnCurrentNode.currentNumber; j++) {
                    arrayToStorage[j] = pointerOnCurrentNode.array[j];
                }

                int newIndex = 0;

                // einfügen der Elemente in das array solange Platz ist & die Collection elemente hat & die currentNumber angeglichen
                while (newIndex < ARRAY_LENGTH && collectionIterator.hasNext()) {

                    pointerOnCurrentNode.array[newIndex] = collectionIterator.next();
                    newIndex++;
                    pointerOnCurrentNode.currentNumber = newIndex;
                }


                // 2/3 solange noch Elemente in der Collection sind werden neue Arrays hinter head eingefügt
                while (collectionIterator.hasNext()) {

                    newIndex = 0;

                    // erzeugen des neuen Arrays
                    Object[] ObjectArray = new Object[ARRAY_LENGTH];
                    T[] array = (T[]) ObjectArray;

                    // füllen des neuen Arrays
                    while (newIndex < ARRAY_LENGTH && collectionIterator.hasNext()) {

                        array[newIndex] = collectionIterator.next();
                        newIndex++;

                    }

                    // falls nur ein Knoten vorhanden ist wird es an tail gehängt, der tail eins verschoben und der pointeronCurentNode auch
                    if (pointerOnCurrentNode == tail) {
                        ListOfArraysItem<T> item = new ListOfArraysItem<>();
                        item.array = array;
                        item.currentNumber = newIndex;
                        item.next = tail.next;
                        tail.next = item;
                        tail = tail.next;
                        pointerOnCurrentNode = tail;

                    }
                    // wenn weitere Elemete folgen:
                    else {
                        // einfügen des Knotens hinter dem pointer(head) und verschieben des pointers auf das aktuelle element

                        ListOfArraysItem<T> item = new ListOfArraysItem<>();
                        item.array = array;
                        item.currentNumber = newIndex;
                        item.next = pointerOnCurrentNode.next;
                        pointerOnCurrentNode.next = item;
                        pointerOnCurrentNode = pointerOnCurrentNode.next;

                    }
                }

                // einfügen der alten Elemente in das Array

                int indexArrayToStorage = 0;

                // solange das Array noch nicht voll ist && das arrayToStorage noch nicht leer bzw null an dem index ist werden die Elemente eingefügt
                // und das arrayToStorage wieder geleert

                while (pointerOnCurrentNode.currentNumber < ARRAY_LENGTH && indexArrayToStorage < arrayToStorage.length) {

                    if (arrayToStorage[indexArrayToStorage] == null)
                        break;

                    pointerOnCurrentNode.array[pointerOnCurrentNode.currentNumber] = arrayToStorage[indexArrayToStorage];
                    arrayToStorage[indexArrayToStorage] = null;

                    pointerOnCurrentNode.currentNumber++;
                    indexArrayToStorage++;

                }

                // falls noch elemente in dem ArrayToStorage sind werden weitere Knoten erzeugt und diese gefüllt
                // es kann höchstens noch ein array angefügt werden weil currentNumber immer <= ARRAY_LENGTH ist

                while (indexArrayToStorage < arrayToStorage.length && arrayToStorage[indexArrayToStorage] != null) {

                    newIndex = 0;

                    // erzeugen des neuen Arrays
                    Object[] ObjectArray = new Object[ARRAY_LENGTH];
                    T[] array = (T[]) ObjectArray;


                    // füllen des Arrays und löschen der Elemente aus dem ArrayToStorage

                    while (newIndex < ARRAY_LENGTH && indexArrayToStorage < arrayToStorage.length) {
                        if (arrayToStorage[indexArrayToStorage] == null)
                            break;
                        array[newIndex] = arrayToStorage[indexArrayToStorage];
                        arrayToStorage[indexArrayToStorage] = null;
                        newIndex++;
                        indexArrayToStorage++;

                    }

                    // falls nur ein Knoten vorhanden ist wird es an tail gehängt, der tail eins verschoben und der pointeronCurentNode auch
                    if (pointerOnCurrentNode == tail) {
                        ListOfArraysItem<T> item = new ListOfArraysItem<>();
                        item.array = array;
                        item.currentNumber = newIndex;
                        item.next = tail.next;
                        tail.next = item;
                        tail = tail.next;
                        pointerOnCurrentNode = tail;
                    }

                    // einfügen des Knotens hinter head und verschieben des pointers auf das aktuelle element
                    else {
                        ListOfArraysItem<T> item = new ListOfArraysItem<>();
                        item.array = array;
                        item.currentNumber = newIndex;
                        item.next = pointerOnCurrentNode.next;
                        pointerOnCurrentNode.next = item;
                        pointerOnCurrentNode = pointerOnCurrentNode.next;
                    }


                }

                return;
            }

            // der index-1 ist an Stelle > 0 in einem Array
            // indexMinusOne steht auf index i-1
            // index i ist auch im gleichen array : kleiner als die currentNumber - nur dieses Array darf verändert werden

            if (indexMinusOne + 1 < pointerOnCurrentNode.currentNumber) {


                // die Elemente ab dem index i werden in das Array zwischengespeichert
                int indexToStorageFromIndexI = indexMinusOne + 1;
                int indexInStorageArray = 0;

                while (indexToStorageFromIndexI < pointerOnCurrentNode.currentNumber) {
                    arrayToStorage[indexInStorageArray] = pointerOnCurrentNode.array[indexToStorageFromIndexI];
                    indexToStorageFromIndexI++;
                    indexInStorageArray++;
                }

                int newIndex = indexMinusOne + 1;

                // einfügen der Elemente in das array solange Platz ist & die Collection elemente hat & die currentNumber angeglichen
                while (newIndex < ARRAY_LENGTH && collectionIterator.hasNext()) {

                    pointerOnCurrentNode.array[newIndex] = collectionIterator.next();
                    newIndex++;
                    pointerOnCurrentNode.currentNumber = newIndex;
                }

                // 2/3 solange noch Elemente in der Collection sind werden neue Arrays hinter dem pointer eingefügt
                while (collectionIterator.hasNext()) {

                    newIndex = 0;

                    // erzeugen des neuen Arrays
                    Object[] ObjectArray = new Object[ARRAY_LENGTH];
                    T[] array = (T[]) ObjectArray;

                    // füllen des neuen Arrays
                    while (newIndex < ARRAY_LENGTH && collectionIterator.hasNext()) {

                        array[newIndex] = collectionIterator.next();
                        newIndex++;

                    }

                    // falls wir im letzten Knoten sind wird es an tail gehängt, der tail eins verschoben und der pointeronCurentNode auch
                    if (pointerOnCurrentNode == tail) {
                        ListOfArraysItem<T> item = new ListOfArraysItem<>();
                        item.array = array;
                        item.currentNumber = newIndex;
                        item.next = tail.next;
                        tail.next = item;
                        tail = tail.next;
                        pointerOnCurrentNode = tail;

                    }
                    // nicht im letzten Element
                    else {
                        // einfügen des Knotens hinter den pointer und verschieben des pointers auf das aktuelle element

                        ListOfArraysItem<T> item = new ListOfArraysItem<>();
                        item.array = array;
                        item.currentNumber = newIndex;
                        item.next = pointerOnCurrentNode.next;
                        pointerOnCurrentNode.next = item;
                        pointerOnCurrentNode = pointerOnCurrentNode.next;

                    }
                }

                // einfügen der alten Elemente in das Array

                int indexArrayToStorage = 0;

                // solange das Array noch nicht voll ist && das arrayToStorage noch nicht leer ist werden die Elemente eingefügt
                // und dann aus dem ArrayToStorage gelöscht

                while (pointerOnCurrentNode.currentNumber < ARRAY_LENGTH && arrayToStorage[indexArrayToStorage] != null) {

                    pointerOnCurrentNode.array[pointerOnCurrentNode.currentNumber] = arrayToStorage[indexArrayToStorage];
                    arrayToStorage[indexArrayToStorage] = null;
                    pointerOnCurrentNode.currentNumber++;
                    indexArrayToStorage++;

                }

                // falls noch elemente in dem ArrayToStorage sind werden weitere Knoten erzeugt und diese gefüllt
                // es kann höchstens noch ein array angefügt werden weil currentNumber immer <= ARRAY_LENGTH ist

                while (arrayToStorage[indexArrayToStorage] != null) {

                    newIndex = 0;

                    // erzeugen des neuen Arrays
                    Object[] ObjectArray = new Object[ARRAY_LENGTH];
                    T[] array = (T[]) ObjectArray;


                    // füllen des Arrays

                    while (newIndex < ARRAY_LENGTH && arrayToStorage[indexArrayToStorage] != null) {
                        array[newIndex] = arrayToStorage[indexArrayToStorage];
                        arrayToStorage[indexArrayToStorage] = null;
                        newIndex++;
                        indexArrayToStorage++;

                    }

                    // falls wir im letzten Knoten sind werden die neuen Knoten an tail gehängt, der tail eins verschoben und der pointeronCurentNode auch
                    if (pointerOnCurrentNode == tail) {
                        ListOfArraysItem<T> item = new ListOfArraysItem<>();
                        item.array = array;
                        item.currentNumber = newIndex;
                        item.next = tail.next;
                        tail.next = item;
                        tail = tail.next;
                        pointerOnCurrentNode = tail;
                    }
                    // einfügen des Knotens hinter head und verschieben des pointers auf das aktuelle element
                    else {
                        ListOfArraysItem<T> item = new ListOfArraysItem<>();
                        item.array = array;
                        item.currentNumber = newIndex;
                        item.next = pointerOnCurrentNode.next;
                        pointerOnCurrentNode.next = item;
                        pointerOnCurrentNode = pointerOnCurrentNode.next;
                    }


                }


            }


            // index i ist im nächsten Array - i muss immer < n sein, da der Fall am anfang behandelt wurde
            else {
                // indexToFindMinusOne steht auf index i-1

                // der index des ArrayToStorage
                int indexInStorageArray = 0;

                // die Elemente ab dem index i werden in das Array zwischengespeichert
                int indexToStorageFromIndexI = indexMinusOne + 1;


                // nur wenn im ersten Array noch Platz ist werden die werte in diesem Array gespeichert
                if (indexToStorageFromIndexI < ARRAY_LENGTH) {
                    // solange der Index in dem Array vorhanden ist und das Element in dem Array nicht null ist
                    while (indexToStorageFromIndexI < pointerOnCurrentNode.currentNumber && pointerOnCurrentNode.array[indexToStorageFromIndexI] != null) {
                        arrayToStorage[indexInStorageArray] = pointerOnCurrentNode.array[indexToStorageFromIndexI];
                        indexToStorageFromIndexI++;
                        indexInStorageArray++;
                    }

                    int newIndex = indexMinusOne + 1;

                    // einfügen der Elemente der collection in das array solange Platz ist & die Collection elemente hat & die currentNumber angeglichen
                    while (newIndex < ARRAY_LENGTH && collectionIterator.hasNext()) {

                        pointerOnCurrentNode.array[newIndex] = collectionIterator.next();
                        newIndex++;
                        pointerOnCurrentNode.currentNumber = newIndex;
                    }
                }

                // das erste Array ist befüllt - wenn noch elemente in der collection sind müssen diese im nächsten Array (das mit index i) gespeichert werden
                // und dann neue Elemente eingefügt werden
                if (collectionIterator.hasNext()) {

                    // Weiterschalten des Pointers auf das Array an dem Index i ist - wir können nicht am letzten element im tail sein,
                    // weil der Fall vorherschon abgeprüft wurde
                    pointerOnCurrentNode = pointerOnCurrentNode.next;

                    // es werden alle Elemente aus dem Array zwischengespeichert ab index 0
                    for (int j = 0; j < pointerOnCurrentNode.currentNumber; j++) {
                        arrayToStorage[indexInStorageArray] = pointerOnCurrentNode.array[j];
                        indexInStorageArray++;
                    }

                    int newIndexArrayI = 0;

                    // das Array wird gefüllt und die currentNumber angepasst
                    while (newIndexArrayI < ARRAY_LENGTH && collectionIterator.hasNext()) {

                        pointerOnCurrentNode.array[newIndexArrayI] = collectionIterator.next();
                        newIndexArrayI++;
                        pointerOnCurrentNode.currentNumber = newIndexArrayI;
                    }


                    // 2/3 solange noch Elemente in der Collection sind werden neue Arrays hinter dem pointer eingefügt und der pointer eins weiter verschoben
                    while (collectionIterator.hasNext()) {

                        int newIndex = 0;

                        // erzeugen des neuen Arrays
                        Object[] ObjectArray = new Object[ARRAY_LENGTH];
                        T[] array = (T[]) ObjectArray;

                        // füllen des neuen Arrays
                        while (newIndex < ARRAY_LENGTH && collectionIterator.hasNext()) {

                            array[newIndex] = collectionIterator.next();
                            newIndex++;

                        }

                        // falls wir im letzten Knoten sind wird es an tail gehängt, der tail eins verschoben und der pointeronCurentNode auch
                        if (pointerOnCurrentNode == tail) {
                            ListOfArraysItem<T> item = new ListOfArraysItem<>();
                            item.array = array;
                            item.currentNumber = newIndex;
                            item.next = tail.next;
                            tail.next = item;
                            tail = tail.next;
                            pointerOnCurrentNode = tail;

                        }
                        // nicht im letzten Element . wird in der Mitte eingefügt
                        else {
                            // einfügen des Knotens hinter den pointer und verschieben des pointers auf das aktuelle element

                            ListOfArraysItem<T> item = new ListOfArraysItem<>();
                            item.array = array;
                            item.currentNumber = newIndex;
                            item.next = pointerOnCurrentNode.next;
                            pointerOnCurrentNode.next = item;
                            pointerOnCurrentNode = pointerOnCurrentNode.next;

                        }
                    }


                }

                // einfügen aller ELemente aus ArrayToSTorage


                int indexArrayToStorage = 0;

                // solange das Array noch nicht voll ist && das arrayToStorage noch nicht leer ist werden die Elemente eingefügt

                while (pointerOnCurrentNode.currentNumber < ARRAY_LENGTH && indexArrayToStorage < arrayToStorage.length) {
                    if (arrayToStorage[indexArrayToStorage] == null)
                        break;
                    pointerOnCurrentNode.array[pointerOnCurrentNode.currentNumber] = arrayToStorage[indexArrayToStorage];
                    arrayToStorage[indexArrayToStorage] = null;

                    pointerOnCurrentNode.currentNumber++;
                    indexArrayToStorage++;

                }

                // falls noch elemente in dem ArrayToStorage sind werden weitere Knoten erzeugt und diese gefüllt
                // es kann höchstens noch ein array angefügt werden weil currentNumber immer <= ARRAY_LENGTH ist

                while (indexArrayToStorage < arrayToStorage.length && arrayToStorage[indexArrayToStorage] != null) {

                    int newIndex = 0;

                    // erzeugen des neuen Arrays
                    Object[] ObjectArray = new Object[ARRAY_LENGTH];
                    T[] array = (T[]) ObjectArray;


                    // füllen des Arrays

                    while (newIndex < ARRAY_LENGTH && indexArrayToStorage < arrayToStorage.length) {
                        if (arrayToStorage[indexArrayToStorage] == null)
                            break;
                        array[newIndex] = arrayToStorage[indexArrayToStorage];
                        arrayToStorage[indexArrayToStorage] = null;
                        newIndex++;
                        indexArrayToStorage++;

                    }

                    // falls wir im letzten Knoten sind werden die neuen Knoten an tail gehängt, der tail eins verschoben und der pointeronCurentNode auch
                    if (pointerOnCurrentNode == tail) {
                        ListOfArraysItem<T> item = new ListOfArraysItem<>();
                        item.array = array;
                        item.currentNumber = newIndex;
                        item.next = tail.next;
                        tail.next = item;
                        tail = tail.next;
                        pointerOnCurrentNode = tail;
                    }
                    // einfügen des Knotens hinter head und verschieben des pointers auf das aktuelle element
                    else {
                        ListOfArraysItem<T> item = new ListOfArraysItem<>();
                        item.array = array;
                        item.currentNumber = newIndex;
                        item.next = pointerOnCurrentNode.next;
                        pointerOnCurrentNode.next = item;
                        pointerOnCurrentNode = pointerOnCurrentNode.next;
                    }


                }


            }


        }
    }


    /**
     * Inserts the elements of given Iterator each with their given offset from the last inserted element (or the first element) into this list.
     *
     * @param iterator The Iterator over the elements (and their offsets) that should be added.
     * @throws IndexOutOfBoundsException If an offset is negative.
     */
    public void insert(Iterator<ElementWithIndex<T>> iterator) throws IndexOutOfBoundsException {


        // der pointer der durch die sequenz durchgeht
        ListOfArraysItem<T> pointerOnCurrentNode = head;

        // wir beginnen bei -1, damit der index nach dem ersten schleifendurchlauf auf 0 steht
        // NumberOfIndizes zählt zum offset durch indexInArray zählt an welchem index im array wir uns befinden

        int indexInArray = -1;

        // erzeugen des Arrays zur Zwischenspeicherung
        Object[] ObjectArrayToStorage = new Object[ARRAY_LENGTH];
        T[] arrayToStorage = (T[]) ObjectArrayToStorage;

        // der fängt bei 1 an, weil wir das erste element an null holen wollen
        int indexToIterate = 1;

        // es wird immer das elemnt an stelle 0 als nächstes ausgegeben
        final int outputIndex = 0;

        // es wird immer an den index ein neues element eingefügt
        int inputIndex = 0;


        int countLength = 0;
        int countOffset = 0;
        boolean lengthHasBeenCounted = false;

        // solange der iterator noch next hat werden elemente eingefügt: Abbruchbedingung1
        while (iterator.hasNext()) {

            ElementWithIndex<T> elementWithIndex = iterator.next();

            // holen des offsets & erweitern des zählers
            int offset = elementWithIndex.getIndex();
            countOffset += offset;

            // falls der offset negativ ist werden die Elemente des ArrayToStorage noch eingefügt
            // und eine Exception geworfen Abbruch 3.1
            if (offset < 0) {

                // wenn indexInArray noch bei -1 steht wird returned
                if (indexInArray == -1) {
                    return;
                }

                // Elemente werden eingefügt
                int indexArrayToStorage = 0;


                int newIndex = indexInArray + 1;

                // wenn sich elemente im ArrayToStorage befinden werden diese ausgetauscht
                // wenn sich Elemente im ArrayToStorage befinden dann wird das Element am aktuellen Index
                // im ArrayToStorage zwischengespeichert und das Element im ArrayToStorage am Index eingefügt
                // und der Index der sich im ArrayToStorage angeschaut wird wird hochgesetzt
                while (arrayToStorage[outputIndex] != null && pointerOnCurrentNode.array[newIndex] != null) {
                    if (newIndex >= ARRAY_LENGTH)
                        break;


                    T element = arrayToStorage[outputIndex];
                    arrayToStorage[outputIndex] = null;

                    // die elemente werden alle nach vorne geschoben, sodass an index 0 das nächste einzufügende element ist
                    while (indexToIterate < arrayToStorage.length && arrayToStorage[indexToIterate] != null) {
                        arrayToStorage[indexToIterate - 1] = arrayToStorage[indexToIterate];
                        arrayToStorage[indexToIterate] = null;
                        indexToIterate++;
                    }
                    // der input wird einen hinter den zu iterieren gesetzt, der erste freie platz und indexToIterate wieder auf 1
                    inputIndex = indexToIterate - 1;
                    indexToIterate = 1;

                    // am nächsten freien index wird dann das element eingesetzt und der inputindex eins hochgsetzt, auf die nächste freie stelle
                    arrayToStorage[inputIndex] = pointerOnCurrentNode.array[newIndex];
                    pointerOnCurrentNode.array[newIndex] = element;
                    inputIndex++;
                    newIndex++;


                }


                // solange am index im arrayToStorage Elemente sind und das Array im Knoten noch nicht voll befüllt ist werden die Elemente übertragen
                while (newIndex < ARRAY_LENGTH && arrayToStorage[indexArrayToStorage] != null) {

                    pointerOnCurrentNode.array[newIndex] = arrayToStorage[indexArrayToStorage];
                    newIndex++;
                    indexArrayToStorage++;
                    pointerOnCurrentNode.currentNumber = newIndex;

                }

                // falls wir im head oder tail sind wird die currentNumber angepasst
                if (pointerOnCurrentNode == head)
                    head.currentNumber = pointerOnCurrentNode.currentNumber;

                if (pointerOnCurrentNode == tail)
                    tail.currentNumber = pointerOnCurrentNode.currentNumber;

                // falls im ArrayTostorage noch ein Element ist wird ein neuer Knoten eingefügt

                if (indexArrayToStorage < arrayToStorage.length && arrayToStorage[indexArrayToStorage] != null) {

                    newIndex = 0;

                    // erzeugen des neuen Arrays
                    Object[] ObjectArray = new Object[ARRAY_LENGTH];
                    T[] array = (T[]) ObjectArray;


                    // füllen des Arrays

                    while (newIndex < ARRAY_LENGTH && indexArrayToStorage < arrayToStorage.length) {
                        array[newIndex] = arrayToStorage[indexArrayToStorage];
                        newIndex++;
                        indexArrayToStorage++;

                    }

                    // falls nur ein Knoten vorhanden ist wird es an tail gehängt, der tail eins verschoben und der pointeronCurentNode auch
                    if (pointerOnCurrentNode == tail) {
                        ListOfArraysItem<T> item = new ListOfArraysItem<>();
                        item.array = array;
                        item.currentNumber = newIndex;
                        item.next = tail.next;
                        tail.next = item;
                        tail = tail.next;
                        pointerOnCurrentNode = tail;
                    }

                    // einfügen des Knotens hinter head und verschieben des pointers auf das aktuelle element
                    else {
                        ListOfArraysItem<T> item = new ListOfArraysItem<>();
                        item.array = array;
                        item.currentNumber = newIndex;
                        item.next = pointerOnCurrentNode.next;
                        pointerOnCurrentNode.next = item;
                        pointerOnCurrentNode = pointerOnCurrentNode.next;
                    }

                    // falls wir im head sind wird die currentNumber angepasst
                    if (pointerOnCurrentNode == head) {
                        head.currentNumber = pointerOnCurrentNode.currentNumber;
                    }


                }

                throw new IndexOutOfBoundsException(offset);
            }

            // der Offset wird bei -1 angefangen zu zählen, weil dann der indexInArray so weit verschoben wird wie die Differenz der Indizes + 1
            int numberOfIndizes = -1;

            // Laufen zu dem index an dem offset ist und das Element eingefügt werden soll

            //Ziel: der index im Array soll auf dem index stehen, an dem das Element eingefügt werden soll
            while (numberOfIndizes < offset) {


                // wenn wir beim letzten Element des Arrays sind wird ein Knoten weitergeschaltet
                if (indexInArray +1 >= pointerOnCurrentNode.currentNumber) {

                    // falls wir am ende des tails sind gehen wir aus der Schleife
                    if (pointerOnCurrentNode.next == null) {

                        if (indexInArray == -1)
                            indexInArray = 0;
                        countLength += pointerOnCurrentNode.currentNumber;
                        lengthHasBeenCounted = true;
                        break;
                    }

                    // der indexinAray wird wieder auf -1 gesetzt und cintinued: der nächste index betrachtet
                    // numberOfIndizes wird nicht hochgsetzt, weil wir keinen Index betrachtet haben.
                    indexInArray = -1;
                    countLength += pointerOnCurrentNode.currentNumber;
                    pointerOnCurrentNode = pointerOnCurrentNode.next;
                    continue;

                }


                numberOfIndizes++;
                indexInArray++;

                // wenn sich Elemente im ArrayToStorage befinden dann wird das Element am aktuellen Index
                // im ArrayToStorage zwischengespeichert und das Element im ArrayToStorage am Index eingefügt
                // und der Index der sich im ArrayToStorage angeschaut wird wird hochgesetzt
                if (arrayToStorage[outputIndex] != null && pointerOnCurrentNode.array[indexInArray] != null) {

                    T element = arrayToStorage[outputIndex];
                    arrayToStorage[outputIndex] = null;

                    // die elemente werden alle nach vorne geschoben, sodass an index 0 das nächste einzufügende element ist
                    while (indexToIterate < arrayToStorage.length && arrayToStorage[indexToIterate] != null) {
                        arrayToStorage[indexToIterate - 1] = arrayToStorage[indexToIterate];
                        arrayToStorage[indexToIterate] = null;
                        indexToIterate++;
                    }
                    // der input wird einen hinter den zu iterieren gesetzt, der erste freie platz und indexToIterate wieder auf 1
                    inputIndex = indexToIterate - 1;
                    indexToIterate = 1;

                    // am nächsten freien index wird dann das element eingesetzt und der inputindex eins hochgsetzt, auf die nächste freie stelle
                    arrayToStorage[inputIndex] = pointerOnCurrentNode.array[indexInArray];
                    pointerOnCurrentNode.array[indexInArray] = element;
                    inputIndex++;


                }


            }


            // der Offset überschreitet die Länge der Sequenz - Abbruchbedingung2
            // die Elemente des ArrayToStorage müssen noch wieder eingefügt werden und die Methode verlassen
            if (lengthHasBeenCounted) {

                // der offset wird größer als die Länge : Abbruchbedingung
                if (countOffset > countLength) {


                    // wenn indexInArray noch bei -1 steht wird returned
                    if (indexInArray == -1) {
                        return;
                    }

                    // Elemente werden eingefügt
                    int indexArrayToStorage = 0;


                    int newIndex = indexInArray + 1;

                    // wenn sich elemente im ArrayToStorage befinden werden diese ausgetauscht
                    // wenn sich Elemente im ArrayToStorage befinden dann wird das Element am aktuellen Index
                    // im ArrayToStorage zwischengespeichert und das Element im ArrayToStorage am Index eingefügt
                    // und der Index der sich im ArrayToStorage angeschaut wird wird hochgesetzt
                    while (arrayToStorage[outputIndex] != null && pointerOnCurrentNode.array[newIndex] != null) {
                        if (newIndex >= ARRAY_LENGTH)
                            break;


                        T element = arrayToStorage[outputIndex];
                        arrayToStorage[outputIndex] = null;

                        // die elemente werden alle nach vorne geschoben, sodass an index 0 das nächste einzufügende element ist
                        while (indexToIterate < arrayToStorage.length && arrayToStorage[indexToIterate] != null) {
                            arrayToStorage[indexToIterate - 1] = arrayToStorage[indexToIterate];
                            arrayToStorage[indexToIterate] = null;
                            indexToIterate++;
                        }
                        // der input wird einen hinter den zu iterieren gesetzt, der erste freie platz und indexToIterate wieder auf 1
                        inputIndex = indexToIterate - 1;
                        indexToIterate = 1;

                        // am nächsten freien index wird dann das element eingesetzt und der inputindex eins hochgsetzt, auf die nächste freie stelle
                        arrayToStorage[inputIndex] = pointerOnCurrentNode.array[newIndex];
                        pointerOnCurrentNode.array[newIndex] = element;
                        newIndex++;


                    }


                    // solange am index im arrayToStorage Elemente sind und das Array im Knoten noch nicht voll befüllt ist werden die Elemente übertragen
                    while (newIndex < ARRAY_LENGTH && arrayToStorage[indexArrayToStorage] != null) {

                        pointerOnCurrentNode.array[newIndex] = arrayToStorage[indexArrayToStorage];
                        newIndex++;
                        indexArrayToStorage++;
                        pointerOnCurrentNode.currentNumber = newIndex;

                    }

                    // falls wir im head oder tail sind wird die currentNumber angepasst
                    if (pointerOnCurrentNode == head)
                        head.currentNumber = pointerOnCurrentNode.currentNumber;

                    if (pointerOnCurrentNode == tail)
                        tail.currentNumber = pointerOnCurrentNode.currentNumber;

                    // falls im ArrayTostorage noch ein Element ist wird ein neuer Knoten eingefügt

                    if (indexArrayToStorage < arrayToStorage.length && arrayToStorage[indexArrayToStorage] != null) {

                        newIndex = 0;

                        // erzeugen des neuen Arrays
                        Object[] ObjectArray = new Object[ARRAY_LENGTH];
                        T[] array = (T[]) ObjectArray;


                        // füllen des Arrays

                        while (newIndex < ARRAY_LENGTH && indexArrayToStorage < arrayToStorage.length) {
                            array[newIndex] = arrayToStorage[indexArrayToStorage];
                            newIndex++;
                            indexArrayToStorage++;

                        }

                        // falls nur ein Knoten vorhanden ist wird es an tail gehängt, der tail eins verschoben und der pointeronCurentNode auch
                        if (pointerOnCurrentNode == tail) {
                            ListOfArraysItem<T> item = new ListOfArraysItem<>();
                            item.array = array;
                            item.currentNumber = newIndex;
                            item.next = tail.next;
                            tail.next = item;
                            tail = tail.next;
                            pointerOnCurrentNode = tail;
                        }

                        // einfügen des Knotens hinter head und verschieben des pointers auf das aktuelle element
                        else {
                            ListOfArraysItem<T> item = new ListOfArraysItem<>();
                            item.array = array;
                            item.currentNumber = newIndex;
                            item.next = pointerOnCurrentNode.next;
                            pointerOnCurrentNode.next = item;
                            pointerOnCurrentNode = pointerOnCurrentNode.next;
                        }

                        // falls wir im head sind wird die currentNumber angepasst
                        if (pointerOnCurrentNode == head) {
                            head.currentNumber = pointerOnCurrentNode.currentNumber;
                        }

                    }
                    return;
                }


                else {
                    // der indexinArray wird eins hochgesetzt, weil durch das break die vorherige schleife frühzeitig unterbrochen wurde
                    indexInArray++;
                }

            }


            // falls das arrayToStorage überlaufen würde werden die Werte aus dem ArrayToStorage wieder eingefügt und die Exception geworfen

            if (inputIndex >= arrayToStorage.length) {

                // wenn indexInArray noch bei -1 steht wird returned
                if (indexInArray == -1) {
                    return;
                }

                // Elemente werden eingefügt
                int indexArrayToStorage = 0;



                int newIndex = indexInArray + 1;

                // wenn sich elemente im ArrayToStorage befinden werden diese ausgetauscht
                // wenn sich Elemente im ArrayToStorage befinden dann wird das Element am aktuellen Index
                // im ArrayToStorage zwischengespeichert und das Element im ArrayToStorage am Index eingefügt
                // und der Index der sich im ArrayToStorage angeschaut wird wird hochgesetzt
                while (arrayToStorage[outputIndex] != null && pointerOnCurrentNode.array[newIndex] != null) {
                    if (newIndex >= ARRAY_LENGTH)
                        break;


                    T element = arrayToStorage[outputIndex];
                    arrayToStorage[outputIndex] = null;

                    // die elemente werden alle nach vorne geschoben, sodass an index 0 das nächste einzufügende element ist
                    while (indexToIterate < arrayToStorage.length && arrayToStorage[indexToIterate] != null) {
                        arrayToStorage[indexToIterate - 1] = arrayToStorage[indexToIterate];
                        arrayToStorage[indexToIterate] = null;
                        indexToIterate++;
                    }
                    // der input wird einen hinter den zu iterieren gesetzt, der erste freie platz und indexToIterate wieder auf 1
                    inputIndex = indexToIterate - 1;
                    indexToIterate = 1;

                    // am nächsten freien index wird dann das element eingesetzt und der inputindex eins hochgsetzt, auf die nächste freie stelle
                    arrayToStorage[inputIndex] = pointerOnCurrentNode.array[newIndex];
                    pointerOnCurrentNode.array[newIndex] = element;
                    newIndex++;


                }


                // solange am index im arrayToStorage Elemente sind und das Array im Knoten noch nicht voll befüllt ist werden die Elemente übertragen
                while (newIndex < ARRAY_LENGTH && arrayToStorage[indexArrayToStorage] != null) {

                    pointerOnCurrentNode.array[newIndex] = arrayToStorage[indexArrayToStorage];
                    newIndex++;
                    indexArrayToStorage++;
                    pointerOnCurrentNode.currentNumber = newIndex;

                }

                // falls wir im head oder tail sind wird die currentNumber angepasst
                if (pointerOnCurrentNode == head)
                    head.currentNumber = pointerOnCurrentNode.currentNumber;

                if (pointerOnCurrentNode == tail)
                    tail.currentNumber = pointerOnCurrentNode.currentNumber;

                // falls im ArrayTostorage noch ein Element ist wird ein neuer Knoten eingefügt

                if (indexArrayToStorage < arrayToStorage.length && arrayToStorage[indexArrayToStorage] != null) {

                    newIndex = 0;

                    // erzeugen des neuen Arrays
                    Object[] ObjectArray = new Object[ARRAY_LENGTH];
                    T[] array = (T[]) ObjectArray;


                    // füllen des Arrays

                    while (newIndex < ARRAY_LENGTH && indexArrayToStorage < arrayToStorage.length) {
                        array[newIndex] = arrayToStorage[indexArrayToStorage];
                        newIndex++;
                        indexArrayToStorage++;

                    }

                    // falls nur ein Knoten vorhanden ist wird es an tail gehängt, der tail eins verschoben und der pointeronCurentNode auch
                    if (pointerOnCurrentNode == tail) {
                        ListOfArraysItem<T> item = new ListOfArraysItem<>();
                        item.array = array;
                        item.currentNumber = newIndex;
                        item.next = tail.next;
                        tail.next = item;
                        tail = tail.next;
                        pointerOnCurrentNode = tail;
                    }

                    // einfügen des Knotens hinter head und verschieben des pointers auf das aktuelle element
                    else {
                        ListOfArraysItem<T> item = new ListOfArraysItem<>();
                        item.array = array;
                        item.currentNumber = newIndex;
                        item.next = pointerOnCurrentNode.next;
                        pointerOnCurrentNode.next = item;
                        pointerOnCurrentNode = pointerOnCurrentNode.next;
                    }

                    // falls wir im head sind wird die currentNumber angepasst
                    if (pointerOnCurrentNode == head) {
                        head.currentNumber = pointerOnCurrentNode.currentNumber;
                    }



                }


                throw new IndexOutOfBoundsException("array could not hold elements to be moved");
            }


            // das Element am aktuellen index das ersetzt werden soll wird im ArrayToStorage zwischengespeichert
            arrayToStorage[inputIndex] = pointerOnCurrentNode.array[indexInArray];


            // das Element wird an indexInArray eingefügt
            pointerOnCurrentNode.array[indexInArray] = elementWithIndex.getElement();


            // wenn wir am Ende des Arrays angekommen sind wird der index wieder auf -1 gesetzt und continued
            if (indexInArray + 1 >= ARRAY_LENGTH) {

                countLength += pointerOnCurrentNode.currentNumber;
                if(pointerOnCurrentNode.next == null){
                    lengthHasBeenCounted = true;
                }
                else {
                    pointerOnCurrentNode = pointerOnCurrentNode.next;
                    indexInArray = -1;
                }

            }


        }


        // Elemene auf dem ArrayToStorage werden hinzugefügt
        int indexArrayToStorage = 0;

        // wenn im array noch platz ist
        if (indexInArray != -1) {
            // einfügen der Elemente aus dem ArrayToSTorage hinter dem eingefügten element

            int newIndex = indexInArray + 1;

            // solange am index im arrayToStorage Elemente sind und das Array im Knoten noch nicht voll befüllt ist werden die Elemente übertragen
            while (newIndex < ARRAY_LENGTH && arrayToStorage[indexArrayToStorage] != null) {

                pointerOnCurrentNode.array[newIndex] = arrayToStorage[indexArrayToStorage];
                newIndex++;
                indexArrayToStorage++;
                pointerOnCurrentNode.currentNumber = newIndex;

            }
        }

        // falls im ArrayTostorage noch mindestens ein Element ist und der vorherige Knoten voll ist  wird ein neuer Knoten eingefügt
        if (indexArrayToStorage < arrayToStorage.length && arrayToStorage[indexArrayToStorage] != null) {

            int newIndex = 0;

            // erzeugen des neuen Arrays
            Object[] ObjectArray = new Object[ARRAY_LENGTH];
            T[] array = (T[]) ObjectArray;


            // füllen des Arrays

            while (newIndex < ARRAY_LENGTH && indexArrayToStorage < arrayToStorage.length) {
                array[newIndex] = arrayToStorage[indexArrayToStorage];
                newIndex++;
                indexArrayToStorage++;

            }

            // falls nur ein Knoten vorhanden ist wird es an tail gehängt, der tail eins verschoben und der pointeronCurentNode auch
            if (pointerOnCurrentNode == tail) {
                ListOfArraysItem<T> item = new ListOfArraysItem<>();
                item.array = array;
                item.currentNumber = newIndex;
                item.next = tail.next;
                tail.next = item;
                tail = tail.next;
                pointerOnCurrentNode = tail;
            }

            // einfügen des Knotens hinter head und verschieben des pointers auf das aktuelle element
            else {
                ListOfArraysItem<T> item = new ListOfArraysItem<>();
                item.array = array;
                item.currentNumber = newIndex;
                item.next = pointerOnCurrentNode.next;
                pointerOnCurrentNode.next = item;
                pointerOnCurrentNode = pointerOnCurrentNode.next;
            }

            if( pointerOnCurrentNode == head)
                head.currentNumber = pointerOnCurrentNode.currentNumber;

            if(pointerOnCurrentNode == tail)
                tail.currentNumber = pointerOnCurrentNode.currentNumber;


        }


    }


    /**
     * Extracts a block of elements of this list. The block is defined by the boundary indices i and j (both included in the block that will be extracted) as a ListOfArrays object.
     * This will delete the extracted elements from the list.
     *
     * @param i The lower boundary index.
     * @param j The higher boundary index.
     * @return The extracted elements as a ListOfArrays object.
     */
    public ListOfArrays<T> extract(int i, int j) {


        // zählen der Gesamtanzahl der Sequenz - alle currentNumbers gemeinsam
        ListOfArraysItem<T> countPointer = head;
        int numberOfElements = 0;
        while (countPointer != null) {
            numberOfElements += countPointer.currentNumber;
            countPointer = countPointer.next;
        }


        if (i < 0) {
            throw new IndexOutOfBoundsException(i);
        }

        if (j > numberOfElements - 1) {
            throw new IndexOutOfBoundsException(j);
        }

        if (i > j) {
            throw new IndexOutOfBoundsException(i + " is greater than " + j);
        }


        // suche nach i
        // wir beginnen bei -1, weil wir bei 0 anfangen zu zählen
        int i2 = -1;


        ListOfArraysItem<T> pointerOnCurrentNode = head;
        ListOfArraysItem<T> previous = head;


        // 1. Laufen zu index i-1
        // solange previousN + pointerOnCurrentNode < i weil wir i suchen
        while (i2 + pointerOnCurrentNode.currentNumber < i) {

            i2 += pointerOnCurrentNode.currentNumber;

            if (pointerOnCurrentNode.next == null)
                break;

            if (pointerOnCurrentNode == head) {

                pointerOnCurrentNode = pointerOnCurrentNode.next;
            } else {

                previous = previous.next;
                pointerOnCurrentNode = pointerOnCurrentNode.next;

            }
        }

        // wir fangen bei -1 an, weil wir hier genau auf dem Index landen wollen - index ist der index an dem wir
        // extrahieren also muss er auf i stehen
        int index = -1;


        // hochzählen zum index an dem sich i befindet - i2 steht danach auf i
        while (i2 < i) {
            index++;
            i2++;
        }


        // erzeugen des Arrays zum speichern der werte i bis j
        Object[] ObjectArray = new Object[j - i + 1];
        T[] arrayToExtract = (T[]) ObjectArray;

        int indexArrayToExtract = 0;

        // wir sind im Knoten in dem sich i befindet am index i mit dem counter
        int countIndizes = i2;

        // wir extrahieren solange die indizes bis wir bei j angekommen sind. j wird auch extrahiert
        while (countIndizes <= j && pointerOnCurrentNode != null) {

            // falls das Ende des Arrays erreicht ist oder
            // in dem knoten kein weiteres Element ist wird solange weitergeschaltet bis wieder ein Knoten mit elementen da ist
            while (index >= ARRAY_LENGTH || pointerOnCurrentNode.array[index] == null) {

                if (pointerOnCurrentNode.next == null)
                    break;

                // der current und previous werden eins weitergeschaltet - falls wir noch im Kopf sind bleibt previous auf dem kopf
                if (pointerOnCurrentNode == head) {

                    pointerOnCurrentNode = pointerOnCurrentNode.next;
                } else {

                    previous = previous.next;
                    pointerOnCurrentNode = pointerOnCurrentNode.next;

                }
                index = 0;
            }

            // das Element wird im arrayToExtract gespeichert, der index im Knoten wird auf null gesetzt
            // und die currentnumber runtergesetzt
            arrayToExtract[indexArrayToExtract] = pointerOnCurrentNode.array[index];
            pointerOnCurrentNode.array[index] = null;
            pointerOnCurrentNode.currentNumber--;


            countIndizes++;
            indexArrayToExtract++;
            index++;

            // falls keine elemente mehr im Array sind wird der Knoten aus der Liste gelöscht
            if (pointerOnCurrentNode.currentNumber == 0) {

                // falls wir im head sind wird der head eins weitergeschaltet : falls das null ist ist danach die ListOfArrays leer
                if (pointerOnCurrentNode == head) {

                    if (head == tail) {
                        head = tail = null;
                        break;
                    }

                    pointerOnCurrentNode = pointerOnCurrentNode.next;
                    previous = previous.next;
                    head = head.next;
                    index = 0;
                }


                // wenn wir im tail sind wird der tail abgeschnitten und der tail auf previous gesetzt, die schleife ist beendet
                else if (pointerOnCurrentNode == tail) {
                    tail = pointerOnCurrentNode = previous;
                    tail.next = null;
                    index = 0;

                }

                // ein element in der Mitte wird entfernt - current wird eins weitergeschaltet und next von previous zeigt auf current
                else {
                    pointerOnCurrentNode = pointerOnCurrentNode.next;
                    previous.next = pointerOnCurrentNode;
                    index = 0;

                }

            }


        }

        if (pointerOnCurrentNode != null) {

            int newIndex = 0;
            // verschieben der Elemente an vordere Indizes falls das so ist
            while (index > 0 && index < ARRAY_LENGTH) {

                // falls an der stelle schon was steht wird der neue Index eins hochgesetzt
                // und wieder durch die schleife gegangen
                if (pointerOnCurrentNode.array[newIndex] != null) {
                    newIndex++;
                    continue;
                }

                pointerOnCurrentNode.array[newIndex] = pointerOnCurrentNode.array[index];
                pointerOnCurrentNode.array[index] = null;
                index++;
                newIndex++;
            }
        }


        return new ListOfArrays<>(arrayToExtract);
    }
}
