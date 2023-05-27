package dev.plagarizers.klotski.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.actors.Board;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.SavesManager;

public class GameScreen implements Screen {

  private Stage stage;
  private Board grid;

  private final KlotskiGame game;

  private final SavesManager savesManager;


  public GameScreen(KlotskiGame game, State state) {
    this.game = game;
    savesManager = new SavesManager();
    State currentState = state;

    if (currentState == null) currentState = State.fromRandomConfiguration();

    grid = new Board(currentState, game.getSkin());

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    stage = game.getStage(new FitViewport(screenWidth, screenHeight, game.getCamera()));
    setupLayout(game.getImageButtonStyle(), game.getSkin());
  }

  private void setupLayout(ImageButton.ImageButtonStyle buttonStyle, Skin skin) {
    ImageButton backButton = new ImageButton(buttonStyle);
    backButton.add(new Label("Back", skin, "ButtonFont", Color.GOLD)); // Add the button text label

    backButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.buttonPressedPlay();
        game.setScreen(new MainMenuScreen(game));
      }
    });

    ImageButton nextMoveButton = new ImageButton(buttonStyle);
    nextMoveButton.add(new Label("Next Move", skin, "ButtonFont", Color.GOLD));

    nextMoveButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.buttonPressedPlay();
        grid.playBestMove();
      }
    });

    ImageButton saveButton = new ImageButton(buttonStyle);
    saveButton.add(new Label("Save", skin, "ButtonFont", Color.GOLD));

    saveButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.buttonPressedPlay();
        savesManager.saveState(grid.getState());
      }
    });


    ImageButton resetButton = new ImageButton(buttonStyle);
    resetButton.add(new Label("Reset", skin, "ButtonFont", Color.GOLD));

    resetButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.buttonPressedPlay();
        grid.reset();
        Gdx.app.log("Reset", "Resetting board");
      }
    });

    ImageButton undoButton = new ImageButton(buttonStyle);
    undoButton.add(new Label("Undo", skin, "ButtonFont", Color.GOLD));

    undoButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.buttonPressedPlay();
        grid.undoMove();
        Gdx.app.log("Undo", "Undoing move");
      }
    });

    Table table = new Table();
    table.setFillParent(true);
    table.add(grid).expand().center().colspan(6).row();
    table.row();
    table.add(undoButton).bottom().fillX().colspan(2).pad(10);
    table.add(nextMoveButton).bottom().fillX().colspan(2).pad(10);
    table.add(saveButton).bottom().fillX().colspan(2).pad(10);
    table.row();
    table.add(resetButton).bottom().fillX().colspan(3).pad(10);
    table.add(backButton).bottom().fillX().colspan(3).pad(10);


    stage.addActor(table);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    // Check if the game is over
    if (grid.getState().isSolved()) {
      game.setScreen(new GameOverScreen(game, grid.getState()));
    }
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
  }
}
