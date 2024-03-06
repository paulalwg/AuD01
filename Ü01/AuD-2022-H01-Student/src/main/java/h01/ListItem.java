package h01;

import org.jetbrains.annotations.Nullable;

/**
 * This class represents a member of a linked list, which stores a value in {@link #key} and a reference to the next member
 * (or {@code null} if this is the last member in this list) in {@link #next}.
 *
 * @param <T> type of value stored in {@link #key}
 */
public class ListItem<T> {

    public ListItem<T> head;
    public ListItem<T> tail;

    /**
     * The value stored in this member of a list.
     *
     * <p>
     * Normally has the same nullability as {@link T} unless uninitialized.
     * </p>
     */
    public T key;

    /**
     * A reference to the next member in this list (or {@code null} if this member is the last element in this list).
     */
    public @Nullable ListItem<T> next;

    /**
     * Empty constructor for creating a new {@link ListItem} object.
     * Does nothing, but must not be removed!
     */
    public ListItem() {

    }


    @Override
    public String toString() {


        StringBuilder result = new StringBuilder();

        ListItem<T> lst = this;

        if ( lst.key == null){
            result.append("null");
            lst = lst.next;
        }

        while (lst != null) {

            if (lst.key == null) {
                result.append("null ");
                lst = lst.next;
                continue;
            }

            if (lst.next == null) {
                result.append(lst.key.toString());
                break;
            }


            result.append(lst.key.toString()).append(" ");
            lst = lst.next;
        }

        return "(" + result + ")";
    }

    public void add(T key) {

        if (head == null) {
            head = tail = this;
            tail.next = new ListItem<T>();
            tail = tail.next;
            tail.key = key;
        } else {
            tail.next = new ListItem<T>();
            tail = tail.next;
            tail.key = key;
        }
    }

    public void addKeyArray(T[] keys) {

        for (T t : keys) {


            if (head == null) {
                head = tail = this;
                tail.next = new ListItem<T>();
                tail = tail.next;
                tail.key = t;
            } else {
                tail.next = new ListItem<T>();
                tail = tail.next;
                tail.key = t;
            }
        }
    }


}

