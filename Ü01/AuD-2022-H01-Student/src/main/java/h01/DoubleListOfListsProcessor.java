package h01;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

public class DoubleListOfListsProcessor {


    /**
     * Partitions a copy of {@code listOfLists} so that the sum of each sub-list does not exceed {@code limit}.
     * This implementation must not modify the input list.
     *
     * @param listOfLists a reference to the head of a list with arbitrary size
     * @param limit       the maximum value that may not be exceeded by the sum of any sub-list in the returned list
     * @return a partitioned list of lists
     * @throws RuntimeException if a single value exceeds {@code limit}
     */
    public static @Nullable ListItem<@Nullable ListItem<Double>> partitionListsAsCopyIteratively(
        @Nullable ListItem<@Nullable ListItem<Double>> listOfLists,
        double limit
    ) {
        if (listOfLists == null)
            return null;

        // Laufpointer auf die Hauptliste
        int posHaupt = 0;


        // head und tail der Rückgabe erstellen

        ListItem<ListItem<Double>> headHaupt = null;
        ListItem<ListItem<Double>> tailHaupt = null;

        // Durch die Hauptliste iterieren

        for (ListItem<ListItem<Double>> pHaupt = listOfLists; pHaupt != null; pHaupt = pHaupt.next) {

            // Laufpointer auf die Einzelliste
            int posEinzel = 0;

            // falls der Key null ist, also die Einzelliste leer, wird ein Element in die hauptliste mit einem key der null ist angehängt.

            if (pHaupt.key == null) {

                if (headHaupt == null) {
                    headHaupt = tailHaupt = new ListItem<ListItem<Double>>();
                    tailHaupt.key = null;
                    continue;
                } else {
                    tailHaupt.next = new ListItem<ListItem<Double>>();
                    tailHaupt = tailHaupt.next;
                    tailHaupt.key = null;
                    continue;


                }
            }

            // Pointer auf den Kopf der Einzelliste
            ListItem<Double> pEinzel = pHaupt.key;


            // durch die gesamte Einzelliste iterieren

            while (pEinzel != null) {

                ListItem<Double> headEinzel = null;
                ListItem<Double> tailEinzel = null;

                // akkumulator erstellen

                double akku = 0;

                // durch die Einzelliste iterieren und eine Kopie erstellen bis zu dem Punkt an dem Limit überschritten wird oder die Liste zuende ist

                while (pEinzel != null && akku <= limit) {


                    //Exception falls einzelnes Element > limit

                    if (pEinzel.key > limit) {

                        // errechnen der diff

                        double diff = pEinzel.key - limit;
                        if (diff < 0)
                            diff = diff*-1;


                        throw new RuntimeException("element at (" + posHaupt + ", " + posEinzel + ") exceeds limit by " + diff);

                    } else {

                        // falls das limit bei addition überschirtten werden würde unterbrechen

                        if (akku + pEinzel.key > limit)
                            break;

                        // wenn der Kopf noch leer ist wird das element in den Kopf kopiert

                        if (headEinzel == null) {

                            headEinzel = tailEinzel = new ListItem<Double>();
                            tailEinzel.key = pEinzel.key;

                            // weiterschalten akku , pEinzel & position

                            akku += pEinzel.key;
                            pEinzel = pEinzel.next;
                            posEinzel++;

                        }

                        // wenn der Kopf nicht mehr leer ist an den tail gehängt

                        else {

                            tailEinzel.next = new ListItem<Double>();
                            tailEinzel = tailEinzel.next;
                            tailEinzel.key = pEinzel.key;

                            // weiterschalten akku  pEinzel und position

                            akku += pEinzel.key;
                            pEinzel = pEinzel.next;
                            posEinzel++;
                        }


                    }


                }


                // einfügen der Einzelliste <= limit in die Kopie

                if (headHaupt == null) {

                    headHaupt = tailHaupt = new ListItem<ListItem<Double>>();
                    tailHaupt.key = headEinzel;

                } else {

                    tailHaupt.next = new ListItem<ListItem<Double>>();
                    tailHaupt = tailHaupt.next;
                    tailHaupt.key = headEinzel;

                }


            }

            // weiterschalten des Laufpointers der Hauptliste

            posHaupt++;


        }

        return headHaupt;
    }


