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
import dev.plagarizers.klotski.gui.util.FontGenerator;
import dev.plagarizers.klotski.gui.util.FontGenerator.FontType;
import dev.plagarizers.klotski.gui.util.FontGenerator.LabelStyleType;

/**
 * The GameScreen class represents the main game screen in the Klotski game.
 * It implements the Screen interface provided by LibGDX.
 */
public class GameScreen implements Screen {
    private final KlotskiGame game; // The main game instance
    private final Stage stage; // The stage for rendering UI elements
    private final Board gameBoard; // The game board
    private final SavesManager savesManager; // The saves manager for managing saved game states
    private Image backgroundImage; // The background image for the save dialog
    private Table saveTable; // The table for the save dialog

    /**
     * Constructs a new GameScreen object.
     *
     * @param game  The main KlotskiGame instance.
     * @param state The initial state of the game.
     */
    public GameScreen(KlotskiGame game, State state) {
        this.game = game;
        this.savesManager = new SavesManager(Gdx.files.getExternalStoragePath());
        State currentState = state != null ? state : State.fromRandomConfiguration();
        this.gameBoard = new Board(currentState);

        this.stage = new Stage(new ScreenViewport(game.getCamera()));
        stage.addActor(game.getBackground());

        stage.addListener(gameBoard.getBoardListener());
        setupLayout();
        setupSaveDialog();
        setSaveDialogVisible(false);
    }

    /**
     * Sets up the layout of the game screen, including buttons and the game board.
     */
    private void setupLayout() {
        TextButton backButton = new TextButton("Back", game.getSkin());
        backButton.getLabel().setStyle(FontGenerator.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));
        backButton.addListener(new BackToMainMenuClickListener(game));

        TextButton nextMoveButton = new TextButton("Next Move", game.getSkin());
        nextMoveButton.getLabel().setStyle(FontGenerator.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));
        nextMoveButton.addListener(gameBoard.getBoardListener().getNextMoveListener());

        TextButton saveButton = new TextButton("Save", game.getSkin());
        saveButton.getLabel().setStyle(FontGenerator.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                setSaveDialogVisible(true);
            }
        });

        TextButton resetButton = new TextButton("Reset", game.getSkin());
        resetButton.getLabel().setStyle(FontGenerator.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));
        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                gameBoard.getGameState().reset();
                Gdx.app.log("Reset", "Resetting board");
            }
        });

        TextButton undoButton = new TextButton("Undo", game.getSkin());
        undoButton.getLabel().setStyle(FontGenerator.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));
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

    /**
     * Sets up the save dialog for saving the game state.
     */
    private void setupSaveDialog() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(0, 0, 1, 1);
        Texture background = new Texture(pixmap);
        pixmap.dispose();

        backgroundImage = new Image(background);
        background.dispose();
        backgroundImage.setFillParent(true);
        backgroundImage.setScaling(Scaling.fill);
        backgroundImage.setSize(stage.getWidth(), stage.getHeight());
        backgroundImage.getColor().a = .8f;
        stage.addActor(backgroundImage);

        saveTable = new Table();
        saveTable.setFillParent(true);
        saveTable.setDebug(game.isDebug());
        saveTable.defaults().space(10);

        Label message = new Label("Please insert a name for the save", FontGenerator.getInstance().getLabelStyle(LabelStyleType.AlertStyle));
        message.setVisible(false);
        message.setAlignment(Align.center);

        Label saveTag = new Label("Name:", game.getSkin());
        saveTag.setStyle(FontGenerator.getInstance().getLabelStyle(LabelStyleType.InfoStyle));
        TextField saveName = new TextField("", game.getSkin());
        saveName.getStyle().font = FontGenerator.getInstance().getFont(FontType.Info);

        TextButton save = new TextButton("SAVE", game.getSkin());
        save.getLabel().setStyle(FontGenerator.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));
        save.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                if (saveName.getText().equals("") || saveName.getText() == null) {
                    message.setVisible(true);
                } else {
                    savesManager.saveState(gameBoard.getState(), saveName.getText());
                    setSaveDialogVisible(false);
                    message.setVisible(false);
                    saveName.setText("");
                }
            }
        });

        TextButton cancel = new TextButton("CANCEL", game.getSkin());
        cancel.getLabel().setStyle(FontGenerator.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));
        cancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                setSaveDialogVisible(false);
                message.setVisible(false);
                saveName.setText("");
            }
        });

        saveTable.add(message).center().fillX().colspan(2);
        saveTable.row();
        saveTable.add(saveTag).center().spaceRight(5);
        saveTable.add(saveName).center().spaceLeft(5);
        saveTable.row();
        saveTable.add(save).center().fill().spaceRight(5);
        saveTable.add(cancel).center().fill().spaceLeft(5);
        stage.addActor(saveTable);
    }

    /**
     * Sets the visibility of the save dialog.
     *
     * @param visible True to make the dialog visible, false otherwise.
     */
    private void setSaveDialogVisible(boolean visible) {
        backgroundImage.setVisible(visible);
        saveTable.setVisible(visible);
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
