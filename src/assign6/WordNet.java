package assign6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

/**
 * This class creates a WordNet that is a semantic lexicon for the English language. 
 * It's a digraph: each vertex v is an integer that represents a synset, 
 * and each directed edge v→w represents that w is a hypernym of v. 
 * This digraph is acyclic and has one vertex—the root—that is an ancestor of every other vertex.
 */
public class WordNet {

    private static final int MAP_SIZE = 100;

    private final HashMap<String, Queue<Integer>> words;

    private final HashMap<Integer, String> ids;

    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || synsets.isEmpty() 
        || hypernyms == null || hypernyms.isEmpty()) {
            throw new IllegalArgumentException("argument is nul");
        }
        words = new HashMap<>(MAP_SIZE);
        ids = new HashMap<>(MAP_SIZE);
        initSynsets(synsets);
        Digraph hyGraph = initDigraph(hypernyms);
        sap = new SAP(hyGraph);
    }

    private Digraph initDigraph(String hypernyms) {
        In in = new In(hypernyms);
        int rootNum = 0;
        Map<Integer, List<Integer>> hyperMap = new HashMap<>(MAP_SIZE);
        int maxId = 0;
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] splitLine = line.split(",");
            if (splitLine.length == 0) {
                throw new IllegalArgumentException("the text of hypernyms file is wrong");
            }
            int v = Integer.parseInt(splitLine[0]);
            if (v > maxId) {
                maxId = v;
            }
            if (splitLine.length == 1) {
                rootNum++;
            }
            List<Integer> hyperList = new ArrayList<>();
            for (int i = 1; i < splitLine.length; i++) {
                hyperList.add(Integer.parseInt(splitLine[i]));
            }
            hyperMap.put(v, hyperList);
        }
        
        if (rootNum != 1) {
            throw new IllegalArgumentException("hypernyms file is invaild as it has no root or multiple root");
        }

        Digraph hyGraph = new Digraph(maxId+1);
        for (Entry<Integer, List<Integer>> entry : hyperMap.entrySet()) {
            for (int edge : entry.getValue()) {
                hyGraph.addEdge(entry.getKey(), edge);
            }
        }
        DirectedCycle cycle = new DirectedCycle(hyGraph);
        if (cycle.hasCycle()) {
            throw new IllegalArgumentException("hypernyms file is invaild as its graph is not DAG");
        }
        return hyGraph;
        
    }

    private void initSynsets(String synsets) {
        In in = new In(synsets);
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] splitLine = line.split(",");
            if (splitLine.length < 2) {
                throw new IllegalArgumentException("the text of synsets file is wrong");
            }
            int id = Integer.parseInt(splitLine[0]);
            ids.put(id, splitLine[1]);
            String[] nouns = splitLine[1].trim().split("\\s+");
            for (int i = 0; i < nouns.length; i++) {
                Queue<Integer> idQueue = new Queue<>();
                if (words.containsKey(nouns[i])) {
                    idQueue = words.get(nouns[i]);
                }
                idQueue.enqueue(id);
                words.put(nouns[i], idQueue);
            }
            
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return words.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return words.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException("argument is null"); 
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException(!isNoun(nounA) ? nounA : nounB + " is not WordNet nouns"); 
        }
        if (nounA.equals(nounB)) {
            return 0;
        }
        return sap.length(words.get(nounA), words.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException("argument is null"); 
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException(!isNoun(nounA) ? nounA : nounB + " is not WordNet nouns"); 
        }
        int ancestor = sap.ancestor(words.get(nounA), words.get(nounB));
        if (ancestor == -1) {
            return null;
        }
        return ids.get(ancestor);
    }

    public static void main(String[] args) {
        WordNet wordNet = new WordNet("..\\wordnet\\synsets1000-subgraph.txt", 
        "..\\wordnet\\hypernyms1000-subgraph.txt");
        System.out.println(wordNet.isNoun("Cryptobranchidae"));
        System.out.println(wordNet.sap("tummy", "tummy"));
       
    }

    
    
}