package dev.plagarizers.klotski.gui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.state.KlotskiSolver;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.Direction;

import java.util.*;

public class Board extends Actor {
  public static final int MIN_DRAG_DISTANCE = 10;
  private State state;
  private int rows;
  private int columns;

  private float itemWidth = 64;
  private float itemHeight = 64;

  private Tile selectedTile;

  private Vector2 dragStartPos = null;

  private List<State> solution;
  private Texture boardTexture;

  private int minSteps = -1;

  private List<Tile> tiles;

  private State startingConfiguration;

  private Stack<State> previousStates;
  private Label movesLabel;

  public Board(State state, Skin skin) {
    this.startingConfiguration = state.clone();
    boardTexture = new Texture(Gdx.files.internal("textures/oldTextures/board2.png"));
    this.rows = State.ROWS;
    this.columns = State.COLS;
    this.state = state.clone();
    this.tiles = new ArrayList<>();
    solution = new ArrayList<>();

    previousStates = new Stack<>();
    previousStates.push(state.clone());

    loadBlocks();
    selectedTile = tiles.get(0);

    movesLabel = new Label("Moves: ", skin, "ButtonFont", Color.GOLD);
    movesLabel.setFontScale(1.5f);
  }

  private void calculateSolution() {
    Gdx.app.log("BoardWidget", "Calculating next best move");
    KlotskiSolver solver = new KlotskiSolver(state.clone());
    minSteps = solver.minSteps();
    solution.clear();
    solution.addAll(solver.getPathToSolution());
  }

  public void playBestMove() {
    Gdx.app.log("BoardWidget", "Playing best move");

    if (state.isSolved()) return;

    calculateSolution(); // Recalculate the solution

    if (solution == null || solution.isEmpty()) {
      // If no solution is available, recalculate and try again
      calculateSolution();
    }

    if (solution.isEmpty()) {
      return;
    }

    selectedTile = tiles.get(0);

    previousStates.push(state.clone());
    State next = solution.remove(0);
    next.setMoves(state.getMoves() + 1);

    state = next.clone();
    loadBlocks();
  }

  public void loadBlocks() {

    Gdx.app.log("BoardWidget", "Loading blocks");
    if (state == null) throw new IllegalStateException("State is null");
    tiles.clear();
    for (Block block : state.getBlocks()) {
      addTile(block);
    }

  }

  public void addTile(Block block) {

    int x = block.getY(), y = block.getX(), width = block.getWidth(), height = block.getHeight();
    // need to swap x and y, to convert coordinate from grid based to screen based
    y = rows - y - height;
    float gridOffsetX = (columns * itemWidth) / 2;
    float gridOffsetY = (rows * itemHeight) / 2;
    float tileX = (x * itemWidth) - gridOffsetX;
    float tileY = (y * itemHeight) - gridOffsetY;
    float tileWidth = width * itemWidth;
    float tileHeight = height * itemHeight;
    Tile tile = new Tile(tileX, tileY, tileWidth, tileHeight);
    tile.setBlock(block);
    if (selectedTile != null && selectedTile.getBlock().equals(block)) {
      selectedTile = tile;
    }
    tiles.add(tile);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    movesLabel.setText("Moves: " + state.getMoves());

    for (Tile tile : tiles) {
      float tileX = getX() + tile.getX();
      float tileY = getY() + tile.getY();

      batch.draw(tile.getTexture(), tileX, tileY, tile.getWidth(), tile.getHeight());

      if (selectedTile == tile) {
        batch.draw(tile.getContourTexture(), tileX, tileY, tile.getWidth(), tile.getHeight());
      }
    }

    movesLabel.setPosition(getX() - itemWidth, getY() - itemHeight * 4);
    movesLabel.draw(batch, parentAlpha);
    batch.draw(boardTexture, getX() - itemWidth * 3, getY() - itemHeight * 3 - itemHeight / 2f, (columns + 2) * itemWidth, (rows + 2.5f) * itemHeight);

  }


  private boolean moveBlock(Block block, Direction direction) {
    boolean result = state.moveBlock(block, direction);

    if (result) {
      previousStates.push(state.clone());
    }

    loadBlocks();
    return result;
  }

