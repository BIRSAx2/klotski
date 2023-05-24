package dev.plagarizers.klotski.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.SavesManager;

import java.util.List;

public class LoadMenuScreen implements Screen {
  private Stage stage;
  private final KlotskiGame game;

  private final SavesManager savesManager = new SavesManager();

  public LoadMenuScreen(KlotskiGame game) {
    this.game = game;
    stage = game.getStage(new ScreenViewport());
    setupLayout(game.getImageButtonStyle(), game.getSkin());
  }

  private void setupLayout(ImageButton.ImageButtonStyle buttonStyle, Skin skin) {
    Table table = new Table();
    table.setFillParent(true);
    stage.addActor(table);

    table.setDebug(game.debug());

    Label title = new Label("SELECT A SAVE SLOT", skin);
    title.setAlignment(Align.center);
    title.setFontScale(1.5f);

    List<String> saves = savesManager.getSavedStatePaths();

    table.add(title).width(Gdx.graphics.getWidth() / 2f);
    table.row();

    ClickListener startFromSave = new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.buttonPressedPlay();
        String saveName = ((Label) event.getTarget()).getText().toString();

        System.out.println("Save name: " + saveName);
        System.out.println("Loading save: " + saveName);
        State save = savesManager.loadStateByName(saveName);
        game.setScreen(new GameScreen(game, save));
      }
    };

    for (String save : saves) {
      String fileName = save.substring(save.lastIndexOf("/") + 1);
      ImageButton saveButton = new ImageButton(buttonStyle);
      saveButton.add(new Label(fileName, skin));
      saveButton.addListener(startFromSave);
      table.add(saveButton).fillX().pad(7);
      table.row();
    }

    ImageButton back = new ImageButton(buttonStyle);
    back.add(new Label("BACK", skin));
    back.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.buttonPressedPlay();
        dispose();
        game.setScreen(new MainMenuScreen(game));
      }
    });
    table.add(back).fill().pad(7);
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
