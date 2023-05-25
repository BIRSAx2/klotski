package dev.plagarizers.klotski.game.state;


import com.google.gson.Gson;
import dev.plagarizers.klotski.game.block.*;
import dev.plagarizers.klotski.game.util.Coordinate;
import dev.plagarizers.klotski.game.util.Direction;

import java.util.*;
import java.util.stream.Collectors;

public class State implements Cloneable {

  private HashMap<Coordinate, Block> blocks;
  private int moves = 0;


  public final static int ROWS = 5, COLS = 4, NUM_PIECES = 10;
  public final static Coordinate GOAL = Coordinate.of(3, 1);


  private State() {
    this.blocks = new HashMap<>();
  }

  public int getMoves() {
    return moves;
  }

  public void setMoves(int moves) {
    this.moves = moves;
  }

  public static State fromDefaultConfiguration() {
    Block[] base = new Block[NUM_PIECES];
    base[0] = new BigBlock(new Coordinate(0, 1));
    base[1] = new HorizontalBlock(new Coordinate(2, 1));
    base[2] = new VerticalBlock(new Coordinate(0, 3));
    base[3] = new VerticalBlock(new Coordinate(0, 0));
    base[4] = new VerticalBlock(new Coordinate(3, 0));
    base[5] = new VerticalBlock(new Coordinate(3, 3));
    base[6] = new SmallBlock(new Coordinate(3, 1));
    base[7] = new SmallBlock(new Coordinate(3, 2));
    base[8] = new SmallBlock(new Coordinate(4, 1));
    base[9] = new SmallBlock(new Coordinate(4, 2));

    State state = new State();

    state.setBlocks(base);
    return state;
  }


  public boolean isSolution() {
    return blocks.get(Coordinate.of(1, 3)).equals(new BigBlock(Coordinate.of(1, 3)));
  }

  public void setBlocks(Block[] base) {
    if (base.length != NUM_PIECES) throw new IllegalArgumentException("Invalid number of blocks");
    for (Block block : base) {
      if (block == null) throw new IllegalArgumentException("Invalid block");
      for (Coordinate coordinate : block.getOccupiedLocations()) {
        this.blocks.put(coordinate, block);
      }
    }
  }

  //  public boolean canMoveBlock(Block block, Direction direction) {
//    Coordinate newTopLeft = block.getLocation().move(direction);
//
//    System.out.println(newTopLeft.getX() + " " + newTopLeft.getY());
//    for (int i = block.getX(); i < block.getX() + block.getWidth() - 1; i++) {
//      Block contained = blocks.get(Coordinate.of(i, block.getY()));
//      if (contained != null && !contained.equals(block)) {
//        return false;
//      }
//
//    }
//    for (int i = block.getY(); i < block.getY() + block.getHeight() - 1; i++) {
//      Block contained = blocks.get(Coordinate.of(block.getX(), i));
//      if (contained != null && !contained.equals(block)) {
//        return false;
//      }
//    }
//    Block cloned = block.clone();
//    cloned.setLocation(newTopLeft);
//    for (Coordinate coordinate : cloned.getOccupiedLocations()) {
//      if (coordinate.getX() < 0 || coordinate.getY()> COLS || coordinate.getY() < 0 || coordinate.getX() > ROWS) {
//        return false;
//      }
//    }
//    System.out.println("Can move block " + block + " in direction " + direction);
//    return true;
//  }
  public boolean canMoveBlock(Block block, Direction direction) {

    Coordinate newTopLeft = block.getLocation().move(direction);

    if (!isValidCoordinate(newTopLeft, block.getWidth(), block.getHeight())) {
      return false;
    }

    Block cloned = block.clone();
    cloned.setLocation(newTopLeft);
    for (Coordinate coordinate : cloned.getOccupiedLocations()) {
      Block contained = blocks.get(coordinate);
      if (contained != null && !contained.equals(block)) {
        return false;
      }
    }
    return true;
  }

  public boolean moveBlock(Block block, Direction direction) {


    if (!canMoveBlock(block, direction)) return false;

    for (Coordinate coordinate : block.getOccupiedLocations()) {
      blocks.remove(coordinate);
    }

    Coordinate newTopLeft = block.getLocation().move(direction);
    block.setLocation(newTopLeft);

    for (Coordinate coordinate : block.getOccupiedLocations()) {
      blocks.put(coordinate, block);
    }

    return true;
  }


  public static State fromRandomConfiguration() {
    return fromDefaultConfiguration();
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

  private boolean isValidCoordinate(Coordinate coordinate, int width, int height) {
    int x = coordinate.getX();
    int y = coordinate.getY();

    // Check if the coordinate is within the boundaries of the puzzle
    if (x >= 0 && y >= 0 && x <= ROWS && y <= COLS) {
      // Check if the coordinate is within the boundaries of the block
      if (y + width <= COLS && x + height <= ROWS) {
        return true;
      }
    }
    return false;
  }


  @SuppressWarnings("NewApi")
  public Block[] getBlocks() {

    // add blocks to array without duplicates

    HashSet<Block> set = new HashSet<>(blocks.values());

    List<Block> list = set.stream().sorted(Comparator.comparing(Block::getWidth).thenComparing(Block::getHeight)).collect(Collectors.toList());
    Collections.reverse(list);


    return list.toArray(new Block[0]);
  }

  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

  public static State fromJson(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, State.class);
  }

  @Override
  public State clone() {
    try {
      return (State) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
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
    state.setBlocks(blocks);
    return state;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < ROWS; i++) {
      sb.append("|");
      for (int j = 0; j < COLS; j++) {
        Coordinate coordinate = Coordinate.of(i, j);
        if (blocks.containsKey(coordinate)) {
          sb.append(blocks.get(coordinate).getIcon());
        } else {
          sb.append(" ");
        }
        sb.append(" | ");
      }
      sb.append("\n");
    }
    return sb.toString();
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


    if (mask <= 0) {
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

    Block block = new Block(Coordinate.of(x, y), height, width);
    return block;
  }


  /**
   * Converts the state to a bit board representation.
   *
   * @return The bit board representation of the state.
   */
  public int[] toBitBoard() {
    int[] bitBoard = new int[NUM_PIECES];
    int j = 0;
    for (Block block : this.getBlocks()) {
      bitBoard[j++] = createBitMaskForBlock(block);
    }
    return bitBoard;
  }


  @Override
  public boolean equals(Object obj) {
    if (obj instanceof State) {
      State state = (State) obj;

      return Arrays.equals(this.getBlocks(), state.getBlocks());
    }
    return false;
  }
}
