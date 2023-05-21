package dev.plagarizers.klotski.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.SavesManager;

import java.util.List;

public class LoadMenuScreen implements Screen {
  private final Stage stage;
  private final Table table;
  private final Skin skin;
  private final Screen caller;
  private final KlotskiGame game;

  private final SavesManager savesManager = new SavesManager();

  public LoadMenuScreen(Screen caller, KlotskiGame game) {
    this.game = game;
    this.caller = caller;
    stage = new Stage(new ScreenViewport());
    Gdx.input.setInputProcessor(stage);

    table = new Table();
    table.setFillParent(true);
    stage.addActor(table);

    table.setDebug(false);

    skin = new Skin(Gdx.files.internal(game.getSkinPath()));


    Label title = new Label("SELECT A SAVE SLOT", skin);
    title.setAlignment(Align.center);
    title.setFontScale(1.5f);

    List<String> saves = savesManager.getSavedStatePaths();

    table.add(title).width(Gdx.graphics.getWidth() / 2f);
    table.row();

    ClickListener startFromSave = new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        String saveName = ((TextButton) event.getListenerActor()).getText().toString();
        System.out.println("Loading save: " + saveName);
        State save = savesManager.loadStateByName(saveName);
        game.setScreen(new GameScreen(game, save));
      }
    };
    for (String save : saves) {
      String fileName = save.substring(save.lastIndexOf("/") + 1);
      TextButton saveButton = new TextButton(fileName, skin);
      saveButton.addListener(startFromSave);
      saveButton.getLabel().setAlignment(Align.left);
      table.add(saveButton).fillX().pad(7);
      table.row();
    }


    TextButton back = new TextButton("BACK", skin);

    back.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        dispose();
        game.setScreen(new MainMenuScreen(game));
      }
    });
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
