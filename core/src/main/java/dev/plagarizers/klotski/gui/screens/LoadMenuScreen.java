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
import dev.plagarizers.klotski.game.util.SavesManager;
import dev.plagarizers.klotski.gui.listeners.BackToMainMenuClickListener;
import dev.plagarizers.klotski.gui.listeners.DeleteSaveClickListener;
import dev.plagarizers.klotski.gui.listeners.StartFromSaveClickListener;
import dev.plagarizers.klotski.gui.util.FontHandler;
import dev.plagarizers.klotski.gui.util.FontHandler.LabelStyleType;
import dev.plagarizers.klotski.gui.util.SoundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * The LoadMenuScreen class represents the load menu screen in the Klotski game.
 * It implements the Screen interface provided by LibGDX.
 */
public class LoadMenuScreen implements Screen {
    private final KlotskiGame game; // The main game instance
    private final SavesManager savesManager; // The saves manager for managing saved game states
    private final Stage stage; // The stage for rendering UI elements
    private Image backgroundImage; // The background image for the confirmation dialog
    private Table confirmTable; // The table for the confirmation dialog
    private TextButton confirmButton; // The confirm button in the confirmation dialog

    /**
     * Constructs a new LoadMenuScreen object.
     *
     * @param game The main KlotskiGame instance.
     */
    public LoadMenuScreen(KlotskiGame game) {
        this.game = game;
        savesManager = new SavesManager(Gdx.files.getExternalStoragePath());

        stage = new Stage(new ScreenViewport(game.getCamera()));
        Gdx.input.setInputProcessor(stage);
        stage.addActor(game.getBackground());

        setupLayout();
        setupConfirmDialog();
        setConfirmDialogVisible(false);
    }

    /**
     * Sets up the layout of the load menu screen, including labels, buttons, and save slots.
     */
    private void setupLayout() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("SELECT A SAVE SLOT", FontHandler.getInstance().getLabelStyle(LabelStyleType.MenuStyle));
        title.setAlignment(Align.center);

        HashMap<String, Integer> saves = savesManager.getSavedStatePaths();

        table.add(title).width(stage.getWidth() / 2f);
        table.row();

        Table savesTable = new Table();
        ScrollPane saveSlots = new ScrollPane(savesTable, game.getSkin());
        saveSlots.setFadeScrollBars(false);
        saveSlots.setFlickScroll(false);
        table.add(saveSlots).maxHeight(stage.getHeight() / 2f).fillX().pad(7);
        table.row();

        for (Map.Entry<String, Integer> save : saves.entrySet()) {
            String key = save.getKey();
            String fileName = key.substring(key.lastIndexOf("/") + 1).replace(".json", "");

            String saveButtonLabel = fileName + "\nMoves: " + save.getValue();
            TextButton saveButton = new TextButton(saveButtonLabel, game.getSkin());
            saveButton.getLabel().setStyle(FontHandler.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));
            saveButton.getLabel().setAlignment(Align.left);
            saveButton.addListener(new StartFromSaveClickListener(fileName, game));
            savesTable.add(saveButton).fill().pad(7);

            TextButton deleteButton = new TextButton("DELETE", game.getSkin());
            deleteButton.getLabel().setStyle(FontHandler.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));
            deleteButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    SoundHandler.getInstance().playButtonClick();
                    setConfirmDialogVisible(true);
                    setDeletedFile(fileName);
                }
            });

            savesTable.add(deleteButton).fill().pad(7);
            savesTable.row();

        }

        if (saves.isEmpty()) {
            Label noSaves = new Label("No saves found", FontHandler.getInstance().getLabelStyle(LabelStyleType.InfoStyle));
            savesTable.add(noSaves).fillX().pad(7);
            savesTable.row();
        }

        saveSlots.validate();

        TextButton back = new TextButton("BACK", game.getSkin());
        back.getLabel().setStyle(FontHandler.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));
        back.addListener(new BackToMainMenuClickListener(game));
        table.add(back).fill().pad(7);
    }

    /**
     * Sets up the confirmation dialog for deleting a save file.
     */
    private void setupConfirmDialog() {
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

        confirmTable = new Table();
        confirmTable.setFillParent(true);
        confirmTable.defaults().space(10);

        Label message = new Label("This action is irreversible\n are you sure you want to continue?", FontHandler.getInstance().getLabelStyle(LabelStyleType.AlertStyle));
        message.setAlignment(Align.center);
        confirmButton = new TextButton("CONFIRM", game.getSkin());
        confirmButton.getLabel().setStyle(FontHandler.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));

        TextButton cancelButton = new TextButton("CANCEL", game.getSkin());
        cancelButton.getLabel().setStyle(FontHandler.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setConfirmDialogVisible(false);
            }
        });

        confirmTable.add(message).center().fillX().colspan(2);
        confirmTable.row();
        confirmTable.add(confirmButton).center().fill().spaceRight(5);
        confirmTable.add(cancelButton).center().fill().spaceLeft(5);
        stage.addActor(confirmTable);
    }

    /**
     * Sets the visibility of the confirmation dialog.
     *
     * @param visible True to make the dialog visible, false otherwise.
     */
    private void setConfirmDialogVisible(boolean visible) {
        backgroundImage.setVisible(visible);
        confirmTable.setVisible(visible);
    }

    /**
     * Sets the deleted file for the confirmation dialog.
     *
     * @param filename The name of the file to be deleted.
     */
    private void setDeletedFile(String filename) {
        confirmButton.clearListeners();
        confirmButton.addListener(new DeleteSaveClickListener(filename, game));
    }

    @Override
    public void show() {
        // Not used
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
