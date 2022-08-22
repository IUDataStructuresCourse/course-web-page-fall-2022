# Lab: Search

This lab is about implementing functions that search for things
in arrays. Create a file named `Search.java` containing
a public class, also named `Search`.

When your lab is complete, submit your `Search.java` file to the
autograder
[Search]([submit](https://autograder.luddy.indiana.edu/web/project/461))
project.

## Linear Search on an Array of Booleans

The most basic but surprisingly useful search function involves an
array `A` of boolean values (`true` or `false`). The function finds
the position of the first `true`, that is, find the smallest index `i`
such that `A[i]` is `true`.  If there are no `true` elements in the
subarray, then `find_first_true` must return the `end` position of the
subarray.  For example, if the input array `A` is

    {false, false, true, false, true}
    
then the result should be 2 because `A[2] == true` and there are no
`true` elements at lower indices (`A[0]` and `A[1]` are both `false`).

Add the following method to the `Search` class and fill-in the
implementation.  Restrict your search to the region within `A` that
starts at the `begin` index and finishes one element before the `end`
index. (This is called a half-open interval.)

    public static int find_first_true(boolean[] A, int begin, int end) {
        ...
    }

The time it takes for your algorithm to run should be proportional to the
length of the array `A`.

Here is another example. Suppose `A` is the array

    {true, false, true, false, true}

and we search in the half-open interval `[1,3)`. The answer should be `2`.

    find_first_true(A, 1, 3) == 2

Test your implementation of `find_first_true` using JUnit by creating
a test class named `SearchTest` in another file named
`SearchTest.java`. Create several methods in the `SearchTest` class
and mark them with the `@Test` attribute. Inside the test methods, use
JUnit's
[Assertions](https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html)
such as `assertEquals` to check for the correct answer.

	import org.junit.jupiter.api.Test;
	import org.junit.jupiter.api.BeforeEach;
	import static org.junit.jupiter.api.Assertions.*;

	public class SearchTest {
	
		@Test
		public void test_find_first_true() {
			boolean[] A = {true, false, true, false, true};
            assertEquals(find_first_true(A, begin, end), 2);
		}
	}



## Linear Search on an Array of Integers

Another search function involves an array of integers, with the goal
of finding the position of the first element that is equal to the `x` parameter.
If there are no elements equal to `x`, return the length of the array.
Implement the following method in the `Search` class.

    public static int find_first_equal(int[] A, int x) {
        ...
    }

As an example, suppose `A` is the array

    {32, 11, 4, 5, 99, 5, 32, 75}
    
then the result of search for `5` should be `3`.

    find_first_equal(A, 5) == 3

Instead of writing the code to solve the problem directly, come up with a way to 
use the `find_first_true` function to accomplish this search.

The time it takes for your algorithm to run should be proportional to the
length of the array `A`.

## Binary Search on an Array of Booleans

Getting back to searching an array of Booleans, suppose that all of the
`false` elements in the array come before all of the `true` elements.
For example, suppose `A` is the array

    {false, false, true, true, true, true, true}

Our job is still to find the position of the first `true` element,
in this case `2`. Come up with an algorithm that is more efficient
than the one you used for `find_first_equal`. That is, your algorithm
should run in time proportional to the logarithm of the length of the array.
Hint: look at the element in the middle and then restrict your
search to the right or left depending on its value. Implement
your algorithm in the following method of the `Search` class.
Again, restrict your search to the half-open interval `[begin,end)`.

    public static int find_first_true_sorted(boolean[] A, int begin, int end) {
        ...
    }
