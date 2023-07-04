package dev.plagarizers.klotski.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.gui.util.FontHandler;
import dev.plagarizers.klotski.gui.util.SoundHandler;

public class TutorialScreen implements Screen {
    private KlotskiGame game;
    private Stage stage;
    private Array<Label> tutorialLabels;
    private Array<Label> titleLabels;
    private float minButtonWidth;
    private int currentLabel;
    private Table table;
    private Cell<Label> labelCell;
    private Cell<Label> titleCell;
    public TutorialScreen(KlotskiGame game) {
        this.game = game;
        this.currentLabel = 0;
        this.minButtonWidth = Gdx.graphics.getWidth() / 6f;
        tutorialLabels = new Array<>();
        titleLabels = new Array<>();
        stage = new Stage(new ScreenViewport(game.getCamera()));
        stage.addActor(game.getBackground());
        setupLabels();
        setupLayout();
    }

    private void setupLabels() {
        Label introTitle = new Label("Welcome to Klotski!", FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.MenuStyle));
        introTitle.setAlignment(Align.center);
        titleLabels.add(introTitle);

        Label introText = new Label("""
            Klotski is a sliding block puzzle game
            where the goal is to move the special
            block (also known as the "hero" block)
            to a specific location on the game board
            by sliding the other blocks out of the way.
            This manual will guide you through the gameplay,
            controls, rules, and features of the Klotski game.""",
            FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.InfoStyle));
        introText.setAlignment(Align.center);
        tutorialLabels.add(introText);

        Label objectiveTitle = new Label("Objective", FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.MenuStyle));
        objectiveTitle.setAlignment(Align.center);
        titleLabels.add(objectiveTitle);

        Label objectiveText = new Label("""
            The objective of the Klotski game is
            to move the hero block to the designated
            target position on the game board.
            The hero block can only move in a straight
            line (horizontally or vertically), and
            other blocks act as obstacles that must be
            strategically moved to create a clear path
            for the hero block.""",
            FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.InfoStyle));
        objectiveText.setAlignment(Align.center);
        tutorialLabels.add(objectiveText);

        Label controlsTitle = new Label("Game Controls", FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.MenuStyle));
        controlsTitle.setAlignment(Align.center);
        titleLabels.add(controlsTitle);

        Label controlsText = new Label("""
            Keyboard Controls:
                Arrow Keys: Move the hero block in
                the corresponding direction (up,
                down, left, right).
                Tab Key: Switch to the next available
                block for control.
                Z Key + Control Key: Undo the last move.
                R Key + Control Key: Reset the game to
                the initial configuration.
                Space Key : Play next best move.

            Mouse Controls:
                Click: Select a block to control.
                Click and Drag: Drag a selected
                block in the desired direction to move it.""",
            FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.InfoStyle));
        controlsText.setAlignment(Align.center);
        tutorialLabels.add(controlsText);

        Label rulesTitle = new Label("Game Rules", FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.MenuStyle));
        rulesTitle.setAlignment(Align.center);
        titleLabels.add(rulesTitle);

        Label rulesText = new Label("""
            Only one block can be moved at a time.
            Blocks can only move in a straight line
            (horizontally or vertically). Blocks
            cannot move through or overlap other blocks.
            The hero block must reach the target position
            to win the game. The game can be reset at any
            time to restart from the initial configuration.""",
            FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.InfoStyle));
        rulesText.setAlignment(Align.center);
        tutorialLabels.add(rulesText);

        Label playTitle = new Label("Play", FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.MenuStyle));
        playTitle.setAlignment(Align.center);
        titleLabels.add(playTitle);

        Label playText = new Label("""
            The game screen consists of the following
            elements:
                Game Board: Displays the game board
                with blocks arranged on it.
                Hero Block: A 2x2 block that needs
                to reach the target position.
                Other Blocks: Obstacle blocks that
                need to be moved to create a path for
                the hero block.
                Moves Counter: Shows the number of
                moves made by the player.
            In addition, the game screen also displays the
            following buttons:
                Back Button: Returns to the main menu.
                Undo Button: Undoes the last move.
                Reset Button: Resets the game to the
                initial configuration.
                Save Button: Saves the current
                game configuration.
                Next Move Button: Plays the next best move.""",
            FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.InfoStyle));
        playTitle.setAlignment(Align.center);
        tutorialLabels.add(playText);

        Label gameOverTitle = new Label("Game Over Screen", FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.MenuStyle));
        gameOverTitle.setAlignment(Align.center);
        titleLabels.add(gameOverTitle);

        Label gameOverText = new Label("""
            When the hero block (2x2) reaches the target
            position, the game is won, and a "Game Over"
            screen is displayed. The "Game Over" screen
            shows the number of moves made to solve the
            puzzle. From this screen, you can choose to
            go back to the main menu or start a new game.""",
            FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.InfoStyle));
        gameOverTitle.setAlignment(Align.center);
        tutorialLabels.add(gameOverText);

        Label saveTitle = new Label("Saving and Loading Games", FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.MenuStyle));
        saveTitle.setAlignment(Align.center);
        titleLabels.add(saveTitle);

        Label saveText = new Label("""
            The Klotski game allows you to save and load
            your progress. You can save the current game
            configuration to continue playing later or load
            a previously saved game. Saved games can be
            accessed from the main menu by pressing on the
            "Load game" button.""",
            FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.InfoStyle));
        saveTitle.setAlignment(Align.center);
        tutorialLabels.add(saveText);

        Label configurationTitle = new Label("Choose Configuration", FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.MenuStyle));
        configurationTitle.setAlignment(Align.center);
        titleLabels.add(configurationTitle);

        Label configurationText = new Label("""
            The Klotski game allows you to choose the
            configuration of the game. You can choose the
            configuration of the game from the main menu by
            pressing on the "Choose configuration" button.
            You can choose from 40 different configurations,
            each with a different difficulty level.
            The difficulty level is indicated by the number
            of moves required to solve the puzzle.
            The difficulty level ranges from 1 to 40, with
            1 being the easiest and 40 being the hardest.""",
            FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.InfoStyle));
        configurationTitle.setAlignment(Align.center);
        tutorialLabels.add(configurationText);

        Label settingsTitle = new Label("Settings", FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.MenuStyle));
        settingsTitle.setAlignment(Align.center);
        titleLabels.add(settingsTitle);

        Label settingsText = new Label("""
            The options menu provides additional functionality
            to enhance your gameplay experience, like the music
            and effects volume, these options can be turned off
            or on from the options.""",
            FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.InfoStyle));
        settingsTitle.setAlignment(Align.center);
        tutorialLabels.add(settingsText);

        Label hintsTitle = new Label("Hints", FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.MenuStyle));
        hintsTitle.setAlignment(Align.center);
        titleLabels.add(hintsTitle);

        Label hintsText = new Label("""
            The Klotski game has a hint feature that can be used
            to get a hint for the next best move. The hint feature
            can be used by pressing on the "Next Move" button.
            Have fun!""",
            FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.InfoStyle));
        hintsTitle.setAlignment(Align.center);
        tutorialLabels.add(hintsText);
    }

    private void setupLayout() {
        table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        TextButton quitButton = new TextButton("QUIT", game.getSkin());
        quitButton.getLabel().setStyle(FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.ButtonStyle));
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                Gdx.app.exit();
            }
        });

        TextButton skipButton = new TextButton("SKIP", game.getSkin());
        skipButton.getLabel().setStyle(FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.ButtonStyle));
        skipButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        TextButton previousButton = new TextButton("BACK", game.getSkin());
        previousButton.getLabel().setStyle(FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.ButtonStyle));
        previousButton.setVisible(false);
        previousButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentLabel - 1 <= 0) previousButton.setVisible(false);
                previousLabel();
            }
        });

        TextButton nextButton = new TextButton("NEXT", game.getSkin());
        nextButton.getLabel().setStyle(FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.ButtonStyle));
        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentLabel == tutorialLabels.size - 1) {
                    game.getScreen().dispose();
                    game.setScreen(new MainMenuScreen(game));
                } else {
                    if (currentLabel == tutorialLabels.size - 2) {
                        Label finish = new Label("FINISH", FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.ButtonStyle));
                        finish.setAlignment(Align.center);
                        nextButton.setLabel(finish);
                    }
                    if (currentLabel + 1 > 0) previousButton.setVisible(true);
                    nextLabel();
                }
            }
        });

        table.add(quitButton).left().minWidth(minButtonWidth);
        table.add(skipButton).right().minWidth(minButtonWidth);
        table.row();
        titleCell = table.add(titleLabels.get(currentLabel)).center().colspan(2).pad(10);
        table.row();
        labelCell = table.add(tutorialLabels.get(currentLabel)).center().colspan(2);
        table.row();
        table.add(previousButton).left().minWidth(minButtonWidth).padTop(20);
        table.add(nextButton).right().minWidth(minButtonWidth).padTop(20);
        stage.addActor(table);
    }

    private void nextLabel() {
        if(currentLabel < tutorialLabels.size) {
            currentLabel++;
            labelCell.setActor(tutorialLabels.get(currentLabel));
            titleCell.setActor(titleLabels.get(currentLabel));
        }
    }

    private void previousLabel() {
        if(currentLabel > 0) {
            currentLabel--;
            labelCell.setActor(tutorialLabels.get(currentLabel));
            titleCell.setActor(titleLabels.get(currentLabel));
        }
    }

    @Override
    public void show() {
        SoundHandler.getInstance().playBackgroundMusic();
        Gdx.input.setInputProcessor(stage);
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