  public void handleInput() {

    if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
      selectNextTile();
    }

    HashMap<Integer, Direction> mappings = new HashMap<>();
    mappings.put(Input.Keys.UP, Direction.UP);
    mappings.put(Input.Keys.DOWN, Direction.DOWN);
    mappings.put(Input.Keys.LEFT, Direction.LEFT);
    mappings.put(Input.Keys.RIGHT, Direction.RIGHT);


    boolean keyPressed = false;

    if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
      undoMove();
    }

    for (Map.Entry<Integer, Direction> entry : mappings.entrySet()) {
      if (Gdx.input.isKeyJustPressed(entry.getKey())) {
        boolean result = moveBlock(selectedTile.getBlock(), entry.getValue());
        keyPressed = true;
        break;
      }
    }

    if (!keyPressed) {
      if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
        selectNextTile();
      }
    }

    // we could skip mouse click processing if the mouse is clicked outside of the board
    if (Gdx.input.justTouched()) {


      // skip if coordinates not in this actor
      dragStartPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
      // Convert screen coordinates to local coordinates

      float localX = Gdx.input.getX();
      float localY = Gdx.input.getY();
      Vector2 localCoords = this.screenToLocalCoordinates(new Vector2(localX, localY));
      localX = localCoords.x;
      localY = localCoords.y;

      // Check if any tile contains the clicked coordinates
      for (int i = 0; i < tiles.size(); i++) {
        Tile tile = tiles.get(i);
        if (tile.contains(localX, localY)) {
          selectedTile = tile;
          break;
        }
      }
    } else if (Gdx.input.isTouched()) {
      Vector2 dragEndPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());

      float dragDistance = dragEndPos.dst(dragStartPos);

      if (dragDistance >= MIN_DRAG_DISTANCE) {

        // Calculate the drag direction based on the start and end positions
        Direction dragDirection = calculateDragDirection(dragStartPos, dragEndPos);

        if (dragDirection != null && selectedTile != null) {
          moveBlock(selectedTile.getBlock(), dragDirection);
        }
      }

    }

  }


  private Direction calculateDragDirection(Vector2 start, Vector2 end) {
    float deltaX = end.x - start.x;
    float deltaY = end.y - start.y;

    if (Math.abs(deltaX) > Math.abs(deltaY)) {
      if (deltaX > 0) {
        return Direction.RIGHT;
      } else {
        return Direction.LEFT;
      }
    } else {
      if (deltaY > 0) {
        return Direction.DOWN;
      } else {
        return Direction.UP;
      }
    }
  }


  public void undoMove() {
    Gdx.app.log("undo", "undoing the last move");
    if (previousStates.isEmpty()) {
      return;
    }
    state = previousStates.pop().clone();
    loadBlocks();
    selectedTile = tiles.get(0);
  }

  private void selectNextTile() {
    if (selectedTile == null) {
      // If no tile is currently selected, select the first tile
      if (!tiles.isEmpty()) {
        selectedTile = tiles.get(0);
      }
    } else {
      // Find the index of the currently selected tile
      int selectedBlockIndex = tiles.indexOf(selectedTile);

      // Select the next tile in the list, or wrap around to the first tile
      int nextIndex = (selectedBlockIndex + 1) % tiles.size();
      selectedTile = tiles.get(nextIndex);
    }
  }

  public void reset() {
    Gdx.app.log("reset", "resetting the board");
    previousStates.clear();
    previousStates.push(startingConfiguration.clone());
    previousStates.push(state.clone());
    state = startingConfiguration.clone();
    loadBlocks();
  }

  public State getState() {
    return state;
  }

  public void setState(State clone) {
    this.state = clone.clone();
    loadBlocks();
  }

  public void setItemHeight(float itemHeight) {
    this.itemHeight = itemHeight;
  }

  public void setItemWidth(float itemWidth) {
    this.itemWidth = itemWidth;
  }

  public float getItemHeight() {
    return itemHeight;
  }

  public float getItemWidth() {
    return itemWidth;
  }
}
