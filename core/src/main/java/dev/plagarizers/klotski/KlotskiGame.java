package dev.plagarizers.klotski;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class KlotskiGame extends ApplicationAdapter {
  private static final int BLOCK_SIZE = 64;
  private static final int BLOCK_COUNT = 10;

  private SpriteBatch batch;
  private Texture blockTexture;
  private Block[] blocks;
  private Block selectedBlock;

  private Vector3 initialClickPos;

  private boolean isDragging;
  private Color[] blockColors;

  private OrthographicCamera camera;

  @Override
  public void create() {
    batch = new SpriteBatch();
    blockTexture = new Texture("block.png");


    blocks = new Block[BLOCK_COUNT];
    blocks[0] = new Block(new Rectangle(1, 0, 2, 2));
    blocks[1] = new Block(new Rectangle(0, 0, 1, 2));
    blocks[2] = new Block(new Rectangle(3, 0, 1, 2));
    blocks[3] = new Block(new Rectangle(0, 2, 1, 2));
    blocks[4] = new Block(new Rectangle(3, 2, 1, 2));
    blocks[5] = new Block(new Rectangle(0, 4, 1, 1));
    blocks[6] = new Block(new Rectangle(1, 4, 1, 1));
    blocks[7] = new Block(new Rectangle(2, 4, 1, 1));
    blocks[8] = new Block(new Rectangle(3, 4, 1, 1));
    blocks[9] = new Block(new Rectangle(1, 3, 2, 1));

    blockColors = new Color[BLOCK_COUNT];
    blockColors[0] = Color.RED;
    blockColors[1] = Color.BLUE;
    blockColors[2] = Color.GREEN;
    blockColors[3] = Color.YELLOW;
    blockColors[4] = Color.BLACK;
    blockColors[5] = Color.BROWN;
    blockColors[6] = Color.FIREBRICK;
    blockColors[7] = Color.SKY;
    blockColors[8] = Color.GOLD;
    blockColors[9] = Color.NAVY;


    camera = new OrthographicCamera();
    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  @Override
  public void render() {

    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
    camera.update();

    handleInput();


    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    for (int i = 0; i < blocks.length; i++) {
      Block block = blocks[i];

      Color color = new Color(blockColors[i]);

      if (block == selectedBlock) {
        color.add(0.5f, 0.5f, 0.5f, 1.0f);
      }
      batch.setColor(color);
      batch.draw(blockTexture, block.getPosition().x * BLOCK_SIZE, block.getPosition().y * BLOCK_SIZE,
        block.getSize().x * BLOCK_SIZE, block.getSize().y * BLOCK_SIZE);
    }
    batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    batch.end();
  }

  private void handleInput() {

    if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
      if (selectedBlock == null) {
        selectedBlock = blocks[0];
      } else {
        int selectedIndex = -1;
        for (int i = 0; i < blocks.length; i++) {
          if (blocks[i] == selectedBlock) {
            selectedIndex = i;
            break;
          }
        }
        if (selectedIndex != -1) {

          selectedBlock = blocks[(selectedIndex + 1) % blocks.length];
        }
      }
    }
    if (Gdx.input.justTouched()) {
      Vector3 clickPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
      camera.unproject(clickPos);
      isDragging = true;
      initialClickPos = new Vector3(clickPos);
      for (Block block : blocks) {
        if (block.contains(clickPos.x, clickPos.y)) {
          if (selectedBlock == null) {
            selectedBlock = block;

          } else {

            if (selectedBlock == block) {
              selectedBlock = null;
            } else {

              selectedBlock = block;
            }
          }
          break;
        }
      }
    }

    if (selectedBlock != null && isDragging && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
      Vector3 dragPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
      camera.unproject(dragPos);

      float deltaX = dragPos.x - initialClickPos.x;
      float deltaY = dragPos.y - initialClickPos.y;

      float threshold = 0.5f;
      int offsetX = 0;
      int offsetY = 0;

      if (Math.abs(deltaX) > threshold) {
        offsetX = MathUtils.round(deltaX);
      }

      if (Math.abs(deltaY) > threshold) {
        offsetY = MathUtils.round(deltaY);
      }

      if (offsetX != 0 || offsetY != 0) {

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
          offsetY = 0;
        } else {
          offsetX = 0;
        }

        moveBlock(selectedBlock, offsetX, offsetY);
      }
    } else {
      isDragging = false;
    }
    if (selectedBlock != null) {
      int offsetX = 0;
      int offsetY = 0;

      if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
        offsetX = -1;
      } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
        offsetX = 1;
      } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
        offsetY = 1;
      } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
        offsetY = -1;
      }

      if (offsetX != 0 || offsetY != 0) {
        moveBlock(selectedBlock, offsetX, offsetY);
      }
    }
  }

  private void moveBlock(Block block, int offsetX, int offsetY) {
    int newX = (int) block.getPosition().x + offsetX;
    int newY = (int) block.getPosition().y + offsetY;

    if (isValidMove(block, newX, newY)) {
      block.getPosition().x = newX;
      block.getPosition().y = newY;
    }
  }

  private boolean isValidMove(Block block, int newX, int newY) {
    if (newX < 0 || newY < 0 || newX + block.getSize().x > 4 || newY + block.getSize().y > 5) {
      return false;
    }

    for (Block otherBlock : blocks) {
      if (otherBlock != block && otherBlock.isColliding(newX, newY, block.getSize().x, block.getSize().y)) {
        return false;
      }
    }

    return true;
  }

  private static class Block {
    private Rectangle position;

    public Block(Rectangle position) {
      this.position = position;
    }

    public Rectangle getPosition() {
      return position;
    }

    public Vector2 getSize() {
      return new Vector2(position.width, position.height);
    }

    public boolean isColliding(int x, int y, float width, float height) {
      return position.x < x + width && position.x + position.width > x &&
        position.y < y + height && position.y + position.height > y;
    }

    public boolean contains(float x, float y) {
      return contains(new Vector2(x, y));
    }

    public boolean contains(Vector2 point) {
      float blockX = position.x * BLOCK_SIZE;
      float blockY = position.y * BLOCK_SIZE;
      float blockWidth = position.width * BLOCK_SIZE;
      float blockHeight = position.height * BLOCK_SIZE;

      return point.x >= blockX && point.x < blockX + blockWidth &&
        point.y >= blockY && point.y < blockY + blockHeight;
    }
  }
}
