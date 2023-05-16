package dev.plagarizers.klotski;

import com.badlogic.gdx.Game;
import dev.plagarizers.klotski.screens.LoadMenuScreen;
import dev.plagarizers.klotski.screens.MainMenuScreen;


public class KlotskiGame extends Game {

  @Override
  public void create() {
    this.setScreen(new MainMenuScreen(this));
  }

  @Override
  public void render() {
    super.render();
  }

  @Override
  public void dispose() {
  }
}
