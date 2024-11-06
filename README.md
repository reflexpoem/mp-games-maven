# Abstract games

A mini-project for Grinnell's CSC-207.

Authors:

* Sunjae Kim
* Yash Malik
* Samuel A. Rebelsky (Starter code)

Instructions:

* **Please add instructions for running the game**

Welcome to Conway's Game of Life.

Command-line arguments:

* -w width - set the width of the board
* -h height - set the height of the board
* -s seed - choose the seed for the random game setup (useful if
  you want to play the same setup multiple times).

The game board is a grid of alive (O) and dead ( ) cells.

Rules of the Game of Life:

1. Any live cell with fewer than two live neighbors dies (underpopulation).
2. Any live cell with two or three live neighbors lives on to the next generation.
3. Any live cell with more than three live neighbors dies (overpopulation).
4. Any dead cell with exactly three live neighbors becomes a live cell (reproduction).

Commands during the game:

* NEXT - Proceed to the next generation.
* QUIT - Exit the game.


Acknowledgements:

We consulted Wikipedia for the rules for John Conway's masterpiece zero-player game.
Source:

This code may be found at <https://github.com/reflexpoem/mp-games-maven.git>. It is based on code found at <https://github.com/Grinnell-CSC207/mp-games-maven>.
