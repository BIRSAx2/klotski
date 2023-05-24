package dev.plagarizers.klotski;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.plagarizers.klotski.screens.MainMenuScreen;

public class KlotskiGame extends Game {
  private Skin skin;
  private boolean debug;
  private ImageButton.ImageButtonStyle buttonStyle;

  @Override
  public void create() {
    debug = false;
    final String skinPath = "skins/default/uiskin.json";
    skin = new Skin(Gdx.files.internal(skinPath));

    TextureRegionDrawable buttonBackground = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/buttons/button.png"))));
    buttonStyle = new ImageButton.ImageButtonStyle();
    buttonStyle.up = buttonBackground;
    buttonStyle.down = buttonBackground.tint(Color.LIGHT_GRAY);

    Gdx.graphics.setWindowedMode(800,800);
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
