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
import dev.plagarizers.klotski.gui.util.FontHandler;
import dev.plagarizers.klotski.gui.util.FontHandler.LabelStyleType;
import dev.plagarizers.klotski.gui.util.SoundHandler;

/**
 * The MainMenuScreen class represents the main menu screen in the Klotski game.
 * It implements the Screen interface provided by LibGDX.
 */
public class MainMenuScreen implements Screen {
    private final KlotskiGame game; // The main game instance
    private Stage stage; // The stage for rendering UI elements

    /**
     * Constructs a new MainMenuScreen object.
     *
     * @param game The main KlotskiGame instance.
     */
    public MainMenuScreen(KlotskiGame game) {
        this.game = game;

        stage = new Stage(new ScreenViewport(game.getCamera()));
        stage.addActor(game.getBackground());
    }

    /**
     * Sets up the layout of the main menu screen, including labels and buttons.
     */
    private void setupLayout() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("KLOTSKI", FontHandler.getInstance().getLabelStyle(LabelStyleType.TitleStyle));
        title.setFontScale(3);

        TextButton newGame = new TextButton("NEW GAME", game.getSkin());
        newGame.getLabel().setStyle(FontHandler.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));
        TextButton configuration = new TextButton("CHOOSE CONFIGURATION", game.getSkin());
        configuration.getLabel().setStyle(FontHandler.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));
        TextButton loadGame = new TextButton("LOAD GAME", game.getSkin());
        loadGame.getLabel().setStyle(FontHandler.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));
        TextButton settings = new TextButton("SETTINGS", game.getSkin());
        settings.getLabel().setStyle(FontHandler.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));
        TextButton quit = new TextButton("QUIT", game.getSkin());
        quit.getLabel().setStyle(FontHandler.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));

        configuration.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundHandler.getInstance().playButtonClick();
                game.getScreen().dispose();
                game.setScreen(new ConfigurationMenuScreen(game));
            }
        });

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundHandler.getInstance().playButtonClick();
                game.getScreen().dispose();
                game.setScreen(new GameScreen(game, State.fromRandomLevel()));
            }
        });

        loadGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundHandler.getInstance().playButtonClick();
                game.getScreen().dispose();
                game.setScreen(new LoadMenuScreen(game));
            }
        });

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SoundHandler.getInstance().playButtonClick();
                game.getScreen().dispose();
                game.setScreen(new SettingsScreen(game));
            }
        });

        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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
        SoundHandler.getInstance().playBackgroundMusic();
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
