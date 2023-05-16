package dev.plagarizers.klotski.game.state;

import dev.plagarizers.klotski.game.block.BigBlock;
import dev.plagarizers.klotski.game.block.Block;

import java.util.*;


/**
 * The KlotskiSolver class provides methods to solve the Klotski puzzle.
 * It utilizes a compressed representation of the board for efficient solving.
 */

public class KlotskiSolver {

  int target = 0b0000_0000_0000_0110_0110;
  private final int[] bitBoard;
  private List<State> pathToSolution = new ArrayList<>();

  /**
   * Constructs a KlotskiSolver object with the initial state of the puzzle.
   *
   * @param state The initial state of the Klotski puzzle.
   */
  public KlotskiSolver(State state) {
    bitBoard = state.toBitBoard();
    Block targetPiece = new BigBlock(State.SOLUTION);
    target = State.createBitMaskForBlock(targetPiece);
  }

  private static int log2(int n) {
    if (n <= 0) throw new IllegalArgumentException();
    return 31 - Integer.numberOfLeadingZeros(n);
  }

  /**
   * Finds the minimum number of steps required to reach the goal state from the initial state.
   * It uses a breadth-first search (BFS) approach to explore the possible states and find the shortest path.
   *
   * @return The minimum number of steps to reach the goal state, or -1 if no path is found.
   */
  public int minSteps() {

    Queue<int[]> queue = new LinkedList<>(Collections.singleton(bitBoard));
    Set<Long> visited = new HashSet<>(Collections.singleton(this.compress(bitBoard)));
    Map<int[], int[]> paths = new HashMap<>();
    paths.put(bitBoard, null);

    return this.bfs(0, queue, target, visited, paths);
  }

  /**
   * Performs a breadth-first search (BFS) to find the shortest path from the start state
   * to the goal state. It explores the states in the queue, generating and enqueueing their
   * next states until the goal state is found or the queue becomes empty.
   *
   * @param step    The current step in the search process.
   * @param queue   The queue of states to explore.
   * @param target  The goal state.
   * @param visited The set of visited states.
   * @param parents The map of parent states.
   * @return The number of steps taken to reach the goal state, or -1 if no path is found.
   */
  private int bfs(int step, Queue<int[]> queue, int target, Set<Long> visited, Map<int[], int[]> parents) {
    // If the queue is empty, no path is found
    if (queue.isEmpty()) return -1;

    step++;
    Queue<int[]> nextQueue = new LinkedList<>();

    while (!queue.isEmpty()) {
      int[] current = queue.remove();

      // Generate and enqueue the next states
      List<int[]> nextStates = this.getNextStates(current);
      for (int[] state : nextStates) {

        // Skip visited states
        if (visited.contains(this.compress(state))) continue;

        // Record the parent state
        parents.put(state, current);

        // If the goal state is found, reconstruct the path and return the number of steps
        if (state[0] == target) {
          pathToSolution = reconstructPath(parents, state);
          return step;
        }
        nextQueue.add(state);
        visited.add(this.compress(state));
        visited.add(this.compress(this.getMirroredState(state)));
      }
    }

    // Continue the BFS with the next queue
    return this.bfs(step, nextQueue, target, visited, parents);
  }

  /**
   * Retrieves the path to the solution after the puzzle is solved.
   *
   * @return A list of states representing the path to the solution.
   */
  public List<State> getPathToSolution() {
    return pathToSolution;
  }

  /**
   * Reconstructs the path from the start state to the goal state using the given map
   * of parent states. Starting from the goal state, it iteratively follows the parent
   * states until reaching the start state, building the path in reverse order.
   *
   * @param parents The map of parent states where the key is the child state and the
   *                value is the corresponding parent state.
   * @param goal    The goal state.
   * @return The list of states representing the path from the start state to the goal state.
   */
  private List<State> reconstructPath(Map<int[], int[]> parents, int[] goal) {
    List<State> path = new ArrayList<>();
    int[] current = goal;

    // Follow the parent states from the goal state to the start state
    while (current != null) {
      path.add(State.fromBitBoard(current));
      current = parents.get(current);
    }

    // Reverse the path to obtain the correct order
    Collections.reverse(path);
    return path;
  }

