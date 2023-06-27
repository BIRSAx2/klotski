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
    assertEquals(coordinate2, coordinate1);
    assertEquals(coordinate1.hashCode(), coordinate2.hashCode());

    assertEquals(coordinate1, coordinate1);
    assertEquals(coordinate2, coordinate2);
    assertEquals(coordinate1.hashCode(), coordinate1.hashCode());
    assertEquals(coordinate2.hashCode(), coordinate2.hashCode());

    assertEquals(coordinate1, coordinate1.clone());
  }

  @Test
  void testToString() {
    assertEquals("(2, 3)", coordinate.toString());
  }

  @Test
  void testClone() {
    Coordinate clone = coordinate.clone();
    assertEquals(coordinate, clone);
    assertEquals(coordinate.getX(), clone.getX());
    assertEquals(coordinate.getY(), clone.getY());
  }

  @Test
  public void testMove() {
    Coordinate coord = new Coordinate(2, 3);

    // Test moving up
    Coordinate newCoord = coord.move(Direction.UP);
    assertEquals(new Coordinate(1, 3), newCoord);

    // Test moving right
    newCoord = coord.move(Direction.RIGHT);
    assertEquals(new Coordinate(2, 4), newCoord);

    // Test moving left
    newCoord = coord.move(Direction.LEFT);
    assertEquals(new Coordinate(2, 2), newCoord);

    // Test moving down
    newCoord = coord.move(Direction.DOWN);
    assertEquals(new Coordinate(3, 3), newCoord);
  }

  @Test
  void testToJson() {
    String expectedJson = "{\"x\":2,\"y\":3}";
    String actualJson = coordinate.toJson();
    assertEquals(expectedJson, actualJson);
  }

  @Test
  void testFromJson() {
    String json = "{\"x\":2,\"y\":3}";
    Coordinate expectedCoordinate = new Coordinate(2, 3);
    Coordinate actualCoordinate = Coordinate.fromJson(json);
    assertEquals(expectedCoordinate, actualCoordinate);
  }
}
