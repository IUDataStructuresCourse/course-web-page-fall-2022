# Project - Routing Wires on a Chip

## Project Description

In this project you will implement a Java program that places wires on
a computer chip. For purposes of this project, a computer chip will be
abstractly represented as a grid of vertices, where each vertex is
connected to the four neighboring vertices (in the directions north,
south, east, and west). To complicate matters, parts of the chip are
already allocated for other uses and may not be used for running
wires. These already-in-use parts are called obstacles. Each obstacle
is a rectangular region of the grid. You will be given a list of pairs
of coordinates and your task is to connect each pair with a wire.  A
coordinate is a pair of integers, with the first being the horizontal
distance from the left edge of the grid, and the second being the
vertical distance from the top edge of the grid.  A wire is a list of
grid points. Wires may not cross one another.  In addition to
connecting all the pairs, your goal is to minimize the aggregate
lengths of all the wires and to minimize the execution time of your
program.

The format of the input file is described as follows.  The first line
is the height of the grid, given as an integer.  The second line is
the width of the grid, also given as an integer.  The third line is
the number of obstacles o.  The next o lines are the obstacles.  Each
line has four integers, separated by spaces. The first two integers
give the upper left coordinate of the obstacle and the second two
integers give the lower right coordinate of the obstacle.  After the
obstacles, there is a line that gives the number of pairs that need to
be connected. The remaining lines in the file are pairs of
space-separated coordinates, where each coordinate is a pair of
space-separated integers.

We have given you the code that reads the input file and creates the grid
with obstacles laid out and the source and destination points specified.
The obstacles are marked with grid cells with value -1. The start
and end points of a wire/path are marked with a number assigned to
that path. All the other cells contain the value 0.

Your function `findPaths` in the `Routing` class should use this grid
to connect a source and a destination with a path, avoiding points
that lie on an obstacle. You should mark the grid once a path has been
found for a pair of points, thus preventing overlapping of paths. That
is, mark all the points in a path with the same number as the start
and end point. The autograder checks these conditions to verify the
correctness of your solution.

Note that a path can have the same source and destination points.

## Your Task

We need you to implement the `findPaths` method.  It takes the board
and the points to be connected as arguments and returns a list of
paths. The board represents the entire chip. Each path represents the
wire used to connect components represented by points. Each path
connects a pair of points in the points array; avoiding obstacles and
other paths while minimizing the total path length required to connect
all points. If two points cannot be connected, then the path should be
null.

Think of a simple way to minimize the path length. You can use the
grid to mark the points that lie on a path.  You might want to use
auxiliary data structures to keep track of the intermediate points
that lie on the path.

We recommend that you start by implementing an algorithm that uses
breadth-first search (BFS), which will solve most of the tests of the
autograder, but not all. Once that is complete, you can experiment
with improvements to BFS or alternative algorithms.

For this assignment we are asking you to describe your solution in
this `README.md` file, and a significant part of your grade 
is based on how good your description is. Things you should include in
the description are:

    1. overview of the algorithm
    2. one or more examples of applying your algorithm to interesting boards
    3. evaluation of your algorithm with respect to finding and minimizing wire layouts
    4. evaluation of the time complexity and wall-clock time of your algorithm.

In this `README.md` file, justify why your improvements are worth the
final points! (Solving all the tests is one good justification,
but you may use other justifications.)

The starter code for this project is in the following files:

* [`Coord.java`](./Coord.java)
* [`Endpoints.java`](./Endpoints.java)
* [`Wire.java`](./Wire.java)
* [`Board.java`](./Board.java)
* [`Routing.java`](./Routing.java)
* [`Utilities.java`](./Utilities.java)


