package dev.plagarizers.klotski.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.SavesManager;

import java.util.List;

public class LoadMenuScreen implements Screen {
    private Stage stage;
    private final KlotskiGame game;
    private final SavesManager savesManager;

    public LoadMenuScreen(KlotskiGame game) {
        this.game = game;
        savesManager = new SavesManager(Gdx.files.getExternalStoragePath());

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), game.getCamera()));
        Gdx.input.setInputProcessor(stage);
        stage.addActor(game.getBackground());

        createUI(game.getImageButtonStyle(), game.getSkin());
        Gdx.app.log("LoadMenuScreen", "LoadMenuScreen initialized");
    }

    private void createUI(ImageButton.ImageButtonStyle buttonStyle, Skin skin) {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setDebug(game.debug());

        Label title = new Label("SELECT A SAVE SLOT", skin);
        title.setAlignment(Align.center);
        title.setFontScale(1.5f);

        List<String> saves = savesManager.getSavedStatePaths();

        table.add(title).width(stage.getWidth() / 2f);
        table.row();

        Table savesTable = new Table();
        ScrollPane saveSlots = new ScrollPane(savesTable, skin);
        saveSlots.setFadeScrollBars(false);
        saveSlots.setFlickScroll(false);
        table.add(saveSlots).maxHeight(stage.getHeight() / 2f).fillX().pad(7);
        table.row();

        for (String save : saves) {
            String fileName = save.substring(save.lastIndexOf("/") + 1).replace(".json", "");

            TextButton saveButton = new TextButton(fileName, skin);
            saveButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("LoadMenuScreen", "Clicked on " + event.getTarget());
                    game.buttonPressedPlay();
                    State saveState = savesManager.loadStateByName(fileName);
                    Gdx.app.log("LoadMenuScreen", "Loaded state from save: " + fileName);
                    game.setScreen(new GameScreen(game, saveState));
                }
            });
            savesTable.add(saveButton).fillX().pad(7);

            TextButton deleteButton = new TextButton("DELETE", skin);
            deleteButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("LoadMenuScreen", "Clicked on " + event.getTarget());
                    game.buttonPressedPlay();
                    savesManager.deleteSave(fileName);
                    Gdx.app.log("LoadMenuScreen", "Deleted save: " + fileName);
                }
            });
            savesTable.add(deleteButton).fillX().pad(7);
            savesTable.row();

            Gdx.app.log("LoadMenuScreen", "Added save button: " + fileName);
        }

        if (saves.isEmpty()) {
            Label noSaves = new Label("No saves found", skin);
            savesTable.add(noSaves).fillX().pad(7);
            savesTable.row();
        }

        saveSlots.validate();

        ImageButton back = new ImageButton(buttonStyle);
        back.add(new Label("BACK", skin));
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("LoadMenuScreen", "Clicked on " + event.getTarget());
                game.buttonPressedPlay();
                game.setScreen(new MainMenuScreen(game));
            }
        });
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
