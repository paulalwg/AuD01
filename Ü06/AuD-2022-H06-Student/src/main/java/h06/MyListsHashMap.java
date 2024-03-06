package h06;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;

public class MyListsHashMap<K, V> implements MyMap<K, V> {
    private final LinkedList<KeyValuePair<K, V>>[] table;
    private final Fct2Int<K> hashFunction;

    /**
     * Creates a new list hash map.
     *
     * @param hashFunction The used hash function.
     */
    @SuppressWarnings("unchecked")
    public MyListsHashMap(Fct2Int<K> hashFunction) {
        this.table = new LinkedList[hashFunction.getTableSize()];
        for (int i = 0; i < this.table.length; i++) {
            this.table[i] = new LinkedList<>();
        }
        this.hashFunction = hashFunction;
    }

    @Override
    public boolean containsKey(K key) {

        LinkedList<KeyValuePair<K, V>> indexList = table[hashFunction.apply(key)];

        for (int i = 0; i < indexList.size(); i++) {

            if (indexList.get(i).getKey().equals(key))
                return true;
        }

        return false;
    }

    @Override
    public @Nullable V getValue(K key) {


        LinkedList<KeyValuePair<K, V>> indexList = table[hashFunction.apply(key)];

        for (int i = 0; i < indexList.size(); i++) {

            if (indexList.get(i).getKey().equals(key))
                return indexList.get(i).getValue();
        }


        return null;

    }

    @Override
    public @Nullable V put(K key, V value) {

        LinkedList<KeyValuePair<K, V>> indexList = table[hashFunction.apply(key)];

        // wenn der key vorhanden ist wird der Value an dem Key ersetzt
        for (int i = 0; i < indexList.size(); i++) {

            // der key ist schon vorhanden: der Value wird ersetzt und der alte zurückgegeben
            if (indexList.get(i).getKey().equals(key)) {
                V oldValue = indexList.get(i).getValue();
                indexList.get(i).setValue(value);
                return oldValue;
            }
        }

         // wenn der key noch nicht vorhanden ist wird ein neues paar eingefügt
        table[hashFunction.apply(key)].add(0, new KeyValuePair<>(key,value));
        return null;

    }

    @Override
    public @Nullable V remove(K key) {

        LinkedList<KeyValuePair<K, V>> indexList = table[hashFunction.apply(key)];

        // wenn der key vorhanden ist wird der Value an dem Key ersetzt
        for (int i = 0; i < indexList.size(); i++) {

            // der key ist schon vorhanden: der Value wird ersetzt und der alte zurückgegeben
            if (indexList.get(i).getKey().equals(key)) {
                V oldValue = indexList.get(i).getValue();
                indexList.remove(i);
                return oldValue;
            }
        }

        // ansonsten wird null zurückgegeben
        return null;

        }
}
