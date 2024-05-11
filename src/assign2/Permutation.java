package assign2;


import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * It takes an integer k as a command-line argument; 
 * reads a sequence of strings from standard input 
 * and prints exactly k of them, uniformly at random.
 */
public class Permutation {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("argus to Permutation are empty");
        }
        int k = Integer.parseInt(args[0]);
        if (k <= 0) {
            return;
        }

        RandomizedQueue<String> queue = new RandomizedQueue<>();
        int i = 1;
        double permuSize = k;
        while (!StdIn.isEmpty()) {

            // Read string first, otherwise this iteration won't jump this string
            // and it will take much longer time to end.
            String s = StdIn.readString();

            // When the size of the queue reaches k, 
            // use bernoulli probability to determine whether we should update the queue by current string to 
            // make sure that we print k items uniformly at random.
            if (queue.size() < k) {
                queue.enqueue(s);
            } else if (StdRandom.bernoulli(permuSize / i)) {
                queue.dequeue();
                queue.enqueue(s);
            }
            i++;
        }

        // the size of queue just euqlas to k, so just iterate over the queue to print permutations
        for (String item : queue) {
            StdOut.println(item);
        }

    }
}
