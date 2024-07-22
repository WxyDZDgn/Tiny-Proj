# Mitchell's Best-Candidate Algorithm

## Overall

This is a project to generate points via Mitchell's Best-Candidate Algorithm.

## Details

In the Main class, the example tries to generate points, using alternative attributes.

It defines a Point's object, which represents a point in rectangular coordinate system.

In the Node class, it defines Nodes, which form the whole Quad-Tree. Meanwhile, there are two categories for Node. One
is just node, and the other is leaf. The former has four children, separating an area into for smaller areas, all of
which are also nodes. As for a leaf-node, it only has one area with some points on it.

For the QuadTree part, it defines a tree with maximum four children in each node, separating the whole plane into tiny
parts. This data structure is used for Mitchell's Best-Candidate Algorithm to make it faster.

## Procedure

Initially, there is one point laying on the plane. Then we need to add n-phases of points into the plane, and there is
exactly one point added every phase.

1. Assuming there are m new points in current phase, for each new point, we call **the value of a point** is the minimum
   distance from it to all the **existing** points on the plane.

2. Then, we find the new point, whose **value** is maximum of all the new points in current phase, and finally add that
   new
   point to the plane.

We repeat the steps above until all the phases have finished. Then the points left on the plane is our result for
Mitchell's Best-Candidate Algorithm.

## Complexity

In original algorithm, every points in every phases need calculation with all the points on the plane, and the
complexity is O((mn)^2).

With QuadTree's help, we can simply optimize it to O(mn log(mn)).
