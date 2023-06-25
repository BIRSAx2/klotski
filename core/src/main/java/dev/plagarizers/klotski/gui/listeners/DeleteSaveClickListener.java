package dev.plagarizers.klotski.gui.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.game.util.SavesManager;
import dev.plagarizers.klotski.gui.screens.LoadMenuScreen;

public class DeleteSaveClickListener extends ClickListener {
    private String saveName;
    private final SavesManager savesManager;

    private final KlotskiGame game;

    public DeleteSaveClickListener(String saveName, KlotskiGame game) {
        this.saveName = saveName;
        this.game = game;
        this.savesManager = new SavesManager(Gdx.files.getExternalStoragePath());
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        Gdx.app.log("LoadMenuScreen", "Clicked on " + event.getTarget());
        game.buttonPressedPlay();
        savesManager.deleteSave(saveName);
        // Note: this is a workaround to refresh the screen
        game.setScreen(new LoadMenuScreen(game));
        Gdx.app.log("LoadMenuScreen", "Deleted save: " + saveName);
    }
}
