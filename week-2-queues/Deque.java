import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size = 0;

    // private doubly linked list for the implementation
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item can't be null");
        Node old = first;
        first = new Node();
        first.item = item;
        first.next = old;
        if (isEmpty()) last = first;
        else first.next.prev = first;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item can't be null");
        Node old = last;
        last = new Node();
        last.item = item;
        last.prev = old;
        if (isEmpty()) first = last;
        else last.prev.next = last;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("The deque is empty");
        Item item = first.item;
        if (size() == 1) {
            last = null;
            first = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("The deque is empty");
        Item item = last.item;
        if (size() == 1) {
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next = null;
        }
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }

    // The iterator is written with the help of lecture slides
    private class DequeueIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No more elements to return");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1); // 1
        StdOut.print(deque);
        deque.addLast(3); // 1 3
        StdOut.print(deque.removeFirst()); // 3
        deque.addFirst(2); // 2 1
        StdOut.print(deque.removeFirst()); // 2
        StdOut.print(deque); // 1
        deque.addFirst(4); // 4 1
        StdOut.print(deque.removeLast()); // 1
    }
}
