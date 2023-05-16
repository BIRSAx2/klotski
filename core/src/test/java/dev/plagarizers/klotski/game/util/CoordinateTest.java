package dev.plagarizers.klotski.game.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

  private Coordinate coordinate;

  @BeforeEach
  void setUp() {
    coordinate = new Coordinate(2, 3);
  }

  @Test
  void testGetX() {
    assertEquals(2, coordinate.getX());
  }

  @Test
  void testSetX() {
    coordinate.setX(5);
    assertEquals(5, coordinate.getX());
  }

  @Test
  void testGetY() {
    assertEquals(3, coordinate.getY());
  }

  @Test
  void testSetY() {
    coordinate.setY(4);
    assertEquals(4, coordinate.getY());
  }

  @Test
  void testAddWithValues() {
    Coordinate result = coordinate.add(1, 2);
    assertEquals(3, result.getX());
    assertEquals(5, result.getY());
  }

  @Test
  void testAddWithCoordinate() {
    Coordinate other = new Coordinate(1, 2);
    Coordinate result = coordinate.add(other);
    assertEquals(3, result.getX());
    assertEquals(5, result.getY());
  }

  @Test
  void testEquals() {
    Coordinate coordinate1 = new Coordinate(2, 3);
    Coordinate coordinate2 = new Coordinate(2, 3);
    Coordinate coordinate3 = new Coordinate(4, 5);

    assertEquals(coordinate1, coordinate2);
    assertNotEquals(coordinate1, coordinate3);
  }

  @Test
  void testHashCode() {
    Coordinate coordinate1 = new Coordinate(2, 3);
    Coordinate coordinate2 = new Coordinate(2, 3);
    Coordinate coordinate3 = new Coordinate(4, 5);

    assertEquals(coordinate1.hashCode(), coordinate2.hashCode());
    assertNotEquals(coordinate1.hashCode(), coordinate3.hashCode());
  }

  @Test
  void testToString() {
    assertEquals("(2, 3)", coordinate.toString());
  }

  @Test
  void testClone() {
    Coordinate clone = coordinate.clone();
    assertNotSame(coordinate, clone);
    assertEquals(coordinate.getX(), clone.getX());
    assertEquals(coordinate.getY(), clone.getY());
  }
}
