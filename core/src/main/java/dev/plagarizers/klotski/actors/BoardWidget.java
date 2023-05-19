package dev.plagarizers.klotski.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.Direction;
import dev.plagarizers.klotski.ui.TileWidget;
import dev.plagarizers.klotski.util.MyShapeRenderer;

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
  private ShapeRenderer shapeRenderer;

  private MyShapeRenderer myShapeRenderer;

  private TileWidget selectedTile;

  private List<TileWidget> tiles;

  public BoardWidget(State state) {
    this.rows = State.ROWS;
    this.columns = State.COLS;
    this.state = state;
    this.shapeRenderer = new ShapeRenderer();
    this.myShapeRenderer = new MyShapeRenderer();
    this.tiles = new ArrayList<>();
    loadBlocks();

    selectedTile = tiles.get(1);
  }

  public List<TileWidget> getTiles() {
    return tiles;
  }

  public void handleInput() {

    Map<Integer, Direction> keyToDirection = new HashMap<>();

    keyToDirection.put(Input.Keys.LEFT, Direction.LEFT);
    keyToDirection.put(Input.Keys.RIGHT, Direction.RIGHT);
    keyToDirection.put(Input.Keys.UP, Direction.UP);
    keyToDirection.put(Input.Keys.DOWN, Direction.DOWN);

    if (selectedTile == null) {
      return;
    }
    for (Map.Entry<Integer, Direction> entry : keyToDirection.entrySet()) {
      if (Gdx.input.isKeyJustPressed(entry.getKey())) {
        System.out.println("key pressed");

        state = state.moveBlock(selectedTile.getBlock(), entry.getValue());
      }
    }
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
    float tileX = getX() + (x * itemWidth) - gridOffsetX;
    float tileY = getY() + (y * itemHeight) - gridOffsetY;
    float tileWidth = width * itemWidth;
    float tileHeight = height * itemHeight;
    TileWidget tile = new TileWidget(tileX, tileY, tileWidth, tileHeight);
    tile.setBlock(block);
    tiles.add(tile);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

    shapeRenderer.setColor(Color.WHITE);

    // Calculate the total size of the grid
    float gridWidth = columns * itemWidth;
    float gridHeight = rows * itemHeight;

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


    myShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

    for (TileWidget tile : tiles) {
      myShapeRenderer.setColor(tile.getColor());
      float tileX = getX() + tile.getX();
      float tileY = getY() + tile.getY();
      myShapeRenderer.roundedRect(tileX + 5, tileY + 5, tile.getWidth() - 10, tile.getHeight() - 10, 10);
    }

    // End rendering shapes
    myShapeRenderer.end();
  }

  public boolean moveTile(TileWidget tile, Direction direction) {
    Block block = tile.getBlock();
    System.out.println("Moving block " + block);

    state = state.moveBlock(block, direction);
    loadBlocks();
//      block.makeMove(direction);
//      state.moveBlock(block, x, y);
//      tile.setX(getX() + (x * itemWidth) - ((columns * itemWidth) / 2));
//      tile.setY(getY() + ((rows - y - block.getHeight()) * itemHeight) - ((rows * itemHeight) / 2));
    return true;
  }

  private void moveSelectedTile(Direction direction) {
    if (selectedTile != null) {
      moveTile(selectedTile, direction);
    }
  }

  public boolean canMove(TileWidget tile, Direction direction) {
    return tile.getBlock().canMove(direction);
  }


}
