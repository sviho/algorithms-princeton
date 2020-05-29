import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item can't be null");
        if (size == arr.length) resize(2 * size);
        arr[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("The queue is empty");
        int randomIndex = StdRandom.uniform(size);
        Item item = arr[randomIndex];
        arr[randomIndex] = arr[size - 1];
        arr[size - 1] = null;
        size--;
        if (size > 0 && size == arr.length / 4) resize(arr.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("The queue is empty");
        int randomIndex = StdRandom.uniform(size);
        return arr[randomIndex];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        if (size >= 0) System.arraycopy(arr, 0, copy, 0, size);
        arr = copy;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // The iterator is written with the help of lecture slides
    private class RandomizedQueueIterator implements Iterator<Item> {
        int i = 0;
        Item[] randomArr;

        public RandomizedQueueIterator() {
            randomArr = (Item[]) new Object[size];
            System.arraycopy(arr, 0, randomArr, 0, size);
            StdRandom.shuffle(randomArr);
        }

        public boolean hasNext() {
            return i < size;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No more elements to return");
            return randomArr[i++];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        randQueue.enqueue(2);
        randQueue.enqueue(3);
        randQueue.enqueue(4);
        randQueue.enqueue(5);
        randQueue.enqueue(6);
        StdOut.print(randQueue.size());
        StdOut.print(randQueue.sample());
        StdOut.print(randQueue.dequeue());
    }
}
