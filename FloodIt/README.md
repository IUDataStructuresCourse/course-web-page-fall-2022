Project: Flood It
-----------------

"Flood It" is a tile coloring game played on a square board of colored
tiles. At each move, the player selects a color (by clicking on one of
the tiles) and the tile in the upper left corner, as well as all
connected neighboring tiles of the same color, are changed to the
selected color. The objective of the game is end up with all the tiles
on the board being the same color while minimizing the number of
moves.

If you haven't played "Flood It" before, please spend some time
playing the game so that you can develop an intuition for how it works
and formulate strategies for game play before trying to implement
anything. There is an online version at the following URL:

http://unixpapa.com/floodit


Your Task
---------

We have written most of the game but we need your help in finishing
it. We would like you to write the `flood` function in the
`Flood` class. The `flood` function takes four parameters:

* `color` - the color that the play just selected.
   It is an instance of the `WaterColor` enum.

* `flooded_list` - a `LinkedList` of coordinates for all of the tiles
   in the current flooded region.

* `tiles` - a two dimensional array of tiles (instances of `Tile`),
   so `tiles[y][x]` accesses the tile at the specified x and y coordinate.
   The x coordinate increase as you go to the
   right and the y coordinate increase as you go down.
   The purpose of this parameter is to give you access to the color
   of a tile, which can be obtained using the `getColor()` method.

* `board_size`: the number of rows of tiles in the board.  (The number
   of columns is the same as the number of rows, so the board is
   square.)

You are to add the new flooded tiles to the `flooded_list`. The
`flooded_list` initially contains the tile at the upper left corner.
You are not responsible for changing the color of any tiles.

We say that a tile neighbors another tile if it is directly above,
below, left, or right, that is, sharing a side with the other one. The
`Coord` class contains some helpful functions: the functions named
`up`, `down`, `left`, and `right` compute the coordinates of the
adjacent tiles; the function `onBoard` tells you whether a coordinate
is on the board, and `neightbors` returns a list of neighboring
coordinates.

An X-colored region is a set of tiles defined as follows:
* A tile of color X is an X-colored region.
* If tile $T$ is color X and adjacent to
  a tile in an X-colored region R, then the union of T and R
   is an X-colored region.

Given a `flooded_list` whose tiles are of color X, the `flood`
function should add every X-colored region to the `flooded_list`,
provided the region contains a tile that is adjacent to a tile in the
`flooded_list`.


Analysis
--------

After implementing and debugging your `flood` function, run the game
in batch timing mode by adding `timing` to the program arguments in
the run configuration window. This will display a graph of the
execution time (along the y-axis) versus the number of tiles on the board (along
the x-axis) and it will save the graph to png file named `result.png`.  Look at the
graph. What function (roughly) fits that graph? (Hint: possibilities
to think about are f(n) = n, f(n) = n^2, f(n) = n log(n).)  What
is the time complexity of your `flood` function based on analyzing the code
in your `flood` function?  Does your analysis match up with what you
see in the graph? If not, double check your analysis.
Is the time complexity of your `flood` function the best it can be
or can you do better?

Try to improve the time complexity of your flood function.
Write new flood functions `flood1`, `flood2`, etc. whose
execution time does not grow so quickly as the number of tiles
increases. When you re-run in batch mode, the timings for these
alternate functions will show up as gray lines. Swap the names of your
versions of `flood` so that your best version is named `flood` (so it
will be the version used interactively).  Re-run the timing, producing
a graph with the best version highlighted in red. What is the
time complexity of your new version?

If your code is so slow that no graph is produced when running in
batch mode, try temporarily reducing the value of
Constants.MAX_BOARD_SIZE_FOR_AUTOPLAY. For each board size, the game
is repeated 5 times to reduce the effect of noise. Turning down the
Constants.NUM_GAMES_TO_AUTOPLAY parameter may save you time during
development.


Submission
----------

Make sure that your project compiles and runs correctly
and that your answers regarding the time complexity of `flood`
and `flood1` are written in a comment above those functions.

Submit the files `Flood.java`, `results.png`, and `README.md` (with answers
to the questions above) to the autograder.

    https://autograder.luddy.indiana.edu/web/project/456


