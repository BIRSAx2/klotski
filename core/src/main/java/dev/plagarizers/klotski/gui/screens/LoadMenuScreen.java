package dev.plagarizers.klotski.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.SavesManager;
import dev.plagarizers.klotski.gui.listeners.DeleteSaveClickListener;
import dev.plagarizers.klotski.gui.listeners.StartFromSaveClickListener;

import java.util.List;

public class LoadMenuScreen implements Screen {
    private Stage stage;
    private final KlotskiGame game;

    private final SavesManager savesManager;

    public LoadMenuScreen(KlotskiGame game) {
        savesManager = new SavesManager(Gdx.files.getExternalStoragePath());

        this.game = game;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        stage = game.getStage(new FitViewport(screenWidth, screenHeight, game.getCamera()));
        setupLayout(game.getImageButtonStyle(), game.getSkin());
        Gdx.app.log("LoadMenuScreen", "LoadMenuScreen initialized");
    }

    private void setupLayout(ImageButton.ImageButtonStyle buttonStyle, Skin skin) {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setDebug(game.debug());

        Label title = new Label("SELECT A SAVE SLOT", skin);
        title.setAlignment(Align.center);
        title.setFontScale(1.5f);

        List<String> saves = savesManager.getSavedStatePaths();

        table.add(title).width(Gdx.graphics.getWidth() / 2f);
        table.row();
        Table savesTable = new Table();
        ScrollPane saveSlots = new ScrollPane(savesTable, skin);
        saveSlots.setFadeScrollBars(false);
        saveSlots.setFlickScroll(false);
        table.add(saveSlots).maxHeight(Gdx.graphics.getHeight() / 2f).fillX().pad(7);
        table.row();


        for (String save : saves) {

            String fileName = save.substring(save.lastIndexOf("/") + 1).replace(".json", "");
            TextButton saveButton = new TextButton(fileName, skin);
            saveButton.addListener(new StartFromSaveClickListener(fileName, game));
            savesTable.add(saveButton).fillX().padBottom(7).padTop(7).padRight(10);


            TextButton deleteButton = new TextButton("DELETE", skin);
            deleteButton.addListener(new DeleteSaveClickListener(fileName, game));
            savesTable.add(deleteButton).fillX().padBottom(7).padTop(7);


            savesTable.row();
            Gdx.app.log("LoadMenuScreen", "Added save button: " + fileName);
        }

        if (saves.size() == 0) {
            Label noSaves = new Label("No saves found", skin);
            savesTable.add(noSaves).fillX().padBottom(7).padTop(7);
            savesTable.row();
        }

        saveSlots.validate();

        ImageButton back = new ImageButton(buttonStyle);
        back.add(new Label("BACK", skin));
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.buttonPressedPlay();
                game.setScreen(new MainMenuScreen(game));
            }
        });
        table.add(back).fill().pad(7);
        Gdx.app.log("LoadMenuScreen", "Added back button");
    }

    // TODO: implement getMoves from file
    private int getMoves() {
        return 42;
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
