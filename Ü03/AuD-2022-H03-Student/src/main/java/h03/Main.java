package h03;


import java.util.LinkedList;
import java.util.List;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {



/*
// Test partialMatchLengthUpdateValues

        int[] searchString = {4,4,4,4,5,4,4,4};

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
            if (searchString[index1] == searchString[index2ToCheck]) {
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

       System.out.println( " K : " + k ) ;



 */
        /*

        int sizeOfAlphabet = 10;
        String searchString = "2";

        // erzeugen der Matrix : y-Ache: sizeOfAlphabet und x-Achse seachString.length+1
        int[][] matrix = new int[sizeOfAlphabet][searchString.length()+1];


System.out.println("Länge : " + matrix[0].length);

         */

/*
// Test H5
        Class<TestEnum1> TestEnum1 = h03.TestEnum1.class;

        EnumIndex<TestEnum1> enum1EnumIndex = new EnumIndex<>(TestEnum1);


        TestEnum1[] searchString = {h03.TestEnum1.ONE,h03.TestEnum1.ONE,h03.TestEnum1.ONE,h03.TestEnum1.THREE,h03.TestEnum1.THREE,
            h03.TestEnum1.THREE,h03.TestEnum1.THREE,h03.TestEnum1.THREE,h03.TestEnum1.THREE,h03.TestEnum1.THREE};

        PartialMatchLengthUpdateValuesAsMatrix test = new PartialMatchLengthUpdateValuesAsMatrix(enum1EnumIndex,searchString);

        int i1 = test.getPartialMatchLengthUpdate(0, h03.TestEnum1.TEN);
        int i2 = test.getPartialMatchLengthUpdate(1, h03.TestEnum1.ONE);
        int i3 = test.getPartialMatchLengthUpdate(5, h03.TestEnum1.TEN);
        int i4 = test.getPartialMatchLengthUpdate(6, h03.TestEnum1.ZERO);
        int i5 = test.getPartialMatchLengthUpdate(4, h03.TestEnum1.ONE);

         */

        /*

// Test H6

        TestEnum1[] searchString = {h03.TestEnum1.THREE,h03.TestEnum1.TWO,h03.TestEnum1.ZERO, TestEnum1.FOUR,};

        Class<TestEnum1> TestEnum1 = h03.TestEnum1.class;
        EnumIndex<TestEnum1> enum1EnumIndex = new EnumIndex<>(TestEnum1);

        PartialMatchLengthUpdateValuesAsAutomaton automat = new PartialMatchLengthUpdateValuesAsAutomaton(enum1EnumIndex, searchString);

        PartialMatchLengthUpdateValuesAsMatrix matrix = new PartialMatchLengthUpdateValuesAsMatrix<>(enum1EnumIndex,searchString);



         */




        System.out.println("Hello World!");



    }
}
