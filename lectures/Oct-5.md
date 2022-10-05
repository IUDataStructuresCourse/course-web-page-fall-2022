# Code Review: Segment Intersection

## Update Height

	protected boolean updateHeight() {
		int oldHeight = this.height;
		int newHeight = max(get_height(this.left), get_height(this.right)) + 1;
		boolean toReturn = oldHeight != newHeight;
		this.height = newHeight;
		return toReturn;
	}
	
	
	
	
	
	
	
	
	
## Another Update Height

	protected boolean updateHeight() {
		int r_height;
		int l_height;
		if(right == null) r_height = -1;
		else r_height = right.height;
		if(left == null) l_height = -1;
		else l_height = left.height;

		int prev = this.height;
		if(l_height > r_height) {
			this.height = l_height +1;
		} else {
			this.height = r_height +1;
		}
		return (prev != this.height);
	}







## Search

    public Node search(K key) {
        if (this.root != null) {
            if (this.find(key, this.root, this.root.parent).data.equals(key)) {
                return this.find(key, this.root, this.root.parent);
            }
        }
        return null;
    }









## Another Search

    public Node search(K key) {
        Node suspect = find(key, root, null);
        if(!suspect.get().equals(key)) {
            return null;
        }
        return suspect;
    }








## Right Rotate

    public Node rightRotate(Node y) {
        Node x = y.left;
        Node T1 = y.right;
        Node T3 = x.left;
        Node T2 = x.right;
        K yData = y.data;
        K xData = x.data;

        y.data = xData;
        y.right = new Node(yData);
        y.right.parent = y;
        y.right.left = T2;
        if (T2 != null) {
            T2.parent = y.right;
            T2.updateHeight();
        }
        y.right.right = T1;
        if (T1 != null) {
            T1.parent = y.right;
            T1.updateHeight();
        }
        y.left = T3;
        if (T3 != null) {
            T3.parent = y;
            T3.updateHeight();
        }

        Node temp = y.right;
        while (temp != null) {
            temp.updateHeight();
            temp = temp.parent;
        }
        return y;
    }


## Another Right Rotate

    void set_left(Node<K> n, Node<K> l) {
        n.left = l;
        if (l != null) l.parent = n;
        n.updateHeight();
    }
	
    void set_right(Node<K> n, Node<K> r) {
        n.right = r;
        if (r != null) r.parent = n;
        n.updateHeight();
    }

    // Replace node n1 with node n2.
    void replace(Node<K> n1, Node<K> n2) {
        if (n1.parent == null) {
            root = n2;
            if (n2 != null) n2.parent = null;
        } else {
            if (n1 == n1.parent.left) {
                set_left(n1.parent, n2);
            } else if (n1 == n1.parent.right) {
                set_right(n1.parent, n2);
            }
        }
    }

    Node right_rotate(Node y) {
        Node x = y.left;
		assert x != null;
        Node B = x.right;
        replace(y, x);
        set_left(y, B);
        set_right(x, y);
        return x;
    }
	

## AVL Insert

    public Node insert(K key) {
        if (this.root == null) {
            this.root = new Node(key);
            this.numNodes = 1;
            return this.root;
        }
        if (this.contains(key)) {
            return null;
        }
        Node addParent = find(key, this.root, this.root.parent);
        Node toReturn;
        if (lessThan.test(key, addParent.data)) {
            addParent.left = new Node(key);
            addParent.updateHeight();
            addParent.left.parent = addParent;
            this.numNodes += 1;
            toReturn = addParent.left;
        } else {
            addParent.right = new Node(key);
            addParent.updateHeight();
            addParent.right.parent = addParent;
            this.numNodes += 1;
            toReturn = addParent.right;
        }
        Node temp = toReturn;
        while (temp != null) {
            temp.updateHeight();
            temp = temp.parent;
        }
        Node toCheck = toReturn;

        while (toCheck != null) {
            if (!(toCheck.isAVL())) {
                int loadFactor = (get_height(toCheck.left) - get_height(toCheck.right));
                // Left Left case
                if (loadFactor > 1 && lessThan.test(key, toCheck.left.data)) {
                    temp =  rightRotate(toCheck);
                    return temp;
                }
                // Right Right Case
                else if (loadFactor < -1 && !(lessThan.test(key, toCheck.right.data))) {
                    temp = leftRotate(toCheck);
                    return temp;
                }
                // Left Right Case
                else if (loadFactor > 1 && !(lessThan.test(key, toCheck.left.data))) {
                    toCheck.left = leftRotate(toCheck.left);
                    temp = rightRotate(toCheck);
                    return temp;
                }
                // Right Left Case
                else if (loadFactor < -1 && lessThan.test(key, toCheck.right.data)) {
                    toCheck.right = rightRotate(toCheck.right);
                    temp = leftRotate(toCheck);
                    return temp;
                }
            }
            toCheck = toCheck.parent;
        }
        return null;
    }

