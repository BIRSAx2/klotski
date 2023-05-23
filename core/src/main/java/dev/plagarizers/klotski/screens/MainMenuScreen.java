package dev.plagarizers.klotski.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;

public class MainMenuScreen implements Screen {
  private final KlotskiGame game;
  private final Stage stage;
  private final Table table;
  private final Skin skin;
  private OrthographicCamera cam;


  public MainMenuScreen(KlotskiGame game) {
    this.game = game;
    Screen thisScreen = this;

    Gdx.graphics.setWindowedMode(800,800);
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    cam = new OrthographicCamera(screenWidth, screenHeight);
    cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
    cam.update();

    stage = new Stage(new FitViewport(screenWidth, screenHeight, cam));


    TextureRegionDrawable buttonBackground = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/buttons/button.png"))));
    Image background = new Image(new Texture(Gdx.files.internal("textures/background.png")));
    background.setScaling(Scaling.fill);
    background.setZIndex(0);
    stage.addActor(background);

    // Create the ImageButton style
    ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
    buttonStyle.up = buttonBackground;
    buttonStyle.down = buttonBackground.tint(Color.LIGHT_GRAY);


    Gdx.input.setInputProcessor(stage);

    table = new Table();
    table.setFillParent(true);
    stage.addActor(table);

    table.setDebug(false);

    skin = new Skin(Gdx.files.internal(game.getSkinPath()));

    Label title = new Label("K L O T S K I", skin);
    title.setFontScale(3f);
//    TextButton newGame = new TextButton("NEW GAME", skin);
    ImageButton newGame = new ImageButton(buttonStyle);
    newGame.add(new Label("NEW GAME", skin));

//    TextButton loadGame = new TextButton("LOAD GAME", skin);
    ImageButton loadGame = new ImageButton(buttonStyle);
    loadGame.add(new Label("LOAD GAME", skin));
//    TextButton settings = new TextButton("SETTINGS", skin);

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
        game.setScreen(new LoadMenuScreen(thisScreen, game));
      }
    });

    settings.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.setScreen(new SettingsScreen(thisScreen, game));
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
//    ScreenUtils.clear(0.176f, 0.067f, 0.365f, 0.135f);
    //ScreenUtils.clear(Color.valueOf("#72751B"));

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
