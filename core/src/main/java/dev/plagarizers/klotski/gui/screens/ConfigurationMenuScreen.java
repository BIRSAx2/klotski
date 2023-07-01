package dev.plagarizers.klotski.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.util.Level;
import dev.plagarizers.klotski.game.util.SavesManager;
import dev.plagarizers.klotski.gui.actors.BoardPreview;
import dev.plagarizers.klotski.gui.listeners.BackToMainMenuClickListener;
import dev.plagarizers.klotski.gui.util.FontGenerator.FontType;
import dev.plagarizers.klotski.gui.util.FontGenerator.LabelStyleType;

import java.util.List;

public class ConfigurationMenuScreen implements Screen {
    private final KlotskiGame game;
    private final Stage stage;
    private final SavesManager savesManager = new SavesManager();

    public ConfigurationMenuScreen(KlotskiGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport(game.getCamera()));
        this.stage.addActor(game.getBackground());
    }

    private void setupLayout() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.setDebug(game.isDebug());

        Label title = new Label("SELECT A CONFIGURATION", game.getFontGenerator().getLabelStyle(LabelStyleType.MenuStyle));
        title.setAlignment(Align.center);
        table.add(title).colspan(6).center().padBottom(20);
        table.row();

        Table selectableLevels = new Table();
        selectableLevels.setDebug(game.isDebug());
        ScrollPane levelSelector = new ScrollPane(selectableLevels, game.getSkin());
        levelSelector.setFadeScrollBars(false);
        levelSelector.setFlickScroll(false);
        table.add(levelSelector).fill().colspan(6).pad(7);
        selectableLevels.defaults().padBottom(20).fillX().colspan(2);

        List<Level> levels = savesManager.loadLevels(Gdx.files.internal("levels/levels.json").reader());
        int i = 0;
        for (Level level : levels) {
            if (i % 3 == 0) {
                selectableLevels.row();
            }

            BoardPreview board = new BoardPreview(level, game.getSkin(), game.getFontGenerator().getLabelStyle(LabelStyleType.InfoStyle));
            board.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.buttonPressedPlay();
                    game.getScreen().dispose();
                    game.setScreen(new GameScreen(game, level.toState()));
                }
            });
            selectableLevels.add(board).pad(10);

            i++;
        }
        levelSelector.validate();
        table.row();

        TextButton backButton = new TextButton("BACK", game.getSkin());
        backButton.getLabel().setStyle(game.getFontGenerator().getLabelStyle(LabelStyleType.ButtonStyle));
        backButton.addListener(new BackToMainMenuClickListener(game));
        table.add(backButton).fill().colspan(6).pad(7);

        stage.addActor(table);
    }

    @Override
    public void show() {
        setupLayout();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(delta, 1 / 60f));
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
