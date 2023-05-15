package dev.plagarizers.klotski.util;

import dev.plagarizers.klotski.model.State;

import java.util.*;

public class KlotskiSolver {

  private int[] bitBoard;

  int target = 0b0000_0000_0000_0110_0110;

  private List<State> pathToSolution = new ArrayList<>();


//  public KlotskiSolver() {
//    bitBoard = new int[10];
//    bitBoard[0] = 0b0110_0110_0000_0000_0000; // S
//    bitBoard[1] = 0b0000_0000_0110_0000_0000; // H
//    bitBoard[2] = 0b1000_1000_0000_0000_0000; // V
//    bitBoard[3] = 0b0001_0001_0000_0000_0000; // V
//    bitBoard[4] = 0b0000_0000_1000_1000_0000; // V
//    bitBoard[5] = 0b0000_0000_0001_0001_0000; // V
//    bitBoard[6] = 0b0000_0000_0000_0000_1000; // C
//    bitBoard[7] = 0b0000_0000_0000_0100_0000; // C
//    bitBoard[8] = 0b0000_0000_0000_0010_0000; // C
//    bitBoard[9] = 0b0000_0000_0000_0000_0001; // C
//  }


  public KlotskiSolver(State state) {
    bitBoard = state.toBitBoard();
  }


  public int minSteps() {

    Queue<int[]> queue = new LinkedList<>(Collections.singleton(bitBoard));
    Set<Long> visited = new HashSet<>(Collections.singleton(this.compress(bitBoard)));
    Map<int[], int[]> paths = new HashMap<>();
    paths.put(bitBoard, null);

    return this.bfs(0, queue, target, visited, paths);
  }


  private int bfs(int step, Queue<int[]> queue, int target, Set<Long> visited, Map<int[], int[]> paths) {

    if (queue.isEmpty()) return -1;

    step++;
    Queue<int[]> nextQueue = new LinkedList<>();

    while (!queue.isEmpty()) {
      int[] current = queue.remove();
      List<int[]> nextStates = this.getNextStates(current);
      for (int[] state : nextStates) {
        if (visited.contains(this.compress(state))) continue;

        paths.put(state, current);

        if (state[0] == target) {
          reconstructPath(paths, state);
          return step;
        }

        nextQueue.add(state);
        visited.add(this.compress(state));
        visited.add(this.compress(this.getMirroredState(state)));
      }
    }


    return this.bfs(step, nextQueue, target, visited, paths);
  }

  public List<State> getPathToSolution() {
    return pathToSolution;
  }

  private void reconstructPath(Map<int[], int[]> steps, int[] current) {

    Stack<int[]> stack = new Stack<>();

    while (steps.get(current) != null) {
      stack.push(current);
      current = steps.get(current);
    }

    pathToSolution.add(State.fromBitBoard(current));

    while (!stack.isEmpty()) {
      pathToSolution.add(State.fromBitBoard(stack.pop()));
    }

  }

  private int[] getMirroredState(int[] state) {
    int len = state.length;
    int[] mirror = new int[len];
    for (int i = 0; i < len; i++) {
      if (this.isOnLeftBorder(state[i])) mirror[i] = state[i] >> (i < 2 ? 2 : 3);
      else if (this.isOnRightBorder(state[i])) mirror[i] = state[i] << (i < 2 ? 2 : 3);
      else if (i > 1) {
        if (this.isOnLeftBorder(state[i] << 1)) mirror[i] = state[i] >> 1;
        if (this.isOnRightBorder(state[i] >> 1)) mirror[i] = state[i] << 1;
      } else mirror[i] = state[i];
    }
    return mirror;
  }


