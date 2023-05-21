package dev.plagarizers.klotski.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.actors.BoardWidget;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.SavesManager;

public class GameScreen implements Screen {

  private Stage stage;
  private State state;

  private BoardWidget grid;
  private OrthographicCamera cam;
  private Skin skin;

  private Table table;
  private TextButton backButton;
  private TextButton nextMoveButton;
  private TextButton saveButton;

  private KlotskiGame game;

  private SavesManager savesManager = new SavesManager();


  public GameScreen(KlotskiGame game, State state) {

    this.game = game;
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    cam = new OrthographicCamera(screenWidth, screenHeight);
    cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
    cam.update();

    stage = new Stage(new FitViewport(screenWidth, screenHeight, cam));

    if (state == null)
      this.state = State.fromDefaultConfiguration();
    else
      this.state = state;


    skin = new Skin(Gdx.files.internal(game.getSkinPath()));


    grid = new BoardWidget(state, skin);

    // Create buttons
    backButton = new TextButton("Back", skin);

    backButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        dispose();
        game.setScreen(new MainMenuScreen(game));
      }
    });

    nextMoveButton = new TextButton("Next Move", skin);

    // Add click listener to nextMoveButton
    nextMoveButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        grid.playBestMove();
      }
    });
    saveButton = new TextButton("Save", skin);

    saveButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        savesManager.saveState(grid.getState());
      }
    });

    // Create table
    table = new Table();
    table.setFillParent(true);

    // Add widgets to the table
    table.add(grid).expand().center().colspan(3).row();
    table.row();
    table.row();
    table.add(backButton).bottom().fillX().pad(10);
    table.add(nextMoveButton).bottom().fillX().pad(10);
    table.add(saveButton).bottom().fillX().pad(10);

    stage.addActor(table);

    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(0.176f, 0.067f, 0.365f, 0.135f);
    grid.handleInput();
    stage.act(delta / 60f);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height);
    cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
    cam.update();
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
    skin.dispose();
  }
}
