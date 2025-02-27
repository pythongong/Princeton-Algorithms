package assign6;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * This class is an immutable data type that identifies an outcast in the WordNet. 
 */
public class Outcast {

    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast         
    public String outcast(String[] nouns) {
        if (nouns == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int maxDistance = -1;
        String outCast = null;
        for (int i = 0; i < nouns.length; i++) {
            int distance = 0;
            for (int j = 0; j < nouns.length; j++) {
                distance += wordNet.distance(nouns[i], nouns[j]);
            }
            if (maxDistance < distance) {
                maxDistance = distance;
                outCast = nouns[i];
            }
        }
        return outCast;

    }
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("..\\wordnet\\synsets.txt", 
        "..\\wordnet\\hypernyms.txt");
        Outcast outcast = new Outcast(wordNet);
        In in = new In("..\\wordnet\\outcast29.txt");
        String[] nouns = in.readAllStrings();
        StdOut.println(outcast.outcast(nouns));
    }
}
