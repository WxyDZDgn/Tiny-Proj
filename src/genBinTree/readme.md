# Generate Binary Tree

## Overall

This is a project to construct a binary tree randomly, including its structure and values.

## Details

In the Main class, the example tries to generate a binary tree with a specific number of nodes.
Then it prints the binary tree in pre-order, mid-order and post-order separately.

BinaryTree class is for the generative binary tree. In addition, it can show the three output format by implement three
different methods.

Node class is for the nodes in the binary tree.

As for the procedure, firstly, shuffle the list like:

> [7, 1, 4, 2, 8, 5, 9, 3, 6, 0]

Then, randomly choose an index, and separate it into three parts:

> [7, 1, 4], 2, [8, 5, 9, 3, 6, 0]

The left part list is for the left node to recurrence the method mentioned, and the right part list is for the right.
The middle of which is for the current node's value.

By the way, if one part list is actually empty, then just fill it with null:

> [], 7, [1, 4]

As is shown, this left part list doesn't have one at all. So this node, valued 7, has no left child (node) but right.

After recursion, the tree is constructed. Then it can be printed by pre-order, mid-order and post-order.

For example, there are a pre-ordered list, a mid-ordered list and a post-ordered list for the same tree, can you
determine the original structure of the tree?

> pre-ordered list: [9, 8, 0, 3, 6, 4, 2, 7, 1, 5]
>
> mid-ordered list: [0, 8, 3, 4, 6, 2, 9, 5, 1, 7]
>
> post-ordered list: [0, 4, 2, 6, 3, 8, 5, 1, 7, 9]