    /**
     * Partitions a copy of {@code listOfLists} so that the sum of each sub-list does not exceed {@code limit}.
     * This implementation must only use recursion and not modify the input list.
     *
     * @param listOfLists a reference to the head of a list with arbitrary size
     * @param limit       the maximum value that may not be exceeded by the sum of any sub-list in the returned list
     * @return a partitioned list of lists
     * @throws RuntimeException if a single value exceeds {@code limit}
     */
    public static @Nullable ListItem<@Nullable ListItem<Double>> partitionListsAsCopyRecursively(
        @Nullable ListItem<@Nullable ListItem<Double>> listOfLists,
        double limit
    ) {

        if (listOfLists == null)
            return null;

        ListItem<ListItem<Double>> pHaupt = listOfLists;
        return partitionListsAsCopyRecursivelyHelperHaupt(pHaupt, null, null, limit, 0);


    }


    // zerlegt die Einzelliste in die einzelnen listen die Limit nicht überschreiten

    /**
     * die Methode geht einmal durch die Hauptliste und lässt jede Einzelliste in eine eigene kleine Hauptliste unterteilen, die dann zusammengehängt werden
     *
     * @param pHaupt    ist der Verweis auf den Kopf der Liste
     * @param headHaupt ist dere Kopf der fertigen Hauptliste der dann zurückgegeben wird
     * @param tailHaupt ist der Schwanz der fertigen Hauptliste an den die neuen Teile der Hauptliste angehängt werden
     * @param limit     ist das limit das die Keys der Einzellisten akkumuliert nicht überschreiten
     * @param posHaupt  ist die Position an der sich die Hauptliste gerade befindet
     * @return den Kopf der fertigen Kopie
     */


    public static ListItem<ListItem<Double>> partitionListsAsCopyRecursivelyHelperHaupt(ListItem<ListItem<Double>> pHaupt, ListItem<ListItem<Double>> headHaupt, ListItem<ListItem<Double>> tailHaupt, double limit, int posHaupt) {


// Rekursionsanker : wenn die Hauptliste einmal durchlaufen ist wird der Kopf zurückgegeben

        if (pHaupt == null)
            return headHaupt;


        // mit dem tail weiterlaufen bis der tail an der letzten Stelle ist
        if (tailHaupt != null && tailHaupt.next != null) {


            return partitionListsAsCopyRecursivelyHelperHaupt(pHaupt, headHaupt, tailHaupt.next, limit, posHaupt);

        }

        // falls eine leere Liste im key ist wird diese eingefügt und eins weitergeschaltet
        if (pHaupt.key == null) {

            if (headHaupt == null) {
                headHaupt = tailHaupt = new ListItem<ListItem<Double>>();
                tailHaupt.key = null;

                // weiterschalten in der Hauptliste

                return partitionListsAsCopyRecursivelyHelperHaupt(pHaupt.next, headHaupt, tailHaupt, limit, posHaupt + 1);

            } else {
                tailHaupt.next = new ListItem<ListItem<Double>>();
                tailHaupt = tailHaupt.next;
                tailHaupt.key = null;

                //weiterschalten in der Hauptliste

                return partitionListsAsCopyRecursivelyHelperHaupt(pHaupt.next, headHaupt, tailHaupt, limit, posHaupt + 1);


            }
        }

        if (headHaupt == null) {

            ListItem<Double> pEinzel = pHaupt.key;

            headHaupt = tailHaupt = partitionListsAsCopyRecursivelyHelperEinzel(pEinzel, null, null, null, null, 0, limit, posHaupt, 0);


            // weiterschalten in der Hauptliste und posHaupt
            return partitionListsAsCopyRecursivelyHelperHaupt(pHaupt.next, headHaupt, tailHaupt, limit, posHaupt + 1);

        } else {

            ListItem<Double> pEinzel = pHaupt.key;

            tailHaupt.next = partitionListsAsCopyRecursivelyHelperEinzel(pEinzel, null, null, null, null, 0, limit, posHaupt, 0);


            // weiterschalten in der Hauptliste und poshaupt
            return partitionListsAsCopyRecursivelyHelperHaupt(pHaupt.next, headHaupt, tailHaupt, limit, posHaupt + 1);
        }

    }


    /**
     * die Methode teilt eine ihr übergebene Einzelliste in einzelne Einzellisten auf die das Limit nicht überschreiten und gibt den Kopf des Teils der Hauptliste zurück
     *
     * @param pEinzel    ist der Verweis auf den Kopf der Einzelliste
     * @param headEinzel der Kopf der momentanen gefilterten Einzelliste
     * @param tailEinzel ist der Schwanz der momentanen gefilterten Einzelliste
     * @param headHaupt  ist der Kopf der Teilhauptliste der zurückgegeben wid
     * @param tailHaupt  ist der Schwanz an den weitere Teile der Hauptliste angehängt werdne
     * @param akku       ist der Akkumulator in dem die Keys der Einzelliste gespeichert werden
     * @param limit      ist das limit das nicht überschritten wird
     * @param posHaupt   ist die Position der Hauptliste
     * @param posEinzel  ist die Position der Einzelliste
     * @return den Kopf der Teilhauptliste
     */


