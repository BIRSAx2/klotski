package dev.plagarizers.klotski.model;

import java.util.*;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class Piece implements Cloneable, Comparable<Piece> {

  private Coordinate coordinate;
  private int width;
  private int height;

  public Piece(Coordinate coordinate, int height, int width) {
    this.coordinate = coordinate;
    this.width = width;
    this.height = height;
  }


  public Coordinate getCoordinate() {
    return coordinate;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void setCoordinate(Coordinate coordinate) {
    this.coordinate = coordinate;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public EnumMap<Direction, List<Coordinate>> adjacentSpaces() {
    EnumMap<Direction, List<Coordinate>> adjacentSpaces = new EnumMap<>(Direction.class);

    final Coordinate upperLeft = new Coordinate(coordinate.getX(), coordinate.getY());
    Coordinate bottomLeft = coordinate.add(0, height - 1);
    Coordinate upperRight = coordinate.add(0, width - 1);


    for (int col = 0; col <= width; col++) {

      Coordinate upperRow = upperLeft.add(0, col);
      Coordinate result = State.applyMoveToCoords(upperRow, Direction.UP);

      if (result != null) {
        if (adjacentSpaces.containsKey(Direction.UP))
          adjacentSpaces.get(Direction.UP).add(result);
        else
          adjacentSpaces.put(Direction.UP, Collections.singletonList(result));
      }

      Coordinate bottomRow = bottomLeft.add(0, col);
      result = State.applyMoveToCoords(bottomRow, Direction.DOWN);
      if (result != null) {
        if (adjacentSpaces.containsKey(Direction.DOWN))
          adjacentSpaces.get(Direction.DOWN).add(result);
        else
          adjacentSpaces.put(Direction.DOWN, Collections.singletonList(result));
      }

    }

    for (int row = 0; row <= height; row++) {
      Coordinate leftCol = upperLeft.add(row, 0);
      Coordinate result = State.applyMoveToCoords(leftCol, Direction.LEFT);
      if (result != null) {
        if (adjacentSpaces.containsKey(Direction.LEFT))
          adjacentSpaces.get(Direction.LEFT).add(result);
        else
          adjacentSpaces.put(Direction.LEFT, Collections.singletonList(result));
      }

      Coordinate rightCol = upperRight.add(row, 0);
      result = State.applyMoveToCoords(rightCol, Direction.RIGHT);
      if (result != null) {
        if (adjacentSpaces.containsKey(Direction.RIGHT))
          adjacentSpaces.get(Direction.RIGHT).add(result);
        else
          adjacentSpaces.put(Direction.RIGHT, Collections.singletonList(result));
      }
    }

    return adjacentSpaces;
  }


  public List<Coordinate> occupiedSpaces() {
    List<Coordinate> spaces = new ArrayList<>();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        spaces.add(coordinate.add(row, col));
      }
    }

    return spaces;
  }


  public boolean makeMove(Direction direction) {
    Coordinate newCoord = State.applyMoveToCoords(coordinate, direction);
    if (newCoord == null)
      return false;

    coordinate = newCoord;
    return true;
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
    return "Piece{" +
      "coordinate=" + coordinate +
      ", width=" + width +
      ", height=" + height +
      '}';
  }

  @Override
  public int compareTo(Piece o) {

    if (this.coordinate.getX() < o.coordinate.getX()) {
      return -1;
    } else if (this.coordinate.getX() > o.coordinate.getX()) {
      return 1;
    } else if (this.coordinate.getY() < o.coordinate.getY()) {
      return -1;
    } else if (this.coordinate.getY() > o.coordinate.getY()) {
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
