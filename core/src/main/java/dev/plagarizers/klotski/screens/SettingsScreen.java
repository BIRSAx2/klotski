package dev.plagarizers.klotski.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.util.Resolution;

public class SettingsScreen implements Screen {
  private Stage stage;
  private final KlotskiGame game;
  SelectBox<Resolution> resolutions;

  public SettingsScreen(KlotskiGame game) {
    this.game = game;
    stage = game.getStage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    setupLayout(game.getImageButtonStyle(), game.getSkin());
  }

  private void setupLayout(ImageButton.ImageButtonStyle buttonStyle, Skin skin) {
    Table table = new Table();
    table.setFillParent(true);
    stage.addActor(table);

    table.setDebug(game.debug());

    Label title = new Label("SETTINGS", skin);
    title.setAlignment(Align.center);
    title.setFontScale(1.5f);
    table.defaults().space(7);

    table.add(title).width(Gdx.graphics.getWidth() / 2f).colspan(2);
    table.row();

    makeSettings(table, skin);

    ImageButton back = new ImageButton(buttonStyle);
    back.add(new Label("BACK", skin));
    table.add(back).space(7).colspan(2);

    back.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.buttonPressedPlay();
        dispose();
        game.setScreen(new MainMenuScreen(game));
      }
    });

  }

  private void makeSettings(Table table, Skin skin) {
    Label musicVolume = new Label("Music Volume", skin);
    musicVolume.setAlignment(Align.left);
    Slider musicVolumeSlider = new Slider(0, 100, 1, false, skin);
    musicVolumeSlider.setValue(game.getMusicVolume() * 100);
    musicVolumeSlider.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        float volume = ((Slider) actor).getValue();
        game.setMusicVolume(volume);
        ((Slider) actor).setValue(volume);
      }
    });
    table.add(musicVolume);
    table.add(musicVolumeSlider).fillX();
    table.row();

    Label effectsVolume = new Label("Effects Volume", skin);
    effectsVolume.setAlignment(Align.left);
    Slider effectsVolumeSlider = new Slider(0, 100, 1, false, skin);
    effectsVolumeSlider.setValue(game.getEffectsVolume() * 100);
    effectsVolumeSlider.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        float volume = ((Slider) actor).getValue();
        game.setEffectsVolume(volume);
      }
    });
    table.add(effectsVolume);
    table.add(effectsVolumeSlider).fillX();
    table.row();

    // TODO: fix the spacing between the resolution and aspect ratio
    Label resolution = new Label("Resolution ", skin);
    resolutions = new SelectBox<>(skin);
    resolutions.setItems(
      new Resolution(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()),
      new Resolution(1920, 1080),
      new Resolution(1280, 720),
      new Resolution(640, 480)
    );
    table.add(resolution);
    table.add(resolutions);
    table.row();
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    float deltaT = Gdx.graphics.getDeltaTime();
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    Resolution selected = resolutions.getSelected();
    if(selected.getWidth() != Gdx.graphics.getWidth() || selected.getHeight() != Gdx.graphics.getHeight()) {
      resize(selected.getWidth(), selected.getHeight());
    }

    stage.act(Math.min(deltaT, 1 / 60f));
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    Gdx.graphics.setWindowedMode(width, height);
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
  }
}