    public static ListItem<ListItem<Double>> partitionListsAsCopyRecursivelyHelperEinzel(ListItem<Double> pEinzel, ListItem<Double> headEinzel, ListItem<Double> tailEinzel, ListItem<ListItem<Double>> headHaupt, ListItem<ListItem<Double>> tailHaupt, double akku, double limit, int posHaupt, int posEinzel) {


        // Rekursionsanker - Liste zuende

        if (pEinzel == null) {
            return headHaupt;
        }


        // wenn ein Element > limit ist wird eine Exception geworfen

        if (pEinzel.key > limit) {

            // errechnen der diff

            double diff = pEinzel.key - limit;
            if (diff < 0)
                diff = diff*-1;

            throw new RuntimeException("element at (" + posHaupt + ", " + posEinzel + ") exceeds limit by " + diff);
        }

        // ansonsten Einfügen des Elements in die Einzelliste
        // wenn der key addiert mit akku <= limit ist wird es als head oder an den tail angehängt und weitergeschaltet

        if (akku + pEinzel.key <= limit) {

            if (headEinzel == null) {
                headEinzel = tailEinzel = new ListItem<Double>();
                headEinzel.key = pEinzel.key;

                //weiterschalten akku

                akku += pEinzel.key;


            } else {

                tailEinzel.next = new ListItem<Double>();
                tailEinzel = tailEinzel.next;
                tailEinzel.key = pEinzel.key;

                //weiterschalten akku

                akku += pEinzel.key;


            }
        }

        // wenn in wir beim letzten Element sind oder der nächste Key das limit überschreiten würde

        if (pEinzel.next == null || akku + pEinzel.next.key > limit) {

            // das Element wird entweder als head angehängt
            if (headHaupt == null) {

                headHaupt = tailHaupt = new ListItem<ListItem<Double>>();
                tailHaupt.key = headEinzel;

                // Weiterschalten - entweder wird das nächste Element geprüft oder die geamte Liste zurückgegeben (wenn pEinzel dann null ist),
                // head und taiLEinzel sind dann wieder null weil eine neue Einzelliste gebildet wird, posEinzel wird auch weitergeschaltet, akku wieder auf 0

                return partitionListsAsCopyRecursivelyHelperEinzel(pEinzel.next, null, null, headHaupt, tailHaupt, 0, limit, posHaupt, posEinzel + 1);


                // oder als tail angehängt
            } else {

                tailHaupt.next = new ListItem<ListItem<Double>>();
                tailHaupt = tailHaupt.next;
                tailHaupt.key = headEinzel;

                // Weiterschalten - entweder wird das nächste Element geprüft oder die geamte Liste zurückgegeben (wenn pEinzel dann null ist),
                // head und taiLEinzel sind dann wieder null weil eine neue Einzelliste gebildet wird, posEinzel wird auch weitergeschaltet, akku wieder auf 0

                return partitionListsAsCopyRecursivelyHelperEinzel(pEinzel.next, null, null, headHaupt, tailHaupt, 0, limit, posHaupt, posEinzel + 1);


            }
        }


        // ansonsten wird das nächste Element angehängt, weil wir nicht am Ende der Liste angekommen sind oder das Limit überschriten werden würde


        return partitionListsAsCopyRecursivelyHelperEinzel(pEinzel.next, headEinzel, tailEinzel, headHaupt, tailHaupt, akku, limit, posHaupt, posEinzel + 1);
    }