  /**
   * Returns the mirrored state of the given state.
   * The mirrored state is obtained by flipping the blocks horizontally
   * along the vertical axis.
   *
   * @param state The state to mirror.
   * @return The mirrored state.
   */
  private int[] getMirroredState(int[] state) {
    int[] mirroredState = new int[state.length];
    for (int i = 0; i < state.length; i++) {
      int block = state[i];
      // Check if the block is on the left border
      if (this.isOnLeftBorder(block)) {
        mirroredState[i] = block >> (i < 2 ? 2 : 3); // Shift the block to the right to mirror it
      }
      // Check if the block is on the right border
      else if (this.isOnRightBorder(block)) {
        mirroredState[i] = block << (i < 2 ? 2 : 3); // Shift the block to the left to mirror it
      }
      // Check if the block is in the middle
      else if (i > 1) {

        // Mirror the block based on its position
        if (this.isOnLeftBorder(block << 1)) mirroredState[i] = block >> 1; // Shift the block to the right to mirror it
        if (this.isOnRightBorder(block >> 1)) mirroredState[i] = block << 1; // Shift the block to the left to mirror it
      }
      // Copy the block as it is (top row)
      else mirroredState[i] = block;
    }
    return mirroredState;
  }

  /**
   * Returns a list of all possible next states from the given current state.
   * A next state is obtained by moving each block in all possible {@code Direction}
   * (up, down, left, and right) based on the current state.
   *
   * @param blocks The current state represented as an array of blocks.
   * @return A list of all possible next states from the current state.
   */
  private List<int[]> getNextStates(int[] blocks) {
    List<int[]> nextStates = new ArrayList<>();

    for (int i = 0; i < blocks.length; i++) {

      int original = blocks[i];

      // Move block upwards
      blocks[i] = original << 4;
      if (isValidBoard(blocks)) {
        nextStates.add(blocks.clone());

        // Move the block upwards twice if not in the first column
        if (i != 0) {
          blocks[i] = original << 8;
          if (isValidBoard(blocks)) nextStates.add(blocks.clone());
        }

        // Move the block to the left if not in the top row and not on the left border
        if (i > 5 && !isOnLeftBorder(original)) {
          blocks[i] = original << 5;
          if (isValidBoard(blocks)) nextStates.add(blocks.clone());
        }

        // Move the block to the right if not in the top row and not on the right border
        if (i > 5 && !isOnRightBorder(original)) {
          blocks[i] = original << 3;
          if (isValidBoard(blocks)) nextStates.add(blocks.clone());
        }
      }

      // Move block downwards
      blocks[i] = original >> 4;
      if (isValidBoard(blocks)) {
        nextStates.add(blocks.clone());
        // Move the block downwards twice if not in the first column
        if (i != 0) {
          blocks[i] = original >> 8;
          if (isValidBoard(blocks)) nextStates.add(blocks.clone());
        }
        // Move the block to the left if not in the top row and not on the left border
        if (i > 5 && !isOnLeftBorder(original)) {
          blocks[i] = original >> 3;
          if (isValidBoard(blocks)) nextStates.add(blocks.clone());
        }
        // Move the block to the right if not in the top row and not on the right border
        if (i > 5 && !isOnRightBorder(original)) {
          blocks[i] = original >> 5;
          if (isValidBoard(blocks)) nextStates.add(blocks.clone());
        }
      }


      // Move the block to the left if not on the left border
      if (!isOnLeftBorder(original)) {
        blocks[i] = original << 1;
        if (isValidBoard(blocks)) {
          nextStates.add(blocks.clone());

          // Move the block to the left twice if not in the first column and not on the left border
          if (i != 0 && !isOnLeftBorder(original << 1)) {
            blocks[i] = original << 2;
            if (isValidBoard(blocks)) nextStates.add(blocks.clone());
          }
          // Move the block to the left and up if not in the top row
          if (i > 5) {
            blocks[i] = original << 5;
            if (isValidBoard(blocks)) nextStates.add(blocks.clone());

            blocks[i] = original >> 3;
            if (isValidBoard(blocks)) nextStates.add(blocks.clone());
          }
        }
      }

      // Move the block to the right if not on the right border
      if (!isOnRightBorder(original)) {
        blocks[i] = original >> 1;
        if (isValidBoard(blocks)) {
          nextStates.add(blocks.clone());

          // Move the block to the right twice if not in the first column and not on the right border
          if (i != 0 && !isOnRightBorder(original >> 1)) {
            blocks[i] = original >> 2;
            if (isValidBoard(blocks)) nextStates.add(blocks.clone());
          }
          // Move the block to the right and down if in the bottom row

          if (i > 5) {
            blocks[i] = original << 3;
            if (isValidBoard(blocks)) nextStates.add(blocks.clone());
            // Move the block to the right and up if not in the top row
            blocks[i] = original >> 5;
            if (isValidBoard(blocks)) nextStates.add(blocks.clone());
          }
        }
      }


      blocks[i] = original;
    }

    return nextStates;
  }

