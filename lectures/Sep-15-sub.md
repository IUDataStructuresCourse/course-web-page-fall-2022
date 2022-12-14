## Review

**Definition** (Big-O) For a given function g, we define O(g) as the
the set of functions that grow similarly or slower than g. More
precisely, f ∈ O(g) iff ∃ k c. ∀ n ≥ k. f(n) ≤ c g(n).

f is asymptotically less-or-equal to g:

     f ≲ g iff f ∈ O(g)


## Example: anagram detection

Two words are **anagrams** of each other if they contain the same
characters, that is, they are a rearrangement of each other.

examples: mary <-> army, silent <-> listen, doctor who <-> torchwood

**Student exercise**: sketch and algorithm for detecting whether two
strings are anagrams of each other (ignoring spaces).








For the following algorithms, what's the time complexity? space
complexity?

* Algorithm 0:
  Generate all permutations of the first word.
  This is O(n!) time and O(n) space.

* Algorithm 1:
  For each character in the first string
  check it off in the second string.
  This is O(n²) time and O(n) space.

* Algorithm 2:
  Sort both strings then 
  compare the sorted strings for equality
  This is O(n log n) time and O(1) space.

* Algorithm 3:
  Count letter occurences in both words and then compare
      the number of occurences of each letter.
  This is O(n) time and O(k) space
  (where k is the number of characters in the alphabet).

## Practice analyzing the time complexity of an algorithm: Insertion Sort

    public static void insertion_sort(int[] A) {
        for (int j = 1; j != A.length; ++j) {  // n iterations,    total: O(n^2)
            int key = A[j];   // O(1)
            int i = j - 1;    // O(1)
            while (i >= 0 && A[i] > key) {    //  n iterations (worst case), total: O(n)
                A[i+1] = A[i]; // O(1)
                i -= 1;        // O(1)        // while loop body: O(1)
            }
			if ... {
			   O(1)
			} else {
			   O(n)
			} // O(n)
            A[i+1] = key; // O(1)
        }                                     // for loop body: O(n)
    }

What is the time complexity of insertion_sort?







Answer:
* inner loop is O(n)
* outer loop is O(n)
* so the entire algorithm is O(n²)

# Time Complexity of Java collection operations

* LinkedList
    * add: O(1)
    * get: O(n)
    * contains: O(n)
    * remove: O(1)

* ArrayList
    * add: O(1)
    * get: O(1)
    * contains: O(n)
    * remove: O(n)

# Common complexity classes:

* O(1)                        constant
* O(log(n))                   logarithmic
* O(n)                        linear
* O(n log(n))
* O(n²), O(n^3), etc.         polynomial
* O(2^n), O(3^n), etc.        exponential
* O(n!)                       factorial


# Lower bounds

**Definition** (Omega) For a given function g(n), we define Ω(g) as
the set of functions that grow at least as fast a g(n):

f ∈ Ω(g) iff ∃ k c, ∀ n ≥ k, 0 ≤ c g(n) ≤ f(n).

**Notation** f ≳ g means f ∈ Ω(g).


# Tight bounds

**Definition** (Theta) For a given function g(n), Θ(g) is the set
of functions that grow at the same rate as g(n):

f ∈ Θ(g) iff ∃ k c₁ c₂, ∀ n ≥ k, 0 ≤ c₁ g(n) ≤ f(n) ≤ c₂ g(n).

We say that g is an *asymptotically tight bound* for each function
in Θ(g).

**Notation** f ≈ g means f ∈ Θ(g)


# Reasoning about bounds.

* Symmetry: f ≈ g iff g ≈ f

* Transpose symmetry: f ≲ g iff g ≳ f

# Relationships between Θ, O, and Ω.

* Θ(g) ⊆ O(g)

* Θ(g) ⊆ Ω(g)

* Θ(g) = Ω(g) ∩ O(g)

# Example: Merge Sort

Divide and conquer!

Split the array in half and sort the two halves.

Merge the two halves.

    private static int[] merge(int[] left, int[] right) {
       int[] A = new int[left.length + right.length];
       int i = 0;
       int j = 0;
       for (int k = 0; k != A.length; ++k) {
           if (i < left.length
               && (j ≥ right.length || left[i] <= right[j])) {
              A[k] = left[i];
              ++i;
           } else if (j < right.length) {
              A[k] = right[j];
              ++j;
           }
       }
       return A;
    }

    public static int[] merge_sort(int[] A) {
       if (A.length > 1) {
           int middle = A.length / 2; // O(1)
           int[] L = merge_sort(Arrays.copyOfRange(A, 0, middle)); // ??
           int[] R = merge_sort(Arrays.copyOfRange(A, middle, A.length)); // ??
           return merge(L, R); // O(n)
       } else {
           return A;
       }
    }

What's the time complexity?

Recursion tree:

               cn                    = cn
             /     \
            /       \
       cn/2        cn/2              = cn
       /  \        /   \
      /    \      /     \
    cn/4  cn/4  cn/4  cn/4           = cn
    ...

Height of the recursion tree is log₂(n).

So the total work is c n log₂(n).

Time complexity is O(n log₂(n)).

