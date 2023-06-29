package dev.plagarizers.klotski;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import dev.plagarizers.klotski.gui.screens.*;

public class KlotskiGame extends Game {
    private final boolean DEBUG = false;
    private AssetManager assetManager;
    private OrthographicCamera camera;
    private final String musicPath = "backgroundMusic.wav";
    private final String buttonPressedSoundPath = "buttonPressedSound.mp3";
    private final String skinPath = "skins/default/uiskin.json";
    private final String buttonTexturePath = "textures/buttons/button.png";
    private final String backgroundTexturePath = "textures/background.png";
    private ImageButton.ImageButtonStyle buttonStyle;
    private float effectsVolume;

    @Override
    public void create() {
        effectsVolume = 0.5f;

        assetManager = new AssetManager();
        assetManager.load(musicPath, Music.class);
        assetManager.load(buttonPressedSoundPath, Sound.class);
        assetManager.load(skinPath, Skin.class);
        assetManager.finishLoading();

        assetManager.get(musicPath, Music.class).setLooping(true);
        assetManager.get(musicPath, Music.class).setVolume(0.5f);
        assetManager.get(musicPath, Music.class).play();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        Texture buttonTexture = new Texture(Gdx.files.internal(buttonTexturePath));
        TextureRegionDrawable buttonBackground = new TextureRegionDrawable(new TextureRegion(buttonTexture));

        buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = buttonBackground;
        buttonStyle.down = buttonBackground.tint(Color.LIGHT_GRAY);

        this.setScreen(new MainMenuScreen(this));
    }

    public boolean isDebug() {
        return DEBUG;
    }

    public ImageButton.ImageButtonStyle getImageButtonStyle() {
        return buttonStyle;
    }

    public Image getBackground() {
        Image background = new Image(new Texture(Gdx.files.internal(backgroundTexturePath)));
        background.setFillParent(true);
        background.setScaling(Scaling.fill);
        return background;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void buttonPressedPlay() {
        assetManager.get(buttonPressedSoundPath, Sound.class).play(effectsVolume);
    }

    public void setMusicVolume(float musicVolume) {
        assetManager.get(musicPath, Music.class).setVolume(musicVolume / 100);
    }

    public float getMusicVolume() {
        return assetManager.get(musicPath, Music.class).getVolume() * 100;
    }

    public void setEffectsVolume(float effectsVolume) {
        this.effectsVolume = effectsVolume / 100;
    }

    public  float getEffectsVolume() {
        return effectsVolume * 100;
    }

    public Skin getSkin() {
        return assetManager.get(skinPath, Skin.class);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
