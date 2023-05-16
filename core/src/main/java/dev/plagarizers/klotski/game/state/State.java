package dev.plagarizers.klotski.game.state;

import dev.plagarizers.klotski.game.block.*;
import dev.plagarizers.klotski.game.util.Coordinate;
import dev.plagarizers.klotski.game.util.Direction;

import java.util.ArrayList;
import java.util.List;

public class State implements Cloneable, Comparable<State> {


  public static final int ROWS = 5;
  public static final int COLS = 4;

  public static final int NUM_PIECES = 10;

  public static final Coordinate SOLUTION = Coordinate.of(3, 1);
  private Block[] blocks = new Block[NUM_PIECES];

  private State() {
  }


  public Block[] getBlocks() {
    return blocks;
  }

  public void setBlocks(Block[] blocks) {
    this.blocks = blocks;
  }

  /**
   * Creates and returns a new State object initialized with the default configuration of blocks.
   *
   * @return The State object with the default block configuration.
   */

  public static State fromDefaultConfiguration() {

    State state = new State();
    Block[] blocks = new Block[NUM_PIECES];
    // default configuration
    blocks[0] = new BigBlock(new Coordinate(0, 1));
    blocks[1] = new HorizontalBlock(new Coordinate(2, 1));
    blocks[2] = new VerticalBlock(new Coordinate(0, 3));
    blocks[3] = new VerticalBlock(new Coordinate(0, 0));
    blocks[4] = new VerticalBlock(new Coordinate(3, 0));
    blocks[5] = new VerticalBlock(new Coordinate(3, 3));
    blocks[6] = new SmallBlock(new Coordinate(3, 1));
    blocks[7] = new SmallBlock(new Coordinate(3, 2));
    blocks[8] = new SmallBlock(new Coordinate(4, 1));
    blocks[9] = new SmallBlock(new Coordinate(4, 2));

    state.blocks = blocks;

    return state;
  }

  /**
   * Applies the given direction to the given coordinate and returns the new coordinate.
   *
   * @param coordinate The coordinate to apply the move to.
   * @param direction  The direction of the move.
   * @return The new coordinate after applying the move, or null if the resulting coordinate is invalid.
   */
  public static Coordinate applyMoveToCoords(Coordinate coordinate, Direction direction) {
    Coordinate newCoord;
    switch (direction) {
      case UP:
        newCoord = coordinate.add(-1, 0);
        if (isValidCoordinate(newCoord)) return newCoord;
        break;
      case RIGHT:
        newCoord = coordinate.add(0, 1);
        if (isValidCoordinate(newCoord)) return newCoord;

        break;
      case LEFT:
        newCoord = coordinate.add(0, -1);
        if (isValidCoordinate(newCoord)) return newCoord;

        break;
      case DOWN:
        newCoord = coordinate.add(1, 0);
        if (isValidCoordinate(newCoord)) return newCoord;
        break;
    }

    return null;
  }

  /**
   * Checks if the given coordinate is a valid coordinate within the board boundaries.
   *
   * @param coordinate The coordinate to check.
   * @return {@code true} if the coordinate is valid, {@code false} otherwise.
   */
  public static boolean isValidCoordinate(Coordinate coordinate) {
    return coordinate.getX() >= 0 && coordinate.getX() < ROWS && coordinate.getY() >= 0 && coordinate.getY() < COLS;
  }

  /**
   * Converts an array of bit board representations to a State object.
   *
   * @param bitBoard The array of bit board representations.
   * @return The corresponding State object.
   */
  public static State fromBitBoard(int[] bitBoard) {
    State state = new State();

    Block[] blocks = new Block[NUM_PIECES];
    for (int i = 0; i < NUM_PIECES; i++) {
      blocks[i] = createBlockFromBitMask(bitBoard[i]);
    }
    state.setPieces(blocks);
    return state;
  }

  /**
   * Converts a block object to a bit mask representation.
   *
   * @param block The block object to convert.
   * @return The bit mask representation of the block.
   */
  public static int createBitMaskForBlock(Block block) {

    int mask = 0b0000_0000_0000_0000_0000;
    int x = block.getLocation().getX();
    int y = block.getLocation().getY();
    for (int row = x; row < x + block.getHeight(); row++) {
      for (int col = y; col < y + block.getWidth(); col++) {
        int index = 16 - (row * State.COLS) + col;
        // Set the corresponding bit to 1
        mask |= (1 << index);
      }
    }
    return mask;
  }

  /**
   * Converts a bit mask representation to a block object.
   *
   * @param mask The bit mask representation of the block.
   * @return The corresponding block object.
   */

