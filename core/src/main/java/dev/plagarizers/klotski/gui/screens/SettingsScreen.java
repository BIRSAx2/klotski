package dev.plagarizers.klotski.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.gui.listeners.BackToMainMenuClickListener;

public class SettingsScreen implements Screen {
    private final KlotskiGame game;
    private final Stage stage;

    public SettingsScreen(KlotskiGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.stage.addActor(game.getBackground());

        setupLayout();
    }

    private void setupLayout() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setDebug(game.isDebug());

        Label title = new Label("SETTINGS", game.getSkin());
        title.setAlignment(Align.center);
        title.setFontScale(1.5f);
        table.defaults().space(7);

        table.add(title).width(Gdx.graphics.getWidth() / 2f).colspan(2).row();

        makeMusicVolumeSettings(table);
        makeEffectsVolumeSettings(table);
        makeBackButton(table);
    }

    private void makeMusicVolumeSettings(Table table) {
        Label musicVolume = new Label("Music Volume", game.getSkin());
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


        table.add(musicVolume).left();
        table.add(musicVolumeSlider).fillX().row();
    }

    private void makeEffectsVolumeSettings(Table table) {
        Label effectsVolume = new Label("Effects Volume", game.getSkin());
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
        table.add(effectsVolume).left();
        table.add(effectsVolumeSlider).fillX().row();
    }


    private void makeBackButton(Table table) {
        TextButton back = new TextButton("BACK", game.getSkin());

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
