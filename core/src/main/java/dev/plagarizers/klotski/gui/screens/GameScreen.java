package dev.plagarizers.klotski.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.SavesManager;
import dev.plagarizers.klotski.gui.actors.Board;

public class GameScreen implements Screen {

    private final KlotskiGame game;
    private final Stage stage;
    private final Board grid;
    private final SavesManager savesManager;

    public GameScreen(KlotskiGame game, State state) {
        this.game = game;
        this.savesManager = new SavesManager(Gdx.files.getExternalStoragePath());
        State currentState = state != null ? state : State.fromRandomConfiguration();
        this.grid = new Board(currentState, game.getSkin());
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), game.getCamera()));
        stage.addActor(game.getBackground());

        setupLayout(game.getSkin());
    }

    private void setupLayout(Skin skin) {
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        TextButton nextMoveButton = new TextButton("Next Move", skin);
        nextMoveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                grid.playBestMove();
            }
        });

        TextButton saveButton = new TextButton("Save", skin);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                savesManager.saveState(grid.getState());
            }
        });

        TextButton resetButton = new TextButton("Reset", skin);
        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                grid.reset();
                Gdx.app.log("Reset", "Resetting board");
            }
        });

        TextButton undoButton = new TextButton("Undo", skin);
        undoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                grid.undoMove();
                Gdx.app.log("Undo", "Undoing move");
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(backButton).bottom().fillX().colspan(2).pad(10);
        table.add(resetButton).bottom().fillX().colspan(2).pad(10);
        table.add(saveButton).bottom().fillX().colspan(2).pad(10);
        table.row();
        table.add(grid).expand().center().colspan(6).row();
        table.row();
        table.add(undoButton).bottom().fillX().colspan(2).pad(10);
        table.add(nextMoveButton).bottom().fillX().colspan(2).pad(10);

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (grid.getState().isSolved()) {
            game.setScreen(new GameOverScreen(game, grid.getState()));
        }

        grid.handleInput();
        stage.act(delta);
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
