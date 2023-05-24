package dev.plagarizers.klotski.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;

public class MainMenuScreen implements Screen {
  private final KlotskiGame game;
  private Stage stage;
  private OrthographicCamera cam;

  public MainMenuScreen(KlotskiGame game) {
    this.game = game;

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    setupCamera(screenWidth, screenHeight);

    setupUI(screenWidth, screenHeight);
  }

  private void setupCamera(float screenWidth, float screenHeight) {
    cam = new OrthographicCamera(screenWidth, screenHeight);
    cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
    cam.update();
  }

  private void setupUI(float screenWidth, float screenHeight) {
    stage = new Stage(new FitViewport(screenWidth, screenHeight, cam));

    Image background = new Image(new Texture(Gdx.files.internal("textures/background.png")));
    background.setScaling(Scaling.fill);
    background.setZIndex(0);
    stage.addActor(background);

    Gdx.input.setInputProcessor(stage);
    setupLayout(game.getImageButtonStyle(), game.getSkin());
  }

  private void setupLayout(ImageButton.ImageButtonStyle buttonStyle, Skin skin) {
    Table table = new Table();
    table.setFillParent(true);
    stage.addActor(table);

    table.setDebug(game.debug());

    Label title = new Label("K L O T S K I", skin);
    title.setFontScale(3f);
    ImageButton newGame = new ImageButton(buttonStyle);
    newGame.add(new Label("NEW GAME", skin));

    ImageButton loadGame = new ImageButton(buttonStyle);
    loadGame.add(new Label("LOAD GAME", skin));

    ImageButton settings = new ImageButton(buttonStyle);
    settings.add(new Label("SETTINGS", skin));
    newGame.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.setScreen(new GameScreen(game, State.fromRandomConfiguration()));
      }
    });

    loadGame.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        dispose();
        game.setScreen(new LoadMenuScreen(game));
      }
    });

    settings.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.setScreen(new SettingsScreen(game));
      }
    });

    table.add(title).center().padBottom(5);
    table.row();
    table.add(newGame).uniform().fillX().pad(5);
    table.row();
    table.add(loadGame).uniform().fillX().pad(5);
    table.row();
    table.add(settings).uniform().fillX().pad(5);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    float deltaT = Gdx.graphics.getDeltaTime();
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
      dispose();
      Gdx.app.exit();
    }

    stage.act(Math.min(deltaT, 1 / 60f));
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
