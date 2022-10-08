# Review of NextPrevBinaryTree

    class Node {
        T data;
        Node left, right, parent;
		
        public Node first() {
            Node n = this;
            while (n.left != null) {
            	n = n.left;
            }
            return n;
        }
		
        public Node first() {
            if(left != null) {
                return left.first();
            } else if(right != null) {
                return right.first();
            }

            return this;
        }		

        public Node last() {
            if (right == null) {
                return this;
            } else {
                return right.last();
            }
        }
		
        public Node nextAncestor() {
            if (parent == null) {
                return null;
			} else if (this == parent.left) {
			    return parent;
			} else { // this == parent.right
            	return parent.nextAncestor();
            }
        }
		
        public Node prevAncestor() {
            Node N = this;
            while (N.parent != null) {
                if (N.parent.right == N)
				    return N.parent;
                N = N.parent;
            }
            return null;
        }		
		
        public Node next() {
            if (this.right == null) {
                return this.nextAncestor();
            } else {
                return right.first();
            }
        }
		
        public Node previous() {
            if (left == null) {
                return prevAncestor();
            } else {
                return left.last();
            }
        }
		
	}
	
    class Iter implements Iterator<T>, ReverseIterator<T> {
        private Node curr;

        @Override
        public T get() {
            return curr.data;
        }

        @Override
        public void retreat() {
            curr = curr.previous();
        }

        @Override
        public void advance() {
            curr = curr.next();
        }

        @Override
        public boolean equals(Object other) {
            return curr == other; // comparing a node to an iterator, bad
        }
		
        @Override
        public boolean equals(Object other) {
            return curr.equals(other); // always false, bad
        }
		
        @Override
        public boolean equals(Object other) {
            return ((Iter) other).curr == curr; // good
        }

        @Override
        public Iter clone(){
            return new Iter(curr);
        }		
    }

## Student Test Code

    public class StudentTest{
        @Test
        public void test() throws Exception{
            /*
             * Creates a Binary Tree of arbitrary length and arbitrary elements
             */
            ArrayList<Integer> array1 = new ArrayList<>();
            Random random = new Random();
            for (int i = 0; i < random.nextInt(10000)+1; i++) {
                array1.add(random.nextInt(10000));
            }
            BinaryTree<Integer> tree1 = new BinaryTree<>(array1);

            //Tests begin()
            Assertions.assertEquals(array1.get(0), tree1.begin().get());
            //Tests rbegin()
            Assertions.assertEquals(array1.get(array1.size()-1), 
                                    tree1.rbegin().get());
            //Tests advance()
            BinaryTree.Iter iter1 = tree1.begin();
            if (!iter1.equals(tree1.end()))
                iter1.advance();
            Assertions.assertEquals(array1.get(1), iter1.get());

            ...
        }
    }


# Finish AVL Trees

Review:

**Definition** The AVL Invariant: the height of two child subtrees may
only differ by 1.

## Algorithm for fixing AVL invariant

From the changed node on up  (there can be several AVL violations)
* update the heights
* suppose x is the lowest node that is not AVL
* if height(x.left) ≤ height(x.right)

    1. if height(x.right.left) ≤ height(x.right.right)

        let k = height(x.right.right)

                     x k+2                                y k+2
                    / \          left_rotate(x)          / \
               k-1 A   y k+1     ===============>   k+1 x   C k
                      / \                              / \
            k or k-1 B   C k                      k-1 A   B k or k-1

    2. if height(x.right.left) > height(x.right.right)

        let k = height(x.right.left)

              k+2 x                               y k+1
                 / \                            /   \
            k-1 A   z k+1    R(z), L(x)      k x     z k
                   / \      =============>    / \   / \
                k y   D k-1              k-1 A   B C   D k-1
                 / \
                B   C <k

* if height(x.left) > height(x.right)

    1. if height(x.left.left) < height(x.left.right)  (note: strictly less!)

        let k = height(x.left.right)

                    x k+2                                 z k+1
                   / \                                  /   \
              k+1 y   D k-1      L(y), R(x)          k y     x k
                 / \            =============>        / \   / \
            k-1 A   z k                              A   B C   D    <k
                   / \
                  B   C <k

    2. if height(x.left.left) ≥ height(x.left.right)  (note: greater-equal!)

        let k = height(l.left.left)

                  x k+2                                  y k+1
                 / \         right_rotate(x)            / \
            k+1 y   C k-1    ===============>        k A   x k+1
               / \                                        / \
            k A   B ≤k                                ≤k B   C k-1


## Example: Remove Node and fix AVL

Recall that we can restore the AVL property using tree rotations:

				y                         x
			   / \    right_rotate(y)    / \
			  x   C  --------------->   A   y
			 / \     <-------------        / \
			A   B     left_rotate(x)      B   C

Given the following AVL Tree, delete the node with key 8.
(This example has two nodes that end up violating the AVL
property.)

			 8
		   /   \
		  5     10
		 / \   / \
		2   6 9   11
	   / \   \      \
	  1   3   7      12
		   \
			4
Solution: 
* Step 1: replace node 8 with node 9

			   9
			 /   \
			5     10
		   / \     \
		  2   6     11
		 / \   \      \
		1   3   7      12
			 \
			  4

* Step 2: find lowest node that breaks the AVL property: node 10
* Step 3: rotate 10 left

			   9
			 /   \
			5      11
		   / \    /  \
		  2   6  10   12
		 / \   \
		1   3   7
			 \
			  4

* Step 4: find lowest node that breaks the AVL property: node 9
* Step 5: rotate 9 right

			  5
			/   \
		   /     \
		  2        9
		 / \     /   \
		1   3   6     11
			 \   \   /  \
			  4   7 10   12


## Time Complexity of Insert and Remove for AVL Tree

O(h) = O(log n)

## Using AVL Trees for sorting

* insert n items: O(n log(n))

* in-order traversal: O(n)

* overall time complexity: O(n log(n))


## ADT's that can be implemented by AVL Trees

* priority queue:
  insert, delete, min
  
* ordered set:
  insert, delete, min, max, next, previous
  

