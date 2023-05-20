package dev.plagarizers.klotski.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.plagarizers.klotski.KlotskiGame;

public class MainMenuScreen implements Screen {
  private final KlotskiGame game;
  private final Stage stage;
  private final Table table;
  private final Skin skin;

  public MainMenuScreen(KlotskiGame game) {
    this.game = game;
    Screen thisScreen = this;
    stage = new Stage(new ScreenViewport());
    Gdx.input.setInputProcessor(stage);

    table = new Table();
    table.setFillParent(true);
    stage.addActor(table);

    table.setDebug(false);

//    skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    skin = new Skin(Gdx.files.internal("skins/pixthulhu-ui/pixthulhu-ui.json"));

    Image title = new Image(new Texture(Gdx.files.internal("title.png")));
    TextButton newGame = new TextButton("NEW GAME", skin);
    TextButton loadGame = new TextButton("LOAD GAME", skin);
    TextButton settings = new TextButton("SETTINGS", skin);

    newGame.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.setScreen(new GameScreen());
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

    table.add(title).width(Gdx.graphics.getWidth() / 3f).fillX().padBottom(5);
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
    ScreenUtils.clear(0.176f, 0.067f, 0.365f, 0.135f);
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
