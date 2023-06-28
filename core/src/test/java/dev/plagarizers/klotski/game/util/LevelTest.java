package dev.plagarizers.klotski.game.util;

import dev.plagarizers.klotski.game.block.BigBlock;
import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.block.HorizontalBlock;
import dev.plagarizers.klotski.game.block.SmallBlock;
import dev.plagarizers.klotski.game.block.VerticalBlock;
import dev.plagarizers.klotski.game.state.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LevelTest {

    @Test
    void testGetName_Level_Returns1() {
        // Arrange
        String name = "Level 1";
        Block[] board = new Block[0];
        Level level = new Level(name, board);

        // Act
        String levelName = level.getName();

        // Assert
        assertEquals(name, levelName, "Unexpected level name");
    }

    @Test
    void testSetName_Level_NameUpdate() {
        // Arrange
        String name = "Level 1";
        Block[] board = new Block[0];
        Level level = new Level(name, board);
        String newName = "Updated Level";

        // Act
        level.setName(newName);
        String updatedName = level.getName();

        // Assert
        assertEquals(newName, updatedName, "Level name not updated correctly");
    }

    @Test
    void testGetBoard_Level_ReturnsBoard() {
        // Arrange
        String name = "Level 1";
        Block[] board = new Block[0];
        Level level = new Level(name, board);

        // Act
        Block[] retrievedBoard = level.getBoard();

        // Assert
        assertArrayEquals(board, retrievedBoard, "Unexpected board");
    }

    @Test
    void testSetBoard_Level_SetBoard() {
        // Arrange
        String name = "Level 1";
        Block[] board = new Block[0];
        Level level = new Level(name, board);
        Block[] newBoard = new Block[1];
        newBoard[0] = new Block(null, 1, 1);

        // Act
        level.setBoard(newBoard);
        Block[] updatedBoard = level.getBoard();

        // Assert
        assertArrayEquals(newBoard, updatedBoard, "Board not set correctly");
    }

    @Test
    void testFromJson_Level_ValidJsonData() {
        // Arrange
        String json = "[{\"level\":\"Level 1\",\"board\":[{\"location\":{\"x\":0,\"y\":0},\"height\":2,\"width\":2,\"type\":\"BigBlock\"}]}]";

        // Act
        List<Level> levels = Level.fromJson(json);
        Level level = levels.get(0);

        // Assert
        assertEquals(1, levels.size(), "Unexpected number of levels");
        assertEquals("Level 1", level.getName(), "Unexpected level name");
        assertEquals(1, level.getBoard().length, "Unexpected board length");
        assertEquals(2, level.getBoard()[0].getHeight(), "Unexpected block height");
        assertEquals(2, level.getBoard()[0].getWidth(), "Unexpected block width");
        assertEquals(Block.BlockType.BigBlock, level.getBoard()[0].getType(), "Unexpected block type");
    }

    @Test
    void testToString_LEvel_ReturnsExpectedString() {
        // Arrange
        String name = "Level 1";
        Block[] board = new Block[0];
        Level level = new Level(name, board);
        String expected = "Level{name='Level 1', board=[]}";

        // Act
        String levelString = level.toString();

        // Assert
        assertEquals(expected, levelString, "Unexpected string representation of level");
    }

    @Test
    void testToState_Level_GeneratesValidStateFromLevel() {
        // Arrange
        Block[] board = {
                new BigBlock(new Coordinate(0, 1)),
                new HorizontalBlock(new Coordinate(2, 1)),
                new VerticalBlock(new Coordinate(0, 3)),
                new VerticalBlock(new Coordinate(0, 0)),
                new VerticalBlock(new Coordinate(3, 0)),
                new VerticalBlock(new Coordinate(3, 3)),
                new SmallBlock(new Coordinate(3, 1)),
                new SmallBlock(new Coordinate(3, 2)),
                new SmallBlock(new Coordinate(4, 1)),
                new SmallBlock(new Coordinate(4, 2))
        };
        Level level = new Level("Sample Level", board);

        // Act
        State state = level.toState();

        // Assert
        assertEquals(10, state.getBlocks().length);
        assertEquals(0, state.getMoves());
        // Add more assertions if needed to verify the state's correctness
    }
}
