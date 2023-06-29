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

import java.util.HashMap;
import java.util.Map;

public class LoadMenuScreen implements Screen {
    private Stage stage;
    private final KlotskiGame game;
    private final SavesManager savesManager;
    private Image backgroundImage;
    private Table confirmTable;

    public LoadMenuScreen(KlotskiGame game) {
        this.game = game;
        savesManager = new SavesManager(Gdx.files.getExternalStoragePath());

        stage = new Stage(new ScreenViewport(game.getCamera()));
        Gdx.input.setInputProcessor(stage);
        stage.addActor(game.getBackground());

        setupLayout();
        setupConfirmDialog();
        setConfirmDialogVisible(false);
        Gdx.app.log("LoadMenuScreen", "LoadMenuScreen initialized");
    }


    private void setupLayout() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setDebug(game.isDebug());

        Label title = new Label("SELECT A SAVE SLOT", game.getSkin());
        title.setAlignment(Align.center);
        title.setFontScale(1.5f);

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
            saveButton.getLabel().setAlignment(Align.left);
            saveButton.addListener(new StartFromSaveClickListener(fileName, game));
            savesTable.add(saveButton).fillX().pad(7);

            TextButton deleteButton = new TextButton("DELETE", game.getSkin());
            deleteButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.buttonPressedPlay();
                    setConfirmDialogVisible(true);
                    setDeletedFile(fileName);
                }
            });
            savesTable.add(deleteButton).fillX().pad(7);
            savesTable.row();

            Gdx.app.log("LoadMenuScreen", "Added save button: " + fileName);
        }

        if (saves.isEmpty()) {
            Label noSaves = new Label("No saves found", game.getSkin());
            savesTable.add(noSaves).fillX().pad(7);
            savesTable.row();
        }

        saveSlots.validate();

        TextButton back = new TextButton("BACK", game.getSkin());
        back.addListener(new BackToMainMenuClickListener(game));
        table.add(back).fill().pad(7);
        Gdx.app.log("LoadMenuScreen", "Added back button");
    }

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
        confirmTable.setDebug(game.isDebug());
        confirmTable.defaults().space(10);

        Label message = new Label("This action is irreversible, are you sure you want to continue?", game.getSkin());
        message.setAlignment(Align.center);
        message.setColor(Color.RED);

        TextButton confirm = new TextButton("CONFIRM", game.getSkin());

        TextButton cancel = new TextButton("CANCEL", game.getSkin());
        cancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buttonPressedPlay();
                setConfirmDialogVisible(false);
            }
        });

        confirmTable.add(message).center().fillX().colspan(2);
        confirmTable.row();
        confirmTable.add(confirm).center().fill().spaceRight(5);
        confirmTable.add(cancel).center().fill().spaceLeft(5);
        stage.addActor(confirmTable);
    }

    private void setConfirmDialogVisible(boolean visible) {
        backgroundImage.setVisible(visible);
        confirmTable.setVisible(visible);
    }

    private void setDeletedFile(String filename) {
        for(Cell cell : confirmTable.getCells()) {
            if(cell.getActor() instanceof TextButton) {
                if(((TextButton) cell.getActor()).getLabel().textEquals("CONFIRM")) {
                    cell.getActor().clearListeners();
                    cell.getActor().addListener(new DeleteSaveClickListener(filename, game));
                }
            }
        }
    }

    @Override
    public void show() {
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
