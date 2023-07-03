package dev.plagarizers.klotski.game.block;

import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.block.Block.BlockType;
import dev.plagarizers.klotski.game.util.Coordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * The BlockTest class is used to test the functionality of the Block class.
 */
class BlockTest {

  /**
   * Test case for the toString() method.
   * It verifies that the string representation of the block is correct.
   */
  @Test
  void testToString_ReturnsStringRepresentation() {
    Block block = new Block(new Coordinate(0, 0), 2, 2, BlockType.BigBlock);
    String expectedString = "Block{coordinate=(0, 0), width=2, height=2}";
    String actualString = block.toString();
    Assertions.assertEquals(expectedString, actualString, "Incorrect string representation of the block.");
  }

  /**
   * Test case for the setWidth() method.
   * It verifies that the width of the block is updated correctly.
   */
  @Test
  void testSetWidth_ModifiesBlockWidth() {
    Block block = new Block(new Coordinate(0, 0), 2, 2, BlockType.BigBlock);
    int newWidth = 3;
    block.setWidth(newWidth);
    Assertions.assertEquals(newWidth, block.getWidth(), "Block width not updated correctly.");
  }

  /**
   * Test case for the setHeight() method.
   * It verifies that the height of the block is updated correctly.
   */
  @Test
  void testSetHeight_ModifiesBlockHeight() {
    Block block = new Block(new Coordinate(0, 0), 2, 2, BlockType.BigBlock);
    int newHeight = 3;
    block.setHeight(newHeight);
    Assertions.assertEquals(newHeight, block.getHeight(), "Block height not updated correctly.");
  }

  /**
   * Test case for the getX() method.
   * It verifies that the X coordinate of the block is returned correctly.
   */
  @Test
  void testGetX_ReturnsXCoordinate() {
    Block block = new Block(new Coordinate(2, 3), 2, 2, BlockType.BigBlock);
    int expectedX = 2;
    int actualX = block.getX();
    Assertions.assertEquals(expectedX, actualX, "Incorrect X coordinate for the block.");
  }

  /**
   * Test case for the getY() method.
   * It verifies that the Y coordinate of the block is returned correctly.
   */
  @Test
  void testGetY_ReturnsYCoordinate() {
    Block block = new Block(new Coordinate(2, 3), 2, 2, BlockType.BigBlock);
    int expectedY = 3;
    int actualY = block.getY();
    Assertions.assertEquals(expectedY, actualY, "Incorrect Y coordinate for the block.");
  }

  /**
   * Test case for the getIcon() method when the block is a BigBlock.
   * It verifies that the correct icon "B" is returned.
   */
  @Test
  void testGetIcon_BigBlock_ReturnsB() {
    Block block = new Block(new Coordinate(0, 0), 2, 2, BlockType.BigBlock);
    String expectedIcon = "B";
    String actualIcon = block.getIcon();
    Assertions.assertEquals(expectedIcon, actualIcon, "Incorrect icon for a big block.");
  }

  /**
   * Test case for the getIcon() method when the block is a SmallBlock.
   * It verifies that the correct icon "S" is returned.
   */
  @Test
  void testGetIcon_SmallBlock_ReturnsS() {
    Block block = new Block(new Coordinate(0, 0), 1, 1, BlockType.SmallBlock);
    String expectedIcon = "S";
    String actualIcon = block.getIcon();
    Assertions.assertEquals(expectedIcon, actualIcon, "Incorrect icon for a small block.");
  }

  /**
   * Test case for the getIcon() method when the block is a VerticalBlock.
   * It verifies that the correct icon "V" is returned.
   */
  @Test
  void testGetIcon_VerticalBlock_ReturnsV() {
    Block block = new Block(new Coordinate(0, 0), 2, 1, BlockType.VerticalBlock);
    String expectedIcon = "V";
    String actualIcon = block.getIcon();
    Assertions.assertEquals(expectedIcon, actualIcon, "Incorrect icon for a vertical block.");
  }

  /**
   * Test case for the getIcon() method when the block is a HorizontalBlock.
   * It verifies that the correct icon "H" is returned.
   */
  @Test
  void testGetIcon_HorizontalBlock_ReturnsH() {
    Block block = new Block(new Coordinate(0, 0), 1, 2, BlockType.HorizontalBlock);
    String expectedIcon = "H";
    String actualIcon = block.getIcon();
    Assertions.assertEquals(expectedIcon, actualIcon, "Incorrect icon for a horizontal block.");
  }

  /**
   * Test case for the getIcon() method when the block is an UnknownBlock.
   * It verifies that the correct icon "X" is returned.
   */
  @Test
  void testGetIcon_UnknownBlock_ReturnsX() {
    Block block = new Block(new Coordinate(0, 0), 3, 3, BlockType.UnknownBlock);
    String expectedIcon = "X";
    String actualIcon = block.getIcon();
    Assertions.assertEquals(expectedIcon, actualIcon, "Incorrect icon for an unknown block.");
  }

  /**
   * Test case for the getOccupiedLocations() method when the block is a 1x1
   * SmallBlock.
   * It verifies that the correct single coordinate is returned.
   */
  @Test
  void testGetOccupiedLocations_1x1Block_ReturnsSingleCoordinate() {
    Block block = new Block(new Coordinate(2, 3), 1, 1, BlockType.SmallBlock);
    List<Coordinate> expectedLocations = Arrays.asList(new Coordinate(2, 3));
    List<Coordinate> actualLocations = block.getOccupiedLocations();
    Assertions.assertEquals(expectedLocations, actualLocations, "Incorrect occupied locations for a 1x1 block.");
  }

