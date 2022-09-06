# Lab: Merge Sort with Linked Lists

In this lab you are asked to implement two versions of mergesort on linked lists,
one that produces a new list (`sort`) and another that works in-place (`sort_in_place`) 
by changing the input list. 

The `sort` function applied to the following list

    [5] -> [2] -> [7] -> [1]

should produce the following new linked list and leave the original one unchanged.

    [1] -> [2] -> [5] -> [7]

Place your `sort` and `sort_in_place` methods in a class named `MergeSort`
in a file named `MergeSort.java`.
Your `sort` function should have a `Node` parameter and `Node` return type,
that point to the first node in the input list and first node in the output list,
respectively. The `Node` class is provided to you in the file [`Node.java`](./Node.java).

	static Node sort(Node N) { ... }

The `sort_in_place` function applied to the following list

    [6] -> [3] -> [8] -> [2]
    
should rearrange the nodes into the following order:

    [2] -> [3] -> [6] -> [8]

This `sort_in_place` function should also take and return a `Node` that
point to the first node in the input and output lists, respectively.

	static Node sort_in_place(Node N) { ... }

Section 7.6 of the textbook describes mergesort on an array. You'll need to
adapt the algorithm for linked lists. The steps of the algorithms are

1. split the input sequence in half,
2. recursively sort the two halves, and
3. merge the two two results into one linked list.

You'll need to implement two versions of the merge algorithm, one that
returns a new linked list and the other that works in-place, rearranging
the nodes.

    static Node merge(Node A, Node B) { ... }
	
	static Node merge_in_place(Node A, Node B) { ... }

For example, `merge` applied to the following two sorted lists

    [1] -> [2] -> [5] -> [7]
    [2] -> [3] -> [6] -> [8]
    
produces the following newly allocated list

    [1] -> [2] -> [2] -> [3] -> [5] -> [6] -> [7] -> [8]

The idea of the merge algorithm is to scan through the two input sequences,
choosing the smaller of the two current elements for the output sequence.

## Testing

Create a file named `MergeSortTest.java` that includes three tests for
each of your merge and sort methods.

## Questions

In a file named `README.md` answer the following questions.

1. What is the time and space complexity of your `merge` function?
2. What is the time and space complexity of your `sort` function?
3. What is the time and space complexity of your `merge_in_place` function?
4. What is the time and space complexity of your `sort_in_place` function?

## Submission

Submit your `MergeSort.java`, `MergeSortTest.java`, and `README.md` files
to the autograder:

[https://autograder.luddy.indiana.edu/web/project/509](https://autograder.luddy.indiana.edu/web/project/509)
