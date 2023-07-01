package dev.plagarizers.klotski.gui.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.util.SavesManager;
import dev.plagarizers.klotski.gui.screens.LoadMenuScreen;
import dev.plagarizers.klotski.gui.util.SoundHandler;

/**
 * The `DeleteSaveClickListener` class is a click listener for deleting a save file.
 * It extends the `ClickListener` class and is responsible for handling the delete save button click event.
 */
public class DeleteSaveClickListener extends ClickListener {

    private final SavesManager savesManager;
    private final KlotskiGame game;
    private String saveName;

    /**
     * Constructs a new `DeleteSaveClickListener` with the specified save name and game instance.
     *
     * @param saveName the name of the save file to be deleted
     * @param game     the game instance
     */
    public DeleteSaveClickListener(String saveName, KlotskiGame game) {
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
        SoundHandler.getInstance().playButtonClick();
        savesManager.deleteSave(saveName);
        // Note: this is a workaround to refresh the screen
        game.setScreen(new LoadMenuScreen(game));
    }
}
