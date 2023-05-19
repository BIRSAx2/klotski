package dev.plagarizers.klotski.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.plagarizers.klotski.game.block.Block;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.ui.GridWidget;

public class GameScreen implements Screen {

  private Stage stage;
  private State state;
  private OrthographicCamera cam;
  private Skin skin;

  public GameScreen() {
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    float gridSize = 64;

    cam = new OrthographicCamera(screenWidth, screenHeight);
    cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
    cam.update();

    stage = new Stage(new FitViewport(screenWidth, screenHeight, cam));
    Gdx.input.setInputProcessor(stage);

    state = State.fromDefaultConfiguration();
    skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

    state = State.fromDefaultConfiguration();
    GridWidget grid = new GridWidget(State.ROWS, State.COLS, gridSize, gridSize);

    for (Block block : state.getBlocks()) {
      grid.addTile(block.getX(), block.getY(), block.getWidth(), block.getHeight());
    }

    float gridWidth = State.COLS * gridSize;
    float gridHeight = State.ROWS * gridSize;
    float startX = (screenWidth - gridWidth) / 2f;
    float startY = (screenHeight - gridHeight) / 2f;
    grid.setPosition(screenWidth / 2, screenHeight / 2);

    stage.addActor(grid);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(0.176f, 0.067f, 0.365f, 0.135f);

    stage.act(delta/60f);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height);
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
    stage.dispose();
  }
}
