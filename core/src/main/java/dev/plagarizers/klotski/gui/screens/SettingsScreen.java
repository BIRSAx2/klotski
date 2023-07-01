package dev.plagarizers.klotski.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.gui.listeners.BackToMainMenuClickListener;
import dev.plagarizers.klotski.gui.util.FontGenerator;
import dev.plagarizers.klotski.gui.util.FontGenerator.LabelStyleType;

/**
 * The SettingsScreen class represents a screen in the Klotski game that allows the player to adjust various settings.
 * It implements the Screen interface provided by LibGDX.
 */
public class SettingsScreen implements Screen {
    private final KlotskiGame game; // The main game instance
    private final Stage stage; // The stage for rendering UI elements

    /**
     * Constructs a new SettingsScreen object.
     *
     * @param game The main KlotskiGame instance.
     */
    public SettingsScreen(KlotskiGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.stage.addActor(game.getBackground());

        setupLayout();
    }

    /**
     * Sets up the layout of the settings screen, including labels, sliders, and buttons.
     */
    private void setupLayout() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setDebug(game.isDebug());

        Label title = new Label("SETTINGS", FontGenerator.getInstance().getLabelStyle(LabelStyleType.MenuStyle));
        title.setAlignment(Align.center);
        table.defaults().space(7);

        table.add(title).width(Gdx.graphics.getWidth() / 2f).colspan(2).row();

        makeMusicVolumeSettings(table);
        makeEffectsVolumeSettings(table);
        makeBackButton(table);
    }

    /**
     * Creates the UI elements for adjusting the music volume setting.
     *
     * @param table The table to add the UI elements to.
     */
    private void makeMusicVolumeSettings(Table table) {
        Label musicVolume = new Label("Music Volume", FontGenerator.getInstance().getLabelStyle(LabelStyleType.InfoStyle));
        musicVolume.setAlignment(Align.left);
        Slider musicVolumeSlider = new Slider(0, 100, 1, false, game.getSkin());
        musicVolumeSlider.setValue(game.getMusicVolume());

        musicVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float volume = ((Slider) actor).getValue();
                game.setMusicVolume(volume);
            }
        });

        musicVolumeSlider.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
            }
        });

        table.add(musicVolume).left();
        table.add(musicVolumeSlider).fillX().row();
    }

    /**
     * Creates the UI elements for adjusting the effects volume setting.
     *
     * @param table The table to add the UI elements to.
     */
    private void makeEffectsVolumeSettings(Table table) {
        Label effectsVolume = new Label("Effects Volume", FontGenerator.getInstance().getLabelStyle(LabelStyleType.InfoStyle));
        effectsVolume.setAlignment(Align.left);
        Slider effectsVolumeSlider = new Slider(0, 100, 1, false, game.getSkin());
        effectsVolumeSlider.setValue(game.getEffectsVolume());

        effectsVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float volume = ((Slider) actor).getValue();
                game.setEffectsVolume(volume);
            }
        });

        effectsVolumeSlider.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
            }
        });

        table.add(effectsVolume).left();
        table.add(effectsVolumeSlider).fillX().row();
    }

    /**
     * Creates the "BACK" button and its associated click listener.
     *
     * @param table The table to add the button to.
     */
    private void makeBackButton(Table table) {
        TextButton back = new TextButton("BACK", game.getSkin());
        back.getLabel().setStyle(FontGenerator.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));

        back.addListener(new BackToMainMenuClickListener(game));
        table.add(back).colspan(2).fillX();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(delta, 1 / 60f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Not used
    }

    @Override
    public void resume() {
        // Not used
    }

    @Override
    public void hide() {
        // Not used
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
