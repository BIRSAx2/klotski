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

public class LoadMenuScreen implements Screen {
  private final Stage stage;
  private final Table table;
  private final Skin skin;
  private final Screen caller;
  private final KlotskiGame game;

  public LoadMenuScreen(Screen caller, KlotskiGame game) {
    this.game = game;
    this.caller = caller;
    stage = new Stage(new ScreenViewport());
    Gdx.input.setInputProcessor(stage);

    table = new Table();
    table.setFillParent(true);
    stage.addActor(table);

    table.setDebug(false);

    skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

    Label title = new Label("SELECT A SAVE SLOT", skin);
    title.setAlignment(Align.center);
    title.setFontScale(1.5f);
    TextButton firstSave = new TextButton(getSaveName() + "\nTotal moves: " + getMoves(getSaveName()), skin);
    firstSave.getLabel().setAlignment(Align.left);
    TextButton secondSave = new TextButton(getSaveName() + "\nTotal moves: " + getMoves(getSaveName()), skin);
    secondSave.getLabel().setAlignment(Align.left);
    TextButton thirdSave = new TextButton(getSaveName() + "\nTotal moves: " + getMoves(getSaveName()), skin);
    thirdSave.getLabel().setAlignment(Align.left);

    TextButton back = new TextButton("BACK", skin);

    back.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        dispose();
//        game.setScreen(new MainMenuScreen(game));
      }
    });

    table.add(title).width(Gdx.graphics.getWidth() / 2f);
    table.row();
    table.add(firstSave).fillX().pad(7);
    table.row();
    table.add(secondSave).fillX().pad(7);
    table.row();
    table.add(thirdSave).fillX().pad(7);
    table.row();
    table.add(back).fill().pad(7);
  }

  public String getSaveName() {
    return "Save 1";
  }

  public int getMoves(String filepath) {
    return 42;
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
