package dev.plagarizers.klotski.game.state;

import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.util.Coordinate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;

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
  void testSetBlockThrowsException() {
    Block[] newBlocks = new Block[11];
    assertThrows(IllegalArgumentException.class, () -> state.setBlocks(newBlocks));
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

    // sort bitBoard
    Arrays.sort(bitBoard);

    State newState = State.fromBitBoard(bitBoard);

    int[] bitBoard2 = newState.toBitBoard();
    Arrays.sort(bitBoard2);

    assertNotNull(newState);
    assertArrayEquals(bitBoard, bitBoard2);

  }


  @Test
  void testIsSolution() {
    State state = State.fromDefaultConfiguration();
    assertFalse(state.isSolution());
  }
}

