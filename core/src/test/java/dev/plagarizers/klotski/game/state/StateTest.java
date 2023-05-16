package dev.plagarizers.klotski.game.state;

import dev.plagarizers.klotski.game.block.BigBlock;
import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.util.Coordinate;
import dev.plagarizers.klotski.game.util.Direction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StateTest {

  private State state;

  @BeforeAll
  void setUp() {
    state = State.fromDefaultConfiguration();
  }

  @BeforeEach
  void resetState() {
    state = State.fromDefaultConfiguration();
  }

  @Test
  void testGetBlocks() {
    Block[] blocks = state.getBlocks();
    assertNotNull(blocks);
    assertEquals(10, blocks.length);
  }

  @Test
  void testSetBlocks() {
    Block[] newBlocks = new Block[10];
    state.setBlocks(newBlocks);
    assertSame(newBlocks, state.getBlocks());
  }

  @Test
  void testApplyMoveToCoords() {
    Coordinate initialCoord = Coordinate.of(0, 1);

    // Test valid move
    Coordinate newCoord = State.applyMoveToCoords(initialCoord, Direction.DOWN);
    assertNotNull(newCoord);
    assertEquals(1, newCoord.getX());
    assertEquals(1, newCoord.getY());

    // Test invalid move
    newCoord = State.applyMoveToCoords(initialCoord, Direction.LEFT);
    assertNotNull(newCoord);
    assertEquals(0, newCoord.getY());
    assertEquals(0, newCoord.getX());

    // Test invalid move

    newCoord = State.applyMoveToCoords(Coordinate.of(0, 0), Direction.LEFT);
    assertNull(newCoord);
  }

  @Test
  void testIsValidCoordinate() {
    assertTrue(State.isValidCoordinate(Coordinate.of(0, 0)));
    assertTrue(State.isValidCoordinate(Coordinate.of(4, 3)));
    assertFalse(State.isValidCoordinate(Coordinate.of(5, 2)));
    assertFalse(State.isValidCoordinate(Coordinate.of(-1, 3)));
  }

  @Test
  void testFromBitBoard() {
    int[] bitBoard = {
      0b0110_0110_0000_0000_0000,
      0b0000_0000_0110_0000_0000,
      0b1000_1000_0000_0000_0000,
      0b0001_0001_0000_0000_0000,
      0b0000_0000_1000_1000_0000,
      0b0000_0000_0001_0001_0000,
      0b0000_0000_0000_0000_1000,
      0b0000_0000_0000_0100_0000,
      0b0000_0000_0000_0010_0000,
      0b0000_0000_0000_0000_0001};

    State newState = State.fromBitBoard(bitBoard);

    assertNotNull(newState);
    assertArrayEquals(bitBoard, newState.toBitBoard());
  }


  @Test
  void testTargetPiece() {
    Block targetPiece = state.targetPiece();
    assertNotNull(targetPiece);
    assertTrue(targetPiece instanceof BigBlock);
  }

  @Test
  void testIsSolution() {
    assertFalse(state.isSolution());

    // Move the target piece to the solution position
    state.getBlocks()[0].getLocation().setX(3);
    state.getBlocks()[0].getLocation().setY(1);
    assertTrue(state.isSolution());
  }
}

