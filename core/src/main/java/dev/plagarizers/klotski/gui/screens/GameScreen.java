package dev.plagarizers.klotski.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.SavesManager;
import dev.plagarizers.klotski.gui.actors.Board;
import dev.plagarizers.klotski.gui.listeners.BackToMainMenuClickListener;

public class GameScreen implements Screen {

    private final KlotskiGame game;
    private final Stage stage;
    private final Board gameBoard;
    private final SavesManager savesManager;

    public GameScreen(KlotskiGame game, State state) {
        this.game = game;
        this.savesManager = new SavesManager(Gdx.files.getExternalStoragePath());
        State currentState = state != null ? state : State.fromRandomConfiguration();
        this.gameBoard = new Board(currentState, game.getSkin());
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), game.getCamera()));
        stage.addActor(game.getBackground());

        stage.addListener(gameBoard.getBoardListener());
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
        nextMoveButton.addListener(gameBoard.getBoardListener().getNextMoveListener());

        TextButton saveButton = new TextButton("Save", skin);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                setupSaveInput();
            }
        });

        TextButton resetButton = new TextButton("Reset", skin);
        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                gameBoard.getGameState().reset();
                Gdx.app.log("Reset", "Resetting board");
            }
        });

        TextButton undoButton = new TextButton("Undo", skin);
        undoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                gameBoard.getGameState().undoMove();
                Gdx.app.log("Undo", "Undoing move");
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(backButton).bottom().fillX().colspan(2).pad(10);
        table.add(resetButton).bottom().fillX().colspan(2).pad(10);
        table.add(saveButton).bottom().fillX().colspan(2).pad(10);
        table.row();
        table.add(gameBoard).expand().center().colspan(6).row();
        table.row();
        table.add(undoButton).bottom().fillX().colspan(2).pad(10);
        table.add(nextMoveButton).bottom().fillX().colspan(2).pad(10);

//        stage.addListener(new BoardListener(grid));
        stage.addActor(table);
    }

    private void setupSaveInput() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(0, 0, 1, 1);
        Texture background = new Texture(pixmap);
        pixmap.dispose();

        Image backgroundTransparent = new Image(background);
        backgroundTransparent.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundTransparent.getColor().a = .6f;
        stage.addActor(backgroundTransparent);
        Table saveInput = new Table();
        saveInput.setFillParent(true);
        saveInput.setDebug(game.isDebug());
        saveInput.defaults().space(10);
        Label message = new Label("Please insert a name for the save", game.getSkin());
        message.setVisible(false);
        message.setAlignment(Align.center);
        message.setColor(Color.RED);
        Label saveTag = new Label("Insert the save name:", game.getSkin());
        TextField saveName = new TextField("", game.getSkin());

        TextButton save = new TextButton("SAVE", game.getSkin());
        save.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                if (saveName.getText().equals("") || saveName.getText() == null) {
                    message.setVisible(true);
                } else {
                    savesManager.saveState(gameBoard.getState(), saveName.getText());
                    backgroundTransparent.setVisible(false);
                    saveInput.setVisible(false);
                }
            }
        });

        TextButton cancel = new TextButton("CANCEL", game.getSkin());
        cancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                backgroundTransparent.setVisible(false);
                saveInput.setVisible(false);
            }
        });

        saveInput.add(message).center().fillX().colspan(2);
        saveInput.row();
        saveInput.add(saveTag).center().width(Gdx.graphics.getWidth() / 4f).spaceRight(5);
        saveInput.add(saveName).center().width(Gdx.graphics.getWidth() / 4f).spaceLeft(5);
        saveInput.row();
        saveInput.add(save).center().width(Gdx.graphics.getWidth() / 6f).spaceRight(5);
        saveInput.add(cancel).center().width(Gdx.graphics.getWidth() / 6f).spaceLeft(5);
        stage.addActor(saveInput);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (gameBoard.getState().isSolved()) {
            game.setScreen(new GameOverScreen(game, gameBoard.getState()));
        }

//        grid.handleInput();
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
