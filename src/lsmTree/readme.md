# LSM Tree

## Overall

This is a project for LSM Tree Structure and its tests.

Note that LSM Tree is a kind of data structure, widely used in NoSQL Databases.

## Details

LSM Tree can be separated to 2 parts, stored in memory and disk. The whole LSM Tree is a kind of forest with a single
tree, the level-0, stored in memory and several trees, from level-1 to level-n, stored in disk.

In the Main class, the example makes some tests for LSM Tree, including insert, delete, update and select.

Memory class is used for level-0 tree's simulation.

Disk class is used for the simulation of trees in disk.

Node class is a structure for SQL item simulation.