    /**
     * Partitions a copy of {@code listOfLists} so that the sum of each sub-list does not exceed {@code limit}.
     * This implementation must not create new sub-lists.
     *
     * @param listOfLists a reference to the head of a list with arbitrary size
     * @param limit       the maximum value that may not be exceeded by the sum of any sub-list in the returned list
     * @return a partitioned list of lists
     * @throws RuntimeException if a single value exceeds {@code limit}
     */
    public static @Nullable ListItem<@Nullable ListItem<Double>> partitionListsInPlaceIteratively(
        @Nullable ListItem<@Nullable ListItem<Double>> listOfLists,
        double limit
    ) {

        ListItem<ListItem<Double>> head = listOfLists;


        if (listOfLists == null)
            return null;

        // Laufpointer auf die Hauptliste
        int posHaupt = 0;


        while (listOfLists != null) {


            ListItem<Double> pEinzel = listOfLists.key;

            int posEinzel = 0;

            // durch die gesamte Einzelliste iterieren
            while (pEinzel != null) {

                double akku = 0;

                // wenn ein Element > limit ist wird eine Exception geworfen

                if (pEinzel.key > limit) {

                    // errechnen der diff

                    double diff = pEinzel.key - limit;
                    if (diff < 0)
                        diff = diff*-1;

                    throw new RuntimeException("element at (" + posHaupt + ", " + posEinzel + ") exceeds limit by " + diff);
                }


                // Referenz die auf das Element zeigt bei dem Limit noch nicht überschritten wurde um dann next auf null zu setzen
                ListItem<Double> cutter;


                // an die Stelle laufen, bevor Limit überschritten wird oder die Einzelliste zuende ist

                while (pEinzel.next != null && akku + pEinzel.key <= limit) {


                    // akku wird hochgeschaltet, pEinzel eins weitergesetzt und posEinzel hochgeschaltet

                    akku += pEinzel.key;

                    // next wird abgeschnitten wenn limit beim nächsten element überschritten wird

                    if (akku + pEinzel.next.key > limit) {

                        cutter = pEinzel;

                        pEinzel = pEinzel.next;

                        cutter.next = null;

                        posEinzel++;
                    }


                    // ansonsten wird weitergelaufen

                    else {

                        pEinzel = pEinzel.next;
                        posEinzel++;
                    }

                }


                // wenn ein Element > limit ist wird eine Exception geworfen

                if (pEinzel.key > limit) {

                    // errechnen der diff

                    double diff = pEinzel.key - limit;
                    if (diff < 0)
                        diff = diff*-1;

                    throw new RuntimeException("element at (" + posHaupt + ", " + posEinzel + ") exceeds limit by " + diff);
                }


                // es war das letzte Element aber das limit wird nicht überschritten

                if (akku + pEinzel.key <= limit)
                    break;

                    // es war das letzte Element und/oder das limit wird überschritten - es wird an ein nächstes Element in der Hauptliste eingefügt
                    // und die schleife wird weiter durchlaufen. die Referenz der Hauptliste zeigt dann auf das neu eingefügt Element, damit die Reihenfolge bestehen bleibt
                    // einfügen des Elements hinter dem jetzigen Element
                else if (akku + pEinzel.key > limit) {

                    ListItem<ListItem<Double>> tmp = new ListItem<>();
                    tmp.key = pEinzel;
                    tmp.next = listOfLists.next;
                    listOfLists.next = tmp;
                    listOfLists = tmp;
                }

            }

            //weiterschalten der Hauptliste

            listOfLists = listOfLists.next;

            posHaupt++;


        }

        return head;

    }


    /**
     * Partitions a copy of {@code listOfLists} so that the sum of each sub-list does not exceed {@code limit}.
     * This implementation must only use recursion and not crete new sub-lists.
     *
     * @param listOfLists a reference to the head of a list with arbitrary size
     * @param limit       the maximum value that may not be exceeded by the sum of any sub-list in the returned list
     * @return a partitioned list of lists
     * @throws RuntimeException if a single value exceeds {@code limit}
     */
    public static @Nullable ListItem<@Nullable ListItem<Double>> partitionListsInPlaceRecursively(
        @Nullable ListItem<@Nullable ListItem<Double>> listOfLists,
        double limit
    ) {

        if (listOfLists == null)
            return null;


        return partitionListsInPlaceRecursivelyHelperHaupt(listOfLists, null, listOfLists.key, limit, 0, 0, 0);


    }


