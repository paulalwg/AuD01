package h06;

public class Hash2IndexFct<T> implements Fct2Int<T> {
    /**
     * Table size used in calculation.
     */
    private int tableSize;

    /**
     * Offset used in calculation.
     */
    private final int offset;

    /**
     * Creates a new hash function h(x) = (x + offset) mod tableSize.
     *
     * @param initTableSize Initial table size used in calculation.
     * @param offset        Offset used in calculation.
     */
    public Hash2IndexFct(int initTableSize, int offset) {
        this.tableSize = initTableSize;
        this.offset = offset;
    }

    /**
     * Calculates the hash value of parameter "key".
     *
     * @param key The key from which to calculate the hash value.
     * @return See exercise sheet.
     */
    @Override
    public int apply(T key) {


        int hashCode = key.hashCode();

        if (hashCode < 0)
            hashCode *= -1;

        int offsetValue = offset;

        int divisor = getTableSize();


        if ( hashCode > (Integer.MAX_VALUE - offsetValue)){

            hashCode %= divisor;
            offsetValue %= divisor;

        }

      long dividend = hashCode + offsetValue;


        return Math.floorMod(dividend, divisor);


    }

    /**
     * Returns the current table size.
     *
     * @return Current table size.
     */
    @Override
    public int getTableSize() {
        return tableSize;
    }

    /**
     * Sets the current table size.
     *
     * @param tableSize New table size.
     */
    @Override
    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }
}
