# Lecture: Code Review & Algorithm Analysis

## Code Review of the Search Lab


### Part 1: `find_first_true`

Student solutions.

#### Solution #1

    public static int find_first_true(boolean[] A, int begin, int end){
        if (A.length >= 1) {
            for (int i = begin; i != end; i++) {
                if (A[i] == true) {
                    return i;
                }
            }
        }
        return end;
    }

Notes:

#### Solution #2

    public static int find_first_true(boolean[] A, int begin) {
		if(A.length == begin || A[begin]) {
			return 0;
		}
		return 1 + find_first_true(A, begin + 1);
	}
	public static int find_first_true(boolean[] A, int begin, int end) {
		return begin + find_first_true(Arrays.copyOf(A, end), begin);		
	}

Notes:

#### Solution #3

    public static int find_first_true(boolean[] A, int begin, int end) {
        for(int i=begin;i!=end;i++) {
            if(A[i]) {
                return i;
            }
        }
        return end;
    }

Notes:

### Part 2: `find_first_equal`

Student solutions.

#### Solution #1

    public static int find_first_equal(int[] A, int x) {
        for (int i = 0; i < A.length; i++) {
            if(A[i] == x){
                return i;
            }
        }
        return A.length;
    }

#### Solution #2

    public static int find_first_equal(int[] A, int x) {
        boolean[] newArr = new boolean[A.length];
        for (int i = 0; i < A.length; i++) {
            if (A[i] == x) {
                newArr[i] = true;
            }
            else {
                newArr[i] = false;
            }
        }
        return find_first_true(newArr, 0, A.length);
    }

#### Solution #3

    public static int find_first_equal(int[] A, int x) {
        boolean[] B = new boolean[A.length];
        for(int i=0;i!=A.length;i++) {
            B[i]=(x==A[i]);
        }
        return find_first_true(B,0, B.length);
    }

### Part 3: `first_first_true_sorted`

Student solutions.

#### Solution #1

    public static int find_first_true_sorted(boolean[] A, int begin, int end) {
        if (end > 1) {
            int middle = (int)end/2;
            if (A[middle] == true){
                while(A[middle] != false){
                    middle--;
                }
                return middle+1;
            }
            else{
                while(A[middle] == false){
                    middle++;
                }
            }
            return middle;
        }
        else if (end == 1) 
		    return 0;
        else {
            return end;
		}
    }

#### Solution #2

    public static int find_first_true_sorted(boolean[] A, int begin, int end) {
        int middle = end - begin;
        if (begin == 0) {
            middle /= 2;
        }
        if(middle < end && middle > begin) {
            if(A[middle]) {
                end = middle;
                return (find_first_true_sorted(A, begin, end));
            } else {
                begin = middle;
                return (find_first_true_sorted(A, begin, end));
            }
        } else {
            if (end == 1 && begin == 0) {
                if(!A[0]) {
                    return end;
                } else {
                    return begin;
                }
            } else {
                return end;
            }
        }
    }

#### Solution #3

    public static int find_first_true_sorted(boolean[] A, int begin, int end) {
        if (A.length == 0) 
		    return 0;
        int mid;
        while(end - begin > 1) {
            mid = (begin + end)/2;
            if(A[mid]) {
                end = mid;
            } else {
                begin = mid;
            }
        }
        if (A[begin]) 
		    return begin;
        else
		    return end;
    }


#### Solution #4

    public static int find_first_true_sorted(boolean[] A, int begin, int end) {
		if(A.length == 0) {
			return 0;
		}
		if(end-begin == 1) {
			if(A[begin]) {
				return begin;
			} else {
				return begin + 1;
			}
		}
		int middle = (int) Math.floor((end-begin)/2) + begin;
		if(A[middle]) {
			return find_first_true_sorted(A, begin, middle);
		} else {
			return find_first_true_sorted(A, middle, end);
		}
	}


## Time Complexity

We are interested in the running time as the inputs grow large.

We care about the growth rate of an algorithm's run-time more than
it's value at a particular point.
	  