    /**
     * die Methode verändert die Hauptliste so, dass sie nur Einzellisten enthält, deren Keys insgesamt <= limit sind.
     *
     * @param listOfLists ist die Liste die verändert wied
     * @param headHaupt   ist der Kopf der veränderten Liste der zurückgegeben wird
     * @param pEinzel     ist der pointer der auf das aktuelle Element der Einzelliste zeigt
     * @param limit       ist das Limit das nicht überschrittenen wird
     * @param akku        ist der akkumulator, in dem die keys gespeichert werden
     * @param posHaupt    ist die position der Hauptliste an der sich die Methode grade befindet fasll eine Exception geworfen wird
     * @param posEinzel   ist die position der Einzelliste an der sich die Methode grade befindet fasll eine Exception geworfen wird
     * @return den Kopf der veränderten Liste
     */
    public static ListItem<@Nullable ListItem<Double>> partitionListsInPlaceRecursivelyHelperHaupt(ListItem<ListItem<Double>> listOfLists, @Nullable ListItem<ListItem<Double>> headHaupt, @Nullable ListItem<Double> pEinzel, double limit, double akku, int posHaupt, int posEinzel) {

        if (listOfLists == null)
            return headHaupt;

        // wenn der key null ist wird falls es das erste Hauptelement ist der Kopf auf dieses verwiesen und ansonsten
        // der Verweis von tail eins weitergeschaltet und die Methode mit dem nächsten Element aufgerufen

        if (listOfLists.key == null) {

            if (headHaupt == null) {
                headHaupt = listOfLists;
            }

            // weiterschalten in der Hauptliste - unterscheidung ob letztes element oder nicht.
            // der tail wird auch weitergeschaltet, damit er auf dem richtigen element steht
            if (listOfLists.next != null)
                return partitionListsInPlaceRecursivelyHelperHaupt(listOfLists.next, headHaupt, listOfLists.next.key, limit, 0, posHaupt + 1, 0);

            else {
                return partitionListsInPlaceRecursivelyHelperHaupt(null, headHaupt, null, limit, 0, posHaupt + 1, 0);

            }

        }


        // wenn die Einzelliste einmal durch ist zum nächsten Element der Hauptliste , wenn das nicht null ist. ansonsten null, falls das limit nicht überschritten wurde wird dem head listOflists zugewiedes
        if (pEinzel == null) {

            if (headHaupt == null) {
                headHaupt = listOfLists;
            }

            if (listOfLists.next != null)
                return partitionListsInPlaceRecursivelyHelperHaupt(listOfLists.next, headHaupt, listOfLists.next.key, limit, 0, posHaupt + 1, 0);

            else {
                return partitionListsInPlaceRecursivelyHelperHaupt(null, headHaupt, null, limit, 0, posHaupt + 1, 0);

            }
        }


        // falls ein Element größer limit ist wird eine Exception geworfen

        if (pEinzel.key > limit) {

            // errechnen der diff

            double diff = pEinzel.key - limit;
            if (diff < 0)
                diff = diff*-1;

            throw new RuntimeException("element at (" + posHaupt + ", " + posEinzel + ") exceeds limit by " + diff);
        }

        // wenn der Key + akku <= limit sind wird akku weitergeschaltet

        if (akku + pEinzel.key <= limit) {
            akku += pEinzel.key;

        }

        // wenn das nächste Element das Limit überschritten wird wird an der Stelle abgeschnitten und mit dem nächsten Element weitergemacht

        if (pEinzel.next != null && pEinzel.next.key + akku > limit) {

            // falls der Kopf noch leer ist wird der Kopf dem übergebenen ListItem zugewiesen
            if (headHaupt == null) {
                headHaupt = listOfLists;

                // abschneiden des Rests der Einzelliste
                ListItem<Double> cutted = pEinzel.next;
                pEinzel.next = null;

                // Erzeugung eines neuen Listenelements und Zuweisung des abgeschnittenene Teil als key
                ListItem<ListItem<Double>> nextElement = new ListItem<ListItem<Double>>();
                nextElement.key = cutted;

                // das einfügen des Elements hinter tail und weiterschalten des tails auf das eingefügte Element
                nextElement.next = listOfLists.next;
                listOfLists.next = nextElement;
                listOfLists = listOfLists.next;


                // aufruf mit dem neuen pEinzel objekt der auf das erste Element der RestEinzelliste zeigt, weiterschaltung posEinzel
                return partitionListsInPlaceRecursivelyHelperHaupt(listOfLists, headHaupt, cutted, limit, 0, posHaupt, posEinzel + 1);


            } else {


                // abschneiden des Rests der Einzelliste
                ListItem<Double> cutted = pEinzel.next;
                pEinzel.next = null;

                // erzeugen eines neuen Elements und Zuweiung des abgeschnittenen Teils als key
                ListItem<ListItem<Double>> nextElement = new ListItem<ListItem<Double>>();
                nextElement.key = cutted;

                // das einfügen des Elements hinter tail und weiterschalten des tails auf das eingefügte Element
                nextElement.next = listOfLists.next;
                listOfLists.next = nextElement;
                listOfLists = listOfLists.next;


                // aufruf mit dem neuen pEinzel objekt der auf das erste Element der RestEinzelliste zeigt, weiterschaltung posEinzel
                return partitionListsInPlaceRecursivelyHelperHaupt(listOfLists, headHaupt, cutted, limit, 0, posHaupt, posEinzel + 1);

            }

        }


        // ansonsten wird die Methode mit dem nächsten Element aufgerufen, da dieses akkumuliert mit dem rest <= limit ist

        return partitionListsInPlaceRecursivelyHelperHaupt(listOfLists, headHaupt, pEinzel.next, limit, akku, posHaupt, posEinzel + 1);
    }


