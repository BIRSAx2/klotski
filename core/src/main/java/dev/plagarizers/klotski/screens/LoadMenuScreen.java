package dev.plagarizers.klotski.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
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

    Label title = new Label("SELECT A SAVE SLOT", skin, "MenuTitleFont", Color.GOLD);
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
      String fileName = getSaveName(save);
      ImageButton saveButton = new ImageButton(buttonStyle);
      Label saveName = new Label(fileName, skin, "ButtonFont", Color.GOLD);
      saveName.setAlignment(Align.left);
      saveButton.add(saveName);
      saveButton.addListener(startFromSave);
      table.add(saveButton).fillX().pad(7);
      table.row();
    }

    ImageButton back = new ImageButton(buttonStyle);
    back.add(new Label("BACK", skin, "ButtonFont", Color.GOLD));
    back.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.buttonPressedPlay();
        game.setScreen(new MainMenuScreen(game));
      }
    });
    table.add(back).fill().pad(7);
  }

  private String getSaveName(String save) {
    String filename = save.trim().substring(save.lastIndexOf("/") + 1, save.length() - 5);
    String[] parts = filename.split("_");
    return "Save: " + parts[1] + "\nMoves: " + getMoves();
  }

  // TODO: implement getMoves from file
  private int getMoves() {
    return 42;
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
  }
}
