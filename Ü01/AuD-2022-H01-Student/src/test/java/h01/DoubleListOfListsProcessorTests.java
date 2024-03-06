package h01;


import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleListOfListsProcessorTests {


    // erzeugt eine Liste mit 100 Hauptelementen , 50 davon haben zwischen 101 und 200 Elemente und key zwischn 0 und 5
    // die letzten 50 haben zwischen 10 und 100 Elemente und key zwischen 101 und 200

    public static ListItem<ListItem<Double>> createList() {


        ListItem<ListItem<Double>> headHaupt2 = new ListItem<>();
        headHaupt2.key = new ListItem<Double>();
        headHaupt2.key.key = 5.0;


        // 50 elemente mit Einzellisten 101-200 Elemente mit keys zwischen 0 und 5

        for (int i = 1; i < 50; i++) {

            ListItem<Double> headEinzel = new ListItem<>();
            headEinzel.key = ThreadLocalRandom.current().nextDouble(0, 6);

            Double[] keys = new Double[ThreadLocalRandom.current().nextInt(100, 200)];

            for (int j = 0; j < keys.length; j++) {
                keys[j] = ThreadLocalRandom.current().nextDouble(0, 6);
            }

            headEinzel.addKeyArray(keys);

            headHaupt2.add(headEinzel);

        }

// die Restlichen 50 haben zwischen 10 und 100 Elemente mit keys zwischen 101 und 200

        for (int i = 0; i < 50; i++) {

            ListItem<Double> headEinzel = new ListItem<>();
            headEinzel.key = ThreadLocalRandom.current().nextDouble(110, 201);

            Double[] keys = new Double[ThreadLocalRandom.current().nextInt(9, 100)];

            for (int j = 0; j < keys.length; j++) {
                keys[j] = ThreadLocalRandom.current().nextDouble(110, 201);
            }

            headEinzel.addKeyArray(keys);

            headHaupt2.add(headEinzel);

        }

        return headHaupt2;
    }




    @Test
    public void partitionListsInPlaceTest() {

        ListItem<ListItem<Double>> test1 = createList();

        ListItem<Double> overlimit = new ListItem<Double>() ; overlimit.key = 500.00;
        test1.add(overlimit);

        ListItem<ListItem<Double>> test2 = new ListItem<>();
        test2.key = new ListItem<Double>(); test2.key.key = 0.0;

        ListItem<Double> e1 = new ListItem<Double> (); e1.key = 5.0;
        for ( int i = 0; i <10; i++){
            e1.add(5.0);

        }
        test2.add(e1);


        ListItem<Double> e2 = new ListItem<Double> (); e2.key = 5.0;
        for ( int i = 0; i <10; i++){
            e1.add(5.0);

        }
        test2.add(e2);

        ListItem<Double> e10 = new ListItem<Double> (); e10.key = 5.0;
        for ( int i = 0; i <10; i++){
            e10.add(5.0);

        }

        test2.key = e10;

        ListItem<Double> ol = new ListItem<>();
        ol.key = 10.0;
        test2.add(ol);




        try {DoubleListOfListsProcessor.partitionListsInPlaceRecursively(test2, 9); }

        catch ( Exception e){

            assertEquals("element at (3, 0) exceeds limit by 1.0", e.getMessage());
        }






        ListItem<ListItem<Double>> resultIteratively1 = DoubleListOfListsProcessor.partitionListsAsCopyIteratively(test1, 1100);


        // sind alle  Elemente einer Einzellisten akkumuliert <= limit ?
        for (ListItem<ListItem<Double>> pIt1 = resultIteratively1; pIt1 != null; pIt1 = pIt1.next) {

            double akku = 0;

            ListItem<Double> pEinzelIt1 = pIt1.key;

            while (pEinzelIt1 != null) {
                akku += pEinzelIt1.key;
                pEinzelIt1 = pEinzelIt1.next;
            }

            assertTrue(akku <= 1100);

        }
        /*

        // sind die Elemente in der gleichen Reihenfolge ?

        ListItem<ListItem<Double>> pHauptCopy1 = testIteratively1;
        ListItem<Double> pEinzelCopy1 = testIteratively1.key;

        // Durchlaufen Originalliste
        for (ListItem<ListItem<Double>> pHaupt1 = test1; pHaupt1 != null; pHaupt1 = pHaupt1.next) {


            // durchlaufen der Einzelliste
            for (ListItem<Double> pEinzel1 = pHaupt1.key; pEinzel1 != null; pEinzel1 = pEinzel1.next) {


                // falls in der Kopie die liste zuende ist wird eins weitergeschaltet

                if (pEinzelCopy1 == null) {
                    if (pHauptCopy1.next == null)
                        break;

                    pHauptCopy1 = pHauptCopy1.next;
                    pEinzelCopy1 = pHauptCopy1.key;
                }

                assertEquals(pEinzel1.key, pEinzelCopy1.key);

                pEinzelCopy1 = pEinzelCopy1.next;

            }

         */

        }




    }



        /*

        // as copy iterativ

        ListItem<ListItem<Double>> testIteratively1 = DoubleListOfListsProcessor.partitionListsAsCopyIteratively(headHaupt1, 1100);


        for (ListItem<ListItem<Double>> pIt1 = testIteratively1; pIt1 != null; pIt1 = pIt1.next) {

            double akku = 0;

            ListItem<Double> pEinzelIt1 = pIt1.key;

            while (pEinzelIt1 != null) {
                akku += pEinzelIt1.key;
                pEinzelIt1 = pEinzelIt1.next;
            }

            assertTrue(akku <= 1100);

        }


        ListItem<ListItem<Double>> pHauptCopy1 = testIteratively1;
        ListItem<Double> pEinzelCopy1 = testIteratively1.key;

        // Durchlaufen Originalliste
        for (ListItem<ListItem<Double>> pHaupt1 = headHaupt1; pHaupt1 != null; pHaupt1 = pHaupt1.next) {


            // durchlaufen der Einzelliste
            for (ListItem<Double> pEinzel1 = pHaupt1.key; pEinzel1 != null; pEinzel1 = pEinzel1.next) {


                // falls in der Kopie die liste zuende ist wird eins weitergeschaltet

                if (pEinzelCopy1 == null) {
                    if (pHauptCopy1.next == null)
                        break;

                    pHauptCopy1 = pHauptCopy1.next;
                    pEinzelCopy1 = pHauptCopy1.key;
                }

                assertEquals(pEinzel1.key, pEinzelCopy1.key);

                pEinzelCopy1 = pEinzelCopy1.next;

            }

        }


        ListItem<ListItem<Double>> testIteratively2 = DoubleListOfListsProcessor.partitionListsAsCopyIteratively(headHaupt2, 1100);


        for (ListItem<ListItem<Double>> pIt2 = testIteratively2; pIt2 != null; pIt2 = pIt2.next) {

            double akku = 0;

            ListItem<Double> pEinzelIt2 = pIt2.key;

            while (pEinzelIt2 != null) {
                akku += pEinzelIt2.key;
                pEinzelIt2 = pEinzelIt2.next;
            }

            assertTrue(akku <= 1100);

        }


        ListItem<ListItem<Double>> pHauptCopy2 = testIteratively2;
        ListItem<Double> pEinzelCopy2 = testIteratively2.key;

        // Durchlaufen Originalliste
        for (ListItem<ListItem<Double>> pHaupt2 = headHaupt2; pHaupt2 != null; pHaupt2 = pHaupt2.next) {


            // durchlaufen der Einzelliste
            for (ListItem<Double> pEinzel2 = pHaupt2.key; pEinzel2 != null; pEinzel2 = pEinzel2.next) {


                // falls in der Kopie die liste zuende ist wird eins weitergeschaltet

                if (pEinzelCopy2 == null) {
                    if (pHauptCopy2.next == null)
                        break;

                    pHauptCopy2 = pHauptCopy2.next;
                    pEinzelCopy2 = pHauptCopy2.key;
                }

                assertEquals(pEinzel2.key, pEinzelCopy2.key);

                pEinzelCopy2 = pEinzelCopy2.next;

            }

        }

        // testen der Längen - die Copie muss länger sein als das original

        ListItem<ListItem<Double>> p = headHaupt1;
        ListItem<ListItem<Double>> pC = testIteratively2;

        int count = 0;
        int countC = 0;

        while (p != null) {
            count++;
            p = p.next;
        }

        while (pC != null) {

            countC++;
            pC = pC.next;
        }


        assertTrue(countC > count);


        // Exception iterativ copy

        try {
            DoubleListOfListsProcessor.partitionListsAsCopyIteratively(headHaupt2, 4);

        } catch (RuntimeException e) {

            assertEquals("element at (0, 0) exceeds limit by 1.0", e.getMessage());
        }


        // rekursiv as copy


        ListItem<ListItem<Double>> testRecursively = DoubleListOfListsProcessor.partitionListsAsCopyRecursively(headHaupt3, 1100);


        for (ListItem<ListItem<Double>> pRec1 = testRecursively; pRec1 != null; pRec1 = pRec1.next) {

            double akku = 0;

            ListItem<Double> pEinzelRec1 = pRec1.key;

            while (pEinzelRec1 != null) {
                akku += pEinzelRec1.key;
                pEinzelRec1 = pEinzelRec1.next;
            }

            assertTrue(akku <= 1100);

        }


        ListItem<ListItem<Double>> pHauptCopyRec1 = testRecursively;
        ListItem<Double> pEinzelCopyRec1 = testRecursively.key;

        // Durchlaufen Originalliste
        for (ListItem<ListItem<Double>> pHauptRec1 = headHaupt3; pHauptRec1 != null; pHauptRec1 = pHauptRec1.next) {


            // durchlaufen der Einzelliste
            for (ListItem<Double> pEinzelRec1 = pHauptRec1.key; pEinzelRec1 != null; pEinzelRec1 = pEinzelRec1.next) {


                // falls in der Kopie die liste zuende ist wird eins weitergeschaltet

                if (pEinzelCopyRec1 == null) {
                    if (pHauptCopyRec1.next == null)
                        break;

                    pHauptCopyRec1 = pHauptCopyRec1.next;
                    pEinzelCopyRec1 = pHauptCopyRec1.key;
                }

                assertEquals(pEinzelRec1.key, pEinzelCopyRec1.key);

                pEinzelCopyRec1 = pEinzelCopyRec1.next;

            }

        }

        // testen der Längen - die Copie muss länger sein als das original

        ListItem<ListItem<Double>> pRec = headHaupt1;
        ListItem<ListItem<Double>> pRecC = testRecursively;

        int countRec1 = 0;
        int countRecC1 = 0;

        while (pRec != null) {
            countRec1++;
            pRec = pRec.next;
        }

        while (pRecC != null) {

            countRecC1++;
            pRecC = pRecC.next;
        }


        assertTrue(countRecC1 > countRec1);


        // Exception iterativ copy

        try {
            DoubleListOfListsProcessor.partitionListsAsCopyRecursively(headHaupt2, 501);

        } catch (RuntimeException e) {

            System.out.println(e.getMessage());

            assertEquals("element at ("+ countRecC1 +", 1) exceeds limit by 1.0", e.getMessage());
        }




    }

         */



























