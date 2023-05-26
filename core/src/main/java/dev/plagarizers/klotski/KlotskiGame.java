package dev.plagarizers.klotski;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.plagarizers.klotski.screens.MainMenuScreen;

// TODO: use assetManager class for shared resources to reduce allocation and memory consumption
public class KlotskiGame extends Game {
  private boolean debug;
  private ImageButton.ImageButtonStyle buttonStyle;
  private OrthographicCamera cam;
  private float musicVolume;
  private float effectsVolume;
  private final String music = "backgroundMusic.wav";
  private final String buttonPressedSound = "buttonPressedSound.mp3";
  private final String skinPath = "skins/default/uiskin.json";
  private AssetManager manager;
  private Stage stage;
  private FreeTypeFontGenerator generator;

  @Override
  public void create() {
    debug = true;
    musicVolume = 0.5f;
    effectsVolume = 0.5f;
    stage = new Stage();
    Gdx.input.setInputProcessor(stage);

    manager = new AssetManager();
    generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));

    manager.load(music, Music.class);
    manager.load(buttonPressedSound, Sound.class);
    manager.load(skinPath, Skin.class);
    //manager.load("fonts/font.ttf", FreeTypeFontGenerator.class);
    manager.finishLoading();
    manager.get(music, Music.class).setLooping(true);
    manager.get(music, Music.class).setVolume(musicVolume);

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
    stage.clear();
    stage.setViewport(viewport);

    Image background = new Image(new Texture(Gdx.files.internal("textures/background.png")));
    background.setScaling(Scaling.fill);
    background.setZIndex(0);
    stage.addActor(background);

    return stage;
  }

  public void buttonPressedPlay() {
    manager.get(buttonPressedSound, Sound.class).play(effectsVolume);
  }

  public void setMusicVolume(float musicVolume) {
    this.musicVolume = musicVolume / 100;
    manager.get(music, Music.class).setVolume(this.musicVolume);
  }

  public float getMusicVolume() {
    return musicVolume;
  }

  public void setEffectsVolume(float effectsVolume) {
    this.effectsVolume = effectsVolume / 100;
  }

  public float getEffectsVolume() {
    return effectsVolume;
  }

  public Skin getSkin() {
    return manager.get(skinPath, Skin.class);
  }

  public void addFont(FreeTypeFontGenerator.FreeTypeFontParameter parameter, String name) {
    BitmapFont generated = generator.generateFont(parameter);
    manager.get(skinPath, Skin.class).add(name, generated, BitmapFont.class);
  }

  public BitmapFont getFont(String name) {
    return manager.get(skinPath, Skin.class).getFont(name);
  }

  @Override
  public void render() {
    if(manager.update(17)) {
      manager.get(music, Music.class).play();
      super.render();
    }
  }

  @Override
  public void dispose() {
    manager.dispose();
    stage.dispose();
    generator.dispose();
  }
}
