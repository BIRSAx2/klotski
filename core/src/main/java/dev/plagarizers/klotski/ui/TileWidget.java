package dev.plagarizers.klotski.ui;

import com.badlogic.gdx.graphics.Color;
import dev.plagarizers.klotski.game.block.Block;

public class TileWidget {
  private float x;
  private float y;
  private float width;
  private float height;

  private Color color;

  private Block block;

  public float getX() {
    return x;
  }

  public void setX(float x) {
    this.x = x;
  }

  public float getY() {
    return y;
  }

  public void setY(float y) {
    this.y = y;
  }

  public float getWidth() {
    return width;
  }

  public void setWidth(float width) {
    this.width = width;
  }

  public float getHeight() {
    return height;
  }

  public void setHeight(float height) {
    this.height = height;
  }

  public Block getBlock() {
    return block;
  }

  public void setBlock(Block block) {
    this.block = block;
  }

  public TileWidget(float x, float y, float width, float height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
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

