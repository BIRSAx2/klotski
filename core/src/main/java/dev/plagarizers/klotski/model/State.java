package dev.plagarizers.klotski.model;

import dev.plagarizers.klotski.util.Pair;

import java.util.*;

public class State implements Cloneable {
  public static final int ROWS = 5;
  public static final int COLS = 4;

  public static final int NUM_PIECES = 10;

  public static final Coordinate SOLUTION = Coordinate.of(3, 1);

  private Piece[] pieces = new Piece[NUM_PIECES];


  public State() {
    pieces[0] = new Piece(new Coordinate(0, 1), 2, 2);
    pieces[1] = new Piece(new Coordinate(0, 0), 2, 1);
    pieces[2] = new Piece(new Coordinate(0, 3), 2, 1);
    pieces[3] = new Piece(new Coordinate(2, 1), 1, 2);
    pieces[4] = new Piece(new Coordinate(3, 0), 2, 1);
    pieces[5] = new Piece(new Coordinate(3, 3), 2, 1);
    pieces[6] = new Piece(new Coordinate(3, 1), 1, 1);
    pieces[7] = new Piece(new Coordinate(3, 2), 1, 1);
    pieces[8] = new Piece(new Coordinate(4, 1), 1, 1);
    pieces[9] = new Piece(new Coordinate(4, 2), 1, 1);
  }


  public State(Piece[] pieces) {
    this.pieces = pieces;
  }

  public Piece targetPiece() {
    return pieces[0];
  }

  public boolean isSolution() {
    return targetPiece().getCoordinate().equals(SOLUTION);
  }

  public int[][] toBoard() {
    int[][] board = new int[ROWS][COLS];
    for (int n = 0; n < pieces.length; n++) {
      Piece piece = pieces[n];
      Coordinate coor = piece.getCoordinate();
      int x = coor.getX();
      int y = coor.getY();
      int height = piece.getHeight();
      int width = piece.getWidth();

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {

          if (board[x + i][y + j] != 0) {
            throw new IllegalStateException("Cannot override value");
          }
          board[x + i][y + j] = n + 1;
        }
      }
    }
    return board;
  }

  public List<Pair<Piece, Direction>> availableMoves() {

    List<Pair<Piece, Direction>> moves = new ArrayList<>();

    List<List<Coordinate>> occupiedSpacesCache = new ArrayList<>();
    for (int i = 0; i < NUM_PIECES; i++) {
      occupiedSpacesCache.add(pieces[i].occupiedSpaces());
    }

    for (int n = 0; n < NUM_PIECES; n++) {
      Piece sourcePiece = pieces[n];
      EnumMap<Direction, List<Coordinate>> adjacentSpaces = sourcePiece.adjacentSpaces();

      for (Map.Entry<Direction, List<Coordinate>> entry : adjacentSpaces.entrySet()) {
        Direction direction = entry.getKey();
        List<Coordinate> spaces = entry.getValue();
        if (spaces.isEmpty()) continue;


        boolean canMove = true;

        for (int m = 0; m < NUM_PIECES; m++) {
          if (m == n) continue;


          Piece targetPiece = pieces[m];
          List<Coordinate> occupiedSpaces = occupiedSpacesCache.get(m);
          for (Coordinate space : spaces) {
            if (occupiedSpaces.contains(space)) {
              canMove = false;
              break;
            }
          }
        }


        if (canMove) {
          moves.add(new Pair<>(sourcePiece, direction));
        }

      }
    }


    return moves;
  }


  public List<State> nextStates2() {
    List<Pair<State, Integer>> nextStates = nextStates();

    List<State> states = new ArrayList<>();
    for (Pair<State, Integer> pair : nextStates) {
      states.add(pair.getFirst());
    }
    return states;
  }

  public List<Pair<State, Integer>> nextStates() {
    List<Pair<State, Integer>> nextStates = new ArrayList<>();
    List<Pair<Piece, Direction>> moves = availableMoves();
    for (Pair<Piece, Direction> move : moves) {
      Piece sourcePiece = move.getFirst();
      State newState = this.clone();

      Piece found = null;
      for (Piece p : newState.pieces) {
        if (p.getCoordinate() == sourcePiece.getCoordinate()) {
          found = p;
          break;
        }
      }

      if (found == null) {
        throw new IllegalStateException("Piece not found");
      }

      nextStates.add(new Pair<>(newState, 1));
    }
    return nextStates;
  }

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
      int x = piece.getCoordinate().getX() & 0b111;
      int y = piece.getCoordinate().getY() & 0b111;
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

  private Integer calculateHeuristic(State state) {
    Coordinate targetCoor = state.targetPiece().getCoordinate();
    int tx = targetCoor.getX();
    int ty = targetCoor.getY();
    int sx = SOLUTION.getX();
    int sy = SOLUTION.getY();
    return Math.abs(tx - sx) + Math.abs(ty - sy);
  }

  public static Coordinate applyMoveToCoords(Coordinate coordinate, Direction direction) {


    Coordinate newCoord;
    switch (direction) {
      case UP:
        newCoord = coordinate.add(-1, 0);
      case DOWN:
        newCoord = coordinate.add(0, 1);
      case LEFT:
        newCoord = coordinate.add(0, -1);
      case RIGHT:
        newCoord = coordinate.add(1, 0);
      default:
        newCoord = coordinate;
    }

    if (newCoord.getX() >= 0 && newCoord.getX() < ROWS && newCoord.getY() >= 0 && newCoord.getY() < COLS)
      return newCoord;

    return null;
  }
}