## Another AVL Insert

    public Node insert(K key) {
        Node n = super.insert(key);
        Node parent = n.parent;
        if (n == null) {
            return n;
        } else {
            if (parent == null){
                root = makeAVL(root,root);
                return root;
            }
			makeAVL(root,parent);
            return root;
        }
    }

    public Node makeAVL(Node root, Node n){
        if (n == null)
            return root;
        else if (n.isAVL()){
            return makeAVL(root,n.parent);
        }
        else if (get_height(n.left) <= get_height(n.right)){
            if (get_height(n.right.left) <= get_height(n.right.right)){
                Node y = rotateLeft(n);
                root = getRoot(y);
                return makeAVL(root,y.parent);
            }else{
                n.right = rotateRight(n.right);
                n.right.parent = n;
                Node y = rotateLeft(n);
                root = getRoot(y);
                return makeAVL(root,y.parent);
            }
        }else{
            if (get_height(n.left.left) < get_height(n.left.right)){
                n.left = rotateLeft(n.left);
                n.left.parent = n;
                Node y = rotateRight(n);
                root = getRoot(y);
                return makeAVL(root,y.parent);
            }else{
                Node y = rotateRight(n);
                root = getRoot(y);
                return makeAVL(root,y.parent);
            }
        }
    }


## Yet Another AVL Insert

    public Node insert(K key) {
        Node q = super.insert(key);
        fixups(q);
        return q;
    }
	
    private void fixups(Node p) {
        while (p != null) {
            p = rebalance(p);
            p.updateHeight();
            p = p.parent;
        }
    }
	
    private Node rebalance(Node node){
        if (! isAVL(node)) {
            if (get_height(node.left) <= get_height(node.right)) {
                if (get_height(node.right.left) <= get_height(node.right.right)) {
                    return left_rotate(node);
                } else {
                    Node r = right_rotate(node.right);
                    set_right(node, r);
                    return left_rotate(node);
                }
            } else { // get_height(node.left) > get_height(node.right)
                if (get_height(node.left.left) < get_height(node.left.right)) {
                    Node l = left_rotate(node.left);
                    set_left(node, l);
                    return right_rotate(node);
                } else { // get_height(node.left.left) >= get_height(node.left.right)
                    return right_rotate(node);
                }
            }
        } else {
            return node;
        }
    }
	
## AVL Remove

    public void remove(K key) {
        if (!(this.contains(key))) {
            return;
        }
        Node deleteMe = this.search(key);

        if (deleteMe.left != null && deleteMe.right != null) {
            deleteWithTwoKids(key);
        } else if (deleteMe.isLeaf()){
            deleteLeaf(key);
        } else {
            deleteWithOneChild(key);
        }
        numNodes -= 1;
    }

    private void deleteLeaf(K key) {
        Node deleteMe = this.search(key);
        if (deleteMe.equals(root))
            this.clear();
            return;
        }
        Node parent = deleteMe.parent;
        if (deleteMe.equals(parent.left)) {
            parent.left = null;
        } else if (deleteMe.equals(parent.right)) {
            parent.right = null;
        }
        updateHeightHelper(deleteMe);
        if (parent != null) {
            balance(parent);
        }
    }

    private void deleteWithTwoKids(K key) {
        Node deleteMe = this.search(key);
        Node replacer = deleteMe.right.first();
        Node replacerParent = replacer.parent;
        deleteMe.data = replacer.data;

        if (deleteMe.right.equals(replacer)) {
            deleteMe.right = replacer.right;
            if (replacer.right != null) {
                replacer.right.parent = deleteMe;
            }
        } else {
            replacerParent.left = replacer.right;
            if (replacer.right != null) {
                replacer.right.parent = replacer.parent;
            }
        }
        updateHeightHelper(replacer);
        if (replacer != null) {
            balance(replacer);
        }
    }


