package dev.plagarizers.klotski.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;

public class MainMenuScreen implements Screen {
  private final KlotskiGame game;
  private Stage stage;

  public MainMenuScreen(KlotskiGame game) {
    this.game = game;


    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    stage = game.getStage(new FitViewport(screenWidth, screenHeight, game.getCamera()));
    setupLayout(game.getImageButtonStyle(), game.getSkin());
  }

  private void setupLayout(ImageButton.ImageButtonStyle buttonStyle, Skin skin) {
    Table table = new Table();
    table.setFillParent(true);
    stage.addActor(table);

    table.setDebug(game.debug());
    FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
    param.size = 15;
    game.addFont(param, "TitleFont");
    FreeTypeFontGenerator.FreeTypeFontParameter param1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
    param1.size = 10;
    game.addFont(param1, "ButtonFont");
    FreeTypeFontGenerator.FreeTypeFontParameter param2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
    param2.size = 12;
    game.addFont(param, "MenuTitleFont");
    FreeTypeFontGenerator.FreeTypeFontParameter param3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
    param3.size = 9;
    game.addFont(param3, "SelectBoxFont");
    Label title = new Label("KLOTSKI", skin, "TitleFont", Color.GOLD);
    title.setFontScale(3f);


    ImageButton newGame = new ImageButton(buttonStyle);
    newGame.add(new Label("NEW GAME (RANDOM)", skin, "ButtonFont", Color.GOLD));

    ImageButton configuration = new ImageButton(buttonStyle);
    configuration.add(new Label("CHOOSE CONFIGURATION", skin, "ButtonFont", Color.GOLD));

    ImageButton loadGame = new ImageButton(buttonStyle);
    loadGame.add(new Label("LOAD GAME", skin, "ButtonFont", Color.GOLD));

    ImageButton settings = new ImageButton(buttonStyle);
    settings.add(new Label("SETTINGS", skin, "ButtonFont", Color.GOLD));

    ImageButton quit = new ImageButton(buttonStyle);
    quit.add(new Label("QUIT", skin, "ButtonFont", Color.GOLD));

    configuration.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.buttonPressedPlay();
        game.setScreen(new ConfigurationMenuScreen(game));
      }
    });
    newGame.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.buttonPressedPlay();
        game.setScreen(new GameScreen(game, State.fromRandomConfiguration()));
      }
    });

    loadGame.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.buttonPressedPlay();
        game.setScreen(new LoadMenuScreen(game));
      }
    });

    settings.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.buttonPressedPlay();
        game.setScreen(new SettingsScreen(game));
      }
    });

    quit.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.dispose();
        System.exit(0);
      }
    });

    table.add(title).center().padBottom(5);
    table.row();
    table.add(newGame).uniform().fillX().pad(5);
    table.row();
    table.add(configuration).uniform().fillX().pad(5);
    table.row();
    table.add(loadGame).uniform().fillX().pad(5);
    table.row();
    table.add(settings).uniform().fillX().pad(5);
    table.row();
    table.add(quit).uniform().fillX().pad(5);

  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    float deltaT = Gdx.graphics.getDeltaTime();
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
  }
}
