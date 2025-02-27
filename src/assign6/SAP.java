package assign6;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * This class is an immutable data type 
 * that finds the shortest ancestral path between two vertices v and w in a digraph. 
 */
public class SAP {

    private final Digraph graph;

    private int minLen;

    private int minAncestor;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        graph = new Digraph(G);
    }


    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        checkVertex(v);
        checkVertex(w);
        bfs(v, w);
        return minLen == Integer.MAX_VALUE ? -1 : minLen;
    }


    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        checkVertex(v);
        checkVertex(w);
        bfs(v, w);
        return minAncestor;

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException("argument is null"); 
        }
        bfs(v, w);
        return minLen == Integer.MAX_VALUE ? -1 : minLen;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException("argument is null"); 
        }
        bfs(v, w);
        return minAncestor;
    }

    private void bfs(int v, int w) {
        Queue<Integer> a = new Queue<>();
        Queue<Integer> b = new Queue<>();
        a.enqueue(v);
        b.enqueue(w);
        bfs(a, b);
    }

    private void checkVertex(int v) {
        if (v < 0 || v >= graph.V()) {
            throw new IllegalArgumentException("argument is beyond the prescribed range");
        }
    }
    
    private void bfs(Iterable<Integer> v, Iterable<Integer> w) {
        Queue<Integer> queue1 = new Queue<>();
        Queue<Integer> queue2 = new Queue<>();
        boolean[] marked1 = new boolean[graph.V()];
        boolean[] marked2 = new boolean[graph.V()];
        int[] distTo1 = new int[graph.V()];
        int[] distTo2 = new int[graph.V()];
        for (Integer i : v) {
            if (i == null) {
                throw new IllegalArgumentException("argument is null");
            }
            checkVertex(i);
            queue1.enqueue(i);
            marked1[i] = true;
        }
        for (Integer i : w) {
            if (i == null) {
                throw new IllegalArgumentException("argument is null");
            }
            checkVertex(i);
            queue2.enqueue(i);
            marked2[i] = true;
        }
        minAncestor = -1;
        minLen = Integer.MAX_VALUE;
        while (!queue1.isEmpty() || !queue2.isEmpty()) {
            if (!queue1.isEmpty()) {
                int p1 = updateQueue(queue1, marked1, distTo1);
                if (p1 != -1 && marked2[p1]) {
                    updateMinAncestor(p1, distTo1, distTo2);
                }
            }
            if (!queue2.isEmpty()) {
                int p2 = updateQueue(queue2, marked2, distTo2);
                if (p2 != -1 && marked1[p2]) {
                    updateMinAncestor(p2, distTo1, distTo2);
                }
            }
        }
        
        
    }


    private int updateQueue(Queue<Integer> queue, boolean[] marked, int[] distTo) {
        int p = queue.dequeue();
        if (distTo[p] > minLen) {
            return -1;
        }
        for (int n : graph.adj(p)) {
            if (marked[n]) {
                continue;
            }
            marked[n] = true;
            queue.enqueue(n);
            distTo[n] = distTo[p] + 1;
        }
        return p;
    }


    private void updateMinAncestor(int p, int[] distTo1, int[] distTo2) {

        if (distTo1[p] >= minLen || distTo2[p] >= minLen) {
            return;
        }

        int totalLen = distTo1[p] + distTo2[p];
        if (totalLen < minLen) {
            minLen = totalLen;
            minAncestor = p;
        }
        
    }


    public static void main(String[] args) {
        In in = new In("..\\wordnet\\digraph25.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Queue<Integer> a = new Queue<>();
        Queue<Integer> b = new Queue<>();
        a.enqueue(13); 
        a.enqueue(23); 
        a.enqueue(24); 
        b.enqueue(6);
        b.enqueue(16);
        b.enqueue(17);
        int length   = sap.length(a, b);
        int ancestor = sap.ancestor(a, b);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length2   = sap.length(v, w);
            int ancestor2 = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length2, ancestor2);
        }
    }
}
