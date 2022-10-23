# Lab: Generic QuickSort

Implement quicksort in a way that is generic enough that it can be
applied to an input sequence that is represented as an Array or a
LinkedList. That is, use the [`Iterator`](./Iterator.java)
interface. Also, the elements of the sequence do not have to be
integers, but instead could be anything that implements the
`java.util.Comparable` interface. Your goal should be to maintain the
time complexity of quicksort, that is, it should have worst case O(n^2)
time complexity and average case O(n log n) time complexity (as is
normal for quicksort).

The starter code and initial tests are in the following files:

* [`QuickSort.java`](./QuickSort.java)
* [`Iterator.java`](./Iterator.java)
* [`Sequence.java`](./Sequence.java)
* [`ArraySequence.java`](./ArraySequence.java)
* [`LinkedList.java`](./LinkedList.java)

# Testing

Create the file `StudentTest.java` with a class named StudentTest and a
method named `test` that thoroughly tests the `quicksort` method in
[`QuickSort.java`](./QuickSort.java). 

Submit your `StudentTest.java` file to the autograder project named
QuickSortTest.

# Submission

Submit your `QuickSort.java` file to the autograder project named QuickSort.
