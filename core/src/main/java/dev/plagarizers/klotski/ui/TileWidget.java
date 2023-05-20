package dev.plagarizers.klotski.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import dev.plagarizers.klotski.game.block.Block;

import java.util.EnumMap;

public class TileWidget {
  private float x;
  private float y;
  private float width;
  private float height;


  private EnumMap<Block.BlockType, Texture> textureMap;
  private EnumMap<Block.BlockType, Color> colorMap = new EnumMap<Block.BlockType, Color>(Block.BlockType.class) {
    {
      put(Block.BlockType.UnknownBlock, Color.WHITE);
      put(Block.BlockType.BigBlock, Color.RED);
      put(Block.BlockType.VerticalBlock, Color.BLUE);
      put(Block.BlockType.HorizontalBlock, Color.GREEN);
      put(Block.BlockType.SmallBlock, Color.YELLOW);
    }
  };
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

  public Texture getTexture() {
    return textureMap.get(block.getType());
  }

  public void setBlock(Block block) {
    this.block = block;
  }

  public TileWidget(float x, float y, float width, float height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    textureMap = new EnumMap<Block.BlockType, Texture>(Block.BlockType.class) {
      {
        put(Block.BlockType.UnknownBlock, new Texture("textures/EmptyBlock.png"));
        put(Block.BlockType.BigBlock, new Texture("textures/BigBlock.png"));
        put(Block.BlockType.VerticalBlock, new Texture("textures/VerticalBlock.png"));
        put(Block.BlockType.HorizontalBlock, new Texture("textures/HorizontalBlock.png"));
        put(Block.BlockType.SmallBlock, new Texture("textures/SmallBlock.png"));
      }
    };
  }

  public Color getColor() {
    return colorMap.get(block.getType());
  }

  public Color getRandomColor() {
    return new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 1);
  }

  public boolean contains(float x, float y) {
    return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
  }
}
