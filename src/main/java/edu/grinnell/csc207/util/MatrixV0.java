package edu.grinnell.csc207.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * An implementation of two-dimensional matrices.
 *
 * @author Yash Malik
 * @author Sunjae Kim
 * @author Samuel A. Rebelsky
 * @param <T> The type of values stored in the matrix.
 */
public class MatrixV0<T> implements Matrix<T> {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /** Holds the actual matrix. */
  private ArrayList<ArrayList<T>> matrix;

  /** Width of the matrix. */
  private int width;

  /** Height of the matrix. */
  private int height;

  /** Default Value to populate matrix with. */
  private T defaultValue;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new matrix of the specified width and height with the given value as the default.
   *
   * @param width The width of the matrix.
   * @param height The height of the matrix.
   * @param def The default value, used to fill all the cells.
   * @throws NegativeArraySizeException If either the width or height are negative.
   */
  public MatrixV0(int width, int height, T def) {
    if (width < 0 || height < 0) {
      throw new NegativeArraySizeException("Width and height must be non-negative.");
    } // check for valid array size.
    this.width = width;
    this.height = height;
    this.defaultValue = def;
    this.matrix = new ArrayList<>(height);

    for (int i = 0; i < height; i++) {
      ArrayList<T> row = new ArrayList<>(width);
      for (int j = 0; j < width; j++) {
        row.add(def);
      } // for-loop
      this.matrix.add(row);
    } // for-loop
  } // MatrixV0(int, int, T)

  /**
   * Create a new matrix of the specified width and height with null as the default value.
   *
   * @param width The width of the matrix.
   * @param height The height of the matrix.
   * @throws NegativeArraySizeException If either the width or height are negative.
   */
  public MatrixV0(int width, int height) {
    this(width, height, null);
  } // MatrixV0

  // +--------------+------------------------------------------------
  // | Core methods |
  // +--------------+

  /**
   * Get the element at the given row and column.
   *
   * @param row The row of the element.
   * @param col The column of the element.
   * @return the value at the specified location.
   * @throws IndexOutOfBoundsException If either the row or column is out of reasonable bounds.
   */
  public T get(int row, int col) {
    CheckBounds.indexCheck(row, col, this.height(), this.width());
    return matrix.get(row).get(col);
  } // get(int, int)

  /**
   * Set the element at the given row and column.
   *
   * @param row The row of the element.
   * @param col The column of the element.
   * @param val The value to set.
   * @throws IndexOutOfBoundsException If either the row or column is out of reasonable bounds.
   */
  public void set(int row, int col, T val) {
    CheckBounds.indexCheck(row, col, this.height(), this.width());
    matrix.get(row).set(col, val);
  } // set(int, int, T)

  /**
   * Determine the number of rows in the matrix.
   *
   * @return the number of rows.
   */
  public int height() {
    return this.height;
  } // height()

  /**
   * Determine the number of columns in the matrix.
   *
   * @return the number of columns.
   */
  public int width() {
    return this.width;
  } // width()

  /**
   * Insert a row filled with the default value.
   *
   * @param row The number of the row to insert.
   * @throws IndexOutOfBoundsException If the row is negative or greater than the height.
   */
  public void insertRow(int row) {
    if (row < 0 || row > height) {
      throw new IndexOutOfBoundsException("Row index out of bounds.");
    } // if-loop
    ArrayList<T> newRow = new ArrayList<>(Arrays.asList((T[]) new Object[width]));
    for (int i = 0; i < width; i++) {
      newRow.set(i, defaultValue);
    } // for-loop
    matrix.add(row, newRow);
    height++;
  } // insertRow(int)

  /**
   * Insert a row filled with the specified values.
   *
   * @param row The number of the row to insert.
   * @param vals The values to insert.
   * @throws IndexOutOfBoundsException If the row is negative or greater than the height.
   * @throws ArraySizeException If the size of vals is not the same as the width of the matrix.
   */
  public void insertRow(int row, T[] vals) throws ArraySizeException {
    if (row < 0 || row > height) {
      throw new IndexOutOfBoundsException("Row index out of bounds.");
    } // if-loop
    if (vals.length != width) {
      throw new ArraySizeException("Row size does not match matrix width.");
    } // if-loop
    ArrayList<T> newRow = new ArrayList<>(Arrays.asList(vals));
    matrix.add(row, newRow);
    height++;
  } // insertRow(int, T[])

  /**
   * Insert a column filled with the default value.
   *
   * @param col The number of the column to insert.
   * @throws IndexOutOfBoundsException If the column is negative or greater than the width.
   */
  public void insertCol(int col) {
    if (col < 0 || col > width) {
      throw new IndexOutOfBoundsException("Column index out of bounds.");
    } // if-loop
    for (ArrayList<T> row : matrix) {
      row.add(col, defaultValue);
    } // for-loop
    width++;
  } // insertCol(int)

