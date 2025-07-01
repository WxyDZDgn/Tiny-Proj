# Area of Union of Rectangles

## Overall

This project implements a solution to compute the area of the union of n non-overlapping axis-aligned rectangles. The
algorithm uses a sweep line approach combined with a segment tree to efficiently calculate the total covered area in O(n
log n) time complexity.

## Details

The project consists of four classes:

### AreaCoverageTest.java

This class contains the main logic for parsing input, processing rectangle data, and calculating the union area. It
implements:

1. **Test Harness**: Predefined test cases with expected results
2. **Event Processing**:
    - Creates "enter" (+1) and "exit" (-1) events for each rectangle's edges
    - Collects and sorts distinct y-coordinates for coordinate compression
3. **Sweep Line Algorithm**:
    - Processes events sorted by x-coordinate
    - Calculates area between events as: `covered_y_length × (current_x - previous_x)`
    - Updates segment tree with rectangle coverage changes

### SegmentTree.java

Implements a segment tree specialized for interval coverage tracking:

- **Build**: Constructs tree from sorted y-coordinates
- **Update**: Modifies coverage counts for y-intervals
- **Query**: Returns total covered length on y-axis
- **PushUp**: Propagates coverage information upward

Key features:

- Handles interval additions/removals efficiently
- Maintains total covered length at root node
- Uses coordinate-compressed y-values

### Node.java

Represents a segment tree node with:

- `left`, `right`: Interval boundaries
- `cover`: Number of rectangles covering this interval
- `len`: Covered length within interval
- `isLeaf`: Flag for terminal nodes

### Tuple.java

Record class representing rectangle edge events:

- `x`: Event x-coordinate (rectangle edge)
- `y1`, `y2`: Vertical interval
- `k`: Event type (+1 for enter, -1 for exit)

## Algorithm Overview

1. **Event Generation**:
    - Create enter/exit events for each rectangle's left/right edges
    - Collect all unique y-coordinates

2. **Coordinate Compression**:
    - Sort distinct y-values
    - Map y-coordinates to compressed indices

3. **Sweep Line Processing**:
    - Sort events by x-coordinate
    - Initialize segment tree with compressed y-intervals

4. **Area Calculation**:
    - Process events in x-order
    - For each event:
        1. Calculate area since last event
        2. Update segment tree coverage
    - Return accumulated area

The segment tree efficiently tracks the currently covered y-intervals during the sweep line pass, enabling O(log n)
updates and O(1) queries for the total covered length.