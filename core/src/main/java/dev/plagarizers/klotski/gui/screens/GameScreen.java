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
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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

        this.stage = new Stage(new ScreenViewport(game.getCamera()));
        stage.addActor(game.getBackground());

        stage.addListener(gameBoard.getBoardListener());
        setupLayout();
    }

    private void setupLayout() {
        TextButton backButton = new TextButton("Back", game.getSkin());
        backButton.addListener(new BackToMainMenuClickListener(game));

        TextButton nextMoveButton = new TextButton("Next Move", game.getSkin());
        nextMoveButton.addListener(gameBoard.getBoardListener().getNextMoveListener());

        TextButton saveButton = new TextButton("Save", game.getSkin());
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                setupSaveInput();
            }
        });

        TextButton resetButton = new TextButton("Reset", game.getSkin());
        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                gameBoard.getGameState().reset();
                Gdx.app.log("Reset", "Resetting board");
            }
        });

        TextButton undoButton = new TextButton("Undo", game.getSkin());
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

        table.add(backButton).colspan(2).bottom().fill().pad(10);
        table.add(resetButton).colspan(2).bottom().fill().pad(10);
        table.add(saveButton).colspan(2).bottom().fill().pad(10);
        table.row();
        table.add(gameBoard).colspan(6).expand().center();
        table.row();
        table.add(undoButton).colspan(3).fill().pad(10);
        table.add(nextMoveButton).colspan(3).fill().pad(10);
        table.row();

        System.out.println(table.getColumns());
        stage.addActor(table);
    }

    private void setupSaveInput() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(0, 0, 1, 1);
        Texture background = new Texture(pixmap);
        pixmap.dispose();

        Image backgroundTransparent = new Image(background);
        background.dispose();
        backgroundTransparent.setFillParent(true);
        backgroundTransparent.setScaling(Scaling.fill);
        backgroundTransparent.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundTransparent.getColor().a = .8f;
        stage.addActor(backgroundTransparent);

        Table saveInput = new Table();
        saveInput.setFillParent(true);
        saveInput.setDebug(game.isDebug());
        saveInput.defaults().space(10);

        Label message = new Label("Please insert a name for the save", game.getSkin());
        message.setVisible(false);
        message.setAlignment(Align.center);
        message.setColor(Color.RED);

        Label saveTag = new Label("Name:", game.getSkin());
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
        saveInput.add(saveTag).center().spaceRight(5);
        saveInput.add(saveName).center().spaceLeft(5);
        saveInput.row();
        saveInput.add(save).center().fill().spaceRight(5);
        saveInput.add(cancel).center().fill().spaceLeft(5);
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
