package dev.plagarizers.klotski.game.block;

import com.google.gson.Gson;
import dev.plagarizers.klotski.game.util.Coordinate;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a block in the Klotski puzzle.
 */
public class Block implements Cloneable {

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
    type = getBlockTypeBySize(this);
  }

  private BlockType getBlockTypeBySize(Block block) {

    if (block.getWidth() == 2 && block.getHeight() == 2) return BlockType.BigBlock;
    if (block.getWidth() == 1 && block.getHeight() == 2) return BlockType.VerticalBlock;
    if (block.getWidth() == 2 && block.getHeight() == 1) return BlockType.HorizontalBlock;
    if (block.getWidth() == 1 && block.getHeight() == 1) return BlockType.SmallBlock;

    throw new IllegalArgumentException("Invalid block size");
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
   * Returns a list of all the coordinates occupied by the block.
   *
   * @return the list of occupied coordinates
   */

  public List<Coordinate> getOccupiedLocations() {
    List<Coordinate> occupiedLocations = new ArrayList<>();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        occupiedLocations.add(location.add(row, col));
      }
    }

    return occupiedLocations;
  }


  public List<Coordinate> getOccupiedLocations(Coordinate startLoc) {

    List<Coordinate> occupiedLocations = new ArrayList<>();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        occupiedLocations.add(startLoc.add(row, col));
      }
    }

    return occupiedLocations;
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
   * Checks if the block can be moved in the specified direction.
   *
   * @param direction the direction to check
   * @return true if the block can be moved in the specified direction, false otherwise
   */

//  public boolean canMove(Direction direction) {
//    return null != State.applyMoveToCoords(location, direction);
//  }

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
    return "Block{" + "coordinate=" + location + ", width=" + width + ", height=" + height + '}';
  }


  /**
   * Returns the icon representation of the block based on its type.
   *
   * @return the icon representation of the block
   */
  public String getIcon() {
    if (this.getType() == BlockType.BigBlock) return "B";
    if (this.getType() == BlockType.SmallBlock) return "S";
    if (this.getType() == BlockType.VerticalBlock) return "V";
    if (this.getType() == BlockType.HorizontalBlock) return "H";
    return "X";
  }

  public int getX() {
    return location.getX();
  }

  public int getY() {
    return location.getY();
  }


  /**
   * Converts the block to a JSON string.
   *
   * @return The JSON representation of the state.
   */
  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

  /**
   * Creates a block object from a JSON string.
   *
   * @param json The JSON string representing the state.
   * @return The State object created from the JSON string.
   */
  public static Block fromJson(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, Block.class);
  }

  /**
   * Represents the types of blocks in the Klotski puzzle.
   */
  public enum BlockType {
    BigBlock, SmallBlock, VerticalBlock, HorizontalBlock, UnknownBlock
  }

  /**
   * Checks if the current block is equal to the specified object.
   *
   * @param obj the object to compare
   * @return true if the current block is equal to the specified object, false otherwise
   */

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (!(obj instanceof Block)) return false;
    Block other = (Block) obj;
    return this.location.equals(other.location) && this.width == other.width && this.height == other.height;
  }

  /**
   * Returns the hash code value for the block.
   *
   * @return the hash code value for the block
   */
  @Override
  public int hashCode() {
    int result = location != null ? location.hashCode() : 0;
    result = 31 * result + height;
    result = 31 * result + width;
    return result;
  }
}