  /**
   * Test case for the getOccupiedLocations() method when the block is a 2x2
   * BigBlock.
   * It verifies that the correct four coordinates are returned.
   */
  @Test
  void testGetOccupiedLocations_2x2Block_ReturnsFourCoordinates() {
    Block block = new Block(new Coordinate(0, 0), 2, 2, BlockType.BigBlock);
    List<Coordinate> expectedLocations = Arrays.asList(
        new Coordinate(0, 0), new Coordinate(0, 1),
        new Coordinate(1, 0), new Coordinate(1, 1));
    List<Coordinate> actualLocations = block.getOccupiedLocations();
    Assertions.assertEquals(expectedLocations, actualLocations, "Incorrect occupied locations for a 2x2 block.");
  }

  /**
   * Test case for the getOccupiedLocations() method when the block is a custom
   * UnknownBlock.
   * It verifies that the correct coordinates are returned.
   */
  @Test
  void testGetOccupiedLocations_CustomBlock_ReturnsExpectedCoordinates() {
    Block block = new Block(new Coordinate(1, 1), 3, 2, BlockType.UnknownBlock);
    List<Coordinate> expectedLocations = Arrays.asList(
        new Coordinate(1, 1), new Coordinate(1, 2),
        new Coordinate(2, 1), new Coordinate(2, 2),
        new Coordinate(3, 1), new Coordinate(3, 2));
    List<Coordinate> actualLocations = block.getOccupiedLocations();
    Assertions.assertEquals(expectedLocations, actualLocations, "Incorrect occupied locations for a custom block.");
  }

  /**
   * Test case for the setLocation() method.
   * It verifies that the block's location is correctly updated.
   */
  @Test
  void testSetLocation_ModifiesBlockLocation() {
    Block block = new Block(new Coordinate(2, 3), 2, 2, BlockType.BigBlock);
    Coordinate newLocation = new Coordinate(4, 5);
    block.setLocation(newLocation);
    Assertions.assertEquals(newLocation, block.getLocation(), "Block location not updated correctly.");
  }

  /**
   * Test case for the clone() method.
   * It verifies that a deep copy of the block is created.
   */
  @Test
  void testClone_CreatesDeepCopyOfBlock() {
    Block block = new Block(new Coordinate(2, 3), 2, 2, BlockType.BigBlock);
    Block clonedBlock = block.clone();
    Assertions.assertNotSame(block, clonedBlock, "Clone is not a separate instance.");
    Assertions.assertEquals(block.getLocation(), clonedBlock.getLocation(), "Clone has different location.");
    Assertions.assertEquals(block.getHeight(), clonedBlock.getHeight(), "Clone has different height.");
    Assertions.assertEquals(block.getWidth(), clonedBlock.getWidth(), "Clone has different width.");
    Assertions.assertEquals(block.getType(), clonedBlock.getType(), "Clone has different type.");
  }

  /**
   * Test case for the equals() method when the blocks are equal.
   * It verifies that true is returned.
   */
  @Test
  void testEquals_EqualBlocks_ReturnsTrue() {
    Block block1 = new Block(new Coordinate(2, 3), 2, 2, BlockType.BigBlock);
    Block block2 = new Block(new Coordinate(2, 3), 2, 2, BlockType.BigBlock);
    Assertions.assertEquals(block1, block2, "Equal blocks not considered equal.");
  }

  /**
   * Test case for the equals() method when the blocks are different.
   * It verifies that false is returned.
   */
  @Test
  void testEquals_DifferentBlocks_ReturnsFalse() {
    Block block1 = new Block(new Coordinate(2, 3), 2, 2, BlockType.BigBlock);
    Block block2 = new Block(new Coordinate(3, 4), 3, 3, BlockType.SmallBlock);
    Assertions.assertNotEquals(block1, block2, "Different blocks considered equal.");
  }

  /**
   * Test case for the toJson() method.
   * It verifies that a valid JSON representation of the block is returned.
   */
  @Test
  void testToJson_ReturnsValidJsonRepresentation() {
    Block block = new Block(new Coordinate(2, 3), 2, 2, BlockType.BigBlock);
    String json = block.toJson();
    // Perform additional assertions on the JSON if needed
    Assertions.assertNotNull(json, "JSON representation is null.");
  }

  /**
   * Test case for the fromJson() method.
   * It verifies that a block object is created from a JSON string.
   */
  @Test
  void testFromJson_CreatesBlockFromJsonString() {
    String json = "{\"location\":{\"x\":2,\"y\":3},\"height\":2,\"width\":2,\"type\":\"BigBlock\"}";
    Block block = Block.fromJson(json);
    Assertions.assertNotNull(block, "Block object not created from JSON string.");
    Assertions.assertEquals(new Coordinate(2, 3), block.getLocation(), "Incorrect location for the block.");
    Assertions.assertEquals(2, block.getHeight(), "Incorrect height for the block.");
    Assertions.assertEquals(2, block.getWidth(), "Incorrect width for the block.");
    Assertions.assertEquals(BlockType.BigBlock, block.getType(), "Incorrect type for the block.");
  }

}
