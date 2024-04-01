package assign2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // initial capacity of underlying resizing array
    private static final int INIT_CAPACITY = 16;

    // array of items
    private Item[] items;         

    // number of elements on queue
    private int size;            

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[INIT_CAPACITY];
        size = 0;
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
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }
        if (items.length == size) {
            resize(2*size);
        }
        items[size++] = item;
    }

    private void resize(int capacity) {
        if (capacity <= size) {
            return;
        }
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // remove and return a random item
    private int getRandomIndex() {
        return StdRandom.uniformInt(0, size);
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        int index = getRandomIndex();
        Item val = items[index];
        items[index] = items[size-1];
        items[size-1] = null;
        size--;
        if (size == items.length/4) {
            resize(items.length/2);
        }
        return val;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        int index = getRandomIndex();
        return items[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private final class  ArrayIterator implements Iterator<Item> {

        private int iteratedNum;

        private Item[] iteratingItems;

        public ArrayIterator() {
            iteratingItems = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                iteratingItems[i] = items[i];
            }
            StdRandom.shuffle(iteratingItems);
        }

        @Override
        public boolean hasNext() {
            return iteratedNum < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Queue underflow");
            }
            Item val = iteratingItems[iteratedNum++];
            return val;
        }
    
        
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++) 
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
        queue.dequeue();
        queue.dequeue();
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }

    }
    
}
