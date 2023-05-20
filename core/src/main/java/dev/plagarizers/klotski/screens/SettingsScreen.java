package dev.plagarizers.klotski.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.plagarizers.klotski.KlotskiGame;

public class SettingsScreen implements Screen {

  private final Stage stage;
  private final Skin skin;
  private final Screen caller;
  private final Table table;

  private final KlotskiGame game;

  public SettingsScreen(Screen caller, KlotskiGame game) {
    this.stage = new Stage(new ScreenViewport());
//    this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    skin = new Skin(Gdx.files.internal("skins/pixthulhu-ui/pixthulhu-ui.json"));

    this.game = game;
    this.caller = caller;
    this.table = new Table();


    Gdx.input.setInputProcessor(stage);

    table.setFillParent(true);
    stage.addActor(table);

    table.setDebug(false);

    Label title = new Label("SETTINGS", skin);
    title.setAlignment(Align.center);
    title.setFontScale(1.5f);

    table.add(title).width(Gdx.graphics.getWidth() / 2f);
    table.row();
    TextButton back = new TextButton("BACK", skin);

    table.add(back).fillX().pad(7);

    back.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        dispose();
        game.setScreen(new MainMenuScreen(game));
      }
    });


  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {

    float deltaT = Gdx.graphics.getDeltaTime();

    ScreenUtils.clear(0.176f, 0.067f, 0.365f, 0.135f);
    stage.act(Math.min(deltaT, 1 / 60f));
    stage.draw();

  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
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