  /**
   * Checks if a block is positioned on the right border of the game board.
   *
   * @param block The block value representing a specific position on the board.
   * @return {@code true} if the block is on the right border, {@code false} otherwise.
   */
  private boolean isOnRightBorder(int block) {
    // 16 means the right border of the bottom row and so on, 12, 8, 4, 0 means the right border of other lines
    int[] borderIndices = new int[]{0, 4, 8, 12, 16};
    for (int index : borderIndices) if (((block >> index) & 1) == 1) return true;
    return false;
  }

  /**
   * Checks if a block is positioned on the left border of the game board.
   *
   * @param block The block value representing a specific position on the board.
   * @return {@code true} if the block is on the left border, {@code false} otherwise.
   */
  private boolean isOnLeftBorder(int block) {
    // 3 means the left border of the bottom row and so on, 7, 11, 15, 19 means the left border of other lines
    int[] borderIndices = new int[]{3, 7, 11, 15, 19};
    for (int index : borderIndices) if (((block >> index) & 1) == 1) return true;
    return false;
  }

  /**
   * Checks if the given board configuration is valid according to the game rules.
   *
   * @param board The array representing the board configuration.
   * @return {@code true} if the board is valid, {@code false} otherwise.
   */
  private boolean isValidBoard(int[] board) {
    if (board.length != 10) return false;
    int occupiedSlots = 0b0;

    for (int block : board) {
      // Check if the block value exceeds the maximum valid value.
      // The maximum valid value is (1 << 20) - 1, which represents all 20 bits set to 1.
      // If the block value is greater than this, it means it has more than 20 bits,
      // indicating an invalid block configuration.

      if (block > ((1 << 20) - 1)) return false;

      // Check for overlapping blocks by bitwise AND operation.
      // If the result is non-zero, it means there is a common bit set,
      // indicating overlapping blocks and an invalid configuration.
      if ((occupiedSlots & block) != 0) return false;
      occupiedSlots |= block;
    }

    // Check if there are any empty spaces on the board
    // by counting the number of bits set in the 'occupiedSlots' variable.
    // The 'occupiedSlots &= occupiedSlots - 1' operation clears the least significant bit in 'occupiedSlots'
    // until all bits representing occupied spaces are removed.
    // If any bits remain, it indicates the presence of empty spaces.
    for (int i = 0; i < 17; i++) occupiedSlots &= occupiedSlots - 1;
    return occupiedSlots != 0;
  }

  /**
   * Compresses the given array of blocks into a single long value.
   * It performs bitwise operations to extract the lowest set bit from each block and combines them into the result.
   *
   * @param blocks The array of blocks to compress.
   * @return The compressed representation of the blocks.
   */
  private Long compress(int[] blocks) {
    int[] sortedBlocks = new int[10];
    sortedBlocks[0] = blocks[0];
    sortedBlocks[1] = blocks[1];


    int[] verticalBlocks = new int[]{blocks[2], blocks[3], blocks[4], blocks[5]};
    Arrays.sort(verticalBlocks);
    sortedBlocks[2] = verticalBlocks[0];
    sortedBlocks[3] = verticalBlocks[1];
    sortedBlocks[4] = verticalBlocks[2];
    sortedBlocks[5] = verticalBlocks[3];


    int[] horizontalBlocks = new int[]{blocks[6], blocks[7], blocks[8], blocks[9]};
    Arrays.sort(horizontalBlocks);
    sortedBlocks[6] = horizontalBlocks[0];
    sortedBlocks[7] = horizontalBlocks[1];
    sortedBlocks[8] = horizontalBlocks[2];
    sortedBlocks[9] = horizontalBlocks[3];


    long compressedValue = 0b0;

    for (int i = 0; i < sortedBlocks.length; i++) {
      // Find the lowest significant bit of the block using the 'log2' method.
      long lowestBit = KlotskiSolver.log2(sortedBlocks[i] & -sortedBlocks[i]);
      // Concatenate the lowest significant bit to the compressed value
      // by shifting it to the appropriate position.
      compressedValue |= (lowestBit << (i * 5));
    }
    return compressedValue;
  }

}
