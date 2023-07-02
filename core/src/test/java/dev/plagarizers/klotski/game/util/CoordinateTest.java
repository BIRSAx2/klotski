package dev.plagarizers.klotski.game.util;

import com.google.gson.Gson;
import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.util.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The CoordinateTest class is used to test the functionality of the Coordinate
 * class.
 */
class CoordinateTest {

  private Coordinate coordinate;

  /**
   * The CoordinateTest class is used to test the functionality of the Coordinate
   * class.
   */
  @BeforeEach
  void setUp_Coordinate_InitializesCoordinate() {
    coordinate = new Coordinate(2, 3);
  }

  /**
   * Test case for the getX() method.
   * It verifies that the X coordinate is returned correctly.
   */
  @Test
  void getX_Coordinate_ReturnsX() {
    // Arrange
    int expectedX = 2;

    // Act
    int x = coordinate.getX();

    // Assert
    assertEquals(expectedX, x, "Expected x-coordinate to be 2");
  }

  /**
   * Test case for the setX() method.
   * It verifies that the X coordinate is set correctly.
   */
  @Test
  void setX_Coordinate_SetsX() {
    // Arrange
    int newX = 5;

    // Act
    coordinate.setX(newX);

    // Assert
    assertEquals(newX, coordinate.getX(), "Expected x-coordinate to be set to 5");
  }

  /**
   * Test case for the getY() method.
   * It verifies that the Y coordinate is returned correctly.
   */
  @Test
  void getY_Coordinate_ReturnsY() {
    // Arrange
    int expectedY = 3;

    // Act
    int y = coordinate.getY();

    // Assert
    assertEquals(expectedY, y, "Expected y-coordinate to be 3");
  }

  /**
   * Test case for the setY() method.
   * It verifies that the Y coordinate is set correctly.
   */
  @Test
  void setY_Coordinate_SetsY() {
    // Arrange
    int newY = 4;

    // Act
    coordinate.setY(newY);

    // Assert
    assertEquals(newY, coordinate.getY(), "Expected y-coordinate to be set to 4");
  }

  /**
   * Test case for the add(int, int) method.
   * It verifies that adding values to the coordinate produces the expected
   * result.
   */
  @Test
  void addWithValue_Coordinate_AddsWithValue() {
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

  /**
   * Test case for the add(Coordinate) method.
   * It verifies that adding another coordinate to the coordinate produces the
   * expected result.
   */
  @Test
  void addWithCoordinate_Coordinate_AddsWithCoordinate() {
    // Arrange
    Coordinate other = new Coordinate(1, 2);
    Coordinate expectedResult = new Coordinate(3, 5);

    // Act
    Coordinate result = coordinate.add(other);

    // Assert
    assertEquals(expectedResult.getX(), result.getX(), "Expected x-coordinate to be 3");
    assertEquals(expectedResult.getY(), result.getY(), "Expected y-coordinate to be 5");
  }

  /**
   * Test case for the subtract(int, int) method.
   * It verifies that subtracting values from the coordinate produces the expected
   * result.
   */
  @Test
  void equals_Coordinate_ReturnsTrue() {
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

  /**
   * Test case for the subtract(Coordinate) method.
   * It verifies that subtracting another coordinate from the coordinate produces
   * the expected result.
   */
  @Test
  void toStringRepresentation_Coordinate_ReturnsStringRepresentation() {
    // Arrange
    String expectedString = "(2, 3)";

    // Act
    String str = coordinate.toString();

    // Assert
    assertEquals(expectedString, str, "Expected string representation to be '(2, 3)'");
  }

  /**
   * Test case for the clone() method.
   * It verifies that the clone is a deep copy of the coordinate.
   */
  @Test
  void clone_CreatesDeepCopyOfCoordinate() {
    // Arrange
    Coordinate clone = coordinate.clone();

    // Act & Assert
    assertEquals(coordinate, clone, "Expected clone to be equal to the original coordinate");
    assertEquals(coordinate.getX(), clone.getX(), "Expected x-coordinate of the clone to be equal to the original");
    assertEquals(coordinate.getY(), clone.getY(), "Expected y-coordinate of the clone to be equal to the original");
  }
}