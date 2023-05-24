package dev.plagarizers.klotski;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.plagarizers.klotski.screens.MainMenuScreen;

public class KlotskiGame extends Game {
  private Skin skin;
  private boolean debug;
  private ImageButton.ImageButtonStyle buttonStyle;
  private OrthographicCamera cam;

  @Override
  public void create() {
    debug = false;
    Gdx.graphics.setWindowedMode(800,800);

    final String skinPath = "skins/default/uiskin.json";
    skin = new Skin(Gdx.files.internal(skinPath));

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    cam = new OrthographicCamera(screenWidth, screenHeight);
    cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
    cam.update();

    TextureRegionDrawable buttonBackground = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/buttons/button.png"))));
    buttonStyle = new ImageButton.ImageButtonStyle();
    buttonStyle.up = buttonBackground;
    buttonStyle.down = buttonBackground.tint(Color.LIGHT_GRAY);

    this.setScreen(new MainMenuScreen(this));
  }

  public boolean debug() {
    return debug;
  }

  public void toggleDebug() {
    debug = !debug;
  }

  public ImageButton.ImageButtonStyle getImageButtonStyle() {
    return buttonStyle;
  }

  public OrthographicCamera getCamera() {
    return cam;
  }

  public Stage getStage(Viewport viewport) {
    Stage stage = new Stage(viewport);

    Image background = new Image(new Texture(Gdx.files.internal("textures/background.png")));
    background.setScaling(Scaling.fill);
    background.setZIndex(0);
    stage.addActor(background);

    Gdx.input.setInputProcessor(stage);
    return stage;
  }

  public Skin getSkin() {
    return skin;
  }

  @Override
  public void render() {
    super.render();
  }

  @Override
  public void dispose() {
    skin.dispose();
  }
}