# Binomial Queues

Overview:
1. Binomial Trees and Heaps
2. Binomial Forests and Queues

## Binomial Trees and Heaps

Recall the binomial coefficient, n choose k, written 

    ( n )
    ( k )

which is the number of ways to choose k elements from a set of size n.
For example, there are three ways to choose 2 elements from a set of
size 3. For {a,b,c} we have {a,b}, {a,c}, {b,c}.  In general, we have:

    ( n )  =    n! / ( k!(n - k)! )
    ( k )


    ( 3 )  = 3! / ( 2!(3 - 2)! ) = 6 / 2 = 3
    ( 2 )

The name "binomial" comes from the Binomial Theorem, which describes
the expansion of powers of a binomial:

    (x + y)^n = Sum from k=0 to n of (n choose k) * x^(n-k) * y^k

For example,

    (x + y)^4 = x^4 + 4x^3y + 6x^2y^2 + 4xy^3 + y^4

Recall Pascal's Triangle
    
                   0=k  (diagonals)
    n=0          1 / 1
              ----/-- /
      1        1 / 1 / 2
            ----/---/-- /
      2      1 / 2 / 1 / 3
           ---/---/---/-- /
      3    1 / 3 / 3 / 1 / 4
         ---/---/---/---/--
      4  1 / 4 / 6 / 4 / 1 
    

From the triangle it is easy to see that each cell is the sum of
the two cells diagonally above it:

    ( n ) = ( n-1 ) + ( n-1 )
    ( k )   ( k-1 )   ( k   )


Def. A **binomial tree** Bn is a tree whose root has n children, where
the first child is B{n-1}, the second is B{n-2}, ..., on down to
the last child, which is B0.

     B0   B1   B2   B3
     o    o    o     o---\
          |    |\    | \  \
          B0   B1 B0 B2 B1 B0

Consider the number of nodes at each depth within a binomial tree.

    depth
    0    o 1  o 1  o   1     _o    1
              |    |\      _/ |\
    1         o 1  o o 2  o   o o  3
                   |      |\  |
    2              o   1  o o o    3
                          |
    3                     o        1

So the name binomial tree comes from there being n choose k nodes at
depth k of tree Bn.

Each binomial tree Bn can be formed by taking two trees of B{n-1}
and putting one of them as a child of the other's root.

    B2           B3
	o                _o
	|\      B2     _/ |\
	o o  ∪  o  =  o   o o
	|       |\    |\  |
	o       o o   o o o
			|	  |
			o	  o

Turning them on their side and aligning by depth:

    1     1 1       1 2 1
    +
      1     1 1       1 2 1
    =
    1 1   1 2 1     1 3 3 1

The height of a binomial tree Bn is n.  binomial tree Bn has 2^n
nodes.  So height = log nodes. It's balanced!

Student exercise: draw B4. how many nodes does it have?  How many
nodes at each depth?  Solution:

    depth
    0    o---\--\    1
         | \  \  \
    1    o   o  o  o 4
         |   |  |
    2    ooo oo o    6
         |   |
    3    ooo o       4
         |
    4    o           1


Def. A **binomial heap** Bn is a binomial tree where each node has a
key and they satisfy the max-heap property.

	class BinomialHeap<K> {
		K key;
		int height;
		PList<BinomialHeap<K>> children;
		BiPredicate<K, K> lessEq;
		...
	}	

The `PList` class is for **persistent lists**. 

In general, a data structure is called **persistent** if it can
provide efficient access to older versions of the data structure.

The `PList` class represents lists in a way that you can add an
element to the list but still access the old list, prior to the
addition. The following is an exerpt from the `PList` class.

	class PList<T> {
		T data;
		PList<T> next;
		PList(T d, PList<T> nxt) { data = d; next = nxt; }

		public static <T> PList<T> addFront(T first, PList<T> rest);
		public static <T> T getFirst(PList<T> n);
		public static <T> PList<T> getNext(PList<T> n);
		...
	}	

