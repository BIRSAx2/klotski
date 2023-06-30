package dev.plagarizers.klotski;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Scaling;
import dev.plagarizers.klotski.gui.screens.*;

import java.util.HashMap;
import java.util.Map;

public class KlotskiGame extends Game {
    private final boolean DEBUG = false;
    private final String musicPath = "backgroundMusic.wav";
    private final String buttonPressedSoundPath = "buttonPressedSound.mp3";
    private final String skinPath = "skins/default/uiskin.json";
    private final String backgroundImageTexturePath = "textures/background.png";
    private OrthographicCamera camera;
    private Music backgroundMusic;
    private Sound buttonPressedSound;
    private Skin gameSkin;
    private float effectsVolume;
    private HashMap<FontType, BitmapFont> fonts;
    private HashMap<LabelStyleType, Label.LabelStyle> labelStyles;

    @Override
    public void create() {
        effectsVolume = 0.5f;

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        buttonPressedSound = Gdx.audio.newSound(Gdx.files.internal(buttonPressedSoundPath));

        gameSkin = new Skin(Gdx.files.internal(skinPath));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        fonts = new HashMap<>();
        labelStyles = new HashMap<>();
        setupFonts();

        this.setScreen(new MainMenuScreen(this));
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter getTitleFontParameter() {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        return parameter;
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter getButtonFontParameter() {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        return parameter;
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter getMenuFontParameter() {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        return parameter;
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter getInfoFontParameter() {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35;
        return parameter;
    }

    private void setupFonts() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/monogram.ttf"));
        fonts.put(FontType.Title, fontGenerator.generateFont(getTitleFontParameter()));
        fonts.put(FontType.Button, fontGenerator.generateFont(getButtonFontParameter()));
        fonts.put(FontType.Menu, fontGenerator.generateFont(getMenuFontParameter()));
        fonts.put(FontType.Info, fontGenerator.generateFont(getInfoFontParameter()));
        fontGenerator.dispose();

        labelStyles.put(LabelStyleType.TitleStyle, new Label.LabelStyle(fonts.get(FontType.Title), Color.GOLD));
        labelStyles.put(LabelStyleType.ButtonStyle, new Label.LabelStyle(fonts.get(FontType.Button), Color.WHITE));
        labelStyles.put(LabelStyleType.MenuStyle, new Label.LabelStyle(fonts.get(FontType.Menu), Color.GOLD));
        labelStyles.put(LabelStyleType.InfoStyle, new Label.LabelStyle(fonts.get(FontType.Info), Color.WHITE));
        labelStyles.put(LabelStyleType.AlertStyle, new Label.LabelStyle(fonts.get(FontType.Info), Color.RED));
    }

    public Label.LabelStyle getLabelStyle(LabelStyleType labelStyleType) {
        return labelStyles.get(labelStyleType);
    }

    public BitmapFont getFont(FontType fontType) {
        return fonts.get(fontType);
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

    public void setMusicVolume(float musicVolume) {
        backgroundMusic.setVolume(musicVolume / 100);
    }

    public float getMusicVolume() {
        return backgroundMusic.getVolume() * 100;
    }

    public void setEffectsVolume(float effectsVolume) {
        this.effectsVolume = effectsVolume / 100;
    }

    public  float getEffectsVolume() {
        return effectsVolume * 100;
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
