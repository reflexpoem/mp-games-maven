package edu.grinnell.csc207.util;

/**
 * @author Yash Malik
 *     <p>This class holds the code that checks for an IndexOutOfBound exception.
 */
public class CheckBounds {

  /**
   * Checks if the given row and column are within bounds.
   *
   * @param row The row index to check.
   * @param col The column index to check.
   * @param height The height (number of rows) of the matrix.
   * @param width The width (number of columns) of the matrix.
   * @throws IndexOutOfBoundsException If the row or column are out of bounds.
   */
  public static void indexCheck(int row, int col, int height, int width) {
    if (row < 0 || row >= height || col < 0 || col >= width) {
      throw new IndexOutOfBoundsException("Row or column out of bounds.");
    } // if-loop
  } // checkBounds method
} // checkBounds class.
