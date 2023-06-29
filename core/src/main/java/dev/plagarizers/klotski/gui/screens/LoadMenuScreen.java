package dev.plagarizers.klotski.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
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

    public LoadMenuScreen(KlotskiGame game) {
        this.game = game;
        savesManager = new SavesManager(Gdx.files.getExternalStoragePath());

        stage = new Stage(new ScreenViewport(game.getCamera()));
        Gdx.input.setInputProcessor(stage);
        stage.addActor(game.getBackground());

        setupLayout();
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
            deleteButton.addListener(new DeleteSaveClickListener(fileName, game));
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
