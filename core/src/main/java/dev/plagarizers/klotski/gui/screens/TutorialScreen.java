package dev.plagarizers.klotski.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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

/**
 * The TutorialScreen class represents the tutorial presented to a new player for the first time.
 * It implements the Screen interface provided by LibGDX.
 */
public class TutorialScreen implements Screen {
    private final KlotskiGame game;
    private final Stage stage;
    private final Array<String> titleStrings;
    private final Array<String> textStrings;
    private final float minButtonWidth;
    private int currentText;
    private Cell<Label> textCell;
    private Cell<Label> titleCell;
    private final Preferences preferences;

    /**
     * Constructs a new TutorialScreen object.
     *
     * @param game  The main KlotskiGame instance.
     * @param preferences The preferences of the game, representing if the tutorial has been completed or skipped.
     */
    public TutorialScreen(KlotskiGame game, Preferences preferences) {
        this.preferences = preferences;
        this.game = game;
        this.currentText = 0;
        this.minButtonWidth = Gdx.graphics.getWidth() / 6f;
        textStrings = new Array<>();
        titleStrings = new Array<>();
        stage = new Stage(new ScreenViewport(game.getCamera()));
        stage.addActor(game.getBackground());
        setupText();
        setupLayout();
    }

    /**
     * Created all the string to be used in the labels for titles and information texts and add them to the corresponding array.
     */
    private void setupText() {
        String introTitle = "Welcome to Klotski!";
        titleStrings.add(introTitle);

        String introText = """
            Klotski is a sliding block puzzle game where
            the goal is to move the special block
            (also known as the "hero" block) to a specific
            location on the game board by sliding the other
            blocks out of the way. This tutorial will guide
            you through the gameplay, controls, rules, and
            features of the Klotski game.""";
        textStrings.add(introText);

        String objectiveTitle = "Objective";
        titleStrings.add(objectiveTitle);

        String objectiveText = """
            The objective of Klotski is to move the hero
            block to the designated target position on the
            game board. The hero block can only move in a
            straight line (horizontally or vertically), and
            other blocks act as obstacles that must be
            strategically moved to create a clear path for
            the hero block.""";
        textStrings.add(objectiveText);

        String controlsTitle = "Game Controls";
        titleStrings.add(controlsTitle);

        String controlsText = """
            Keyboard Controls:
                Arrow Keys: Move the hero block up, down, left
                or right.
                Tab Key: Switch to next available block.
                Z Key + Control Key: Undo the last move.
                R Key + Control Key: Reset the board.
                Space Key : Play next best move.

            Mouse Controls:
                Click: Select a block to control.
                Click and Drag: Drag the selected block in the
                desired direction to move it.""";
        textStrings.add(controlsText);

        String rulesTitle = "Game Rules";
        titleStrings.add(rulesTitle);

        String rulesText = """
            Only one block can be moved at a time. Blocks
            can only move in a straight line (horizontally
            or vertically). Blocks cannot move through or
            overlap other blocks. The hero block must reach
            the target position to win the game. The game
            can be reset at any time to restart from the
            initial configuration.""";
        textStrings.add(rulesText);

        String playTitle = "Play";
        titleStrings.add(playTitle);

        String playText = """
            The game screen has:
                Game Board: Displays the game board and
                blocks inside.
                Hero Block: the 2x2 block that must reach
                the target position.
                Other Blocks: Obstacle blocks that
                need to be moved to finish the game.
                Moves Counter: Shows the number of moves
                made.
            In addition, the game screen has also 5 buttons:
                Back Button: Returns to the main menu.
                Undo Button: Undoes the last move.
                Reset Button: Resets the game to the initial
                configuration.
                Save Button: Saves the current game
                configuration.
                Next Move Button: Plays the next best move.""";
        textStrings.add(playText);

        String gameOverTitle = "Game Over Screen";
        titleStrings.add(gameOverTitle);

        String gameOverText = """
            When the hero block (2x2) reaches the target
            position, the game is won, and a "Game Over"
            screen is displayed. The "Game Over" screen
            shows the number of moves made to solve the
            puzzle. From this screen, you can choose to
            go back to the main menu or start a new game.""";
        textStrings.add(gameOverText);

        String saveTitle = "Saving and Loading Games";
        titleStrings.add(saveTitle);

        String saveText = """
            The Klotski game allows you to save and load
            your progress. You can save the current game
            configuration to continue playing later or
            load a previously saved game. Saved games can
            be accessed from the main menu by pressing on
            the "Load game" button.""";
        textStrings.add(saveText);

        String configurationTitle = "Choose Configuration";
        titleStrings.add(configurationTitle);

        String configurationText = """
            The Klotski game allows you to choose the
            configuration of the game. You can choose the
            configuration of the game from the main menu by
            pressing on the "Choose configuration" button.
            You can choose from 40 different configurations,
            each with a different difficulty level.
            The difficulty level is indicated by the number
            of moves required to solve the puzzle.
            The difficulty level ranges from 1 to 40, with
            1 being the easiest and 40 being the hardest.""";
        textStrings.add(configurationText);

        String settingsTitle = "Settings";
        titleStrings.add(settingsTitle);

        String settingsText = """
            The options menu provides additional
            functionality to enhance your gameplay
            experience, like the music and effects
            volume, these options can be turned off
            or on from the options.""";
        textStrings.add(settingsText);

        String hintsTitle = "Hints";
        titleStrings.add(hintsTitle);

        String hintsText = """
            The Klotski game has a hint feature
            that can be used to get a hint for
            the next best move. The hint feature
            can be used by pressing on the "Next
            Move" button.
            Have fun!""";
        textStrings.add(hintsText);
    }

