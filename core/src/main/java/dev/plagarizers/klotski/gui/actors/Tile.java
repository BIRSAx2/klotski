package dev.plagarizers.klotski.gui.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import dev.plagarizers.klotski.game.block.Block;

import java.util.EnumMap;


/**
 * The `Tile` class represents a tile on the game board.
 * It extends the `Actor` class and is responsible for rendering the tile and its components.
 */
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

    /**
     * Constructs a new `Tile` object with the specified position and dimensions.
     *
     * @param x      the x-coordinate of the tile
     * @param y      the y-coordinate of the tile
     * @param width  the width of the tile
     * @param height the height of the tile
     */
    public Tile(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Renders the tile and its components.
     *
     * @param batch       the sprite batch to render to
     * @param parentAlpha the parent alpha value
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
        batch.draw(getContourTexture(), getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }

    /**
     * Returns the x-coordinate of the tile.
     *
     * @return the x-coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the tile.
     *
     * @param x the x-coordinate to set
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the tile.
     *
     * @return the y-coordinate
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the tile.
     *
     * @param y the y-coordinate to set
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Returns the width of the tile.
     *
     * @return the width
     */
    public float getWidth() {
        return width;
    }

    /**
     * Sets the width of the tile.
     *
     * @param width the width to set
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * Returns the height of the tile.
     *
     * @return the height
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets the height of the tile.
     *
     * @param height the height to set
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Returns the block associated with the tile.
     *
     * @return the block
     */
    public Block getBlock() {
        return block;
    }

    /**
     * Sets the block associated with the tile.
     *
     * @param block the block to set
     */
    public void setBlock(Block block) {
        this.block = block;
    }

    /**
     * Returns the texture of the tile based on the block type.
     *
     * @return the texture
     */
    public Texture getTexture() {
        return TEXTURES.get(block.getType());
    }

    /**
     * Returns the contour texture of the tile based on the block type.
     *
     * @return the contour texture
     */
    public Texture getContourTexture() {
        return CONTOURS.get(block.getType());
    }

    /**
     * Checks if the tile contains the specified coordinates.
     *
     * @param x the x-coordinate to check
     * @param y the y-coordinate to check
     * @return true if the tile contains the coordinates, false otherwise
     */
    public boolean contains(float x, float y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
    }
}
