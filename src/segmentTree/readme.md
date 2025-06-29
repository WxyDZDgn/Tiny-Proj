# Segment Tree Implementation

## Overall

This project implements a segment tree data structure that supports efficient range queries (sum over a range) and range
updates (adding a delta to every element in a range). The implementation uses lazy propagation to optimize range
updates.

## Details

The project consists of three classes:

### SegmentTreeTest.java

This class is responsible for automatically testing the segment tree implementation. It generates a random list of
integers and performs a series of random operations (queries and updates). For each query, it compares the result from
the segment tree with a naive computation (using a copied list). If all tests pass, it prints "Test Passed".

Key features:

- Generates a list of random integers (size: `TOTAL_LIST_SIZE`)
- Performs `TOTAL_OPER_COUNT` operations (either query or update at random)
- In a query operation: checks the sum of a random range from the segment tree against the naive sum
- In an update operation: adds a random value to every element in a random range in both structures
- Runs `TOTAL_TEST_COUNT` full test cycles

### SegmentTree.java

This class represents the segment tree. It is built from a list of integers and provides two main methods:

- `query(l, r)`: returns the sum of elements in the range [l, r]
- `updateDelta(l, r, delta)`: adds `delta` to every element in the range [l, r]

The segment tree is built recursively:

1. Root node covers the entire range
2. Each non-leaf node splits its range into two halves
3. Leaf nodes store individual elements

### Node.java

This class represents a node in the segment tree. Each node stores:

- `left` and `right`: range boundaries
- `value`: sum of elements in the range
- `lazy`: pending update value (for lazy propagation)
- `leftNode` and `rightNode`: child nodes

Key operations:

- `updateDelta()`: applies updates using lazy propagation
- `query()`: computes range sums while propagating pending updates

## Lazy Propagation Mechanism

1. When updating a range that fully covers a node:
    - Update is stored in `lazy`
    - Node's value is immediately updated
2. When querying/updating partially overlapping ranges:
    - Pending lazy values are pushed to children
    - Children are processed recursively
    - Node value is recalculated from children

This ensures O(log n) time complexity for both queries and updates.