package dev.plagarizers.klotski.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

public class GridWidget extends Actor {
  private int rows;
  private int columns;
  private float itemWidth;
  private float itemHeight;
  private ShapeRenderer shapeRenderer;
  private List<Tile> tiles;

  public GridWidget(int rows, int columns, float itemWidth, float itemHeight) {
    this.rows = rows;
    this.columns = columns;
    this.itemWidth = itemWidth;
    this.itemHeight = itemHeight;
    this.shapeRenderer = new ShapeRenderer();
    this.tiles = new ArrayList<>();
  }

  public void addTile(int x, int y, int width, int height) {
    // need to swap x and y, to convert coordinate from grid based to screen based
    int tmp = x;
    x = y;
    y = tmp;
    y = rows - y - height;
    float gridOffsetX = (columns * itemWidth) / 2;
    float gridOffsetY = (rows * itemHeight) / 2;
    float tileX = getX() + (x * itemWidth) - gridOffsetX;
    float tileY = getY() + (y * itemHeight) - gridOffsetY;
    float tileWidth = width * itemWidth;
    float tileHeight = height * itemHeight;
    tiles.add(new Tile(tileX, tileY, tileWidth, tileHeight));
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


    MyShapeRenderer myShapeRenderer = new MyShapeRenderer();
    myShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

    myShapeRenderer.setColor(Color.GREEN);

    for (Tile tile : tiles) {
      myShapeRenderer.setColor(tile.getColor());
      float tileX = getX() + tile.x;
      float tileY = getY() + tile.y;
      myShapeRenderer.roundedRect(tileX + 5, tileY + 5, tile.width - 10, tile.height - 10, 10);
    }

    // End rendering shapes
    myShapeRenderer.end();
  }

  private static class Tile {
    private float x;
    private float y;
    private float width;
    private float height;

    private Color color;

    public Tile(float x, float y, float width, float height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
    }

    public void setColor(Color color) {
      this.color = color;
    }

    public Color getColor() {
      if (color == null) {
        color = getRandomColor();
      }
      return color;
    }

    public Color getRandomColor() {
      return new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 1);
    }
  }


  public class MyShapeRenderer extends ShapeRenderer {
    /**
     * Draws a rectangle with rounded corners of the given radius.
     */
    public void roundedRect(float x, float y, float width, float height, float radius) {
      // Central rectangle
      super.rect(x + radius, y + radius, width - 2 * radius, height - 2 * radius);

      // Four side rectangles, in clockwise order
      super.rect(x + radius, y, width - 2 * radius, radius);
      super.rect(x + width - radius, y + radius, radius, height - 2 * radius);
      super.rect(x + radius, y + height - radius, width - 2 * radius, radius);
      super.rect(x, y + radius, radius, height - 2 * radius);

      // Four arches, clockwise too
      super.arc(x + radius, y + radius, radius, 180f, 90f);
      super.arc(x + width - radius, y + radius, radius, 270f, 90f);
      super.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
      super.arc(x + radius, y + height - radius, radius, 90f, 90f);
    }
  }
}