    /**
     * Sets up the layout of the tutorial screen, including buttons, listeners and the main text label.
     */
    private void setupLayout() {
        Table table = new Table();
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
                SoundHandler.getInstance().playButtonClick();
                preferences.putBoolean("done", true);
                preferences.flush();
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
                SoundHandler.getInstance().playButtonClick();
                if(currentText - 1 <= 0) previousButton.setVisible(false);
                previousLabel();
            }
        });

        TextButton nextButton = new TextButton("NEXT", game.getSkin());
        nextButton.getLabel().setStyle(FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.ButtonStyle));
        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundHandler.getInstance().playButtonClick();
                if(currentText == textStrings.size - 1) {
                    preferences.putBoolean("done", true);
                    preferences.flush();
                    game.getScreen().dispose();
                    game.setScreen(new MainMenuScreen(game));
                } else {
                    if (currentText == textStrings.size - 2) {
                        Label finish = new Label("FINISH", FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.ButtonStyle));
                        finish.setAlignment(Align.center);
                        nextButton.setLabel(finish);
                    }
                    if (currentText + 1 > 0) previousButton.setVisible(true);
                    nextLabel();
                }
            }
        });

        Label title = new Label(titleStrings.get(currentText), FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.MenuStyle));
        title.setAlignment(Align.center);

        Label description = new Label(textStrings.get(currentText), FontHandler.getInstance().getLabelStyle(FontHandler.LabelStyleType.InfoStyle));
        description.setAlignment(Align.left);

        table.add(quitButton).left().minWidth(minButtonWidth);
        table.add(skipButton).right().minWidth(minButtonWidth);
        table.row();
        titleCell = table.add(title).center().colspan(2).pad(10);
        table.row();
        textCell = table.add(description).center().colspan(2);
        table.row();
        table.add(previousButton).left().minWidth(minButtonWidth).padTop(20);
        table.add(nextButton).right().minWidth(minButtonWidth).padTop(20);
        stage.addActor(table);
    }

    /**
     * Advance the current text, getting the next text for the info and tile from the corresponding array and insert it in the right label
     */
    private void nextLabel() {
        if(currentText < textStrings.size) {
            currentText++;
            titleCell.getActor().setText(titleStrings.get(currentText));
            textCell.getActor().setText(textStrings.get(currentText));
        }
    }

    /**
     * Decrement the current text, getting the previous text for the info and tile from the corresponding array and insert it in the right label
     */
    private void previousLabel() {
        if(currentText > 0) {
            currentText--;
            titleCell.getActor().setText(titleStrings.get(currentText));
            textCell.getActor().setText(textStrings.get(currentText));
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
        // not used
    }

    @Override
    public void resume() {
        // not used
    }

    @Override
    public void hide() {
        // not used
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
