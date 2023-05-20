package dev.plagarizers.klotski.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.state.KlotskiSolver;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.Direction;
import dev.plagarizers.klotski.ui.TileWidget;
import dev.plagarizers.klotski.util.MyShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class BoardWidget extends Actor {
  private State state;

  private Skin skin;
  private int rows;
  private int columns;
  private float itemWidth = 64;
  private float itemHeight = 64;

  private float gridHeight, gridWidth;
  private float topLeftX, topLeftY;
  private ShapeRenderer shapeRenderer;

  private MyShapeRenderer myShapeRenderer;
  private TileWidget selectedTile;

  private int selectedBlockIndex = 1;

  private Vector2 dragStartPos = null;

  private List<State> solution;

  private int minSteps = -1;

  private List<TileWidget> tiles;


  public BoardWidget(State state, Skin skin) {
    this.rows = State.ROWS;
    this.columns = State.COLS;
    this.state = state;
    this.shapeRenderer = new ShapeRenderer();
    this.myShapeRenderer = new MyShapeRenderer();
    this.tiles = new ArrayList<>();
    this.skin = skin;

    // Calculate the total size of the grid
    gridWidth = columns * itemWidth;
    gridHeight = rows * itemHeight;


    loadBlocks();
  }

  private void calculateSolution() {
    KlotskiSolver solver = new KlotskiSolver(state);
    minSteps = solver.minSteps();
    solution = solver.getPathToSolution();
  }


  public int getMinSteps() {
    return minSteps;
  }

  public void playBestMove() {

    if (solution == null) {
      calculateSolution();
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
    tiles.add(tile);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {

    batch.end();
    shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

    shapeRenderer.setColor(Color.LIGHT_GRAY);

    shapeRenderer.line(0, 1, 10, 1);


    // Calculate the position of the top-left corner of the grid
    float startX = getX() - (gridWidth / 2);
    float startY = getY() - (gridHeight / 2);

    // Render vertical grid lines
    for (int i = 0; i <= columns; i++) {
      float x = startX + (i * itemWidth);
      shapeRenderer.line(x, startY, x, startY + gridHeight);
    }

    // Render horizontal grid lines
    for (int i = 0; i <= rows; i++) {
      float y = startY + (i * itemHeight);
      shapeRenderer.line(startX, y, startX + gridWidth, y);
    }
    shapeRenderer.end();

    batch.begin();
    // Render the tiles
    for (TileWidget tile : tiles) {
      batch.setColor(Color.WHITE);
      if (selectedTile == tile) batch.setColor(Color.LIGHT_GRAY);
      float tileX = getX() + tile.getX();
      float tileY = getY() + tile.getY();

      batch.draw(tile.getTexture(), tileX + 5, tileY + 5, tile.getWidth() - 10, tile.getHeight() - 10);
    }
  }

  public void handleInput() {
    if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
      state = state.moveBlock(selectedBlockIndex, Direction.LEFT);
      loadBlocks();

    } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
      state = state.moveBlock(selectedBlockIndex, Direction.RIGHT);
      loadBlocks();

    } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
      state = state.moveBlock(selectedBlockIndex, Direction.UP);
      loadBlocks();

    } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
      state = state.moveBlock(selectedBlockIndex, Direction.DOWN);
      loadBlocks();

    }

    if (Gdx.input.justTouched()) {
      dragStartPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
      // Convert screen coordinates to local coordinates
      float localX = Gdx.input.getX() - getX();
      float localY = -(Gdx.input.getY() - getY());

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

      if (dragDirection != null) {
        state = state.moveBlock(selectedBlockIndex, dragDirection);
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
}
