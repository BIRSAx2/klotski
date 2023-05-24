package dev.plagarizers.klotski.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.actors.BoardWidget;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.SavesManager;

public class GameScreen implements Screen {

  private Stage stage;
  private State state;

  private final BoardWidget grid;

  private final KlotskiGame game;

  private final SavesManager savesManager;

  private int numberOfMoves = 0;

  public GameScreen(KlotskiGame game, State state) {
    this.game = game;

    savesManager = new SavesManager();

    if (state == null) this.state = State.fromRandomConfiguration();
    else this.state = state;
    grid = new BoardWidget(state, game.getSkin());

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    stage = game.getStage(new FitViewport(screenWidth, screenHeight, game.getCamera()));
    setupLayout(game.getImageButtonStyle(), game.getSkin());
  }

  private void setupLayout(ImageButton.ImageButtonStyle buttonStyle, Skin skin) {
    ImageButton backButton = new ImageButton(buttonStyle);
    backButton.add(new Label("Back", skin)); // Add the button text label

    backButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        dispose();
        game.setScreen(new MainMenuScreen(game));
      }
    });

    ImageButton nextMoveButton = new ImageButton(buttonStyle);
    nextMoveButton.add(new Label("Next Move", skin));

    nextMoveButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        numberOfMoves++;
        grid.playBestMove();
      }
    });

    ImageButton saveButton = new ImageButton(buttonStyle);
    saveButton.add(new Label("Save", skin));

    saveButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        savesManager.saveState(grid.getState());
      }
    });

    Table table = new Table();
    table.setFillParent(true);
    Label numberOfMovesLabel = new Label("Number of moves: " + numberOfMoves, skin);
    table.add(numberOfMovesLabel).colspan(3).pad(10).row();
    table.add(grid).expand().center().colspan(3).row();
    table.row();
    table.row();
    table.add(backButton).bottom().fillX().pad(10);
    table.add(nextMoveButton).bottom().fillX().pad(10);
    table.add(saveButton).bottom().fillX().pad(10);

    stage.addActor(table);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    grid.handleInput();
    stage.act(delta / 60f);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height);
    //cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
    //cam.update();
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
