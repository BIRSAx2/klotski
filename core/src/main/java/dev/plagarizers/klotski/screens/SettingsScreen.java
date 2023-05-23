package dev.plagarizers.klotski.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
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
    skin = new Skin(Gdx.files.internal(game.getSkinPath()));
    Image background = new Image(new Texture(Gdx.files.internal("textures/background.png")));
    background.setScaling(Scaling.fill);
    background.setZIndex(0);
    stage.addActor(background);

    this.game = game;
    this.caller = caller;
    this.table = new Table();


    Gdx.input.setInputProcessor(stage);

    table.setFillParent(true);
    stage.addActor(table);

    table.setDebug(false);

    TextureRegionDrawable buttonBackground = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/buttons/button.png"))));

    // Create the ImageButton style
    ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
    buttonStyle.up = buttonBackground;
    buttonStyle.down = buttonBackground.tint(Color.LIGHT_GRAY);


    Label title = new Label("SETTINGS", skin);
    title.setAlignment(Align.center);
    title.setFontScale(1.5f);

    table.add(title).width(Gdx.graphics.getWidth() / 2f);
    table.row();
//    TextButton back = new TextButton("BACK", skin);

    ImageButton back = new ImageButton(buttonStyle);
    back.add(new Label("BACK", skin));
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

//    ScreenUtils.clear(0.176f, 0.067f, 0.365f, 0.135f);
    //ScreenUtils.clear(Color.valueOf("#72751B"));

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
