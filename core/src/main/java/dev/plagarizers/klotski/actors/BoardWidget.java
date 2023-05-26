package dev.plagarizers.klotski.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.state.KlotskiSolver;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardWidget extends Actor {
  private State state;
  private int rows;
  private int columns;

  private float itemWidth = 64;
  private float itemHeight = 64;

  private TileWidget selectedTile;

  private int selectedBlockIndex = 1;

  private Vector2 dragStartPos = null;

  private List<State> solution;
  private Texture boardTexture;

  private int minSteps = -1;

  private List<TileWidget> tiles;

  private State startingConfiguration;

  public BoardWidget(State state, Skin skin) {
    this.startingConfiguration = state.clone();
    boardTexture = new Texture(Gdx.files.internal("textures/board.png"));
    this.rows = State.ROWS;
    this.columns = State.COLS;
    this.state = state;
    this.tiles = new ArrayList<>();

    loadBlocks();
  }

  private void calculateSolution() {
    KlotskiSolver solver = new KlotskiSolver(state);
    minSteps = solver.minSteps();
    solution = solver.getPathToSolution();
  }

  public void playBestMove() {

    selectedTile = tiles.get(0);
    selectedBlockIndex = 0;
    if (solution == null) {
      calculateSolution();
      loadBlocks();
    }

    if (solution.isEmpty()) {
      return;
    }

    state = solution.remove(0);
    loadBlocks();
  }

  public void loadBlocks() {
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
    TileWidget tile = new TileWidget(tileX, tileY, tileWidth, tileHeight);
    tile.setBlock(block);
    if (state.getBlocks()[selectedBlockIndex].equals(block)) {
      selectedTile = tile;
    }
    tiles.add(tile);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {

    batch.draw(boardTexture, getX() - itemWidth * 3, getY() - itemHeight * 3 - itemHeight / 2f, (columns + 2) * itemWidth, (rows + 2.5f) * itemHeight);
    for (TileWidget tile : tiles) {
      float tileX = getX() + tile.getX();
      float tileY = getY() + tile.getY();

      batch.draw(tile.getTexture(), tileX, tileY, tile.getWidth(), tile.getHeight());

      if (selectedTile == tile) {
        batch.draw(tile.getContourTexture(), tileX, tileY, tile.getWidth(), tile.getHeight());
      }
    }

  }

  public void handleInput() {

    HashMap<Integer, Direction> mappings = new HashMap<>();
    mappings.put(Input.Keys.UP, Direction.UP);
    mappings.put(Input.Keys.DOWN, Direction.DOWN);
    mappings.put(Input.Keys.LEFT, Direction.LEFT);
    mappings.put(Input.Keys.RIGHT, Direction.RIGHT);


    boolean keyPressed = false;

    for (Map.Entry<Integer, Direction> entry : mappings.entrySet()) {
      if (Gdx.input.isKeyJustPressed(entry.getKey())) {
        boolean result = state.moveBlock(selectedTile.getBlock(), entry.getValue());
        loadBlocks();
        selectedTile.setColor(result ? Color.GREEN : Color.RED);
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
        TileWidget tile = tiles.get(i);
        if (tile.contains(localX, localY)) {
          selectedTile = tile;
          selectedBlockIndex = i;
          break;
        }
      }
    } else if (Gdx.input.isTouched()) {
      Vector2 dragEndPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());

      // Calculate the drag direction based on the start and end positions
      Direction dragDirection = calculateDragDirection(dragStartPos, dragEndPos);

      if (dragDirection != null && selectedBlockIndex != -1) {
        state.moveBlock(selectedTile.getBlock(), dragDirection);
        loadBlocks();

      }
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
      selectNextTile();
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

  private void selectNextTile() {
    if (selectedTile == null) {
      // If no tile is currently selected, select the first tile
      if (!tiles.isEmpty()) {
        selectedTile = tiles.get(0);
      }
    } else {
      // Find the index of the currently selected tile
      selectedBlockIndex = tiles.indexOf(selectedTile);

      // Select the next tile in the list, or wrap around to the first tile
      int nextIndex = (selectedBlockIndex + 1) % tiles.size();
      selectedBlockIndex = nextIndex;
      selectedTile = tiles.get(nextIndex);
    }
  }

  public void reset() {
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
}
