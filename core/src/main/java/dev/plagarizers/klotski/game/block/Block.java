package dev.plagarizers.klotski.game.block;

import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.Coordinate;
import dev.plagarizers.klotski.game.util.Direction;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;


/**
 * Represents a block in the Klotski puzzle.
 */
public class Block implements Cloneable, Comparable<Block> {

  private Coordinate location;
  private int height;
  private int width;

  public BlockType getType() {
    return type;
  }

  public void setType(BlockType type) {
    this.type = type;
  }

  private BlockType type = BlockType.UnknownBlock;


  /**
   * Constructs a Block object with the specified location, height, and width.
   *
   * @param location the coordinate representing the top-left corner of the block
   * @param height   the height of the block
   * @param width    the width of the block
   */
  public Block(Coordinate location, int height, int width) {
    this.location = location;
    this.width = width;
    this.height = height;
  }


  public Block(Coordinate location, int height, int width, BlockType type) {
    this.location = location;
    this.width = width;
    this.height = height;
    this.type = type;
  }


  /**
   * Returns the location of the block.
   *
   * @return the coordinate representing the top-left corner of the block
   */
  public Coordinate getLocation() {
    return location;
  }

  /**
   * Sets the location of the block.
   *
   * @param coordinate the new coordinate representing the top-left corner of the block
   */
  public void setLocation(Coordinate coordinate) {
    this.location = coordinate;
  }

  /**
   * Returns the width of the block.
   *
   * @return the width of the block
   */
  public int getWidth() {
    return width;
  }

  /**
   * Sets the width of the block.
   *
   * @param width the new width of the block
   */
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * Returns the height of the block.
   *
   * @return the height of the block
   */
  public int getHeight() {
    return height;
  }

  /**
   * Sets the height of the block.
   *
   * @param height the new height of the block
   */
  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * Returns a map of the adjacent spaces of the block in each direction.
   *
   * @return the map of adjacent spaces, with directions as keys and lists of coordinates as values
   */
  public EnumMap<Direction, List<Coordinate>> adjacentSpaces() {
    EnumMap<Direction, List<Coordinate>> adjacentSpaces = new EnumMap<>(Direction.class);
    for (Direction direction : Direction.values()) {
      List<Coordinate> coordinates;
      if (direction == Direction.UP || direction == Direction.DOWN) {
        coordinates = new ArrayList<>(width);
      } else {
        coordinates = new ArrayList<>(height);
      }
      adjacentSpaces.put(direction, coordinates);
    }


    final Coordinate upperLeft = location.clone();
    Coordinate bottomLeft = location.add(height - 1, 0);
    Coordinate upperRight = location.add(0, width - 1);


    Coordinate result;
    for (int col = 0; col < width; col++) {

      Coordinate upperRow = upperLeft.add(0, col);
      result = State.applyMoveToCoords(upperRow, Direction.UP);

      if (result != null) {
        adjacentSpaces.get(Direction.UP).add(result);
      }

      Coordinate bottomRow = bottomLeft.add(0, col);

      result = State.applyMoveToCoords(bottomRow, Direction.DOWN);
      if (result != null) {
        adjacentSpaces.get(Direction.DOWN).add(result);
      }

    }

    for (int row = 0; row < height; row++) {
      Coordinate leftCol = upperLeft.add(row, 0);
      result = State.applyMoveToCoords(leftCol, Direction.LEFT);
      if (result != null) {
        adjacentSpaces.get(Direction.LEFT).add(result);
      }

      Coordinate rightCol = upperRight.add(row, 0);
      result = State.applyMoveToCoords(rightCol, Direction.RIGHT);
      if (result != null) {
        adjacentSpaces.get(Direction.RIGHT).add(result);
      }
    }

    return adjacentSpaces;
  }


  /**
   * Returns a list of all the coordinates occupied by the block.
   *
   * @return the list of occupied coordinates
   */
  public List<Coordinate> occupiedSpaces() {

    List<Coordinate> spaces = new ArrayList<>();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        spaces.add(location.add(row, col));
      }
    }


    return spaces;
  }

  /**
   * Moves the block in the specified direction if possible.
   *
   * @param direction the direction in which to move the block
   * @return true if the block was moved successfully, false otherwise
   */
  public boolean makeMove(Direction direction) {
    Coordinate newCoord = State.applyMoveToCoords(location, direction);
    if (newCoord == null) return false;

    location = newCoord;
    return true;
  }

  /**
   * Checks if the block can be moved in the specified direction.
   *
   * @param direction the direction to check
   * @return true if the block can be moved in the specified direction, false otherwise
   */

  public boolean canMove(Direction direction) {
    return null != State.applyMoveToCoords(location, direction);
  }

  /**
   * Creates a deep copy of the block.
   *
   * @return a cloned instance of the block
   */
  @Override
  public Block clone() {
    return new Block(location.clone(), height, width, type);
  }

  @Override
  public String toString() {
    return "Piece{" + "coordinate=" + location + ", width=" + width + ", height=" + height + '}';
  }


  /**
   * Compares this block with the specified block for order.
   *
   * @param other the block to be compared
   * @return a negative integer, zero, or a positive integer as this block is less than, equal to, or greater than the specified block
   */
  @Override
  // not sure if we need this
  public int compareTo(Block other) {

    if (this.location.getX() < other.location.getX()) {
      return -1;
    } else if (this.location.getX() > other.location.getX()) {
      return 1;
    } else if (this.location.getY() < other.location.getY()) {
      return -1;
    } else if (this.location.getY() > other.location.getY()) {
      return 1;
    } else if (this.width < other.width) {
      return -1;
    } else if (this.width > other.width) {
      return 1;
    } else if (this.height < other.height) {
      return -1;
    } else if (this.height > other.height) {
      return 1;
    }
    return 0;
  }

  public int getX() {
    return location.getX();
  }

  public int getY() {
    return location.getY();
  }


  public static enum BlockType {
    BigBlock, SmallBlock, VerticalBlock, HorizontalBlock, UnknownBlock
  }
}
