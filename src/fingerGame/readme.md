# Finger Game

## Overall

This is a two-players game, who take out two hands with number _1_ signed.
Two players make an operation in turn, and the one who makes his two hands _escape_, then the player wins.

A hand is figured as _escape_ iff it signs number _0_.

For one operation, the player:

1. choose a hand that has not been escaped;
2. touch the other's hand, which is also not escaped;
3. if your hand is signed as number _x_, and the other is number _y_, then calculate number _z_ = _x_ + _y_ mod _10_;
4. sign your selected hand with number _z_.

## Details

Two classes have main methods that can be run:

### class OutputSimulationSteps

By running the method in it, you can receive the operations, the two players has done during the game, in the output.

### class OutputSolutionTable

After running main method, you'll get an HTML file, where the status about whether the first to go can win or not is
shown.
