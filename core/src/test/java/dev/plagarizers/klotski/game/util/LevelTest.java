package dev.plagarizers.klotski.game.util;

import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.state.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LevelTest {

    @Test
    public void testGetName() {
        String name = "Level 1";
        Block[] board = new Block[0];
        Level level = new Level(name, board);

        assertEquals(name, level.getName());
    }

    @Test
    public void testSetName() {
        String name = "Level 1";
        Block[] board = new Block[0];
        Level level = new Level(name, board);

        String newName = "Updated Level";
        level.setName(newName);

        assertEquals(newName, level.getName());
    }

    @Test
    public void testGetBoard() {
        String name = "Level 1";
        Block[] board = new Block[0];
        Level level = new Level(name, board);

        assertArrayEquals(board, level.getBoard());
    }

    @Test
    public void testSetBoard() {
        String name = "Level 1";
        Block[] board = new Block[0];
        Level level = new Level(name, board);

        Block[] newBoard = new Block[1];
        newBoard[0] = new Block(null, 1, 1);
        level.setBoard(newBoard);

        assertArrayEquals(newBoard, level.getBoard());
    }

    @Test
    public void testFromJson() {
        String json = "[{\"level\":\"Level 1\",\"board\":[{\"location\":{\"x\":0,\"y\":0},\"height\":2,\"width\":2,\"type\":\"BigBlock\"}]}]";
        List<Level> levels = Level.fromJson(json);

        assertEquals(1, levels.size());
        Level level = levels.get(0);
        assertEquals("Level 1", level.getName());
        assertEquals(1, level.getBoard().length);
        assertEquals(2, level.getBoard()[0].getHeight());
        assertEquals(2, level.getBoard()[0].getWidth());
        assertEquals(Block.BlockType.BigBlock, level.getBoard()[0].getType());
    }

    @Test
    public void testToString() {
        String name = "Level 1";
        Block[] board = new Block[0];
        Level level = new Level(name, board);

        String expected = "Level{name='Level 1', board=[]}";
        assertEquals(expected, level.toString());
    }

    public void testToState() {
        // Create a sample block
        Coordinate location = new Coordinate(0, 0);
        int height = 2;
        int width = 2;
        Block block = new Block(location, height, width);

        // Create a sample level with the block
        Block[] blocks = { block };
        Level level = new Level("Test Level", blocks);

        // Call the toState() method
        State state = level.toState();

        // Assert the state has the same block
        Assertions.assertEquals(block, state.getBlocks()[0]);
    }
}
