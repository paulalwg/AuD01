package h06;

public class LinearProbing<T> implements BinaryFct2Int<T> {
    /**
     * Hash function used in object internal operations. Set in the constructor.
     */
    private final Fct2Int<T> hashFct;

	/**
	 * Creates an object of the LinearProbing class.
	 * The object uses the hash function specified in the "hashFct" parameter.
	 * Therefore, the hash function is stored in the object attribute of the same name for further use.
     *
	 * @param hashFct The hash function to be used in upcoming tasks.
	 */
    public LinearProbing(Fct2Int<T> hashFct) {
        this.hashFct = hashFct;
    }

	/**
	 * Calculates the hash value of parameter "key" by using the hash function passed
	 * when the object was created and adds the offset specified in the parameter "offset".
     *
	 * @param key    The key from which to calculate the hash value.
	 * @param offset The offset to add.
	 * @return See exercise sheet.
	 */
	@Override
	public int apply(T key, int offset) {

        int a = hashFct.apply(key);
        int tableSize = getTableSize();

        if ( a > Integer.MAX_VALUE - offset){

            a %= tableSize;
            offset %= tableSize;
        }

        int sum = a + offset;

        return sum % tableSize;


	}

    /**
     * Returns the current table size.
     *
     * @return Current table size.
     */
    @Override
    public int getTableSize() {
        return hashFct.getTableSize();
    }

    /**
     * Sets the current table size.
     *
     * @param tableSize New table size.
     */
    @Override
    public void setTableSize(int tableSize) {
        hashFct.setTableSize(tableSize);
    }
}
