package dev.plagarizers.klotski.model;

import java.util.*;

public class Piece implements Cloneable, Comparable<Piece> {

  private Coordinate location;
  private int height;
  private int width;

  public Piece(Coordinate location, int height, int width) {
    this.location = location;
    this.width = width;
    this.height = height;
  }


  public Coordinate getLocation() {
    return location;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void setLocation(Coordinate coordinate) {
    this.location = coordinate;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public EnumMap<Direction, List<Coordinate>> adjacentSpaces() {
//    EnumMap<Direction, List<Coordinate>> adjacentSpaces = new EnumMap<>(Direction.class);

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


  public List<Coordinate> occupiedSpaces() {

    List<Coordinate> spaces = new ArrayList<>();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        spaces.add(location.add(row, col));
      }
    }


    return spaces;
  }


  public boolean makeMove(Direction direction) {
    Coordinate newCoord = State.applyMoveToCoords(location, direction);
    if (newCoord == null) return false;

    location = newCoord;
    return true;
  }

  public boolean canMove(Direction direction) {
    return null != State.applyMoveToCoords(location, direction);
  }


  @Override
  public Piece clone() {
    try {
      return (Piece) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }

  @Override
  public String toString() {
    return "Piece{" + "coordinate=" + location + ", width=" + width + ", height=" + height + '}';
  }

  @Override
  // not sure if we need this
  public int compareTo(Piece o) {

    if (this.location.getX() < o.location.getX()) {
      return -1;
    } else if (this.location.getX() > o.location.getX()) {
      return 1;
    } else if (this.location.getY() < o.location.getY()) {
      return -1;
    } else if (this.location.getY() > o.location.getY()) {
      return 1;
    } else if (this.width < o.width) {
      return -1;
    } else if (this.width > o.width) {
      return 1;
    } else if (this.height < o.height) {
      return -1;
    } else if (this.height > o.height) {
      return 1;
    }
    return 0;
  }
}
