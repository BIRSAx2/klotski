package dev.plagarizers.klotski.gui.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.Level;

import java.util.ArrayList;
import java.util.List;

public class BoardPreview extends Actor {

  private State state;

  private int rows;

  private int columns;

  private float itemWidth = 32;
  private float itemHeight = 32;

  private Label levelLabel;

  private Skin skin;

  private ShapeRenderer shapeRenderer;

  private List<Tile> tiles;

  public BoardPreview(Level level, Skin skin) {

    this.tiles = new ArrayList<>();
    this.state = level.toState();
    this.rows = State.ROWS;
    this.columns = State.COLS;
    this.skin = skin;
    levelLabel = new Label("Level: " + level.getName(), skin, "ButtonFont", Color.GOLD);
    levelLabel.setFontScale(1.5f);

    shapeRenderer = new ShapeRenderer();
    loadBlocks();
    this.setWidth((columns + 1) * itemWidth);
    this.setHeight((rows + 2) * itemHeight);


  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    levelLabel.setFillParent(true);
    levelLabel.setPosition(getX(), getY() + getHeight() - levelLabel.getHeight());
    levelLabel.draw(batch, parentAlpha);

    batch.draw(tiles.get(0).getTexture(), getX(), getY(), getWidth(), getHeight() - itemHeight);
    batch.end();

    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setAutoShapeType(true);

    for (Tile tile : tiles) {
      shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
      shapeRenderer.setColor(tile.getColor());
      // TODO: this is a temporary fix, implement better coordinates.
      // Possible solutions: use an array where every element is a square and has a pointer to the element over it.
      // Make the element hava a color and should be simpler to draw the preview
      float tileX = getWidth() * 0.80f + getX() + tile.getX();
      float tileY = getY() + getWidth() / 2 + tile.getY() + 23;

      shapeRenderer.rect(tileX, tileY, tile.getWidth(), tile.getHeight());
      shapeRenderer.set(ShapeRenderer.ShapeType.Line);
      shapeRenderer.setColor(Color.DARK_GRAY);
      shapeRenderer.rect(tileX, tileY, tile.getWidth(), tile.getHeight());

    }
    shapeRenderer.end();


    batch.begin();
  }


  public void loadBlocks() {
    Gdx.app.log("BoardPreview", "Loading blocks");
    if (state == null) throw new IllegalStateException("State is null");
    tiles.clear();
    for (Block block : state.getBlocks()) {
      addTile(block);
    }

    System.out.println(this.tiles.size());
  }

  public void addTile(Block block) {
    int x = block.getY(), y = block.getX(), width = block.getWidth(), height = block.getHeight();
    // need to swap x and y to convert coordinates from grid-based to screen-based
    y = rows - y - height;

    float tileX = (x * itemWidth);// - gridOffsetX;
    float tileY = (y * itemHeight);// - gridOffsetY;
    float tileWidth = width * itemWidth;
    float tileHeight = height * itemHeight;

    Tile tile = new Tile(tileX, tileY, tileWidth, tileHeight);
    tile.setBlock(block);
    tiles.add(tile);
  }

}
