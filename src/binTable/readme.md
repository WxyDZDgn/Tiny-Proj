# Bin Table

## Overall

This is a project, which can summon _Bin Table_. The table is two-dimensional. Each line has some numbers. One person
keeps a number in mind and checks each line that whether it contains the number or not. The other person can obtain the
number promptly via the lines checked instead of seeking carefully.

The numbers in the table are placed optimally. Formally, if a number's _i_ bit is _1_, then the _i_-th line contains it.
Similarly, if the _i_-th line in the table involves a number, then the number's _i_ bit is _1_.

## Details

Main class can be run, which outputs _Bin Table_ from number 1 to number _MAX_VALUE_.

The output with _MAX_VALIE = 7_ looks like this:

> [4, 5, 6, 7]
>
> [2, 3, 6, 7]
>
> [1, 3, 5, 7]

For example, if one is thinking number _3_, then he clicks line _2, 3_, as they all contain the number he thought.

- Line 1 is not checked;
- Line 2 is checked;
- Line 3 is checked.

Then we can use _011_ to represent number _3_, which is also its binary notation.