  /**
   * Insert a column filled with the specified values.
   *
   * @param col The number of the column to insert.
   * @param vals The values to insert.
   * @throws IndexOutOfBoundsException If the column is negative or greater than the width.
   * @throws ArraySizeException If the size of vals is not the same as the height of the matrix.
   */
  public void insertCol(int col, T[] vals) throws ArraySizeException {
    if (col < 0 || col > width) {
      throw new IndexOutOfBoundsException("Column index out of bounds.");
    } // if-loop
    if (vals.length != height) {
      throw new ArraySizeException("Column size does not match matrix height.");
    } // if-loop
    for (int i = 0; i < height; i++) {
      matrix.get(i).add(col, vals[i]);
    } // for-loop
    width++;
  } // insertCol(int, T[])

  /**
   * Delete a row.
   *
   * @param row The number of the row to delete.
   * @throws IndexOutOfBoundsException If the row is negative or greater than or equal to the
   *     height.
   */
  public void deleteRow(int row) {
    if (row < 0 || row >= height) {
      throw new IndexOutOfBoundsException("Row index out of bounds.");
    } // if-loop
    matrix.remove(row);
    height--;
  } // deleteRow(int)

  /**
   * Delete a column.
   *
   * @param col The number of the column to delete.
   * @throws IndexOutOfBoundsException If the column is negative or greater than or equal to the
   *     width.
   */
  public void deleteCol(int col) {
    if (col < 0 || col >= width) {
      throw new IndexOutOfBoundsException("Column index out of bounds.");
    } // if-loop
    for (ArrayList<T> row : matrix) {
      row.remove(col);
    } // for-loop
    width--;
  } // deleteCol(int)

  /**
   * Fill a rectangular region of the matrix.
   *
   * @param startRow The top edge / row to start with (inclusive).
   * @param startCol The left edge / column to start with (inclusive).
   * @param endRow The bottom edge / row to stop with (exclusive).
   * @param endCol The right edge / column to stop with (exclusive).
   * @param val The value to store.
   * @throw IndexOutOfBoundsException If the rows or columns are inappropriate.
   */
  public void fillRegion(int startRow, int startCol, int endRow, int endCol, T val) {

    for (int row = startRow; row < endRow; row++) {
      for (int col = startCol; col < endCol; col++) {
        set(row, col, val);
      } // for-loop
    } // for-loop
  } // fillRegion(int, int, int, int, T)

  /**
   * Fill a line (horizontal, vertical, diagonal).
   *
   * @param startRow The row to start with (inclusive).
   * @param startCol The column to start with (inclusive).
   * @param deltaRow How much to change the row in each step.
   * @param deltaCol How much to change the column in each step.
   * @param endRow The row to stop with (exclusive).
   * @param endCol The column to stop with (exclusive).
   * @param val The value to store.
   * @throw IndexOutOfBoundsException If the rows or columns are inappropriate.
   */
  public void fillLine(
      int startRow, int startCol, int deltaRow, int deltaCol, int endRow, int endCol, T val) {

    int row = startRow;
    int col = startCol;
    while (row < endRow && col < endCol) {
      set(row, col, val);
      row += deltaRow;
      col += deltaCol;
    } // while-loop
  } // fillLine(int, int, int, int, int, int, T)

  /**
   * A make a copy of the matrix. May share references (e.g., if individual elements are mutable,
   * mutating them in one matrix may affect the other matrix) or may not.
   *
   * @return a copy of the matrix.
   */
  public Matrix clone() {
    MatrixV0<T> newMatrix = new MatrixV0<>(width, height, defaultValue);
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        newMatrix.set(row, col, this.get(row, col));
      } // for-loop
    } // for-loop
    return newMatrix;
  } // clone()

  /**
   * Determine if this object is equal to another object.
   *
   * @param other The object to compare.
   * @return true if the other object is a matrix with the same width, height, and equal elements;
   *     false otherwise.
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } // if-condition
    if (!(other instanceof MatrixV0<?>)) {
      return false;
    } // if-condition
    MatrixV0<?> that = (MatrixV0<?>) other;
    if (this.width != that.width || this.height != that.height) {
      return false;
    } // if-condition
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (!this.get(row, col).equals(that.get(row, col))) {
          return false;
        } // if condition
      } // for-loop
    } // for-loop
    return true;
  } // equals(Object)

  /**
   * Compute a hash code for this matrix. Included because any object that implements `equals` is
   * expected to implement `hashCode` and ensure that the hash codes for two equal objects are the
   * same.
   *
   * @return the hash code.
   */
  public int hashCode() {
    int multiplier = 7;
    int code = this.width() + multiplier * this.height();
    for (int row = 0; row < this.height(); row++) {
      for (int col = 0; col < this.width(); col++) {
        T val = this.get(row, col);
        if (val != null) {
          // It's okay if the following computation overflows, since
          // it will overflow uniformly.
          code = code * multiplier + val.hashCode();
        } // if
      } // for col
    } // for row
    return code;
  } // hashCode()
} // class MatrixV0
