package dev.plagarizers.klotski.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.SavesManager;
import dev.plagarizers.klotski.gui.listeners.BackToMainMenuClickListener;
import dev.plagarizers.klotski.gui.util.FontHandler;
import dev.plagarizers.klotski.gui.util.FontHandler.LabelStyleType;
import dev.plagarizers.klotski.gui.util.SoundHandler;

public class GameOverScreen implements Screen {
    private final KlotskiGame game;
    private final SavesManager savesManager = new SavesManager();
    private final State state;
    private final Stage stage;

    private final int minSteps;

    /**
     * Constructs a GameOverScreen object.
     *
     * @param game  The KlotskiGame instance.
     * @param state The game state when the game is over.
     */
    public GameOverScreen(KlotskiGame game, State state, int minSteps) {
        this.state = state;
        this.game = game;
        this.minSteps = minSteps;
        stage = new Stage(new ScreenViewport(game.getCamera()));

        stage.addActor(game.getBackground());
    }

    /**
     * Sets up the layout of the game over screen.
     */
    private void setupLayout() {
        Table table = new Table();
        table.setFillParent(true);

        Label title = new Label("Game Over", FontHandler.getInstance().getLabelStyle(LabelStyleType.MenuStyle));
        title.setAlignment(Align.center);

        table.add(title).width(Gdx.graphics.getWidth() / 2f).padBottom(10);
        table.row();

        Label score = new Label("You solved the puzzle in " + state.getMoves() + " moves", FontHandler.getInstance().getLabelStyle(LabelStyleType.InfoStyle));
        score.setAlignment(Align.center);
        table.add(score).width(Gdx.graphics.getWidth() / 2f).padBottom(10).row();

        Label minMoves = new Label("This the puzzle can be solved in " + minSteps + " moves", FontHandler.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));
        minMoves.setAlignment(Align.center);
        table.add(minMoves).width(Gdx.graphics.getWidth() / 2f).padBottom(10).row();

        TextButton backButton = new TextButton("BACK MAIN MENU", game.getSkin());
        backButton.getLabel().setStyle(FontHandler.getInstance().getLabelStyle(LabelStyleType.ButtonStyle));

        backButton.addListener(new BackToMainMenuClickListener(game));

        table.add(backButton).pad(7);

        stage.addActor(table);
    }

    @Override
    public void show() {
        setupLayout();
        Gdx.input.setInputProcessor(stage);
        SoundHandler.getInstance().playVictoryMusic();
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
