package dev.plagarizers.klotski.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.SavesManager;
import dev.plagarizers.klotski.gui.listeners.BackToMainMenuClickListener;

public class GameOverScreen implements Screen {
    private Stage stage;
    private final KlotskiGame game;
    private final SavesManager savesManager = new SavesManager();

    private final State state;

    public GameOverScreen(KlotskiGame game, State state) {
        this.state = state;
        this.game = game;
        stage = new Stage(new ScreenViewport(game.getCamera()));

        stage.addActor(game.getBackground());
    }

    private void setupLayout() {
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(game.isDebug());

        Label title = new Label("Game Over", game.getSkin());
        title.setAlignment(Align.center);
        title.setFontScale(1.5f);

        table.add(title).width(Gdx.graphics.getWidth() / 2f).padBottom(10);
        table.row();

        Label score = new Label("You solved the puzzle in " + state.getMoves() + " moves", game.getSkin());
        score.setAlignment(Align.center);
        score.setFontScale(1.2f);
        table.add(score).width(Gdx.graphics.getWidth() / 2f).padBottom(10).row();

        TextButton backButton = new TextButton("BACK", game.getSkin());

        backButton.addListener(new BackToMainMenuClickListener(game));
        table.add(backButton).fill().colspan(6).pad(7).row();

        stage.addActor(table);
    }

    @Override
    public void show() {
        setupLayout();
        Gdx.input.setInputProcessor(stage);
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
        stage.dispose();
    }
}