The `addFront` method returns a new `PList` with the given `first`
element and whose subsequent elements are the same as the `rest`.

The `getFirst` method returns the first element of the list.

The `getNext` method returns the list as it was before the first
element was added.

Note that the `addFront` and `getNext` methods do not mutate (make
changes) to any `PList` objects.

The `PList` class includes several more methods that come in handy.


## Binomial Forests and Queues

Def. A **binomial forest** is a collection of binomial trees.

We can implement a priority queue with a forest of binomial heaps.
It's a forest to allow for numbers of nodes that are not powers
of 2. 

We'll store the forest in order of *increasing* height and we will not
allow two trees of the same height.  The forest is represented as a
persistent list.

	public class BinomialQueue<K> {
		PList<BinomialHeap<K>> forest;
		BiPredicate<K,K> lessEq;
		...
	}

Binomial queues support an efficient union operation, as well as
insert and extract_max. To accomplish the union operation, we'll need
to know how to merge two binomial forests, which in turn will need a
way to link two binomial trees of the same height into one tree.

If two binomial trees have the same height, linking them is easy.
Just make one of the trees the first child of the other.  Pick the one
with the larger key to be on top to maintain the max heap property.

	Bk  ∪  Bk =  B{k+1}
	B2  ∪  B2 =  B3

	o       o      o______
	|\      |\     |   \  \
	o o  ∪  o o =  o    o  o
	|       |      |\   |
	o       o      o o  o
				   |
				   o

	// @precondition this.height == other.height
	BinomialHeap<K> link(BinomialHeap<K> other) {
		if (lessEq.test(other.key, this.key)) {
			PList<BinomialHeap<K>> kids = new PList<>(other, this.children);
			return new BinomialHeap<>(this.key, this.height + 1, kids);
		} else {
			PList<BinomialHeap<K>> kids = new PList<>(this, other.children);
			return new BinomialHeap<>(other.key, other.height + 1, kids);
		}
	}

Now, when merging two binomial forests, we'll also need a function
that inserts a single tree into a forest.  This is like the algorithm
for long-hand binary addition where each k is a digit.

### Insert Operation

Insert tree

	o
	|
	o

into forest

	B0 B1 B2
	o  o  o
	   |  |\
	   o  o o
		  |
		  o

First we link the two B1's:

	o   o   o
	| ∪ | = |\
	o   o   o o
			|
			o

and we continue with the insert, which forces us to link the new B2
with the other B2 to get B3 (see above), so we get the forest:

	B0 B3


You will implement the `insert` method in lab.

	static <K extends Comparable<K>> PList<BinomialHeap<K>>
	insert(BinomialHeap<K> n, PList<BinomialHeap<K>> ns);

### push

In the `BinomialQueue`, we use `insert` to implement `push` as follows.

	public void push(K key) {
        BinomialHeap<K> heap = new BinomialHeap<>(key, 0, null, lessEq);
        this.forest = insert(heap, this.forest);
	}

### Merge Operation

The merge function takes two binomial forests, ordered by increasing
height, and turns them into a single forest.  This algorithm is
analogous to the merge of merge sort.  

Examples:

	merge [B0 B2] with [B1 B4] = [B0,B1,B2,B4],
	merge [B0 B1] with [B1 B4] = [B0 B2 B4].

You will implement `merge` in lab.

	PList<BinomialHeap<K>>
	merge(PList<BinomialHeap<K>> ns1, PList<BinomialHeap<K>> ns2);

### pop

1. In the forest, find the tree with the largest key
2. Remove that tree, but merge all of its children into the forest.
   This requires reversing the children so that they are
   ordered by increasing height.

Here's the code:

	public K pop() {
		BinomialHeap<K> max = PList.find_max(this.forest);
		this.forest = PList.remove(max, this.forest);
		PList<BinomialHeap<K>> kids = PList.reverse(max.children, null);
		this.forest = merge(this.forest, kids);
		return max.key;
	}

