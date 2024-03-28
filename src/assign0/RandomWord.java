package assign0;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
    
        String champion = null;
        int count = 0;

        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            count++;
            // Detemine whether this word can be a champion
            if (StdRandom.bernoulli(1.0 / count)) {
                champion = word;
            }
        }

        // If we have a champion word, print it
        if (champion != null) {
            StdOut.print(champion);
        } 
    }
}
