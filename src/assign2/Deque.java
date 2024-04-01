package assign2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node head;

    private Node tail;

    private int size;

    // construct an empty deque
    public Deque() {
        head = new Node();
        tail = new Node();
        size = 0;
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
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }
        Node newNode = new Node();
        newNode.val = item;
        if (isEmpty()) {
            head.next = newNode;
            tail.previous = newNode;
        } else {
            Node oldHead = head.next;
            newNode.next = oldHead;
            oldHead.previous = newNode;
            head.next = newNode;
        }
        
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }
        Node newNode = new Node();
        newNode.val = item;
        if (isEmpty()) {
            head.next = newNode;
            tail.previous = newNode;
        } else {
            Node oldTail = tail.previous;
            newNode.previous = oldTail;
            oldTail.next = newNode;
            tail.previous = newNode;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("dequeue is empty");
        }
        Node oldHead = head.next;
        head.next = head.next.next;
        Node newHead = head.next;
        if (newHead != null) {
            newHead.previous = null;
        } else {
            tail.previous = null;
        }
        size--;
        return oldHead.val;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("dequeue is empty");
        }
        Node oldTail = tail.previous;
        tail.previous = tail.previous.previous;
        Node newTail = tail.previous;
        if (newTail != null) {
            newTail.next = null;
        } else {
            head.next = null;
        }
        size--;
        return oldTail.val;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }

    private final class LinkedIterator implements Iterator<Item> {

        private Node current;

        public LinkedIterator() {
            current = head.next;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("dequeue is empty");
            }
            Item val = current.val;
            current = current.next;
            return val;
        }
        
    }

    private final class Node {
        private Item val;

        private Node previous;

        private Node next;
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
         deque.addLast(2);
         System.out.println("first: "+deque.removeFirst());     
         deque.addLast(4);
         deque.addLast(5);
         deque.addLast(6);
         deque.addLast(7);
         deque.addLast(8);
         deque.addLast(9);
         System.out.println("first: "+deque.removeFirst());
         System.out.println("last: "+deque.removeLast());
         for (Integer integer : deque) {
            System.out.println(integer);
         }
    }


}
