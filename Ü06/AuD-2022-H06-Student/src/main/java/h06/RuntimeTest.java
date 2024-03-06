package h06;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.stream.LongStream;

public class RuntimeTest {
    private final static int TEST_SET_SIZE = 1_000;

    /**
     * Generates two test data sets with 1,000 dates each.
     * The first test data set is in component 0 of the returned array and is initialized with true.
     * The second test data set is in component 1 of the returned array and is initialized with false.
     * The dates are between 1970 and 2022.
     *
     * @return Two test data sets of 1,000 dates each.
     */
    public static MyDate[][] generateTestdata() {

        MyDate[][] result = new MyDate[2][1000];

        Random random = new Random();


        for (int i = 0; i < 1000; i++) {

            Calendar date = Calendar.getInstance();

            // erstellen des bounds
            Calendar bound = Calendar.getInstance();
            bound.set(2022, Calendar.DECEMBER, 31, 23, 59, 59);

            date.setTimeInMillis(random.nextLong(0, bound.getTimeInMillis() + 1));


            result[0][i] = new MyDate(date, true);
            result[1][i] = new MyDate(date, false);

        }
        return result;
    }

    /**
     * Generates a test set.
     *
     * @param i        See exercise sheet.
     * @param j        See exercise sheet.
     * @param k        See exercise sheet.
     * @param l        See exercise sheet.
     * @param testData The testdata used.
     * @return A test set.
     */
    public static TestSet<MyDate> createTestSet(int i, int j, int k, int l, MyDate[][] testData) {

        int index;
        MyMap<MyDate, MyDate> hashTable;
        int initialSize;


        if (i == 1)
            index = 0;

        else
            index = 1;


        // MyIndexHoppingHashMap
        if (j == 1) {

            // initialsize
            if (l == 1) {
                initialSize = 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2;
            } else {
                initialSize = 2 * 2 * 2 * 2 * 2 * 2;
            }


            // function

            BinaryFct2Int<MyDate> binaryFct2Int;

            if (k == 1) {

                Hash2IndexFct<MyDate> fct = new Hash2IndexFct<>(initialSize, 0);
                binaryFct2Int = new LinearProbing<>(fct);
            } else {

                Hash2IndexFct<MyDate> fct1 = new Hash2IndexFct<>(initialSize, 0);
                Hash2IndexFct<MyDate> fct2 = new Hash2IndexFct<>(initialSize, 42);
                binaryFct2Int = new DoubleHashing<>(fct1, fct2);

            }


            hashTable = new MyIndexHoppingHashMap<>(initialSize, 0.75, 0.75, binaryFct2Int);


        }
        // MyListsHashMap
        else {

            int testSize = 0;
            for (int m = 0; m < testData.length; m++) {
                testSize += testData[i].length;
            }

            if (l == 1) {

                initialSize = 3 * testSize;
            } else {
                initialSize = testSize / 10;
            }

            Hash2IndexFct<MyDate> fct = new Hash2IndexFct<>(initialSize, 0);
            hashTable = new MyListsHashMap<>(fct);


        }


        return new TestSet<>(hashTable, testData[index]);


    }

    /**
     * Tests the given test set.
     *
     * @param testSet The test set to test.
     */
    public static void test(TestSet<MyDate> testSet) {

        MyDate[] data = testSet.getTestData();

        MyMap<MyDate, MyDate> hashmap = testSet.getHashTable();

        // einfügen der ersten 750 Elemente
        for (int i = 0; i < 750; i++) {

            hashmap.put(data[i], data[i]);
        }

        // Überprüfen ob alle Elemente vorkommen
        for (MyDate date : data) {

            hashmap.containsKey(date);
        }

        // ermitteln des Values
        for (MyDate date : data) {

            hashmap.getValue(date);
        }

        //löschen

        for (MyDate date : data) {

            hashmap.remove(date);
        }


    }
}
