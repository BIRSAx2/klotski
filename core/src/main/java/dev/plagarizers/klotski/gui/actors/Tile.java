package dev.plagarizers.klotski.gui.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import dev.plagarizers.klotski.game.block.Block;

import java.util.EnumMap;

public class Tile extends Actor {
    private static final EnumMap<Block.BlockType, Texture> TEXTURES = new EnumMap<>(Block.BlockType.class) {
        {
            put(Block.BlockType.UnknownBlock, new Texture("textures/blocks/big_block.png"));
            put(Block.BlockType.BigBlock, new Texture("textures/blocks/big_block.png"));
            put(Block.BlockType.VerticalBlock, new Texture("textures/blocks/vertical_block.png"));
            put(Block.BlockType.HorizontalBlock, new Texture("textures/blocks/horizontal_block.png"));
            put(Block.BlockType.SmallBlock, new Texture("textures/blocks/small_block.png"));
        }
    };
    private static final EnumMap<Block.BlockType, Texture> CONTOURS = new EnumMap<>(Block.BlockType.class) {
        {
            put(Block.BlockType.UnknownBlock, new Texture("textures/blocks/big_block_contour.png"));
            put(Block.BlockType.BigBlock, new Texture("textures/blocks/big_block_contour.png"));
            put(Block.BlockType.VerticalBlock, new Texture("textures/blocks/vertical_block_contour.png"));
            put(Block.BlockType.HorizontalBlock, new Texture("textures/blocks/horizontal_block_contour.png"));
            put(Block.BlockType.SmallBlock, new Texture("textures/blocks/small_block_contour.png"));
        }
    };
    private float x;
    private float y;
    private float width;
    private float height;
    private Block block;


    public Tile(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
        batch.draw(getContourTexture(), getX(), getY(), getWidth(), getHeight());

        super.draw(batch, parentAlpha);
    }

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

    public Texture getTexture() {
        return TEXTURES.get(block.getType());
    }

    public Texture getContourTexture() {
        return CONTOURS.get(block.getType());
    }

    public boolean contains(float x, float y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
    }
}
