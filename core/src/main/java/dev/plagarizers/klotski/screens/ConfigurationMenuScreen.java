package dev.plagarizers.klotski.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.actors.BoardPreview;
import dev.plagarizers.klotski.game.util.Level;
import dev.plagarizers.klotski.game.util.SavesManager;

import java.util.List;

public class ConfigurationMenuScreen implements Screen {
  private Stage stage;
  private final KlotskiGame game;
  private final SavesManager savesManager = new SavesManager();

  public ConfigurationMenuScreen(KlotskiGame game) {
    this.game = game;
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    stage = game.getStage(new FitViewport(screenWidth, screenHeight, game.getCamera()));

  }


  private void setupLayout(ImageButton.ImageButtonStyle buttonStyle, Skin skin) {

    Table table = new Table();
    table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    table.setFillParent(true);
    stage.addActor(table);
    table.setDebug(game.debug());


    Label title = new Label("SELECT A CONFIGURATION", skin, "MenuTitleFont", Color.GOLD);
    title.setAlignment(Align.center);
    title.setFontScale(1.5f);

    table.add(title).colspan(6).center().padBottom(20);
    table.row();

    // TODO: this is a temporary fix, do not use in the final build
    Table selectableLevels = new Table();
    selectableLevels.setDebug(game.debug());
    ScrollPane levelSelector = new ScrollPane(selectableLevels, skin);
    levelSelector.setFadeScrollBars(false);
    levelSelector.setFlickScroll(false);
    table.add(levelSelector).fill().colspan(6).pad(7);
    selectableLevels.defaults().padBottom(20).fillX().colspan(2);
    List<Level> levels = savesManager.loadLevels();

    //levels = levels.subList(0, 6); // TODO: remove this line once we figure out how to make the table scrollable

    int i = 0;
    for (Level level : levels) {
      if (i % 3 == 0) {
        selectableLevels.row();
        //table.row();
      }

      BoardPreview board = new BoardPreview(level, skin);
      board.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          game.buttonPressedPlay();
          game.setScreen(new GameScreen(game, level.toState()));
        }
      });
      selectableLevels.add(board).pad(10).fillX();
      //table.add(board);

      i++;
    }
    levelSelector.validate();
    table.row();


    ImageButton backButton = new ImageButton(buttonStyle);
    backButton.add(new Label("Back", skin, "ButtonFont", Color.GOLD)); // Add the button text label

    backButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.buttonPressedPlay();
        game.setScreen(new MainMenuScreen(game));
      }
    });
    table.add(backButton).fill().colspan(6).pad(7);

    stage.addActor(table);
  }


  @Override
  public void show() {
    setupLayout(game.getImageButtonStyle(), game.getSkin());
    Gdx.input.setInputProcessor(stage);
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

  }
}