  private List<int[]> getNextStates(int[] blocks) {
    List<int[]> nextStates = new ArrayList<>();

    for (int i = 0; i < blocks.length; i++) {

      int original = blocks[i];


      blocks[i] = original << 4;
      if (isValidBoard(blocks)) {
        nextStates.add(blocks.clone());
        if (i != 0) {
          blocks[i] = original << 8;
          if (isValidBoard(blocks)) nextStates.add(blocks.clone());
        }
        if (i > 5 && !isOnLeftBorder(original)) {
          blocks[i] = original << 5;
          if (isValidBoard(blocks)) nextStates.add(blocks.clone());
        }
        if (i > 5 && !isOnRightBorder(original)) {
          blocks[i] = original << 3;
          if (isValidBoard(blocks)) nextStates.add(blocks.clone());
        }
      }


      blocks[i] = original >> 4;
      if (isValidBoard(blocks)) {
        nextStates.add(blocks.clone());
        if (i != 0) {
          blocks[i] = original >> 8;
          if (isValidBoard(blocks)) nextStates.add(blocks.clone());
        }
        if (i > 5 && !isOnLeftBorder(original)) {
          blocks[i] = original >> 3;
          if (isValidBoard(blocks)) nextStates.add(blocks.clone());
        }
        if (i > 5 && !isOnRightBorder(original)) {
          blocks[i] = original >> 5;
          if (isValidBoard(blocks)) nextStates.add(blocks.clone());
        }
      }


      if (!isOnLeftBorder(original)) {
        blocks[i] = original << 1;
        if (isValidBoard(blocks)) {
          nextStates.add(blocks.clone());

          if (i != 0 && !isOnLeftBorder(original << 1)) {
            blocks[i] = original << 2;
            if (isValidBoard(blocks)) nextStates.add(blocks.clone());
          }
          if (i > 5) {
            blocks[i] = original << 5;
            if (isValidBoard(blocks)) nextStates.add(blocks.clone());

            blocks[i] = original >> 3;
            if (isValidBoard(blocks)) nextStates.add(blocks.clone());
          }
        }
      }


      if (!isOnRightBorder(original)) {
        blocks[i] = original >> 1;
        if (isValidBoard(blocks)) {
          nextStates.add(blocks.clone());

          if (i != 0 && !isOnRightBorder(original >> 1)) {
            blocks[i] = original >> 2;
            if (isValidBoard(blocks)) nextStates.add(blocks.clone());
          }
          if (i > 5) {
            blocks[i] = original << 3;
            if (isValidBoard(blocks)) nextStates.add(blocks.clone());

            blocks[i] = original >> 5;
            if (isValidBoard(blocks)) nextStates.add(blocks.clone());
          }
        }
      }


      blocks[i] = original;
    }

    return nextStates;
  }

  private boolean isOnRightBorder(int block) {


    int[] rightBorders = new int[]{0, 4, 8, 12, 16};
    for (int rightBorder : rightBorders) if (((block >> rightBorder) & 1) == 1) return true;
    return false;
  }

  private boolean isOnLeftBorder(int block) {
    // Left border array, binary pair of low 0's count, 3 means the left border of the bottom row
    // and so on, 7, 11, 15, 19 means the left border of other lines
    int[] leftBorders = new int[]{3, 7, 11, 15, 19};
    for (int leftBorder : leftBorders) if (((block >> leftBorder) & 1) == 1) return true;
    return false;
  }


  private boolean isValidBoard(int[] board) {
    if (board.length != 10) return false;
    int occupying = 0b0;
    for (int block : board) {
      if (block > ((1 << 20) - 1)) return false;
      if ((occupying & block) != 0) return false;
      occupying |= block;
    }

    for (int i = 0; i < 17; i++) occupying &= occupying - 1;
    return occupying != 0;
  }


  private Long compress(int[] blocks) {
    int[] temp = new int[10];
    temp[0] = blocks[0];
    temp[1] = blocks[1];


    int[] vArr = new int[]{blocks[2], blocks[3], blocks[4], blocks[5]};
    Arrays.sort(vArr);
    temp[2] = vArr[0];
    temp[3] = vArr[1];
    temp[4] = vArr[2];
    temp[5] = vArr[3];


    int[] cArr = new int[]{blocks[6], blocks[7], blocks[8], blocks[9]};
    Arrays.sort(cArr);
    temp[6] = cArr[0];
    temp[7] = cArr[1];
    temp[8] = cArr[2];
    temp[9] = cArr[3];


    long res = 0b0;

    for (int i = 0; i < temp.length; i++) {

      long lowest = KlotskiSolver.log2(temp[i] & -temp[i]);


      res |= (lowest << (i * 5));
    }
    return res;
  }

  private static int log2(int n) {
    if (n <= 0) throw new IllegalArgumentException();
    return 31 - Integer.numberOfLeadingZeros(n);
  }


}
