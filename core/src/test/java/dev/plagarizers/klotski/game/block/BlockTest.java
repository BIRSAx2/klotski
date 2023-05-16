package dev.plagarizers.klotski.game.block;


import dev.plagarizers.klotski.game.util.Coordinate;
import dev.plagarizers.klotski.game.util.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

  private Coordinate location;

  @BeforeEach
  void setUp() {
    location = new Coordinate(1, 2);
  }

  @Test
  void getLocation() {

    Block block = new Block(location, 3, 4);
    assertEquals(location, block.getLocation());
  }

  @Test
  void setLocation() {

    Block block = new Block(location, 3, 4);
    Coordinate newLocation = new Coordinate(5, 6);
    block.setLocation(newLocation);
    assertEquals(newLocation, block.getLocation());
  }

  @Test
  void getWidth() {

    Block block = new Block(location, 3, 4);
    assertEquals(4, block.getWidth());
  }

  @Test
  void setWidth() {

    Block block = new Block(location, 3, 4);
    block.setWidth(5);
    assertEquals(5, block.getWidth());
  }

  @Test
  void getHeight() {

    Block block = new Block(location, 3, 4);
    assertEquals(3, block.getHeight());
  }

  @Test
  void setHeight() {

    Block block = new Block(location, 3, 4);
    block.setHeight(5);
    assertEquals(5, block.getHeight());
  }

  @Test
  void adjacentSpaces() {
    Block block = new Block(location, 3, 4);
    assertEquals(4, block.adjacentSpaces().size());
  }

  @Test
  void occupiedSpaces() {
    Block block = new Block(location, 3, 4);
    assertEquals(3 * 4, block.occupiedSpaces().size());
  }

  @Test
  void makeMove() {

    Block block = new Block(location, 3, 4);
    block.makeMove(Direction.UP);
    assertEquals(new Coordinate(0, 2), block.getLocation());
  }

  @Test
  void canMove() {

    Block block = new Block(location, 3, 4);
    assertEquals(true, block.canMove(Direction.UP));
  }

  @Test
  void testClone() {

    Block block = new Block(location, 3, 4);
    Block clone = block.clone();

    assertTrue(block.compareTo(clone) == 0);
    block.setHeight(17);

    assertFalse(block.compareTo(clone) == 0);
  }

  @Test
  void compareTo() {

    Block block = new Block(location, 3, 4);
    Block block2 = new Block(location, 3, 4);
    assertEquals(0, block.compareTo(block2));
  }
}
