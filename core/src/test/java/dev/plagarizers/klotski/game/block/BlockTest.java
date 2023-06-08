package dev.plagarizers.klotski.game.block;


import dev.plagarizers.klotski.game.util.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BlockTest {

  private Coordinate location;

  @BeforeEach
  void setUp() {
    location = new Coordinate(1, 2);
  }

  @Test
  void getLocation() {

    Block block = new Block(location, 2, 2);
    assertEquals(location, block.getLocation());
  }

  @Test
  void setLocation() {

    Block block = new Block(location, 1, 1);
    Coordinate newLocation = new Coordinate(5, 6);
    block.setLocation(newLocation);
    assertEquals(newLocation, block.getLocation());
  }

  @Test
  void getWidth() {

    Block block = new Block(location, 1, 2);
    assertEquals(2, block.getWidth());
  }

  @Test
  void setWidth() {
    Block block = new Block(location, 1, 1);
    block.setWidth(5);
    assertEquals(5, block.getWidth());
  }

  @Test
  void getHeight() {

    Block block = new Block(location, 2, 2);
    assertEquals(2, block.getHeight());
  }

  @Test
  void setHeight() {

    Block block = new Block(location, 1, 2);
    block.setHeight(5);
    assertEquals(5, block.getHeight());
  }


  @Test
  void occupiedSpaces() {
    Block block = new Block(location, 2, 1);
    assertEquals(2*1, block.occupiedSpaces().size());
  }


  @Test
  void testClone() {

    Block block = new Block(location, 1, 1);
    Block clone = block.clone();

    assertEquals(0, block.compareTo(clone));
    block.setHeight(17);

    assertNotEquals(0, block.compareTo(clone));
  }

  @Test
  void compareTo() {

    Block block = new Block(location, 2, 2);
    Block block2 = new Block(location, 2, 2);
    assertEquals(0, block.compareTo(block2));
  }
}
