package dev.plagarizers.klotski.game.block;
import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.block.Block.BlockType;
import dev.plagarizers.klotski.game.util.Coordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class BlockTest {

  @Test
  void testGetIcon_BigBlock_ReturnsB() {
    Block block = new Block(new Coordinate(0, 0), 2, 2, BlockType.BigBlock);
    String expectedIcon = "B";
    String actualIcon = block.getIcon();
    Assertions.assertEquals(expectedIcon, actualIcon, "Incorrect icon for a big block.");
  }

  @Test
  void testGetIcon_SmallBlock_ReturnsS() {
    Block block = new Block(new Coordinate(0, 0), 1, 1, BlockType.SmallBlock);
    String expectedIcon = "S";
    String actualIcon = block.getIcon();
    Assertions.assertEquals(expectedIcon, actualIcon, "Incorrect icon for a small block.");
  }

  @Test
  void testGetIcon_VerticalBlock_ReturnsV() {
    Block block = new Block(new Coordinate(0, 0), 2, 1, BlockType.VerticalBlock);
    String expectedIcon = "V";
    String actualIcon = block.getIcon();
    Assertions.assertEquals(expectedIcon, actualIcon, "Incorrect icon for a vertical block.");
  }

  @Test
  void testGetIcon_HorizontalBlock_ReturnsH() {
    Block block = new Block(new Coordinate(0, 0), 1, 2, BlockType.HorizontalBlock);
    String expectedIcon = "H";
    String actualIcon = block.getIcon();
    Assertions.assertEquals(expectedIcon, actualIcon, "Incorrect icon for a horizontal block.");
  }

  @Test
  void testGetIcon_UnknownBlock_ReturnsX() {
    Block block = new Block(new Coordinate(0, 0), 3, 3, BlockType.UnknownBlock);
    String expectedIcon = "X";
    String actualIcon = block.getIcon();
    Assertions.assertEquals(expectedIcon, actualIcon, "Incorrect icon for an unknown block.");
  }

  @Test
  void testGetOccupiedLocations_1x1Block_ReturnsSingleCoordinate() {
    Block block = new Block(new Coordinate(2, 3), 1, 1, BlockType.SmallBlock);
    List<Coordinate> expectedLocations = Arrays.asList(new Coordinate(2, 3));
    List<Coordinate> actualLocations = block.getOccupiedLocations();
    Assertions.assertEquals(expectedLocations, actualLocations, "Incorrect occupied locations for a 1x1 block.");
  }

  @Test
  void testGetOccupiedLocations_2x2Block_ReturnsFourCoordinates() {
    Block block = new Block(new Coordinate(0, 0), 2, 2, BlockType.BigBlock);
    List<Coordinate> expectedLocations = Arrays.asList(
            new Coordinate(0, 0), new Coordinate(0, 1),
            new Coordinate(1, 0), new Coordinate(1, 1)
    );
    List<Coordinate> actualLocations = block.getOccupiedLocations();
    Assertions.assertEquals(expectedLocations, actualLocations, "Incorrect occupied locations for a 2x2 block.");
  }

  @Test
  void testGetOccupiedLocations_CustomBlock_ReturnsExpectedCoordinates() {
    Block block = new Block(new Coordinate(1, 1), 3, 2, BlockType.UnknownBlock);
    List<Coordinate> expectedLocations = Arrays.asList(
            new Coordinate(1, 1), new Coordinate(1, 2),
            new Coordinate(2, 1), new Coordinate(2, 2),
            new Coordinate(3, 1), new Coordinate(3, 2)
    );
    List<Coordinate> actualLocations = block.getOccupiedLocations();
    Assertions.assertEquals(expectedLocations, actualLocations, "Incorrect occupied locations for a custom block.");
  }

  @Test
  void testSetLocation_ModifiesBlockLocation() {
    Block block = new Block(new Coordinate(2, 3), 2, 2, BlockType.BigBlock);
    Coordinate newLocation = new Coordinate(4, 5);
    block.setLocation(newLocation);
    Assertions.assertEquals(newLocation, block.getLocation(), "Block location not updated correctly.");
  }

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

  @Test
  void testEquals_EqualBlocks_ReturnsTrue() {
    Block block1 = new Block(new Coordinate(2, 3), 2, 2, BlockType.BigBlock);
    Block block2 = new Block(new Coordinate(2, 3), 2, 2, BlockType.BigBlock);
    Assertions.assertEquals(block1, block2, "Equal blocks not considered equal.");
  }

  @Test
  void testEquals_DifferentBlocks_ReturnsFalse() {
    Block block1 = new Block(new Coordinate(2, 3), 2, 2, BlockType.BigBlock);
    Block block2 = new Block(new Coordinate(3, 4), 3, 3, BlockType.SmallBlock);
    Assertions.assertNotEquals(block1, block2, "Different blocks considered equal.");
  }

  @Test
  void testToJson_ReturnsValidJsonRepresentation() {
    Block block = new Block(new Coordinate(2, 3), 2, 2, BlockType.BigBlock);
    String json = block.toJson();
    // Perform additional assertions on the JSON if needed
    Assertions.assertNotNull(json, "JSON representation is null.");
  }

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

  // Add more tests as needed

}
