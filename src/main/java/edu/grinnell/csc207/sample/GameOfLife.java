package edu.grinnell.csc207.sample;

import edu.grinnell.csc207.util.ArrayUtils;
import edu.grinnell.csc207.util.IOUtils;
import edu.grinnell.csc207.util.Matrix;
import edu.grinnell.csc207.util.MatrixV0;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * An implementation of John Conway's Game of Life.
 *
 * @author
 */
public class GameOfLife {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default width of the board.
   */
  static final int DEFAULT_WIDTH = 20;

  /**
   * The default number of rows (height) of the board.
   */
  static final int DEFAULT_HEIGHT = 10;

  // +----------------+----------------------------------------------
  // | Helper methods |
  // +----------------+

  /**
   * Print the instructions for Conway's Game of Life.
   *
   * @param pen
   *  The PrintWriter used to print the instructions.
   */
  public static void printInstructions(PrintWriter pen) {
    pen.println("""
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
        """);
  } // printInstructions(PrintWriter)

  /**
   * Print the current state of the game board.
   *
   * @param pen
   *   The PrintWriter used for output.
   * @param board
   *   The current game board.
   */
  static void printBoard(PrintWriter pen, Matrix<String> board) {
    pen.println();
    for (int row = 0; row < board.height(); row++) {
      for (int col = 0; col < board.width(); col++) {
        pen.print(board.get(row, col) + " ");
      }
      pen.println();
    }
    pen.println();
  } // printBoard

  /**
   * Count the number of alive neighbors for a given cell.
   *
   * @param board
   *   The game board.
   * @param row
   *   The row of the cell.
   * @param col
   *   The column of the cell.
   * @return The number of alive neighbors.
   */
  static int countAliveNeighbors(Matrix<String> board, int row, int col) {
    int count = 0;
    for (int dr = -1; dr <= 1; dr++) {
      for (int dc = -1; dc <= 1; dc++) {
        if (dr == 0 && dc == 0) continue; // Skip the cell itself
        int r = row + dr;
        int c = col + dc;
        try {
          if ("O".equals(board.get(r, c))) {
            count++;
          }
        } catch (IndexOutOfBoundsException e) {
          // Cells outside the board are considered dead; do nothing
        }
      }
    }
    return count;
  } // countAliveNeighbors

  /**
   * Compute the next generation of the game board based on Conway's rules.
   *
   * @param board
   *   The current game board.
   * @return The next generation game board.
   */
  static Matrix<String> nextGeneration(Matrix<String> board) {
    Matrix<String> newBoard = new MatrixV0<>(board.width(), board.height(), " ");
    for (int row = 0; row < board.height(); row++) {
      for (int col = 0; col < board.width(); col++) {
        int aliveNeighbors = countAliveNeighbors(board, row, col);
        String currentCell = board.get(row, col);
        if ("O".equals(currentCell)) {
          if (aliveNeighbors < 2 || aliveNeighbors > 3) {
            newBoard.set(row, col, " "); // Cell dies
          } else {
            newBoard.set(row, col, "O"); // Cell lives
          }
        } else {
          if (aliveNeighbors == 3) {
            newBoard.set(row, col, "O"); // Cell becomes alive
          } else {
            newBoard.set(row, col, " "); // Cell remains dead
          }
        }
      }
    }
    return newBoard;
  } // nextGeneration

  /**
   * Set up a new game board with random alive and dead cells.
   *
   * @param width
   *   The width of the board.
   * @param height
   *   The height of the board.
   * @param seed
   *   The seed for the random number generator.
   *
   * @return the newly created board
   */
  static Matrix<String> setupBoard(int width, int height, int seed) {
    Random random = new Random(seed);
    Matrix<String> board = new MatrixV0<>(width, height, " ");
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (random.nextDouble() < 0.3) { // 30% chance to be alive
          board.set(row, col, "O");
        } else {
          board.set(row, col, " ");
        }
      }
    }
    return board;
  } // setupBoard

  /**
   * Count the total number of alive cells on the board.
   *
   * @param board
   *   The game board.
   * @return The total number of alive cells.
   */
  static int countTotalAlive(Matrix<String> board) {
    int total = 0;
    for (int row = 0; row < board.height(); row++) {
      for (int col = 0; col < board.width(); col++) {
        if ("O".equals(board.get(row, col))) {
          total++;
        }
      }
    }
    return total;
  } // countTotalAlive

  // +------+--------------------------------------------------------
  // | Main |
  // +------+

  /**
   * Run Conway's Game of Life.
   *
   * @param args
   *   Command-line arguments.
   */
  public static void main(String[] args) throws IOException {
    PrintWriter pen = new PrintWriter(System.out, true);
    BufferedReader eyes = new BufferedReader(new InputStreamReader(System.in));

    int width = DEFAULT_WIDTH;
    int height = DEFAULT_HEIGHT;
    int seed = new Random().nextInt();

    // Process the command line
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-w":
          try {
            width = Integer.parseInt(args[++i]);
          } catch (Exception e) {
            System.err.printf("Invalid width: %s (not an integer)\n", args[i]);
            return;
          }
          if (width < 4) {
            System.err.printf("Invalid width: %s (less than 4)\n", width);
            return;
          }
          break;

        case "-h":
          try {
            height = Integer.parseInt(args[++i]);
          } catch (Exception e) {
            System.err.printf("Invalid height: %s (not an integer)\n", args[i]);
            return;
          }
          if (height < 4) {
            System.err.printf("Invalid height: %s (less than 4)\n", height);
            return;
          }
          break;

        case "-s":
          try {
            seed = Integer.parseInt(args[++i]);
          } catch (Exception e) {
            System.err.printf("Invalid seed number: %s\n", args[i]);
            return;
          }
          break;

        default:
          System.err.printf("Invalid command-line flag: %s\n", args[i]);
          return;
      }
    }

    // Get started
    printInstructions(pen);
    pen.print("Hit return to start the game");
    pen.flush();
    eyes.readLine();

    // Set up the board
    Matrix<String> board = setupBoard(width, height, seed);

    // Run the game
    pen.println("Game setup with seed " + seed);
    pen.println();

    String[] commands = new String[] {"NEXT", "QUIT"};
    boolean done = false;
    while (!done) {
      printBoard(pen, board);
      pen.println("Total Alive Cells: " + countTotalAlive(board));
      String command = IOUtils.readCommand(pen, eyes, "Action (NEXT/QUIT): ", commands);
      switch (command.toUpperCase()) {
        case "NEXT":
          board = nextGeneration(board);
          break;

        case "QUIT":
          done = true;
          break;

        default:
          pen.printf("Unexpected command: '%s'. Please try again.\n", command);
          break;
      }
    }

    // And we're done
    pen.println("Thank you for playing Conway's Game of Life!");
    pen.close();
  } // main(String[])
} // class GameOfLife
