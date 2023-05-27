package dev.plagarizers.klotski.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import dev.plagarizers.klotski.game.block.Block;

import java.util.EnumMap;

public class Tile extends Actor {
  private float x;
  private float y;
  private float width;
  private float height;

  @Override
  public void draw(Batch batch, float parentAlpha) {

    batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
    batch.draw(getContourTexture(), getX(), getY(), getWidth(), getHeight());

    super.draw(batch, parentAlpha);
  }

  private EnumMap<Block.BlockType, Texture> textureMap = new EnumMap<Block.BlockType, Texture>(Block.BlockType.class) {
    {
      put(Block.BlockType.UnknownBlock, new Texture("textures/blocks/big_block.png"));
      put(Block.BlockType.BigBlock, new Texture("textures/blocks/big_block.png"));
      put(Block.BlockType.VerticalBlock, new Texture("textures/blocks/vertical_block.png"));
      put(Block.BlockType.HorizontalBlock, new Texture("textures/blocks/horizontal_block.png"));
      put(Block.BlockType.SmallBlock, new Texture("textures/blocks/small_block.png"));
    }
  };

  private EnumMap<Block.BlockType, Color> colorMap = new EnumMap<Block.BlockType, Color>(Block.BlockType.class) {
    {
      put(Block.BlockType.UnknownBlock, Color.WHITE);
      put(Block.BlockType.BigBlock, Color.valueOf("F9E2AF"));
      put(Block.BlockType.VerticalBlock, Color.valueOf("009FBD"));
      put(Block.BlockType.HorizontalBlock, Color.valueOf("210062"));
      put(Block.BlockType.SmallBlock, Color.valueOf("77037B"));
    }
  };


  private EnumMap<Block.BlockType, Texture> contourMap = new EnumMap<Block.BlockType, Texture>(Block.BlockType.class) {
    {
      put(Block.BlockType.UnknownBlock, new Texture("textures/blocks/big_block_contour.png"));
      put(Block.BlockType.BigBlock, new Texture("textures/blocks/big_block_contour.png"));
      put(Block.BlockType.VerticalBlock, new Texture("textures/blocks/vertical_block_contour.png"));
      put(Block.BlockType.HorizontalBlock, new Texture("textures/blocks/horizontal_block_contour.png"));
      put(Block.BlockType.SmallBlock, new Texture("textures/blocks/small_block_contour.png"));
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

  public Texture getContourTexture() {
    return contourMap.get(block.getType());
  }

  public void setBlock(Block block) {
    this.block = block;
  }

  public Tile(float x, float y, float width, float height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;


  }

  @Override
  public Color getColor() {
    return colorMap.get(block.getType());
  }

  public boolean contains(float x, float y) {
    return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
  }
}
