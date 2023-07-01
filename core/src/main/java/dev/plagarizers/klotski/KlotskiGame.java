package dev.plagarizers.klotski;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Scaling;
import dev.plagarizers.klotski.gui.screens.MainMenuScreen;

public class KlotskiGame extends Game {
    public static final String SKIN_PATH = "skins/default/uiskin.json";

    public static final String PREVIEWS_PATH = "levels/previews/";
    private final boolean DEBUG = false;
    private final String musicPath = "backgroundMusic.wav";
    private final String buttonPressedSoundPath = "buttonPressedSound.mp3";
    private final String backgroundImageTexturePath = "textures/background.png";
    private OrthographicCamera camera;
    private Music backgroundMusic;
    private Sound buttonPressedSound;
    private Skin gameSkin;
    private float effectsVolume;

    @Override
    public void create() {
        effectsVolume = 0.5f;

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        buttonPressedSound = Gdx.audio.newSound(Gdx.files.internal(buttonPressedSoundPath));

        gameSkin = new Skin(Gdx.files.internal(SKIN_PATH));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();


        this.setScreen(new MainMenuScreen(this));
    }

    public boolean isDebug() {
        return DEBUG;
    }

    public Image getBackground() {
        Image background = new Image(new Texture(Gdx.files.internal(backgroundImageTexturePath)));
        background.setFillParent(true);
        background.setScaling(Scaling.fill);
        return background;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void buttonPressedPlay() {
        buttonPressedSound.play(effectsVolume);
    }

    public float getMusicVolume() {
        return backgroundMusic.getVolume() * 100;
    }

    public void setMusicVolume(float musicVolume) {
        backgroundMusic.setVolume(musicVolume / 100);
    }

    public float getEffectsVolume() {
        return effectsVolume * 100;
    }

    public void setEffectsVolume(float effectsVolume) {
        this.effectsVolume = effectsVolume / 100;
    }

    public Skin getSkin() {
        return gameSkin;
    }
    
    @Override
    public void dispose() {
        gameSkin.dispose();
        backgroundMusic.dispose();
        buttonPressedSound.dispose();
    }
}
