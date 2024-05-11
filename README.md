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
The workspace contains two folders by default, where:

- src: the folder to maintain sources which are my solution codes.
- lib: the folder to maintain dependency which is given by the course.

Meanwhile, the compiled output files will be generated in the bin folder by default.


# Assignment 1

[Assignment 1: percolation](https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php)

This assignment test the application of union-find data type.

PercolationVisualizer class is a test class given by the course.

The most diffcult part is how to determine whether the site is connected to the top or bottom row
as you only have one root in union-find.
I create the virtual top root and bottom root
and use extra union-find to determine whether the site is connected to the top first
But it can't pass the bonus test.
Then I use an byte array to store the status of each site and get the bonus score.

# Assignment 2
[Assignment 2 : Queues](https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php) 

This assignment tests if you understand the queue and modify it for different applications.

Implementation of Permutation is not as easy as I imaged first although it just print k permutations.
If you add all items to the queue, then you won't pass the bonus test. 

# Assignment 3
[Assignment 3 : colinear](https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php)

This assignment tests the applciation of sort algorthims.

BruteCollinearPoints implements brute force solution.

LineSegment uses the standard libaray sort to optimize.

# Assignment 4
[Assignment 4 : 8puzzle](https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php)

This assignment tests if you understand the search algorthims and modify it for different applications.

# Assignment 5
[Assignment 5 : kd-trees](https://coursera.cs.princeton.edu/algs4/assignments/kdtree/specification.php)

This assignment tests if you understand the binary search tree and modify it for different applications.

# Assignment 6
[Assignment 6 : WordNet](https://coursera.cs.princeton.edu/algs4/assignments/wordnet/specification.php)

This assignment tests the applciation of directed graph.

# Assignment 7
[Assignment 7 : Seam-Carving](https://coursera.cs.princeton.edu/algs4/assignments/seam/specification.php)

This assignment tests if you understand the shortest path algorthims and modify it for different applications.


It's actually the same as dynmaic programming in this assignment.

# Assignment 8
[Assignment 8 : Baseball Elimenation](https://coursera.cs.princeton.edu/algs4/assignments/baseball/specification.php)

This assignment tests the applciation of max-flow algorthims 

# Assignment 9
[Assignment 9 : Boggle](https://coursera.cs.princeton.edu/algs4/assignments/boggle/specification.php)

This assignment test if you understand trie and modify it for different applications.

# Assignment 10
[Assignment 10 : Burrows-Wheeler](https://coursera.cs.princeton.edu/algs4/assignments/burrows/specification.php)

This assignment test if you understand the string sort algorithms and modify it for different applications. 

If you use the standard libaray sort, then you won't pass the timing test. 

I use LSD algorithm to optimize CircularSuffixArray first 
then realize that it's even smaller than the standard sort.
Then I use the modified MSD algorithm and pass the test.