  public static Block createBlockFromBitMask(int mask) {


    if(mask <=0) {
      return null;
    }
    int y = -1, x = -1, width = -1, height = -1;

    for (int row = 0; row < State.ROWS; row++) {
      for (int col = 0; col < State.COLS; col++) {
        int bit = (mask >> (State.ROWS - row - 1) * State.COLS + col) & 1;

        if (bit == 1) {
          if (y == -1) {
            // Found the first cell of the block
            y = col;
            x = row;
            width = 1;
            height = 1;
          } else {
            // Expand the dimensions of the block if necessary
            width = Math.max(width, col - y + 1);
            height = Math.max(height, row - x + 1);
          }
        }
      }
    }

    return new Block(Coordinate.of(x, y), height, width);
  }

  public void setPieces(Block[] blocks) {
    this.blocks = blocks;
  }

  /**
   * Returns the target piece (block) that needs to be moved to the solution position.
   *
   * @return The target piece.
   */
  public Block targetPiece() {
    return blocks[0];
  }

  /**
   * Checks if the state is a solution state where the target piece is at the solution position.
   *
   * @return True if the state is a solution state, false otherwise.
   */

  public boolean isSolution() {
    return targetPiece().getLocation().equals(SOLUTION);
  }

  /**
   * Converts the state to a two-dimensional board representation.
   *
   * @return The board representation of the state.
   */
  public int[][] toBoard() {
    int[][] board = new int[ROWS][COLS];
    for (int n = 0; n < blocks.length; n++) {
      Coordinate location = blocks[n].getLocation();

      for (int i = 0; i < blocks[n].getHeight(); i++) {
        for (int j = 0; j < blocks[n].getWidth(); j++) {

          if (board[location.getX() + i][location.getY() + j] != 0) {
            throw new IllegalStateException("Cannot override value");
          }
          board[location.getX() + i][location.getY() + j] = n + 1;
        }
      }
    }
    return board;
  }

  /**
   * Generates a string representation of the state.
   *
   * @return The string representation of the state.
   */
  @SuppressWarnings("DefaultLocale")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int[] row : toBoard()) {
      for (int cell : row) {
        sb.append(" ").append(cell);
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Calculates the hash code of the state.
   *
   * @return The hash code of the state.
   */
  @Override
  public int hashCode() {

    List<Integer> flattenedCoordinates = new ArrayList<>();

    for (Block block : blocks) {

      // Extract x and y coordinates of each block and add them to the list
      int x = block.getLocation().getX() & 0b111; // lower 3 bits
      int y = block.getLocation().getY() & 0b111; // lower 3 bits
      flattenedCoordinates.add(x);
      flattenedCoordinates.add(y);
    }

    int hash = 0;
    int shift = 0;

    for (int bits : flattenedCoordinates) {
      hash |= bits << (shift * 3); // Shift the bits and OR them with the hash
      shift++;
      if (shift == 21) { // Assuming the maximum number of pieces is 10
        break;
      }
    }

    return hash;
  }

  /**
   * Checks if the given object is equal to the current state.
   *
   * @param obj The object to compare with the state.
   * @return True if the object is equal to the state, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    State other = (State) obj;

    int selfHash = hashCode();
    int otherHash = other.hashCode();

    return selfHash == otherHash;
  }

  /**
   * Creates a deep copy of the state.
   *
   * @return The cloned State object.
   */
  public State clone() {
    Block[] newBlocks = new Block[NUM_PIECES];
    for (int i = 0; i < NUM_PIECES; i++) {
      newBlocks[i] = blocks[i].clone();
    }
    State newState = new State();
    newState.setPieces(newBlocks);
    return newState;
  }

  /**
   * Compares the current state with the given object for ordering.
   *
   * @param o The object to compare with the state.
   * @return 0 if the objects are equal, 1 otherwise.
   * @throws NullPointerException If the object is null.
   * @throws ClassCastException   If the object is not of type State.
   */

  @Override
  public int compareTo(State o) {

    if (o == null) {
      throw new NullPointerException();
    }

    if (this.equals(o)) {
      return 0;
    }

    return 1;
  }

  /**
   * Converts the state to a bit board representation.
   *
   * @return The bit board representation of the state.
   */
  public int[] toBitBoard() {
    int[] bitBoard = new int[NUM_PIECES];
    int j = 0;
    for (Block block : blocks) {
      bitBoard[j++] = createBitMaskForBlock(block);
    }
    return bitBoard;
  }

}
