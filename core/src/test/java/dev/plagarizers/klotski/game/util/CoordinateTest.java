package dev.plagarizers.klotski.game.util;

import com.google.gson.Gson;
import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.util.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoordinateTest {

  private Coordinate coordinate;

  @BeforeEach
  void testSetUp_Coordinate_SetUp() {
    coordinate = new Coordinate(2, 3);
  }

  @Test
  void testGetCoordinate_Coordinate_ReturnsX() {
    // Arrange
    int expectedX = 2;

    // Act
    int x = coordinate.getX();

    // Assert
    assertEquals(expectedX, x, "Expected x-coordinate to be 2");
  }

  @Test
  void testSetX_Coordinate_SetX() {
    // Arrange
    int newX = 5;

    // Act
    coordinate.setX(newX);

    // Assert
    assertEquals(newX, coordinate.getX(), "Expected x-coordinate to be set to 5");
  }

  @Test
  void testGetCoordinate_CoordinateTest_ReturnsY() {
    // Arrange
    int expectedY = 3;

    // Act
    int y = coordinate.getY();

    // Assert
    assertEquals(expectedY, y, "Expected y-coordinate to be 3");
  }

  @Test
  void testSetX_Coordinate_SetY() {
    // Arrange
    int newY = 4;

    // Act
    coordinate.setY(newY);

    // Assert
    assertEquals(newY, coordinate.getY(), "Expected y-coordinate to be set to 4");
  }

  @Test
  void testAddWithValues_Coordinate_AddWhitValue() {
    // Arrange
    int valueX = 1;
    int valueY = 2;
    Coordinate expectedResult = new Coordinate(3, 5);

    // Act
    Coordinate result = coordinate.add(valueX, valueY);

    // Assert
    assertEquals(expectedResult.getX(), result.getX(), "Expected x-coordinate to be 3");
    assertEquals(expectedResult.getY(), result.getY(), "Expected y-coordinate to be 5");
  }

  @Test
  void testAddWithCoordinate_Coordinate_AddWhitCoordinate() {
    // Arrange
    Coordinate other = new Coordinate(1, 2);
    Coordinate expectedResult = new Coordinate(3, 5);

    // Act
    Coordinate result = coordinate.add(other);

    // Assert
    assertEquals(expectedResult.getX(), result.getX(), "Expected x-coordinate to be 3");
    assertEquals(expectedResult.getY(), result.getY(), "Expected y-coordinate to be 5");
  }

  @Test
  void testEquals_Coordinate_ReturnsTrue() {
    // Arrange
    Coordinate coordinate1 = new Coordinate(2, 3);
    Coordinate coordinate2 = new Coordinate(2, 3);
    Coordinate coordinate3 = new Coordinate(4, 5);

    // Act & Assert
    assertEquals(coordinate1, coordinate2, "Expected coordinates to be equal");
    assertEquals(coordinate2, coordinate1, "Expected coordinates to be equal");
    assertEquals(coordinate1.hashCode(), coordinate2.hashCode(), "Expected hash codes to be equal");
    assertEquals(coordinate1, coordinate1, "Expected coordinate to be equal to itself");
    assertEquals(coordinate2, coordinate2, "Expected coordinate to be equal to itself");
    assertEquals(coordinate1.hashCode(), coordinate1.hashCode(), "Expected hash code to be equal to itself");
    assertEquals(coordinate2.hashCode(), coordinate2.hashCode(), "Expected hash code to be equal to itself");
    assertEquals(coordinate1, coordinate1.clone(), "Expected coordinate to be equal to its clone");
  }

  @Test
  void testStringRepresentation_Coordinate_toStringRepresentation() {
    // Arrange
    String expectedString = "(2, 3)";

    // Act
    String str = coordinate.toString();

    // Assert
    assertEquals(expectedString, str, "Expected string representation to be '(2, 3)'");
  }

  @Test
  void testClone_CreatesDeepCopyOfCoordinate() {
    // Arrange
    Coordinate clone = coordinate.clone();

    // Act & Assert
    assertEquals(coordinate, clone, "Expected clone to be equal to the original coordinate");
    assertEquals(coordinate.getX(), clone.getX(), "Expected x-coordinate of the clone to be equal to the original");
    assertEquals(coordinate.getY(), clone.getY(), "Expected y-coordinate of the clone to be equal to the original");
  }
}
