package dev.plagarizers.klotski;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.gui.screens.*;

import java.util.HashMap;
import java.util.Map;

public class KlotskiGame extends Game {
    private boolean debug;
    private AssetManager assetManager;
    private Stage stage;
    private OrthographicCamera camera;
    private final String musicPath = "backgroundMusic.wav";
    private final String buttonPressedSoundPath = "buttonPressedSound.mp3";
    private final String skinPath = "skins/default/uiskin.json";
    private final String buttonTexturePath = "textures/buttons/button.png";
    private final String backgroundTexturePath = "textures/background.png";
    private ImageButton.ImageButtonStyle buttonStyle;
    private Sound buttonPressedSound;
    private Music backgroundMusic;
    private HashMap<ScreenType, Screen> screens;

    @Override
    public void create() {
        screens = new HashMap<>();
        debug = false;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        assetManager = new AssetManager();
        assetManager.load(musicPath, Music.class);
        assetManager.load(buttonPressedSoundPath, Sound.class);
        assetManager.load(skinPath, Skin.class);
        assetManager.finishLoading();

        backgroundMusic = assetManager.get(musicPath, Music.class);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        Texture buttonTexture = new Texture(Gdx.files.internal(buttonTexturePath));
        TextureRegionDrawable buttonBackground = new TextureRegionDrawable(new TextureRegion(buttonTexture));

        buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = buttonBackground;
        buttonStyle.down = buttonBackground.tint(Color.LIGHT_GRAY);
        makeScreens();

        this.setScreen(screens.get(ScreenType.MainMenu));
    }

    private void makeScreens() {
        screens.put(ScreenType.MainMenu, new MainMenuScreen(this));
        screens.put(ScreenType.Game, new GameScreen(this, State.fromRandomConfiguration()));
        screens.put(ScreenType.LoadSave, new LoadMenuScreen(this));
        screens.put(ScreenType.LoadConfig, new ConfigurationMenuScreen(this));
        screens.put(ScreenType.Settings, new SettingsScreen(this));
    }

    public Screen getScreen(ScreenType type) {
        return screens.get(type);
    }

    public boolean isDebug() {
        return debug;
    }

    public void toggleDebug() {
        debug = !debug;
    }

    public ImageButton.ImageButtonStyle getImageButtonStyle() {
        return buttonStyle;
    }

    public Image getBackground() {
        return new Image(new Texture(Gdx.files.internal(backgroundTexturePath)));
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Stage getStage(Viewport viewport) {
        stage.clear();
        stage.setViewport(viewport);

        Image background = new Image(new Texture(Gdx.files.internal(backgroundTexturePath)));
        background.setScaling(Scaling.fill);
        background.setZIndex(0);
        stage.addActor(background);
        return stage;
    }

    public void buttonPressedPlay() {
        buttonPressedSound = assetManager.get(buttonPressedSoundPath, Sound.class);
        buttonPressedSound.play(0.5f);
    }

    public void setMusicVolume(float musicVolume) {
        backgroundMusic.setVolume(musicVolume / 100);
    }

    public float getMusicVolume() {
        return backgroundMusic.getVolume() * 100;
    }

    public void setEffectsVolume(float effectsVolume) {
        // Not implemented in the provided code, you can add your implementation here
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Skin getSkin() {
        return assetManager.get(skinPath, Skin.class);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        for(Map.Entry<ScreenType, Screen> entry : screens.entrySet()) {
            entry.getValue().dispose();
        }
    }

}
