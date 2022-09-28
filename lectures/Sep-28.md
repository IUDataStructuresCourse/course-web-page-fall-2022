# Assertions, Pre and Post-conditions, Correctness

We've used assertions in our tests, but assertions placed inside our
algorithms and data structure operations can help detect bugs.

Let's see how this works on a familiar example, the binary search
algorithm on arrays.

    int find_first_true_sorted(boolean[] A, int begin, int end) {
        if (begin == end) {
            return end;
        } else {
            int n = end - begin;
            int mid = begin + (n / 2);
            if (A[mid]) {
                return find_first_true_sorted(A, begin, mid);
            } else {
                return find_first_true_sorted(A, mid + 1, end);
            }
        }
    }

While debugging, have you ever wondered whether the bug is in a
particular function or in some code that calls the function?  The
following ideas help to figure out who is at fault.

Definition. A function's **precondition** is a predicate that needs to
be true before the call to the function in order for the function to
operate correctly.

Definition. A function's **postcondition** is a predicate that will be
true after the call to the function.

The pair of precondition and postcondition for a function is a
**contract** between the function and its caller. The caller must make
sure that the precondition is true, and then the function makes sure
that the postcondition is true.

## Precondition for Binary Search

What's the precondition for `find_first_true_sorted`?

Answser: The array A is sorted in the range `[begin,end)`.

We can document and check this precondition by placing an
`assert` statement at the beginning of `find_first_true_sorted`.

    int find_first_true_sorted(boolean[] A, int begin, int end) {
	    assert is_sorted(A, begin, end);
        if (begin == end) {
            ...
		} else {
		    ...
		}
    }

where `is_sorted` is a helper function:

    boolean is_sorted(boolean[] A, int begin, int end) {
        for (int i = begin; i != end; ++i) {
            if (i + 1 != end) {
                if (A[i] == true && A[i + 1] == false)
                    return false;
            }
        }
        return true;
    }

Running the code in an assertion may increase the runtime of your program
considerably, so by default, Java does not check assertions. 
However, you can turn on assertion checking with the flag

    -ea
	
in the VM options of IntelliJ's Run/Debug Configurations.

With the addition of `assert is_sorted(...)`, if you accidentally test
`find_first_true_sorted` on an unsorted array, this `assert` will
signal an error immediately and you'll know to fix your test.

## Postcondition for Binary Search

What's the postcondition for `find_first_true_sorted`?

The return value `i` is either
1) the index of the first `true` in the array, or
2) the index is equal to `end`.

Let's unpack "the first `true`in the array". 
Obviously, we have

    A[i] == true

but is also means that the values in the lower indices are `false`.
So we need the following helper function:

    static boolean all_false(boolean[] A, int begin, int end) {
        for (int i = begin; i != end; ++i) {
            if (A[i] == true)
                return false;
        }
        return true;
    }

Now the postcondition can be written as

    all_false(A, begin, result) && (result == end || A[result] == true)
	
Similar to the precondition, you can add an `assert` statement for the
postcondition, but this time at the end of the function, before every
`return`.

    public static int fft_sorted(boolean[] A, int begin, int end) {
        assert is_sorted(A, begin, end);
        if (begin == end) {
            int result = end;
            assert all_false(A, begin, result)
			       && (result == end || A[result] == true);
            return result;
        } else {
            int n = end - begin;
            int mid = begin + (n / 2);
            if (A[mid]) {
                int result = fft_sorted(A, begin, mid);
                assert all_false(A, begin, result) 
				       && (result == end || A[result] == true);
                return result;
            } else {
                int result = fft_sorted(A, mid + 1, end);
                assert all_false(A, begin, result) 
				       && (result == end || A[result] == true);
                return result;
            }
        }
    }

## Correctness of Binary Search

The `fft_sorted` function is recursive, so it makes calls to
`fft_sorted`, and we need to make sure that the precondition is
satisfied before the calls.

Going a bit futher, to show the correctness of `fft_sorted`, we just
need to make sure that the postcondition is satisfied.

### The first call to `fft_sorted`:

	...
	if (A[mid]) {
		int result = fft_sorted(A, begin, mid);
		...

Is the precondition satisifed, that is, is the range `[begin,mid)`
sorted? Yes, because `mid <= end` and `[begin,end)` is sorted.
We could add an `assert` for this:

	...
	if (A[mid]) {
        assert is_sorted(A, begin, mid);
		int result = fft_sorted(A, begin, mid);
		...

After the call, we know that the postcondition is true.
Let's write that explicitly as an `assert`.

	...
	if (A[mid]) {
        assert is_sorted(A, begin, mid);
		int result = fft_sorted(A, begin, mid);
        assert all_false(A, begin, result) 
		       && (result == mid || A[result] == true);
		...

Notice how `end` became `mid` because we called `fft_sorted` with `mid`
as the argument for the `end` parameter.

Looking at the next couple statements, we have the `assert` for the
postcondition and the return:

        assert all_false(A, begin, result) 
		       && (result == end || A[result] == true);
        return result;

Is the postcondition satisfied?

* `all_false(A, begin, result)`? Yes, from the postcondition of the
  recursive call.
  
* `result == end || A[result] == true`?
  From the postcondition of the recursive call, we know
  `result == mid || A[result] == true`. Let's consider both cases.
  
    * Suppose `result == mid`. We know `A[mid] == true`,
	  so therefore `A[result] == true`.
	  
	* Suppose `A[result] == true`. We're immediately done.

### The second call to `fft_sorted`:

    ...
	} else {
		int result = fft_sorted(A, mid + 1, end);
    ...

Is the precondition satisified? That is, is `[mid+1,end)` sorted?
Yes, because `begin < mid + 1` .
We could add an `assert` for this:

    ...
	} else {
		assert is_sorted(A, mid+1, end);
		int result = fft_sorted(A, mid + 1, end);
    ...

After the call, we know that the postcondition is true.
Let's write that explicitly as an `assert`.

    ...
	} else {
		assert is_sorted(A, mid+1, end);
		int result = fft_sorted(A, mid + 1, end);
        assert all_false(A, mid + 1, result)
               && (result == end || A[result] == true);
    ...

Notice how `begin` became `mid + 1` because we called `fft_sorted` with
`mid + 1` as the argument for the `begin` parameter.

Looking at the next couple statements, we have the `assert` for the
postcondition and the return:

	assert all_false(A, begin, result) 
		   && (result == end || A[result] == true);
	return result;

Is the postcondition satisfied?

* `all_false(A, begin, result)`? 
   We know that `all_false(A, mid + 1, result)`
   and that `A[mid] == false`.
   So `all_false(A, mid, result)`.
   But what about the range `[begin,mid)`?
   We know that the array is sorted, and `A[mid] == false`,
   so the elements in `[begin,mid)` must also be `false`.
* `result == end || A[result] == true`?
   Yes, from the postcondition of the recursive call.

### The base case

Here's the nonrecursive case of `fft_sorted`:

    if (begin == end) {
		int result = end;
		assert all_false(A, begin, result)
				&& (result == end || A[result] == true);
		return result;
    } ...

Is the postcondition satisfied?

* `all_false(A, begin, result)` is vacuously true because the range
   `[begin,begin)` is empty.
   
* `result == end || A[result] == true` is true because `result == end`.

