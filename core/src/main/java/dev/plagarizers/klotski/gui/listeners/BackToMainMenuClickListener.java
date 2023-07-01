package dev.plagarizers.klotski.gui.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.gui.screens.MainMenuScreen;

/**
 * The `BackToMainMenuClickListener` class is a click listener for the back-to-main-menu button.
 * It extends the `ClickListener` class and is responsible for handling the click event and transitioning back to the main menu screen.
 */
public class BackToMainMenuClickListener extends ClickListener {

    private final KlotskiGame game;

    /**
     * Constructs a new `BackToMainMenuClickListener` with the specified `KlotskiGame` instance.
     *
     * @param game the `KlotskiGame` instance
     */
    public BackToMainMenuClickListener(KlotskiGame game) {
        this.game = game;
    }

    /**
     * Handles the click event when the back-to-main-menu button is clicked.
     *
     * @param event the input event
     * @param x     the x-coordinate of the click position
     * @param y     the y-coordinate of the click position
     */
    @Override
    public void clicked(InputEvent event, float x, float y) {
        game.buttonPressedPlay();
        game.getScreen().dispose();
        game.setScreen(new MainMenuScreen(game));
    }
}
