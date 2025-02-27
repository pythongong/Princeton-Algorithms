# Princeton Algorithm

This repository contains my solution for the assignments of  PrincetonaAlgorithm course.

All my solutions has gained 100 score with bonus

Algorithms1 grade
![algorithms1 grade](https://github.com/pythongong/images/blob/main/image.png)

Algorithms2 grade
![algorithms2 grade](https://github.com/pythongong/images/blob/main/algorithms%202.jpeg)

# Course Link
[Coursera course website : part1](https://www.coursera.org/learn/algorithms-part1/home/welcome)

[Coursera course website : part2](https://www.coursera.org/learn/algorithms-part2/home/welcome)


# Folder Structure
The workspace contains two folders:

- `src`: This folder contains all solution codes.
- `lib`: This folder contains the course library.
- `bin`: This folder will be generated if you compile the programs.

# Requirements
Java 1.8 or later

# Assignment 1

[Assignment 1: percolation](https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php)


This assginment estimates the percolation threshold of a system using a Monte Carlo simulation. 
The simulation is performed on an n-by-n grid of sites, where each site can be open or blocked. 
The system percolates if there is a full site in the bottom row that is connected to an open site in the top row. 
It performs multiple trials and computes the sample mean, standard deviation, and confidence intervals for the percolation threshold.

**Classes**
- `Percolation`: This class contains the implementation of the percolation system. It models the grid of sites, allows opening of sites, and determines if the system percolates.

- `PercolationStats`: This class handles multiple independent trials of percolation experiments on an n-by-n grid, computes the percolation threshold, and outputs the statistical results.To run the program, provide two command-line arguments: the grid size (`n`) and the number of trials (`T`).

- `PercolationVisualizer`: It's a test class given by the course. 

**Difficulty**

The most diffcult part is how to determine whether the site is connected to the top or bottom row
as you only have one root in union-find.
I create the virtual top root and bottom root
and use extra union-find to determine whether the site is connected to the top first
But it can't pass the bonus test.
Then I use an byte array to store the status of each site and get the bonus score.


# Assignment 2
[Assignment 2 : Queues](https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php) 

This assignment is to write a generic data type for a deque and a randomized queue. 

**Classes**
- `Deque`: A generic double-ended queue.
- `RandomizedQueue`: A generic randomized queue. It is similar to a stack or queue, except that the item removed is chosen uniformly at random among items in the data structure.
- `Permutation`: This class is a client program that takes an integer k as a command-line argument; reads a sequence of strings from standard input using `StdIn.readString();` and prints exactly k of them, uniformly at random. It prints each item from the sequence at most once.

**Difficulty**:
Implementation of Permutation is not as easy as I imaged first although it just print k permutations.
If you add all items to the queue, then you won't pass the bonus test. 

# Assignment 3
[Assignment 3 : colinear](https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php)

This assignment find every (maximal) line segment that connects a subset of 4 or more of the points, given a set of n distinct points in the plane. 

**Classes**
- `BruteCollinearPoints`: This class implements brute force solution to find all line segments. It examines 4 points at a time and checks whether they all lie on the same line segment, returning all such line segments. 

- `FastCollinearPoints` This class gives a faster, sorting-based solution to find all line segments.It sorts the points according to the slopes they makes with a point p.  If any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p, these points, together with p, are collinear.

- `LineSegment`: The helpful data type given by the course. It represents a line segement. 

- `Point`: The point data type given by the course. Some functions need to be completed by the students. 

# Assignment 4
[Assignment 4 : 8puzzle](https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php)

This assignment requires the student to solve the 8-puzzle problem. 

**Classes**
- `Board`: This class is a immutable data type that models an n-by-n board with sliding tiles.
- `Solver`: This class implements A* search to solve n-by-n slider puzzles. 


# Assignment 5
[Assignment 5 : kd-trees](https://coursera.cs.princeton.edu/algs4/assignments/kdtree/specification.php)

This assignment needs the student create a kd-tree that supports efficient range search (find all of the points contained in a query rectangle) and nearest-neighbor search (find a closest point to a query point). 

**Classes**
- `PointSET`: This class implements a kd-tree by using `TreeSet`.

- `KdTree`: This class implements a kd-tree by using the 2d-tree that is a generalization of a BST to two-dimensional keys. 

- `KdTreeVisualizer`: A test class given by the course. It computes and draws the 2d-tree that results from the sequence of points clicked by the user in the standard drawing window.

- `RangeSearchVisualizer`: A test class given by the course. It reads a sequence of points from a file (specified as a command-line argument) and inserts those points into a 2d-tree. Then, it performs range searches on the axis-aligned rectangles dragged by the user in the standard drawing window.

- `NearestNeighborVisualizer`: A test class given by the course. It reads a sequence of points from a file (specified as a command-line argument) and inserts those points into a 2d-tree. Then, it performs nearest-neighbor queries on the point corresponding to the location of the mouse in the standard drawing window.



# Assignment 6
[Assignment 6 : WordNet](https://coursera.cs.princeton.edu/algs4/assignments/wordnet/specification.php)

This assignment asks the students to create a WordNet data type and find its shortest ancestral path and outcast. 

**Classes**
- `WordNet`: This class creates a WordNet that is a semantic lexicon for the English language. It's a digraph: each vertex v is an integer that represents a synset, and each directed edge v→w represents that w is a hypernym of v. This digraph is acyclic and has one vertex—the root—that is an ancestor of every other vertex.

- `SAP`: This class is an immutable data type that finds the shortest ancestral path between two vertices v and w in a digraph. 

- `Outcast`: This class is an immutable data type that identifies an outcast in the WordNet. 

# Assignment 7
[Assignment 7 : Seam-Carving](https://coursera.cs.princeton.edu/algs4/assignments/seam/specification.php)

This assignment implements the seam-carving algorithm. This algorithm is a content-aware image resizing technique where the image is reduced in size by one pixel of height (or width) at a time. 

**Classes** 
- `SeamCarver`: This class implements the seam-carving algorithm by using the shortest path algorthims It's actually the same as dynmaic programming.
-  `PrintEnergy`, `PrintSeams`, `ResizeDemo`: The test classes provided by the course. 


# Assignment 8
[Assignment 8 : Baseball Elimenation](https://coursera.cs.princeton.edu/algs4/assignments/baseball/specification.php)

This assignment requires the students to determine which teams have been mathematically eliminated from winning their division, given the standings in a sports division at some point during the season.

**Classes**
- `BaseballElimination`: This class uses the max-flow algorthim to solve the problem. 

# Assignment 9
[Assignment 9 : Boggle](https://coursera.cs.princeton.edu/algs4/assignments/boggle/specification.php)

This asignment asks the students to write a program to play the word game Boggle

- `BoggleBoard`: This class is an immutable data type for representing Boggle boards

- `BoggleSolver`: This class finds all valid words in a given Boggle board, using a given dictionary. It's a variation of the trie. 

# Assignment 10
[Assignment 10 : Burrows-Wheeler](https://coursera.cs.princeton.edu/algs4/assignments/burrows/specification.php)


This assignment implements the Burrows–Wheeler data compression algorithm.

**Classes**
- `MoveToFront`: This class implements the move-to-front encoding that maintains an ordered sequence of the characters in the alphabet by repeatedly reading a character from the input message; printing the position in the sequence in which that character appears; and moving that character to the front of the sequence. 

- `CircularSuffixArray`: This class implements the key component in the Burrows–Wheeler transform, known as the circular suffix array, which describes the abstraction of a sorted array of the n circular suffixes of a string of length n. 

- `BurrowsWheeler`: This class implements a sorting-based Burrows–Wheeler algorithm. It rearranges the characters in the input so that there are lots of clusters with repeated characters. 

**Difficulty**

If you use JDK's sort method, you won't pass the timing test. I use LSD algorithm to optimize CircularSuffixArray first but then I realize that it's even slower than the standard sort.
Then I use the modified MSD algorithm and pass the test.