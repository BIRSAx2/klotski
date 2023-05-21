package dev.plagarizers.klotski;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import dev.plagarizers.klotski.screens.MainMenuScreen;

public class KlotskiGame extends Game {


  private Skin skin;

  private String skinPath = "skins/default/uiskin.json";

  @Override
  public void create() {
    skin = new Skin(Gdx.files.internal(skinPath));
    this.setScreen(new MainMenuScreen(this));
  }

  public String getSkinPath() {
    return skinPath;
  }

  @Override
  public void render() {
    super.render();
  }

  @Override
  public void dispose() {
  }
}
