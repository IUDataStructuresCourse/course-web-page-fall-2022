# Lab: Binomial Heaps

Implement the `isHeap()` method in the `BinomialHeap` and
`BinomialQueue` classes.

Implement the `insert()` and `merge()` helper methods in the
`BinomialQueue` class.

The starting code for the project is in these files:
* [BinomialQueue.java](./BinomialQueue.java)
* [PList.java](./PList.java)

## BinomialHeap

The following is an excerpt from the `BinomialHeap` class.

```java
    class BinomialHeap<K> {
    K key;
    int height;
    PList<BinomialHeap<K>> children;
    BiPredicate<K, K> lessEq;

    boolean isHeap();

    BinomialHeap<K> link(BinomialHeap<K> other) { ...}
		...
}
```
    
The `isHeap()` method should verify that the tree rooted at the 
current instance of `BinomialHeap` satisfies the max heap property,
that is, that the key of each parent is greater or equal to
the keys of its children.

## BinomialQueue

The following is an excerpt from the `BinomialQueue` class.

```java
    public class BinomialQueue<K> {
    PList<BinomialHeap<K>> forest;
    BiPredicate<K, K> lessEq;

    public BinomialQueue(BiPredicate<K, K> le) { ...}

    public void push(K key) { ...}

    public K pop() { ...}

    public boolean isHeap();

    static <K> PList<BinomialHeap<K>>
    insert(BinomialHeap<K> n, PList<BinomialHeap<K>> ns);

    static <K> PList<BinomialHeap<K>>
    merge(PList<BinomialHeap<K>> ns1,
          PList<BinomialHeap<K>> ns2);
			  
	    ...
}
```

The `insert()` method in `BinomialQueue` should put the tree `n` into
the forest `ns` while maintaining two invariants:

1. the trees in the forest must be sorted by increasing heights, and
2. there should not be two trees with the same height.

(Hint: the `link()` method of `BinomialHeap` takes two trees of the
same height and combines them into a new tree with one greater
height.)

The `merge()` method in `BinomialQueue` should combine two forests
into a single forest that satisfies the two invariants listed above.


## Testing

Create the file `StudentTest.java` with a class named `StudentTest`
and a method named `test` that thoroughly tests the public `push` and
`pop` methods of the `BinomialQueue`. Submit your test to the
autograder project named BinomialHeapTest .

## Submission

Submit your `BinomialQueue.java` file to the autograder project named
BinomialHeap.
