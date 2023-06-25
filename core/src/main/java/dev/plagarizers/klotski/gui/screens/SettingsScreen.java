package dev.plagarizers.klotski.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.gui.util.Resolution;

public class SettingsScreen implements Screen {
    private final KlotskiGame game;
    private final Stage stage;
    private SelectBox<Resolution> resolutions;

    public SettingsScreen(KlotskiGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.stage.addActor(game.getBackground());

        setupLayout(game.getSkin());
    }

    private void setupLayout(Skin skin) {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setDebug(game.debug());

        Label title = new Label("SETTINGS", skin);
        title.setAlignment(Align.center);
        title.setFontScale(1.5f);
        table.defaults().space(7);

        table.add(title).width(Gdx.graphics.getWidth() / 2f).colspan(2).row();

        makeMusicVolumeSettings(table, skin);
        makeEffectsVolumeSettings(table, skin);
        makeResolutionSettings(table, skin);
        makeBackButton(table, skin);
    }

    private void makeMusicVolumeSettings(Table table, Skin skin) {
        Label musicVolume = new Label("Music Volume", skin);
        musicVolume.setAlignment(Align.left);
        Slider musicVolumeSlider = new Slider(0, 100, 1, false, skin);
        musicVolumeSlider.setValue(game.getMusicVolume() * 100);


        musicVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float volume = ((Slider) actor).getValue();
                game.setMusicVolume(volume);
                ((Slider) actor).setValue(volume);

            }
        });


        table.add(musicVolume).left();
        table.add(musicVolumeSlider).fillX().row();
    }

    private void makeEffectsVolumeSettings(Table table, Skin skin) {
        Label effectsVolume = new Label("Effects Volume", skin);
        effectsVolume.setAlignment(Align.left);
        Slider effectsVolumeSlider = new Slider(0, 100, 1, false, skin);
        effectsVolumeSlider.setValue(game.getEffectsVolume() * 100);
        effectsVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float volume = ((Slider) actor).getValue();
                game.setEffectsVolume(volume);
            }
        });
        table.add(effectsVolume).left();
        table.add(effectsVolumeSlider).fillX().row();
    }

    private void makeResolutionSettings(Table table, Skin skin) {
        Label resolution = new Label("Resolution", skin);
        resolutions = new SelectBox<>(skin);
        resolutions.setItems(new Resolution(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), new Resolution(1920, 1080), new Resolution(1280, 720), new Resolution(640, 480));
        table.add(resolution).left();
        table.add(resolutions).fillX().row();
    }

    private void makeBackButton(Table table, Skin skin) {
        TextButton back = new TextButton("BACK", skin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.buttonPressedPlay();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        table.add(back).colspan(2).fillX();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Resolution selected = resolutions.getSelected();
        if (selected.getWidth() != Gdx.graphics.getWidth() || selected.getHeight() != Gdx.graphics.getHeight()) {
            resize(selected.getWidth(), selected.getHeight());
        }

        stage.act(Math.min(delta, 1 / 60f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        Gdx.graphics.setWindowedMode(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