    /**
     * Writes out {@code listOfLists} (well, the sub-lists) to {@code writer}.
     *
     * @param writer      the writer to write to
     * @param listOfLists the list of lists to write out
     */
    public static void write(Writer writer, @Nullable ListItem<@Nullable ListItem<Double>> listOfLists) {

        if (listOfLists == null)
            return;


        for (ListItem<ListItem<Double>> pHaupt = listOfLists; pHaupt != null; pHaupt = pHaupt.next) {
            String s = "";

            // erstellen des Pointers auf die Einzelliste
            ListItem<Double> pEinzel = pHaupt.key;

            // falls die Einzelliste leer ist  wird nur "#" geschrieben
            if (pEinzel == null) {
                try {
                    writer.write("#\n");
                } catch (IOException ignored) {

                }
                continue;
            }

            // erstellen des Strings der Einzelliste
            while (pEinzel != null) {


                // wenn wir beim allerletzen Element ( also letztes Element der Hauptliste und letztes Element der
                //Einzelliste) angekommen sind kein weiterer Zeilenumbruch.
                if (pHaupt.next == null && pEinzel.next == null) {

                    s += pEinzel.key.toString();
                    pEinzel = pEinzel.next;
                    continue;
                }

                // wenn wir beim letzten Element angekommen sind
                if (pEinzel.next == null) {
                    s += pEinzel.key.toString() + "\n";

                    pEinzel = pEinzel.next;
                    continue;
                }
                // ansonsten wird der String mit dem key + # erweitert

                s += pEinzel.key.toString() + "#";
                pEinzel = pEinzel.next;
            }
            try {
                writer.write(s);
            } catch (IOException ignored) {

            }
        }


    }


    /**
     * Reads a list of lists from {@code reader} and returns it.
     *
     * @param reader the reader to read from
     * @return a list of lists of double
     */
    public static @Nullable ListItem<@Nullable ListItem<Double>> read(BufferedReader reader) {

        // erstellen head & tail
        ListItem<ListItem<Double>> headHaupt = null;
        ListItem<ListItem<Double>> tailHaupt = null;

        while (true) {

            // erstellen des Strings der Einzelliste
            String s = "";


            // lesen der Zeile und speichern in s
            try {
                s = reader.readLine();

                // Abbruchbedingung
                if ( s == null )
                    break;
            } catch (IOException ignored) {
            }

            // falls ein Hauptelement leer ist wird ein Listenelement mit key == null eingefügt und continued

            if (s.equals("#")) {

                ListItem<ListItem<Double>> element = new ListItem<>();
                element.key = null;

                if (headHaupt == null) {
                    headHaupt = tailHaupt = element;


                } else {
                    tailHaupt.next = element;
                    tailHaupt = tailHaupt.next;
                }

                continue;

            }
            // kein null key :
            // erstellen der Werte als StringArray
            String[] arrayEinzelString = s.split("#");

            // verweise auf head und tail der Einzelliste
            ListItem<Double> headEinzel = null;
            ListItem<Double> tailEinzel = null;

            // erstellen der Einzelliste in einer schleife

            for (String value : arrayEinzelString) {

                ListItem<Double> einzel = new ListItem<>();
                einzel.key = Double.valueOf(value);

                if (headEinzel == null) {
                    headEinzel = tailEinzel = einzel;


                } else {
                    tailEinzel.next = einzel;
                    tailEinzel = tailEinzel.next;
                }

            }


            ListItem<ListItem<Double>> element = new ListItem<ListItem<Double>>();
            element.key = headEinzel;

            if (headHaupt == null) {
                headHaupt = tailHaupt = element;


            } else {
                tailHaupt.next = element;
                tailHaupt = tailHaupt.next;
            }
        }

        // der Kopf wird zurückgegeben

        return headHaupt;

    }

}