We focus on the worst-cast run-time of algorithms because we want to
offer guarantees to the user of an algorithm
	  
Our goal is to classify functions that grow at a similar rate,
ignoring details that don't matter.

Functions to think about: n, 10n, lg n, n lg n, 10 n², n² + n

## Asymptotic Upper Bound

**Definition** (Big-O) For a given function g, we define **O(g)** as the
the set of functions that grow similarly or slower than g. More
precisely, f ∈ O(g) iff ∃ k c. ∀ n ≥ k. f(n) ≤ c g(n).

We say that g(n) is an *asymptotic upper bound* of all the functions
in the set O(g).

**Notation** We write f ≲ g iff f ∈ O(g), and say that f is
asymptotically less-or-equal to g.

Demonstrate the main idea and the role of k and c.

https://www.desmos.com/calculator

e.g. 4x versus x²

1. Is 4x ≲ x²?
2. Is x² ≲ 4x?
   
e.g. x + 10 versus x

1. Is x + 10 ≲ x?
2. Is x ≲ x + 10?

**Example** Let's show that (n² + n + 10) ∈ O(n²).
So we need to find a constant c such that

n² + n + 10 ≤ c n²   when n is large.

We divide both sides by n².

1 + 1/n + 10/n² ≤ c

When n is large, the terms 1/n and 10/n² get very small, so we need to
choose something a bit bigger than 1, so we choose c = 2.

Next we need to find a constant k such that

n² + n + 10 ≤ 2 n²   when n is greater than k.

We compute a handful of values of these functions.

| n   | n² + n + 10  | 2n²  |
| --- | ------------ | ---- |
| 1   | 12           |  2   |
| 2   | 16           |  8   |
| 3   | 22           | 18   |
| 4   | 30           | 32   |
| 5   | 40           | 50   |
| 6   | 52           | 72   |

We see that from n=4 and onwards, 2n² is greater than n² + n + 10, so
we choose k = 4. We have some emprical evidence that we've made the
correct choices, but to know for sure, we prove the following theorem.

**Theorem** ∀ n ≥ 4, n² + n + 10 ≤ 2 n².

Proof. We proceed by induction on n.
* Base case (n = 0) The theorem is trivially true
    because it's not true that 0 ≥ 4.
* Induction case. Let n be any integer such that n ≥ 4
    and n² + n + 10 ≤ 2 n² (IH). We need to show that

		(n+1)² + (n+1) + 10 ≤ 2 (n+1)²

	which we can rearrange to the following, separating out parts that
	match the induction hypothesis (IH) from the rest.

		(n² + n + 10) + (2n + 2) ≤ 2n² + (4n + 2)

	Thanks to the IH, it suffices to show that 

		2n + 2 ≤ 4n + 2

	which is true because n ≥ 4 ≥ 0.


## Student exercise

Show that 2 log n ∈ O(n / 2). 

Hint: experiment with values of n that are powers of 2 because it is
easy to take the log of them:

	log(2) = 1
	log(4) = 2
	log(8) = 3
	...

## Reasoning about asymptotic upper bounds

* Polynomials:
    If f(n) = cᵢ nⁱ + ... + c₁ n¹ + c₀, 
	then f ∈ O(nⁱ).
* Addition:
    If f₁ ∈ O(g) and f₂ ∈ O(g),
    then f₁ + f₂ ∈ O(g).
* Multiplication:
    If f₁ ∈ O(g₁) and f₂ ∈ O(g₂),
    then f₁ * f₂ ∈ O(g₁*g₂).
* Reflexivity:
    f ∈ O(f)
* Transitivity:
    f ∈ O(g) and g ∈ O(h) implies f ∈ O(h)

* Example: anagram detection

    Two words are **anagrams** of each other if they contain the same
    characters, that is, they are a rearrangement of each other.

    examples: mary <-> army, silent <-> listen, doctor who <-> torchwood

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
      This is O(n lg n) time and O(1) space.

    * Algorithm 3:
      Count letter occurences in both words and then compare
          the number of occurences of each letter.
      This is O(n) time and O(k) space
      (where k is the number of characters in the alphabet).
