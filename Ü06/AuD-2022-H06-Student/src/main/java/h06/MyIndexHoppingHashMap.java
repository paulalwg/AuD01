package h06;

import org.jetbrains.annotations.Nullable;

public class MyIndexHoppingHashMap<K, V> implements MyMap<K, V> {
    private final double resizeThreshold;
    private final double resizeFactor;
    private K[] theKeys;
    private V[] theValues;
    private boolean[] occupiedSinceLastRehash;
    private int occupiedCount = 0;
    private final BinaryFct2Int<K> hashFunction;

    /**
     * Create a new index hopping hash map.
     *
     * @param initialSize     The initial size of the hashmap.
     * @param resizeFactor    The resize factor which determines the new size after the resize threshold is reached.
     * @param resizeThreshold The threshold after which the hash table is resized.
     * @param hashFunction    The used hash function.
     */
    @SuppressWarnings("unchecked")
    public MyIndexHoppingHashMap(int initialSize, double resizeFactor, double resizeThreshold, BinaryFct2Int<K> hashFunction) {
        this.theKeys = (K[]) new Object[initialSize];
        this.theValues = (V[]) new Object[initialSize];
        this.occupiedSinceLastRehash = new boolean[initialSize];

        this.resizeFactor = resizeFactor;
        this.resizeThreshold = resizeThreshold;
        this.hashFunction = hashFunction;
        this.hashFunction.setTableSize(initialSize);
    }

    @Override
    public boolean containsKey(K key) {

        return searchKeyIndex(key) > -1;
    }

    @Override
    public @Nullable V getValue(K key) {

        int index = searchKeyIndex(key);

        if (index == -1) {
            return null;
        }

        else {
            return theValues[index];
        }

    }

    @Override
    public @Nullable V put(K key, V value) {

        if (occupiedCount + 1 > occupiedSinceLastRehash.length * resizeThreshold) {
            rehash();
        }

        int index = this.searchKeyIndex(key);

        // der key ist nicht vorhanden
        if (index == - 1 ) {

            index = hashFunction.apply(key, 0);
            int hashFunctionI = 1;

            int count = 0;

            // solange noch nicht alle indizes durchlaufen sind
            while (count < occupiedSinceLastRehash.length) {

                // wenn der key noch nicht vorhanden war
                if (theKeys[index] == null) {
                    theKeys[index] = key;
                    theValues[index] = value;
                    occupiedSinceLastRehash[index] = true;
                    occupiedCount++;
                    return null;
                }

                index = hashFunction.apply(key,hashFunctionI);
                hashFunctionI++;
                count++;
            }

            return null;
        }

        // der key ist vorhanden
            V oldValue = theValues[index];
            theValues[index] = value;
            occupiedSinceLastRehash[index] = true;
            return oldValue;

    }

    /**
     * die Methode sucht den Index an dem der Key im Array theKeys vorhanden ist oder wenn am berechneten index null steht
     * und somit der Key nicht vorhanden ist
     *
     * @param key ist der key nach dem gesucht wird
     * @return den index oder -1 wenn der key nicht vorhanden ist
     */
    public int searchKeyIndex(K key) {

        int index = hashFunction.apply(key, 0);
        int hashFunctionI = 1;

        int count = 0;
        while (count < occupiedSinceLastRehash.length) {


            // wenn null an einem berechneten index steht ist der key nicht vorhanden
            if (theKeys[index] == null) {

                // wenn hier true ist dann wird weitergesucht
                if (occupiedSinceLastRehash[index]) {
                    index = hashFunction.apply(key, hashFunctionI);
                    hashFunctionI++;
                    count++;
                    continue;
                }

                return -1;
            }

            // wenn der key gefunden wird wird der index zurückgegeben
            if (theKeys[index].equals(key))
                return index;


            // wenn nicht wird am nächsten Index weitergesucht
            index = hashFunction.apply(key, hashFunctionI);
            hashFunctionI++;
            count++;


        }

        return -1;
    }

    @Override
    public @Nullable V remove(K key) {

        int index = searchKeyIndex(key);

            if (index == -1)
                return null;


        else {

            V oldValue = theValues[index];
            theKeys[index] = null;
            theValues[index] = null;
            return oldValue;
        }
    }

    /***
     * Creates a new bigger hashtable (current size multiplied by resizeFactor)
     * and inserts all elements of the old hashtable into the new one.
     */
    @SuppressWarnings("unchecked")
    private void rehash() {


        Object[] theKeysObject = new Object[ (int) resizeFactor * this.theKeys.length];
        K [] theKeys = (K[]) theKeysObject;

        Object[] theValuesObject = new Object[ (int) resizeFactor * this.theValues.length];
        V [] theValues = (V[]) theValuesObject;

        boolean[] occupiedSinceLastRehash = new boolean[(int) resizeFactor * this.occupiedSinceLastRehash.length];

        K[] oldTheKeys = this.theKeys;
        V[] oldTheValues = this.theValues;
        boolean[] oldOccupiedSinceLastRehash = this.occupiedSinceLastRehash;

        this.theKeys = theKeys;
        this.theValues = theValues;
        this.occupiedSinceLastRehash = occupiedSinceLastRehash;
        this.occupiedCount = 0;
        this.hashFunction.setTableSize(theKeys.length);

        // einsetzen der alten Werte in die neuen größeren Arrays
        for ( int i = 0; i < oldTheKeys.length; i++){

            if (oldTheKeys[i] != null && oldTheValues[i] != null){
                put (oldTheKeys[i], oldTheValues[i]);
                this.occupiedSinceLastRehash[i] = true;

            }
        }


    }
}
