package dev.plagarizers.klotski.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;

public class MainMenuScreen implements Screen {
    private final KlotskiGame game;
    private Stage stage;

    public MainMenuScreen(KlotskiGame game) {
        this.game = game;


        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        stage = game.getStage(new FitViewport(screenWidth, screenHeight, game.getCamera()));
        setupLayout(game.getImageButtonStyle(), game.getSkin());
    }

    private void setupLayout(ImageButton.ImageButtonStyle buttonStyle, Skin skin) {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setDebug(game.debug());
        Label title = new Label("KLOTSKI", skin);
        title.setFontScale(2.5f);

        Button newGame = new Button( skin);
        newGame.add(new Label("NEW GAME", skin));
        TextButton loadGame = new TextButton("LOAD GAME", skin);
        TextButton settings = new TextButton("SETTINGS", skin);
        TextButton quit = new TextButton("QUIT", skin);
        TextButton configuration = new TextButton("CHOOSE CONFIGURATION", skin);



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
                System.exit(0);
            }
        });
        table.add(title).center().padBottom(5);
        table.row();
        table.add(newGame).uniform().fillX().pad(5);
        table.row();
        table.add(configuration).uniform().fillX().pad(5);
        table.row();
        table.add(loadGame).uniform().fillX().pad(5);
        table.row();
        table.add(settings).uniform().fillX().pad(5);
        table.row();
        table.add(quit).uniform().fillX().pad(5);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        float deltaT = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(deltaT, 1 / 60f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        //cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        //cam.update();
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
    }
}
