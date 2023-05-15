package dev.plagarizers.klotski.model;

import dev.plagarizers.klotski.util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class State implements Cloneable, Comparable {
  public static final int ROWS = 5;
  public static final int COLS = 4;

  public static final int NUM_PIECES = 10;

  public static final Coordinate SOLUTION = Coordinate.of(3, 1);

  public void setPieces(Piece[] pieces) {
    this.pieces = pieces;
  }

  private Piece[] pieces = new Piece[NUM_PIECES];


  public State() {
    // default configuration
    pieces[0] = new Piece(new Coordinate(0, 1), 2, 2); // S
    pieces[1] = new Piece(new Coordinate(2, 1), 1, 2); // H
    pieces[2] = new Piece(new Coordinate(0, 3), 2, 1); // V
    pieces[3] = new Piece(new Coordinate(0, 0), 2, 1); // V
    pieces[4] = new Piece(new Coordinate(3, 0), 2, 1); // V
    pieces[5] = new Piece(new Coordinate(3, 3), 2, 1); // V
    pieces[6] = new Piece(new Coordinate(3, 1), 1, 1); // C
    pieces[7] = new Piece(new Coordinate(3, 2), 1, 1); // C
    pieces[8] = new Piece(new Coordinate(4, 1), 1, 1); // C
    pieces[9] = new Piece(new Coordinate(4, 2), 1, 1); // C
  }


  public State(Piece[] pieces) {
    this.pieces = pieces;
  }

  public Piece targetPiece() {
    return pieces[0];
  }

  public boolean isSolution() {
    return targetPiece().getLocation().equals(SOLUTION);
  }

  public int[][] toBoard() {
    int[][] board = new int[ROWS][COLS];
    for (int n = 0; n < pieces.length; n++) {
      Coordinate location = pieces[n].getLocation();

      for (int i = 0; i < pieces[n].getHeight(); i++) {
        for (int j = 0; j < pieces[n].getWidth(); j++) {

          if (board[location.getX() + i][location.getY() + j] != 0) {
            throw new IllegalStateException("Cannot override value");
          }
          board[location.getX() + i][location.getY() + j] = n + 1;
        }
      }
    }
    return board;
  }


//  we could need this later, let's leave it here for the moment
//  public List<Pair<Piece, Direction>> availableMoves() {
//    List<Pair<Piece, Direction>> moves = new ArrayList<>();
//    List<List<Coordinate>> occupiedSpacesCache = Arrays.stream(pieces).map(Piece::occupiedSpaces).collect(Collectors.toList());
//
//
//    for (int n = 0; n < pieces.length; n++) {
//      Piece sourcePiece = pieces[n];
//      EnumMap<Direction, List<Coordinate>> adjSpaces = sourcePiece.adjacentSpaces();
//
//      for (Direction direction : adjSpaces.keySet()) {
//        List<Coordinate> spaces = adjSpaces.get(direction);
//
//        if (spaces.isEmpty()) {
//          continue;
//        }
//
//        boolean canMove = true;
//
//        for (int m = 0; m < pieces.length; m++) {
//
//          final int m1 = m;
//          if (n == m) {
//            continue;
//          } else if (spaces.stream().anyMatch(s -> occupiedSpacesCache.get(m1).contains(s))) {
//            canMove = false;
//            break;
//          }
//        }
//
//        if (canMove) {
//          moves.add(new Pair<>(sourcePiece, direction));
//        }
//      }
//    }
//
//    return moves;
//  }

  @SuppressWarnings("DefaultLocale")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int[] row : toBoard()) {
      for (int cell : row) {
        sb.append(String.format("%3d", cell));
      }
      sb.append(System.lineSeparator());
    }
    return sb.toString();
  }

  @Override
  public int hashCode() {

    List<Integer> flattenedCoordinates = new ArrayList<>();

    for (Piece piece : pieces) {
      int x = piece.getLocation().getX() & 0b111;
      int y = piece.getLocation().getY() & 0b111;
      flattenedCoordinates.add(x);
      flattenedCoordinates.add(y);
    }

    int hash = 0;
    int shift = 0;

    for (int i = 0; i < flattenedCoordinates.size(); i++) {
      int bits = flattenedCoordinates.get(i);
      hash |= bits << (shift * 3);
      shift++;
      if (shift == 21) { // Assuming the maximum number of pieces is 10
        break;
      }
    }

    return hash;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    State other = (State) obj;

    int selfHash = hashCode();
    int otherHash = other.hashCode();

    return selfHash == otherHash;
  }

  public State clone() {
    Piece[] newPieces = new Piece[NUM_PIECES];
    for (int i = 0; i < NUM_PIECES; i++) {
      newPieces[i] = pieces[i].clone();
    }
    return new State(newPieces);
  }


  public static Coordinate applyMoveToCoords(Coordinate coordinate, Direction direction) {
    Coordinate newCoord;
    switch (direction) {
      case UP:
        newCoord = coordinate.add(-1, 0);
        if (isValidCoordinate(newCoord)) return newCoord;
        break;
      case RIGHT:
        newCoord = coordinate.add(0, 1);
        if (isValidCoordinate(newCoord)) return newCoord;

        break;
      case LEFT:
        newCoord = coordinate.add(0, -1);
        if (isValidCoordinate(newCoord)) return newCoord;

        break;
      case DOWN:
        newCoord = coordinate.add(1, 0);
        if (isValidCoordinate(newCoord)) return newCoord;
        break;
    }

    return null;
  }


  public static boolean isValidCoordinate(Coordinate coordinate) {
    return coordinate.getX() >= 0 && coordinate.getX() < ROWS && coordinate.getY() >= 0 && coordinate.getY() < COLS;
  }

  @Override
  public int compareTo(Object o) {

    if (o == null) {
      throw new NullPointerException();
    }

    if (!(o instanceof State)) {
      throw new ClassCastException();
    }

    if (this.equals(o)) {
      return 0;
    }

    return 1;
  }


  public int[] toBitBoard() {

    int[] bitBoard = new int[NUM_PIECES];

    int j = 0;
    for (Piece piece : pieces) {
      int mask = 0b0000_0000_0000_0000_0000;
      int x = piece.getLocation().getX();
      int y = piece.getLocation().getY();
      for (int row = x; row < x + piece.getHeight(); row++) {
        for (int col = y; col < y + piece.getWidth(); col++) {
          int index = 16 - (row * State.COLS) + col;
          mask |= (1 << index);
        }
      }
      bitBoard[j++] = mask;
    }

    return bitBoard;
  }


  @SuppressWarnings("NewApi")
  public static State fromBitBoard(int[] bitBoard) {
    State state = new State();

    Piece[] pieces = new Piece[NUM_PIECES];
    for (int i = 0; i < NUM_PIECES; i++) {
      pieces[i] = extractPiece(bitBoard[i]);
    }
    state.setPieces(pieces);
    return state;
  }

  private static Piece extractPiece(int block) {
    int y = -1, x = -1, width = -1, height = -1;

    for (int row = 0; row < State.ROWS; row++) {
      for (int col = 0; col < State.COLS; col++) {
        int bit = (block >> (State.ROWS - row - 1) * State.COLS + col) & 1;

        if (bit == 1) {
          if (y == -1) {
            y = col;
            x = row;
            width = 1;
            height = 1;
          } else {
            width = Math.max(width, col - y + 1);
            height = Math.max(height, row - x + 1);
          }
        }
      }
    }

    return new Piece(Coordinate.of(x, y), height, width);
  }
}
