package assign2;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }
        for (String item : queue) {
            if (k == 0) {
                break;
            }
            System.out.println(item);
            k--;
        }

    }
}
