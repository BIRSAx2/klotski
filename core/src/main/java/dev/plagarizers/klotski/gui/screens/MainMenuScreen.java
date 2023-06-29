package dev.plagarizers.klotski.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;

public class MainMenuScreen implements Screen {
    private final KlotskiGame game;
    private Stage stage;

    public MainMenuScreen(KlotskiGame game) {
        this.game = game;

        stage = new Stage(new ScreenViewport(game.getCamera()));
        stage.addActor(game.getBackground());

    }

    private void setupLayout() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setDebug(game.isDebug());

        Label title = new Label("KLOTSKI", game.getSkin());
        title.setFontScale(2.5f);

        TextButton newGame = new TextButton("NEW GAME", game.getSkin());
        TextButton configuration = new TextButton("CHOOSE CONFIGURATION", game.getSkin());
        TextButton loadGame = new TextButton("LOAD GAME", game.getSkin());
        TextButton settings = new TextButton("SETTINGS", game.getSkin());
        TextButton quit = new TextButton("QUIT", game.getSkin());

        configuration.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.buttonPressedPlay();
                game.setScreen(new ConfigurationMenuScreen(game));
            }
        });

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.buttonPressedPlay();
                game.setScreen(new GameScreen(game, State.fromRandomConfiguration()));
            }
        });

        loadGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.buttonPressedPlay();
                game.setScreen(new LoadMenuScreen(game));
            }
        });

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.buttonPressedPlay();
                game.setScreen(new SettingsScreen(game));
            }
        });

        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.dispose();
                Gdx.app.exit();
            }
        });

        table.add(title).center().padBottom(5).row();
        table.add(newGame).uniform().fillX().pad(5).row();
        table.add(configuration).uniform().fillX().pad(5).row();
        table.add(loadGame).uniform().fillX().pad(5).row();
        table.add(settings).uniform().fillX().pad(5).row();
        table.add(quit).uniform().fillX().pad(5);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        setupLayout();
    }

    @Override
    public void render(float delta) {
        float deltaT = Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(deltaT);
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
