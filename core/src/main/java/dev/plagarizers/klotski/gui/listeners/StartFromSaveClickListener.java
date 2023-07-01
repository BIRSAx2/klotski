package dev.plagarizers.klotski.gui.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.SavesManager;
import dev.plagarizers.klotski.gui.screens.GameScreen;


/**
 * The `StartFromSaveClickListener` class is a click listener for starting a game from a saved state.
 * It extends the `ClickListener` class and is responsible for handling the start from save button click event.
 */
public class StartFromSaveClickListener extends ClickListener {

    private String saveName;
    private SavesManager savesManager;
    private KlotskiGame game;

    /**
     * Constructs a new `StartFromSaveClickListener` with the specified save name and game instance.
     *
     * @param saveName the name of the save file to start the game from
     * @param game     the game instance
     */
    public StartFromSaveClickListener(String saveName, KlotskiGame game) {
        this.saveName = saveName;
        this.game = game;
        this.savesManager = new SavesManager(Gdx.files.getExternalStoragePath());
    }

    /**
     * Handles the click event.
     *
     * @param event the input event
     * @param x     the x-coordinate of the click position
     * @param y     the y-coordinate of the click position
     */
    @Override
    public void clicked(InputEvent event, float x, float y) {
        // Handle click event
        Gdx.app.log("LoadMenuScreen", "Clicked on " + event.getTarget());
        game.buttonPressedPlay();
        State save = savesManager.loadStateByName(saveName);
        Gdx.app.log("LoadMenuScreen", "Loaded state from save: " + saveName);
        game.getScreen().dispose();
        game.setScreen(new GameScreen(game, save));
    }
}
